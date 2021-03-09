node {
  stage('Prepare') {
    echo 'Preparing.....'
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'c8d53f2f-d365-4159-82dd-050eceaa2bac', url: 'https://github.com/jonayet/jenkins-changset-poc.git']]])
  }

  def translationsUtils = load('deploy/translations-utils.groovy')

  stage('Build translations') {
   if (!translationsUtils.containsTranslationsChange(currentBuild.changeSets)) {
    echo 'Translations change not detected'
    return
   }

    echo 'Translations change detected, trigger external job.'
    translationsUtils.deploy('development', '124')
  }

  if (!translationsUtils.containsOtherChange(currentBuild.changeSets)) {
    echo 'Others change not detected'
    currentBuild.result = 'SUCCESS'
    return
  }

  stage('Build others') {
    echo 'Building others...'
  }
}