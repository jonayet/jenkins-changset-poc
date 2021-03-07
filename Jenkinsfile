  def containsTranslationsChange = false
  def containsOtherChange = false
  def matchTranslationFiles = "translations/.*\\.json$"
  def matchOtherFiles = "^(?!${matchTranslationFiles}).*$"

pipeline {
    agent any
    stages {
        stage('Prepare') {
            steps {
                echo 'Preparing.....'
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'c8d53f2f-d365-4159-82dd-050eceaa2bac', url: 'https://github.com/jonayet/jenkins-changset-poc.git']]])
            }
        }

        stage('Determine if contains translations change.....') {
            when {
              // if changeset contains any translations change
              changeset pattern: matchTranslationFiles, comparator: 'REGEXP'
            }
            steps {
                script {
                    containsTranslationsChange = true
                }
                echo 'Translations change detected'
            }
        }

        stage('Determine if contains other change.....') {
            when {
                // if changeset contains changes other than translations
                changeset pattern: matchOtherFiles, comparator: 'REGEXP'
            }
            steps {
                script {
                    containsOtherChange = true
                }
                echo 'Other change detected'
            }
        }

        // script {
        //   if(!containsOtherChange) {
        //     currentBuild.result = 'SUCCESS'
        //     return
        //   }
        // }

        stage('Build others...') {
            steps {
                echo 'Building others...'
            }
        }
    }
}