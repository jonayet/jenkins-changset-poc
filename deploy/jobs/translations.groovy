import config
import groovy.transform.Field

@Field
static jobName = "${config.rootFolderName}/utils/translations"

@Field
static pipeline = 'deploy/translations-pipeline.groovy'

static forEnv(def dsl, String environment) {
  def options = [
    shallow: 1
  ]

  dsl.pipelineJob("${translations.jobName}/${environment}") {
    description "Upload translations to S3 bucket."

    parameters {
      stringParam('commit', '', 'Commit to get translations from.')
    }

    definition {
      cpsScm {
        scm {
          git {
            remote {
              url(config.glassCloneOptions.gitUrl)
              credentials(config.glassCloneOptions.gitCredentials)
            }

            branch(options.branch ?: 'master')

            extensions {
              cleanBeforeCheckout()
              pruneStaleBranch()
              wipeWorkspace()

              cloneOption {
                noTags(true)
                shallow(options.shallow > 0)

                if (options.shallow > 0) {
                  depth(options.shallow)
                }

                reference(config.glassCloneOptions.referenceRepo)
                timeout(config.glassCloneOptions.timeout)
              }
            }
          }
        }

        scriptPath(translations.pipeline)
        lightweight(true)
      }
    }

    properties {
      disableResume()
      disableConcurrentBuilds()

      buildDiscarder {
        strategy {
          logRotator {
            daysToKeepStr('-1')
            numToKeepStr('3')
            artifactDaysToKeepStr('7')
            artifactNumToKeepStr('-1')
          }
        }
      }
    }
  }
}

folder(translations.jobName)
translations.forEnv(this, 'development')
translations.forEnv(this, 'staging')
translations.forEnv(this, 'production')