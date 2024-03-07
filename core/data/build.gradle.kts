plugins {
    id("weather.network")
    id("weather.android.library")
}

android {
    namespace = "com.example.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    api(project(":core:common"))
}