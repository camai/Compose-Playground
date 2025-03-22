plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.core)
}

android {
    namespace = "com.jg.composeplayground.core.data"
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    // JVM 대상 버전을 17로 설정
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    // 도메인 모듈만 의존
    implementation(projects.core.domain)
    // 모델 모듈 의존성 추가
    implementation(projects.core.model)
    // 순환 참조 방지를 위해 datastore 의존성 제거
    // implementation(projects.core.datastore)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}