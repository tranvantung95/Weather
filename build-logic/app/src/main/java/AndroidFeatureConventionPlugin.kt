import com.android.build.gradle.LibraryExtension
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
//                    testInstrumentationRunner =
//                        "com.amazonaws.testing.AmazonawsRunner"
                }
                buildFeatures {
                    dataBinding = true
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
            }
        }
    }
}