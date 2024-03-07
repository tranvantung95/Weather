plugins {
    id("weather.android.library")
}

android {
    namespace = "com.example.feature.localstorage.data"
}

dependencies {
    implementation(project(":feature:localstorage:domain"))
    api(project(":core:data"))
    implementation(libs.coroutines.core)
    implementation(libs.koin.core)
    implementation(libs.gson)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

}