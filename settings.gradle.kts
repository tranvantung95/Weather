pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "Wheather"
include(":app")
include(":core:data")
include(":core:presentation")
include(":core:common")
include(":feature:wheather:domain")
include(":feature:wheather:data")
include(":feature:wheather:presentation")
include(":core:domain")
include(":core:testing")
include(":feature:localstorage")
include(":feature:localstorage:domain")
include(":feature:localstorage:data")
include(":core:testing:unitest")
include(":core:testing:androidtest")


include(":core:testing:data")
