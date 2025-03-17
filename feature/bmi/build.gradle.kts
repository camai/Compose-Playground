plugins {
    alias(libs.plugins.android.feature)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.jg.composeplayground.feature.bmi"
    
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

dependencies {
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Hilt와 ViewModel 통합을 위한 의존성
    implementation(libs.hilt.navigation.compose)
    
    // Navigation
    implementation(libs.androidx.navigation.compose)
    
    // Serialization
    implementation(libs.kotlinx.serialization.json)
    
    // BMI 기능에 필요한 추가 의존성
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    
    // Compose 의존성 추가
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    
    // 테스트 의존성
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}