pipeline {
    agent any
    stages {
        stage('Example Build') {
            steps {
                echo 'Hello World'
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'c8d53f2f-d365-4159-82dd-050eceaa2bac', url: 'https://github.com/jonayet/jenkins-changset-poc.git']]])
            }
        }
        stage('Example JSON Deploy') {
            when {
              changeset "**/*.json"
            }
            steps {
                echo 'Deploying JSON'
            }
        }
        stage('Example Jenkins Deploy') {
            when {
              changeset "Jenkinsfile"
            }
            steps {
                echo 'Deploying Jenkinsfile'
            }
        }
    }
}