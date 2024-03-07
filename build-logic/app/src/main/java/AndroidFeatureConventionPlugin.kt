import com.android.build.gradle.LibraryExtension
import com.example.wheatherbuildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("weather.android.library")
                apply("kotlin-kapt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.example.androidtest.WeatherRunner"
                }
                buildFeatures {
                    viewBinding = true
                }
                packaging {
                    resources.excludes.add("META-INF/*")
                }
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", libs.findLibrary("koin.android").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("appcompat").get())
                add("implementation", libs.findLibrary("koin.core").get())
                add("implementation", libs.findLibrary("coroutines.core").get())
                add("implementation", libs.findLibrary("lifecycle.ext").get())
                add("implementation", libs.findLibrary("lifecycle.viewmodel").get())
                add("implementation", libs.findLibrary("core.ktx").get())
                add("implementation", libs.findLibrary("androidx.fragment").get())
                add("implementation", libs.findLibrary("androidx.fragment.ktx").get())
                add("implementation", libs.findLibrary("mpchart").get())
                add("implementation", libs.findLibrary("recyclerView").get())
            }
        }
    }
}