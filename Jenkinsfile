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
								def jarName = "demo-build-${env.BUILD_NUMBER}.jar"
								sh "cp target/*.jar target/${jarName}"
								archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: false
								sh 'curl -I http://192.168.100.6:32000 || echo "cannot reach nexus"'
								sh 'ls -la target/*.jar'
								writeFile file: 'settings.xml', text: """
                                  <settings>
                                    <servers>
                                    <server>
                                      <id>nexus-releases</id>
                                      <username>${NEXUS_USERNAME}</username>
                                      <password>${NEXUS_PASSWORD}</password>
                                    </server>
                                    </servers>
                                  </settings>
                                  """
								sh """
									mvn deploy:deploy-file -DgroupId=com.example -DartifactId=jenkins-demo -Dversion=1.0-${env.BUILD_NUMBER} -Dpackaging=jar -Dfile=target/${jarName} -Durl=http://192.168.100.6:32000/repository/maven-releases/ -DrepositoryId=nexus-releases -DgeneratePom=true
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