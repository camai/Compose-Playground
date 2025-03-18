# Compose Playground

Compose Playground는 Jetpack Compose를 활용한 안드로이드 앱 프로젝트입니다. 다양한 기능을 모듈화하여 구현하고 있습니다.

## 프로젝트 구조

```
Compose-Playground/
├── app/                    # 메인 앱 모듈
├── build-logic/           # Gradle 빌드 로직
│   └── convention/        # Gradle 컨벤션 플러그인
└── feature/              # 기능 모듈
    ├── bmi/              # BMI 계산기 모듈
    └── lotto/            # 로또 번호 생성기 모듈
```

## 기술 스택

- **언어**: Kotlin
- **UI 프레임워크**: Jetpack Compose
- **아키텍처**: MVI (Model-View-Intent)
- **빌드 시스템**: Gradle (Kotlin DSL)
- **의존성 관리**: Version Catalog

## 주요 기능

### BMI 계산기
- 키와 몸무게를 입력하여 BMI 지수 계산
- BMI 결과에 따른 상태 표시
- 직관적인 UI/UX

### 로또 번호 생성기
- 수동 번호 선택
- 자동 번호 생성
- 부분 자동 생성 (수동 + 자동)
- 번호 초기화 기능
- 정렬된 번호 표시

## 개발 환경 설정

1. 프로젝트 클론
```bash
git clone [repository-url]
```

2. Android Studio에서 프로젝트 열기

3. Gradle 동기화 실행

4. 앱 실행

## 빌드 및 실행

```bash
# 디버그 빌드
./gradlew assembleDebug

# 릴리즈 빌드
./gradlew assembleRelease
```

## 모듈 구조

### app 모듈
- 메인 앱 진입점
- 네비게이션 관리
- 공통 UI 컴포넌트

### feature 모듈
- 각 기능별 독립적인 모듈
- MVI 패턴 기반 구현
- 재사용 가능한 UI 컴포넌트

### build-logic 모듈
- Gradle 빌드 설정
- 버전 관리
- 공통 빌드 로직

## 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.
