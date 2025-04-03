# 계산기 (Calculator) 모듈

클린 아키텍처를 기반으로 구현된 계산기 기능 모듈입니다. Room 데이터베이스를 활용해 계산 기록을 저장하고 조회할 수 있습니다.

## 기능 소개

- **기본 계산 기능**: 덧셈(+), 뺄셈(-), 곱셈(×), 나눗셈(÷) 연산 지원
- **계산 결과 실시간 표시**: 숫자와 연산자 입력 시 실시간으로 결과값 미리 표시
- **계산 기록 저장**: 계산 완료 후 수식과 결과값을 자동 저장
- **계산 기록 조회**: 이전에 수행한 계산 내역을 시간순으로 조회
- **계산 기록 삭제**: 저장된 모든 계산 기록 삭제 가능

## 아키텍처

이 모듈은 클린 아키텍처 패턴을 따르며, 다음과 같은 레이어로 구성되어 있습니다:

```
 UI (feature/calculator)
    ↓
Domain (core/domain)
    ↓
 Data (core/data)
    ↓
Database (core/database)
```

### 주요 구성 요소

#### UI Layer (feature/calculator)
- **화면**
  - `CalculatorScreen.kt`: 메인 계산기 화면
  - `CalculatorKeypadScreen.kt`: 계산기 키패드 UI
  - `HistoryScreen.kt`: 계산 기록 조회 화면
- **ViewModel**
  - `CalculatorViewModel.kt`: 계산 로직 및 상태 관리

#### Domain Layer (core/domain)
- **UseCase**
  - `GetAllHistoriesUseCase.kt`: 모든 계산 기록 조회
  - `SaveHistoryUseCase.kt`: 계산 기록 저장
  - `DeleteAllHistoriesUseCase.kt`: 모든 계산 기록 삭제
- **Model**
  - `CalculatorHistory.kt`: 계산 기록 도메인 모델

#### Data Layer (core/data)
- **Repository**
  - `CalculatorHistoryRepository.kt`: 계산 기록 데이터 접근 인터페이스
  - `CalculatorHistoryRepositoryImpl.kt`: 계산 기록 데이터 접근 구현체
- **DataSource**
  - `CalculatorHistoryLocalDataSource.kt`: 로컬 데이터 소스 인터페이스

#### Database Layer (core/database)
- **Entity**
  - `CalculatorHistoryEntity.kt`: 계산 기록 데이터베이스 엔티티
- **DAO**
  - `CalculatorHistoryDao.kt`: 계산 기록 데이터베이스 접근 인터페이스
- **DataSource 구현체**
  - `CalculatorHistoryLocalDataSourceImpl.kt`: 로컬 데이터 소스 구현체

## 계산 로직

계산기는 다음과 같은 방식으로 동작합니다:

1. 사용자가 숫자와 연산자를 입력하면 `CalculatorViewModel`에서 입력을 처리합니다.
2. 연산자 입력 시 실시간으로 계산 결과를 미리 보여줍니다.
3. `=` 버튼을 누르면 최종 계산 결과를 표시하고 데이터베이스에 기록을 저장합니다.
4. 계산 과정에서 연산자 우선순위를 고려하여 정확한 계산을 수행합니다.

계산 로직은 `Calculator` 클래스에 캡슐화되어 있어 유지보수가 용이하며, 표현식 처리와 계산이 분리되어 있습니다.

## 사용 기술

- **Jetpack Compose**: 선언적 UI 구현
- **MVVM 패턴**: UI와 비즈니스 로직 분리
- **Kotlin Flow**: 반응형 데이터 처리
- **Hilt**: 의존성 주입
- **Room Database**: 로컬 데이터 저장
- **Clean Architecture**: 코드 계층화 및 관심사 분리

## 화면 구성

### 계산기 화면
- 상단: 입력된 수식과 계산 결과 표시 영역
- 하단: 숫자 및 연산자 키패드
- 왼쪽 하단: 계산 기록 버튼
- 오른쪽 하단: 계산 결과 버튼(=)

### 계산 기록 화면
- 계산 기록 목록: 수식과 결과값 표시
- 하단: 계산 기록 전체 삭제 버튼

## 사용 방법

### 기본 계산
1. 숫자 키패드를 사용하여 숫자 입력
2. 연산자 키패드를 사용하여 연산자 입력
3. 다시 숫자 입력
4. `=` 버튼을 눌러 결과 확인

### 계산 기록 보기
1. 왼쪽 하단의 기록 버튼 클릭
2. 이전 계산 기록 목록 확인

### 계산 기록 삭제
1. 계산 기록 화면에서 하단의 '계산 기록 삭제' 버튼 클릭

## 향후 개선 사항

- 괄호 연산 지원
- 퍼센트(%) 연산 지원
- 복잡한 수식 처리 기능 강화
- 개별 계산 기록 삭제 기능 추가
- 다크 모드 지원 