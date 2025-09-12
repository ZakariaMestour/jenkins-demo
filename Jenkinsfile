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
					sh 'mvn -f jenkins-demo/pom.xml clean compile'
                    sh 'mvn -f jenkins-demo/pom.xml test'
                    sh 'mvn -f jenkins-demo/pom.xml package'

                }
            }
            post{
				always{
					junit 'jenkins-demo/target/surefire-reports/*.xml'
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