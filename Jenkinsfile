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
                  - name: docker
                    image: docker:24.0
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

                                // Debug: Show environment variables
                                sh 'echo "BUILD_NUMBER: ${BUILD_NUMBER}"'
                                sh 'echo "BUILD_NUMBER env: ${env.BUILD_NUMBER}"'

                                // Set variables explicitly
                                def version = "1.0.${env.BUILD_NUMBER}"
                                def jarName = "jenkins-demo-${version}.jar"

                                // Debug: Show variables
                                sh "echo 'Version: ${version}'"
                                sh "echo 'JAR Name: ${jarName}'"

                                // Copy and rename JAR
                                sh "cp target/*.jar target/${jarName}"

                                // Debug: Verify the copy worked
                                sh 'ls -la target/'

                                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: false

                                // Debug: Show final URL that will be used
                                def uploadUrl = "http://192.168.100.6:32000/repository/maven-releases/com/example/jenkins-demo/${version}/${jarName}"
                                sh "echo 'Upload URL: ${uploadUrl}'"

                                // Now try the upload
                                sh """
                                    curl -v -u \${NEXUS_USERNAME}:\${NEXUS_PASSWORD} \
                                    --upload-file target/${jarName} \
                                    ${uploadUrl}
                                """
                            }
                        }
                    }
                }
            }
        }
        stage('Build Docker Image'){
			steps{
				container('docker'){
					sh 'docker --version'
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