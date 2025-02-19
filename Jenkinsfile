pipeline {
    agent any
    stages {
        stage('github clone') {
            steps {
                git branch: 'main', credentialsId: 'github_personal_access_token', url: 'https://github.com/gudtjr2949/skin-api.git'
            }
        }
        stage('Build') {
            steps {
                dir("./skin-api") {
                    sh "chmod +x ./gradlew"
                    sh "./gradlew clean build -x test"
                }
            }
        }
        stage('Deployment') {
            steps {
                sshagent(credentials: ['ssh_key']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no ubuntu@3.238.121.86
                        scp /var/lib/jenkins/workspace/skin-api/skin-api/build/libs/skin-api-0.0.1-SNAPSHOT.jar ubuntu@3.238.121.86:/home/ubuntu/api-server
                        ssh -t ubuntu@3.238.121.86 /home/ubuntu/api.sh
                    '''
                }
            }
        }
    }
}