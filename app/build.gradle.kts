plugins {
    id("weather.android.application")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.wheather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wheather"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))
    implementation(project(":core:common"))
    implementation(project(":feature:wheather:data"))
    implementation(project(":feature:wheather:domain"))
    implementation(project(":feature:wheather:presentation"))
    implementation(project(":feature:localstorage:data"))
    implementation(project(":feature:localstorage:domain"))
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}