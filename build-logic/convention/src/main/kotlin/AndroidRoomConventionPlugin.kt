import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Room 의존성과 설정을 위한 플러그인
 */
class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                // KSP 플러그인 적용 (Room 어노테이션 처리를 위해)
                apply("com.google.devtools.ksp")
            }

            // libs 카탈로그 접근
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // Room 의존성 추가
                add("implementation", libs.findLibrary("room-runtime").get())
                add("implementation", libs.findLibrary("room-ktx").get())
                add("ksp", libs.findLibrary("room-compiler").get())
                
                // Room 테스트 의존성 (필요시)
                add("testImplementation", libs.findLibrary("junit").get())
            }
        }
    }
} 