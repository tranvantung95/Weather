plugins {
    id("weather.android.feature")
}

android {
    namespace = "com.example.core.presentation"
}

dependencies {
    implementation(project(":core:domain"))
}