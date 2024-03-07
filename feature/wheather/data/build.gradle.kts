plugins {
    id("weather.android.library")
    id("weather.network")
}

android {
    namespace = "com.example.feature.wheather.data"
}

dependencies {
    api(project(":core:data"))
    implementation(project(":feature:wheather:domain"))
    api(project(":core:testing:unitest"))
}