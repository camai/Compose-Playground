plugins {
    alias(libs.plugins.android.feature)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.room)
}

android {
    namespace = "com.jg.composeplayground.feature.lotto"
    
    // build-logic에서 기본값 설정한 항목들은 제거
    // 아래 항목들은 특정 모듈만의 설정이 필요할 때만 사용

    // Kotlin 2.0에서 필요한 설정
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
}

// Room 스키마 디렉토리 설정
room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // 공통 의존성
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    
    // 테스트 의존성
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}