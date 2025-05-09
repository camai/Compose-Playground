import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
    jvmToolchain(17)
}

group = "com.jg.composeplayground.buildlogic"

// 추가 리포지토리 설정
repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.hilt.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "jg.composeplayground.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "jg.composeplayground.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "jg.composeplayground.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "jg.composeplayground.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "jg.composeplayground.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidCore") {
            id = "jg.composeplayground.android.core"
            implementationClass = "AndroidCoreConventionPlugin"
        }
        register("androidCommon") {
            id = "jg.composeplayground.android.common"
            implementationClass = "AndroidCommonConventionPlugin"
        }
        register("androidHilt") {
            id = "jg.composeplayground.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "jg.composeplayground.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidFirebase") {
            id = "jg.composeplayground.android.firebase"
            implementationClass = "AndroidFirebaseConventionPlugin"
        }
    }
} 