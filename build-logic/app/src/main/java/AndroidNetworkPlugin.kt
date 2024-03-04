import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.example.wheatherbuildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidNetworkPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    buildConfigField(
                        type = "String",
                        name = "BASE_URL",
                        value = "http://api.openweathermap.org/"
                    )
                }
            }
            dependencies {
                add("implementation", libs.findLibrary("coroutines.core").get())
                add("implementation", libs.findLibrary("okhttp.logging").get())
                add("implementation", libs.findLibrary("retrofit.core").get())
                add("implementation", libs.findLibrary("retrofit-kotlin-serialization").get())
                add("implementation", libs.findLibrary("gson.convert").get())
                add("implementation", libs.findLibrary("gson").get())
            }
        }
    }
}