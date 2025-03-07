import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("jg.composeplayground.android.library")
                
                // UI 플러그인 적용
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
        }
    }
} 