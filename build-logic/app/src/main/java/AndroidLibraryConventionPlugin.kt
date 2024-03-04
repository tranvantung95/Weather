import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.example.wheatherbuildlogic.configureKotlinAndroid
import com.example.wheatherbuildlogic.disableUnnecessaryAndroidTests
import com.example.wheatherbuildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit4").get())
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("coroutines.core").get())
                add("implementation", libs.findLibrary("lifecycle.ext").get())
                add("implementation", libs.findLibrary("lifecycle.viewmodel").get())
                add("implementation", libs.findLibrary("core.ktx").get())
                add("implementation", libs.findLibrary("androidx.fragment").get())
                add("implementation", libs.findLibrary("androidx.fragment.ktx").get())

//                add("testImplementation", project(":core:testing"))
//                add("androidTestImplementation", project(":core:testing"))

                add("androidTestImplementation", libs.findLibrary("espresso.core").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-rules").get())
                add("androidTestImplementation", libs.findLibrary("androidx.test.runner").get())
                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("junit4").get())
                add("testImplementation", libs.findLibrary("androidx-test-core").get())
                add("testImplementation", libs.findLibrary("androidx-test-rules").get())
                add("testImplementation", libs.findLibrary("androidx.test.runner").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("mockk-android").get())
                add("testImplementation", libs.findLibrary("koin-test").get())
                add("testImplementation", libs.findLibrary("koin-test-junit4").get())
                add("testImplementation", libs.findLibrary("androidx-arch-test").get())
                add("testImplementation", libs.findLibrary("coroutine-test").get())
                add("testImplementation", libs.findLibrary("turbine").get())
            }
        }
    }
}