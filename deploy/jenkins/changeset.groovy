@NonCPS
def isTranslationPath(def path) {
  // match ONLY following path pattern
  // translations/brand/en_IN.json
  // lib/translations-reader/__test__/__missing_translations__/brand-cs_CZ.json
  // lib/translations-reader/__test__/__snapshots__/brand-en_IN.json
  def translationPathPattern = /^(translations|lib\/translations-reader).*\W[a-z]{2}_[A-Z]{2}\.json$/
  return path ==~ translationPathPattern
}

@NonCPS
def containsPath(def changeSets, Closure checkPath) {
  for (def changeSet : changeSets) {
    for (def entry : changeSet) {
      for (def file : entry.affectedFiles) {
        if (checkPath(file.path))
          return true
      }
    }
  }
  return false
}

@NonCPS
def containsTranslations(def changeSets) {
  return containsPath(changeSets, {
    path -> isTranslationPath(path)
  })
}

@NonCPS
def containsOnlyTranslations(def changeSets) {
  def containsOtherChange = containsPath(changeSets, {
    file -> !isTranslationPath(file)
  })
  return containsTranslationsChange(changeSets) && !containsOtherChange
}
