def gitUrl = "https://github.com/example/project.git"

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

        scriptPath('deployment/translations-pipeline.groovy')
        lightweight(true)
      }
    }
}