node {
  stage('Prepare') {
    echo 'Preparing.....'
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'c8d53f2f-d365-4159-82dd-050eceaa2bac', url: 'https://github.com/jonayet/jenkins-changset-poc.git']]])
  }

  def changeset = load('deploy/jenkins/changeset.groovy')
  def translations = load('deploy/jenkins/translations.groovy')

  if (changeset.containsOnlyTranslations(currentBuild.changeSets)) {
    println 'Only translations changes are detected, lets test and upload to S3.'

    translations.installDependencies().call()
    translations.runRests().call()
    translations.uploadToS3('development').call()

    println 'Translations successfully upload to S3!'
    currentBuild.result = 'SUCCESS'
    return
  }

  if (changeset.containsTranslations(currentBuild.changeSets)) {
    println 'Translations changes are detected, lets upload to S3.'

    translations.installDependencies().call()
    translations.runRests().call()
    translations.uploadToS3('development').call()
  }

  stage('Build others') {
    echo 'Building others...'
  }
}