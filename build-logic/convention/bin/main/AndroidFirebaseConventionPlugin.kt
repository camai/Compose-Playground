import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Firebase 의존성과 설정을 위한 플러그인
 */
class AndroidFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // libs 카탈로그 접근
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            
            dependencies {
                // Firebase BOM
                add("implementation", platform(libs.findLibrary("firebase-bom").get()))
                
                // Firebase 필수 모듈
                add("implementation", libs.findLibrary("firebase-analytics").get())
                
                // 필요에 따라 추가 가능한 모듈들
                // 기본적으로 Auth와 Firestore를 포함
                add("implementation", libs.findLibrary("firebase-auth").get())
                add("implementation", libs.findLibrary("firebase-firestore").get())
            }
        }
    }
} 