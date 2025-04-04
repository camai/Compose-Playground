import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("jg.composeplayground.android.library")
                apply("jg.composeplayground.android.library.compose")

                // UI 플러그인 적용
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
        }
    }
}