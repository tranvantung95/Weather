plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

dependencies {
    implementation(libs.gson)
    api(project(":core:testing:data"))
    api(libs.junit)
    api(libs.junit4)
    api(libs.mockk)
    api(libs.koin.test)
    api(libs.koin.test.junit4)
    api(libs.coroutine.test)
    api(libs.turbine)
    api(libs.mock.websever)
}