import groovy.transform.Field
import config

@Field
static jobName = "${config.rootFolderName}/utils/translations"

static forEnv(def dsl, String environment) {
  def options = [
    shallow: 1
  ]

  dsl.pipelineJob("${translations_job.jobName}/${environment}") {
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

        scriptPath('deploy/translations-pipeline.groovy')
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

folder(translations_job.jobName)
translations_job.forEnv(this, 'development')
translations_job.forEnv(this, 'staging')
translations_job.forEnv(this, 'production')
