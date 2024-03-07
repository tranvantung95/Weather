plugins {
    id("weather.android.library")

}

android {
    namespace = "com.example.androidtest"
}

dependencies {
    api(libs.junit)
    api(libs.junit4)
    api(libs.androidx.test.ext.junit)
    api(libs.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.coroutine.test)
    api(libs.mockk.android)
    api(libs.mockk)
    api(libs.androidx.arch.test)
    api(libs.koin.test)
    api(libs.koin.android)
    api(libs.kaspresso)
    api(libs.koin.test.junit4)
    api(libs.mock.websever)
    api(libs.fragment.testing)
    api(project(":core:testing:data"))
    api(project(":core:testing:unitest"))

}