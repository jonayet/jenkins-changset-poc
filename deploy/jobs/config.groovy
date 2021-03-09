import groovy.transform.Field

@Field
static rootFolderName = 'glass'


static glassCloneOptions = [
  gitUrl: 'https://github.com/jonayet/jenkins-changset-poc.git',
  gitCredentials: 'github'
]