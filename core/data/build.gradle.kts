plugins {
    id("weather.network")
}

android {
    namespace = "com.example.core.data"
}

dependencies {
    implementation(project(":core:domain"))
}