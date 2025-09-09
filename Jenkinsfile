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
                  containers:
                  - name: git
                    image: alpine/git
                    command: ['cat']
                    tty: true
                  - name : maven
                    image: maven:3.8.5
                    command: ['cat']
                    tty: true
                  - name: docker
                    image: docker:24.0
                    command: ['cat']
                    tty: true
                  - name: kubectl
                    image: bitnami/kubectl:latest
                    command: ['cat']
                    tty: true
            """
        }
    }
    stages{
		stage('checking out the code'){
			steps{
				container('git'){
					sh 'git version'
                }
            }
        }
        stage('building the code'){
			steps{
				container('maven'){
					sh 'mvn -version'
                }
            }
        }
        stage('building the docker image'){
			steps{
				container('docker'){
					sh 'docker --version'
                }
            }
        }
        stage('deploying the application'){
			steps{
				container('kubectl'){
					sh 'kubectl version --client'
                }
            }
        }
    }
}