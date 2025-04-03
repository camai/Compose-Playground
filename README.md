# Compose Playground

Compose Playground는 Jetpack Compose를 활용한 안드로이드 앱 프로젝트입니다. 다양한 기능을 모듈화하여 구현하고 있습니다.

## 프로젝트 구조

```
Compose-Playground/
├── app/                    # 메인 앱 모듈
├── build-logic/           # Gradle 빌드 로직
│   └── convention/        # Gradle 컨벤션 플러그인
├── core/                 # 코어 모듈
│   ├── database/         # 로컬 데이터베이스
│   ├── data/             # 데이터 레이어
│   ├── domain/           # 도메인 레이어
│   ├── model/            # 도메인 모델
│   └── common/           # 공통 유틸리티
└── feature/              # 기능 모듈
    ├── bmi/              # BMI 계산기 모듈
    ├── lotto/            # 로또 번호 생성기 모듈
    ├── diary/            # 시크릿 다이어리
    └── calculator/       # 계산기 모듈
```

## 기술 스택

- **언어**: Kotlin
- **UI 프레임워크**: Jetpack Compose
- **아키텍처**: 클린 아키텍처 (Clean Architecture)
- **빌드 시스템**: Gradle (Kotlin DSL)
- **의존성 관리**: Version Catalog
- **로컬 저장소**: Room Database
- **비동기 처리**: Kotlin Coroutines & Flow
- **의존성 주입**: Hilt

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

### 시크릿 다이어리
- 일기 작성
- 일기 목록 조회
- 일기 상세 조회
- 일기 수정
- 일기 삭제
- 비밀번호 입력 및 저장

### 계산기
- 기본 연산 기능 (덧셈, 뺄셈, 곱셈, 나눗셈)
- 계산 결과 실시간 표시
- 계산 기록 저장 및 조회
- 저장된 계산 기록 삭제

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

### core 모듈
- **database**: Room 데이터베이스 구현
- **data**: 리포지토리 및 데이터 소스 구현
- **domain**: 유스케이스 및 비즈니스 로직
- **model**: 공통 도메인 모델
- **common**: 공통 유틸리티 및 확장 함수

### feature 모듈
- 각 기능별 독립적인 모듈
- 클린 아키텍처 기반 구현
- 재사용 가능한 UI 컴포넌트

### build-logic 모듈
- Gradle 빌드 설정
- 버전 관리
- 공통 빌드 로직

## 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.
