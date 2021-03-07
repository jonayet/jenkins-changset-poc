pipeline {
    agent any
    stages {
        // script {
        //   def containsTranslationsChange = false
        //   def containsOtherChange = false
        // }

        stage('Prepare') {
            steps {
                echo 'Preparing.....'
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'c8d53f2f-d365-4159-82dd-050eceaa2bac', url: 'https://github.com/jonayet/jenkins-changset-poc.git']]])
            }
        }

        stage('Determine if contains translations change.....') {
            when {
              changeset "translations/**/*.json"
            }
            steps {
                // containsTranslationsChange = true
                echo 'Translations change detected'
            }
        }

        stage('Determine if contains other change.....') {
            when {
                allOf {
                    changeset "**/*.*";
                    not {
                      changeset "translations/**/*.json"
                    }
                }
            }
            steps {
                // containsOtherChange = true
                echo 'Other change detected'
            }
        }

        // if(!containsOtherChange) {
        //   currentBuild.result = 'SUCCESS'
        //   return
        // }

        stage('Build others...') {
            steps {
                echo 'Building others...'
            }
        }
    }
}