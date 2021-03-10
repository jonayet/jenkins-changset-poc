@NonCPS
def testStage = {
  return {
    stage('Test translations') {
      echo 'Test completed'
    }
  }
}

return this