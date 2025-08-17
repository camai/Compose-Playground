import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("jg.composeplayground.android.library")
                apply("jg.composeplayground.android.library.compose")
                apply("jg.composeplayground.android.hilt") // Hilt 자동 적용
                
                // UI 플러그인 적용
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            // libs 카탈로그 접근
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":common:common"))
                add("implementation", project(":common:ui"))
                add("implementation", project(":core:data"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:model"))
                
                // Hilt Navigation Compose 자동 추가
                add("implementation", libs.findLibrary("hilt-navigation-compose").get())
            }
        }
    }
} 