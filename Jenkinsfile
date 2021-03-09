// match ONLY following path pattern
// translations/brand/en_IN.json
// lib/translations-reader/__test__/__missing_translations__/brand-cs_CZ.json
// lib/translations-reader/__test__/__snapshots__/brand-en_IN.json
@NonCPS
translationPathPattern = /^(translations|lib\/translations-reader).*\W[a-z]{2}_[A-Z]{2}\.json$/

@NonCPS
def isTranslationPath(def path) {
  return path ==~ translationPathPattern
} 

@NonCPS
def containsPath(def changeSets, Closure checkPath) {
  for (def changeSet : changeSets) {
    for (def entry : changeSet) {
      for (def file : entry.affectedFiles) {
        if (checkPath(file.path))
          return true
      }
    }
  }
  return false
}

@NonCPS
def containsTranslationsChange(def changeSets) {
  return containsPath(changeSets, {
    path -> isTranslationPath(path)
  })
}

@NonCPS
def containsOtherFileChange(def changeSets) {
  return containsPath(changeSets, {
    file -> !isTranslationPath(filePath)
  })
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