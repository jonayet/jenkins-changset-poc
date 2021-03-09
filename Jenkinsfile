node {
  stage('Prepare') {
    echo 'Preparing.....'
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'c8d53f2f-d365-4159-82dd-050eceaa2bac', url: 'https://github.com/jonayet/jenkins-changset-poc.git']]])
  }

  def changeset = load('deploy/changeset.groovy')

  stage('Build translations') {
   if (!changeset.containsTranslationsChange(currentBuild.changeSets)) {
    echo 'Translations change not detected'
    return
   }

   def translations = load('deploy/jobs/translations.groovy')

    echo 'Translations change detected, trigger external job.'
    translations.deploy('124')
  }

  if (!changeset.containsOtherFileChange(currentBuild.changeSets)) {
    echo 'Others change not detected'
    currentBuild.result = 'SUCCESS'
    return
  }

  stage('Build others') {
    echo 'Building others...'
  }
}