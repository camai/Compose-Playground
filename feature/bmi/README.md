# 🧮 BMI 계산기 모듈

![BMI 계산기](https://img.shields.io/badge/Feature-BMI_계산기-blue)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack_Compose-green)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple)
![Hilt](https://img.shields.io/badge/DI-Hilt-orange)
![MVVM](https://img.shields.io/badge/Pattern-MVVM-yellow)

> 체질량지수(BMI)를 계산하고 건강 상태를 확인할 수 있는 기능 모듈입니다.

## 📋 기능 소개

BMI(Body Mass Index, 체질량지수) 계산기는 사용자의 키와 몸무게를 입력받아 체질량지수를 계산하고, 그에 따른 건강 상태를 표시합니다.

### 핵심 기능
- 키(cm)와 몸무게(kg) 입력
- BMI 계산 (체중(kg) / (키(m) * 키(m)))
- 결과에 따른 건강 상태 분류
  - 저체중: BMI < 18.5
  - 정상: 18.5 ≤ BMI < 23.0
  - 과체중: 23.0 ≤ BMI < 25.0
  - 비만: 25.0 ≤ BMI < 30.0
  - 고도비만: BMI ≥ 30.0
- 결과 초기화 및 재계산

## 🏗️ 모듈 구조

```
feature/bmi/
├── viewmodel/        # 비즈니스 로직 및 상태 관리
│   └── BmiViewModel.kt
├── screen/           # UI 컴포넌트
│   └── BmiScreen.kt
└── navigation/       # 모듈 내 네비게이션 설정
    └── BmiNavigation.kt
```

## 🔄 사용 방법

### 모듈 종속성 추가
```kotlin
// settings.gradle.kts
include(":feature:bmi")
```

### 모듈 사용
```kotlin
// 해당 모듈로 이동하기 위한 네비게이션
NavHostController.navigateToBmi()

// 또는 NavGraph에 BMI 스크린 추가
NavGraphBuilder.bmiScreen()
```

## 🛠️ 기술 스택

- **아키텍처**: MVVM (Model-View-ViewModel)
- **UI**: Jetpack Compose
- **상태 관리**: StateFlow
- **의존성 주입**: Hilt
- **네비게이션**: Jetpack Navigation Compose

## 📊 BMI 계산 방식

BMI는 다음과 같은 공식으로 계산됩니다:
```
BMI = 체중(kg) / (키(m) * 키(m))
```

계산된 BMI 값은 소수점 2자리까지 표시됩니다.

## 🔍 모듈 특징

- **모듈화된 설계**: 독립적으로 기능할 수 있는 독립 모듈
- **최신 Android 개발 기술**: Jetpack Compose, Hilt, Kotlin Flow 등 사용
- **테마 대응**: Material 3 디자인 시스템 준수
- **직관적인 UI**: 사용자 친화적 인터페이스
- **다양한 피드백**: 건강 상태에 따른 시각적 피드백

## 🎨 UI 미리보기

(이미지 영역: BMI 계산기 화면 스크린샷)
