plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

dependencies {
    api(project(":core:domain"))
    implementation(project(":core:testing:unitest"))
    implementation(libs.coroutines.core)
    implementation(libs.koin.core)
}