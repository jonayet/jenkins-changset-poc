pipeline {
    agent any
    stages {
        stage('Example Build') {
            steps {
                echo 'Hello World'
            }
        }
        stage('Example Deploy') {
            when {
              changeset "**/*.json"
            }
            steps {
                echo 'Deploying JSON'
            }
        }
        stage('Example Deploy') {
            when {
              changeset "Jenkinsfile"
            }
            steps {
                echo 'Deploying Jenkinsfile'
            }
        }
    }
}