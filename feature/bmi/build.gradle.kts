plugins {
    id("jg.composeplayground.android.feature")
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jg.composeplayground.feature.bmi"
    
    // build-logic에서 기본값 설정한 항목들은 제거
    // 아래 항목들은 특정 모듈만의 설정이 필요할 때만 사용
}

dependencies {
    // 기본 의존성은 build-logic의 AndroidFeatureConventionPlugin에서 설정됨
    
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // BMI 기능에 필요한 추가 의존성
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    
    // 테스트 의존성
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}