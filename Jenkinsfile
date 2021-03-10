node {
  stage('Prepare') {
    echo 'Preparing.....'
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'c8d53f2f-d365-4159-82dd-050eceaa2bac', url: 'https://github.com/jonayet/jenkins-changset-poc.git']]])
  }

  def changeset = load('deploy/jenkins/changeset.groovy')
  def translations = load('deploy/jenkins/translations.groovy')

  // stage('Build translations') {
  //  if (!translationsUtil.containsTranslationsChange(currentBuild.changeSets)) {
  //   echo 'Translations change not detected'
  //   return
  //  }

  //   echo 'Translations change detected, trigger external job.'
  //   translationsUtil.deploy('development', '125')
  // }

  // if (!translationsUtil.containsOtherChange(currentBuild.changeSets)) {
  //   echo 'Others change not detected'
  //   currentBuild.result = 'SUCCESS'
  //   return
  // }

  translations.testStage().call()

  stage('Build others') {
    echo 'Building others...'
  }
}