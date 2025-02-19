pipeline {
    agent any

    environment {
        APP_NAME = 'skin-api'
        JAR_NAME = 'build/libs/skin-api.jar'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/gudtjr2949/skin-api.git'
            }
        }
        stage('Build Application') {
            steps {
                sh 'cd skin-api/skin-api'
                sh './gradlew clean build -x test'
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    sh 'fuser -k 9090/tcp || true'
                    sh "nohup java -jar ${JAR_NAME} --server.port=9090"
                }
            }
        }
    }

    post {
        success {
            echo '✅ 배포 성공!'
        }
        failure {
            echo '❌ 배포 실패!'
        }
    }
}
