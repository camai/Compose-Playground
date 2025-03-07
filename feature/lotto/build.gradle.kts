plugins {
    id("jg.composeplayground.android.feature")
    id("jg.composeplayground.android.room")
}

android {
    namespace = "com.jg.composeplayground.feature.lotto"
    
    // build-logic에서 기본값 설정한 항목들은 제거
    // 아래 항목들은 특정 모듈만의 설정이 필요할 때만 사용
}

dependencies {
    // 기본 의존성은 build-logic의 AndroidFeatureConventionPlugin에서 설정됨
    // Room 의존성은 AndroidRoomConventionPlugin에서 설정됨
    
    // Firebase - 플러그인 없이 라이브러리만 사용
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    
    // Lotto 기능에 필요한 추가 의존성
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    
    // 테스트 의존성
    testImplementation(libs.junit)
}