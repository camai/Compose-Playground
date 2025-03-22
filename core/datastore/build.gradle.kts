plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.core)
}

android {
    namespace = "com.jg.composeplayground.core.datastore"
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
    // data 모듈에 의존 (AppSettingDataStoreSource 인터페이스가 위치)
    implementation(projects.core.data)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    
    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}