def gitUrl = "https://github.com/jonayet/jenkins-changset-poc.git"

pipelineJob("utils/translations") {
    description "Builds MyProject from master branch."
    parameters {
        stringParam('COMMIT', 'HEAD', 'Commit to build')
    }

    definition {
      cpsScm {
        scm {
          git {
            remote {
              url gitUrl
              branch "origin/master"
            }

            branch('master')

            extensions {
              cleanBeforeCheckout()
              pruneStaleBranch()
              wipeWorkspace()
            }
          }
        }

        scriptPath('deploy/translations-pipeline.groovy')
        lightweight(true)
      }
    }
}