pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://kotlin.bintray.com/kotlinx")
    }
    resolutionStrategy {
        eachPlugin{
            when (requested.id.id) {
                "kotlinx-serialization"->{
                    useModule("org.jetbrains.kotlinx:kotlinx-gradle-serialization-plugin:${requested.version}")
                }
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Actors_And_Movies"
include(":app")
 