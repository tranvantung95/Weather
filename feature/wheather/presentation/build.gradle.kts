plugins {
    id("weather.android.feature")
}

android {
    namespace = "com.example.wheather.presentation"
}

dependencies {
    api(project(":core:presentation"))
    api(project(":core:testing:androidtest"))
    api(project(":feature:wheather:domain"))
    implementation(project(":feature:localstorage:domain"))
    implementation(libs.gson)
}