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
    }
}