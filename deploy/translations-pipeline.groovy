node {
  stage('Prepare') {
    echo "${params.commitId}"
    echo 'success!'
  }
}