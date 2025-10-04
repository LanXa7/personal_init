rootProject.name = "personal_init"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")


include("common")
findProject(":common")?.name = "common"

include("model")
findProject(":model")?.name = "model"

include("service")
findProject(":service")?.name = "service"
