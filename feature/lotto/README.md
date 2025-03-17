# 로또 번호 생성기 모듈 (Lotto Module)

## 개요

로또 번호 생성기 모듈은 사용자가 로또 번호를 직접 선택하거나 자동으로 생성할 수 있는 기능을 제공합니다. 이 모듈은 Jetpack Compose를 사용한 UI와 MVI(Model-View-Intent) 패턴을 적용하여 구현되었습니다.

## 주요 기능

- **번호 수동 선택**: 1~45 사이의 번호를 직접 선택하여 최대 6개까지 추가
- **자동 번호 생성**: 버튼 클릭으로 랜덤한 6개의 번호 자동 생성
- **부분 자동 생성**: 일부 번호를 수동으로 선택하고 나머지는 자동으로 생성
- **번호 초기화**: 선택한 모든 번호 초기화 기능

## 아키텍처

이 모듈은 MVI(Model-View-Intent) 패턴을 적용하여 단방향 데이터 흐름을 구현했습니다:

```
View(UI) → Intent → ViewModel → State → View(UI)
```

### 주요 구성 요소

- **LottoViewModel**: 상태 관리 및 비즈니스 로직 처리
  - 사용자 액션(Intent)을 처리하고 상태를 업데이트
  - 로또 번호 생성 및 관리 로직 포함

- **UI 계층**:
  - `LottoRoute`: 진입점 컴포넌트, ViewModel과 화면 연결
  - `LottoScreen`: 메인 UI 컴포넌트
  - `LottoNumberPicker`: 번호 선택 컴포넌트
  - `LottoActionButtons`: 액션 버튼 그룹
  - `LottoNumbers`: 선택된 번호 표시 컴포넌트

## 사용 방법

### 모듈 통합

다음과 같이 다른 모듈에서 로또 기능을 통합할 수 있습니다:

```kotlin
import com.jg.composeplayground.lotto.navigation.navigateToLotto
import com.jg.composeplayground.lotto.navigation.lottoScreen

// NavGraph 설정에 로또 화면 추가
fun NavGraphBuilder.appGraph() {
    // 다른 화면 설정...
    lottoScreen()
}

// 로또 화면으로 이동
navController.navigateToLotto()
```

### 커스터마이징

NumberCircle 컴포넌트를 통해 로또 번호 표시 스타일을 커스터마이징할 수 있습니다:

```kotlin
NumberCircle(
    number = 7,
    size = 48, // 크기 조정 (기본값: 36)
    colorSeed = 2 // 색상 시드 지정 (특정 색상 사용)
)
```

## 의존성

이 모듈은 다음 라이브러리에 의존합니다:

- Jetpack Compose UI 컴포넌트
- Hilt (의존성 주입)
- Kotlin Coroutines 및 Flow
- Jetpack Navigation Compose

## 설정 및 구성

프로젝트에 모듈을 추가하려면:

1. `settings.gradle.kts`에 모듈 추가:
   ```kotlin
   include(":feature:lotto")
   ```

2. 앱 모듈의 `build.gradle.kts`에 의존성 추가:
   ```kotlin
   implementation(project(":feature:lotto"))
   ```

3. 앱의 네비게이션 설정에 로또 화면 통합

## 구현 세부 사항

- MVI 패턴을 통한 단방향 데이터 흐름
- 상태 관리에 불변 객체(Immutable State) 사용
- 함수형 프로그래밍 기법 활용 (Kotlin Flow, Sequence 등)
- UI 컴포넌트의 재사용 및 책임 분리

## 개선 사항 및 향후 계획

- 로또 번호 저장 기능 추가
- 과거 당첨 번호 조회 기능
- 더 다양한 번호 생성 알고리즘 제공 