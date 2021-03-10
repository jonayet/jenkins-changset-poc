@NonCPS
def testStage() {
  return {
    stage('Test translations') {
      echo 'Test completed 1'
    }
  }
}

return this