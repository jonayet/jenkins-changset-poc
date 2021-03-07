def containsOtherChange = false
def matchTranslationFiles = 'translations/.*\\.json$'
def matchOtherFiles = "^(?!${matchTranslationFiles}).*\$"

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
        echo 'Translations change detected, trigger external job.'

        build job: 'utils/translations', parameters: [
          string(name: 'COMMIT', value: 'HEAD')
        ]
      }
    }

    stage('Determine if contains other change.....') {
      when {
        // if changeset contains changes other than translations
        changeset pattern: matchOtherFiles, comparator: 'REGEXP'
      }
      steps {
        echo 'Other change detected.'
        script {
          containsOtherChange = true
        }
      }
    }

    stage('Build others...') {
      when {
        expression {
          return containsOtherChange
        }
      }
      steps {
        echo 'Building others...'
      }
    }
  }
}