def utils = load('deploy/jenkins/utils.groovy')

def installDependencies() {
  return {
    stage('Translations : Install dependencies') {
      println 'Install translations dependencies....'
      utils.withNvmrc {
        sh "sudo chown -R \$USER:\$USER ${env.WORKSPACE}"
        sh 'NODE_ENV= yarn @glass/translations-reader install --prefer-offline --no-progress --frozen-lockfile'
      }
    }
  }
}

def runRests(def environment) {
  return {
    stage('Translations : Test') {
      println 'Testing translations....'
      utils.withNvmrc {
        sh "sudo chown -R \$USER:\$USER ${env.WORKSPACE}"
        sh 'NODE_ENV= yarn @glass/translations-reader test'
      }
    }
  }
}

def uploadToS3(def environment) {
  return {
    stage('Translations : Upload to S3') {
      withCredentials([[
        $class: 'AmazonWebServicesCredentialsBinding',
        credentialsId: 's3-adidas-glass-assets',
        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
      ]]) {
        sh "yarn workspace @glass/translations-reader upload ${environment}"
      }
    }
  }
}

return this