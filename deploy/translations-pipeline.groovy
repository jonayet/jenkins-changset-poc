node {
  stage('Prepare') {
    echo "${params.commit}"
    echo 'success!'
  }
}