def containsOtherChange = false
def matchTranslationFiles = 'translations/.*\\.json$'
def matchOtherFiles = "^(?!${matchTranslationFiles}).*\$"

@NonCPS
def containsTranslationsChange(def changeSets) {
  def translationFilePattern = /translations\/.*\.json$/
  for (def changeSet : changeSets) {
    for (def entry : changeSet) {
      for (def file : entry.affectedFiles) {
        if (file.path ==~ translationFilePattern)
          return true
      }
    }
  }
  return false
}

@NonCPS
def containsOtherFileChange(def changeSets) {
  def translationFilePattern = /translations\/.*\.json$/
  for (def changeSet : changeSets) {
    for (def entry : changeSet) {
      for (def file : entry.affectedFiles) {
        if (!(file.path ==~ translationFilePattern))
          return true
      }
    }
  }
  return false
}

node {
  stage('Prepare') {
    echo 'Preparing.....'
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'c8d53f2f-d365-4159-82dd-050eceaa2bac', url: 'https://github.com/jonayet/jenkins-changset-poc.git']]])
  }

  stage('Build translations') {
   if (!containsTranslationsChange(currentBuild.changeSets)) {
    echo 'Translations change not detected'
    return
   }

    echo 'Translations change detected, trigger external job.'
    build job: 'utils/translations', parameters: [
      string(name: 'COMMIT', value: 'HEAD')
    ]
  }

  if (!containsOtherFileChange(currentBuild.changeSets)) {
    currentBuild.result = 'SUCCESS'
    return
  }

  stage('Build others') {
    echo 'Building others...'
  }
}