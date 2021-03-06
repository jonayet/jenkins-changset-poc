import groovy.transform.Field

@Field
static rootFolderName = 'glass'

@Field
static glassCloneOptions = [
  gitUrl: 'https://github.com/jonayet/jenkins-changset-poc.git',
  gitCredentials: 'github',
  referenceRepo: '/home/ubuntu/.cache/glass',
  timeout: 10
]