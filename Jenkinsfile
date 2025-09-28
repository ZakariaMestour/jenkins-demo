pipeline{
	agent{
		kubernetes{
			namespace 'jenkins'
            yaml """
                apiVersion: v1
                kind: Pod
                metadata:
                  namespace: jenkins
                  name: jenkins-agent
                spec:
                  serviceAccountName: jenkins-k8s
                  containers:
                  - name: git
                    image: alpine/git
                    command: ['cat']
                    tty: true
                  - name : maven
                    image: maven:3.8.5
                    command: ['cat']
                    tty: true
                    volumeMounts:
                      - name: m2-cache
                        mountPath: /root/.m2
                  - name: kaniko
                    image: gcr.io/kaniko-project/executor:debug
                    command: ['cat']
                    tty: true
                  - name: kubectl
                    image: beli/kubectl-shell
                    command: ['cat']
                    tty: true
                  volumes:
                    - name: m2-cache
                      persistentVolumeClaim:
                        claimName: maven-cache-pvc

            """
        }
    }
    stages{

		stage('Build App'){
			steps{
				container('maven'){
					sh 'mvn  clean compile'
                    sh 'mvn  test'
                    sh 'mvn  package'

                }
            }
            post{
				always{
					junit 'target/surefire-reports/*.xml'
                }
               	success{
                    container('maven'){
                        withCredentials([usernamePassword(credentialsId: 'nexus-credentials', passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USERNAME')]){
                            script{
                                // Debug: Show what files exist
                                sh 'ls -la target/'

                                // Set variables in Groovy (not shell)
                                def buildNumber = env.BUILD_NUMBER
                                def version = "1.0.${buildNumber}"
                                def jarName = "jenkins-demo-${version}.jar"

                                // Debug: Show variables (using echo, not sh)
                                echo "Build Number: ${buildNumber}"
                                echo "Version: ${version}"
                                echo "JAR Name: ${jarName}"

                                // Copy and rename JAR
                                sh "cp target/demo-0.0.1-SNAPSHOT.jar target/${jarName}"
                                //sh "rm -f target/demo-0.0.1-SNAPSHOT.jar"
                                // Debug: Verify the copy worked
                                sh 'ls -la target/'

                                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: false

                                // Debug: Show final URL
                                def uploadUrl = "http://192.168.100.6:32000/repository/maven-releases/com/example/jenkins-demo/${version}/${jarName}"
                                echo "Upload URL: ${uploadUrl}"

                                // Upload to Nexus
                                sh """
                                    curl -v -u \${NEXUS_USERNAME}:\${NEXUS_PASSWORD} \
                                    --upload-file target/${jarName} \
                                    '${uploadUrl}'
                                """
                            }
                        }
                    }
                }
            }
        }
        stage('Build Docker Image'){
			steps{
				container('kaniko'){

                    withCredentials([usernamePassword(credentialsId: 'docker_hub', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]){
                        script{
                          def dockerHubUsername = 'zakariamestour'
                          def imageName = "${dockerHubUsername}/jenkins-demo"
                          def imageTag = "1.0.${env.BUILD_NUMBER}"
                          sh """
                            echo "{\"auths\":{\"https://index.docker.io/v1/\":{\"username\":\"${DOCKERHUB_USERNAME}\",\"password\":\"${DOCKERHUB_PASSWORD}\"}}}" > /kaniko/.docker/config.json
                          """
                          sh """
                          /kaniko/executor \
                          --dockerfile=Dockerfile \
                          --context=. \
                          --destination=${imageName}:${imageTag} \
                          --destination=${imageName}:latest \
                          --cache=true \
                          --compressed-caching=false

                          """
                          echo "Image pushed to DockerHub: ${imageName}:${imageTag}"

                        }
                    }


                }
            }
        }
        stage('Deploy'){
			steps{
				container('kubectl'){
					sh 'kubectl get po -n jenkins'
                }
            }
        }
        
    }
}