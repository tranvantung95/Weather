
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}
dependencies{
    implementation(project(":core:testing:unitest"))
}

