# 일기장 (Diary) 모듈

클린 아키텍처를 기반으로 구현된 일기장 기능 모듈입니다. Room 데이터베이스를 사용하여 일기를 작성, 조회, 수정, 삭제할 수 있는 기능을 제공합니다.

## 기능 소개

- **일기 작성**: 새로운 일기를 작성하고 저장할 수 있습니다.
- **일기 목록 조회**: 저장된 모든 일기를 목록으로 확인할 수 있습니다.
- **일기 상세 조회**: 저장된 일기의 내용을 확인할 수 있습니다.
- **일기 수정**: 기존 일기의 내용을 수정할 수 있습니다.
- **일기 삭제**: 더 이상 필요하지 않은 일기를 삭제할 수 있습니다 (길게 누르기).

## 아키텍처

이 모듈은 클린 아키텍처 패턴을 따르며, 다음과 같은 레이어로 구성되어 있습니다:

```
 UI (feature/diary)
    ↓
Domain (core/domain)
    ↓
 Data (core/data)
    ↓
Database (core/database)
```

### 주요 구성 요소

#### UI Layer (feature/diary)
- **화면**
  - `DiaryScreen.kt`: 일기 목록 표시 및 관리
  - `WritingScreen.kt`: 일기 작성 및 수정
- **ViewModel**
  - `DiaryViewModel.kt`: 일기 목록 관리 및 삭제 로직
  - `WritingViewModel.kt`: 일기 작성 및 수정 로직

#### Domain Layer (core/domain)
- **UseCase**
  - `GetAllDiariesUseCase.kt`: 모든 일기 목록 조회
  - `GetDiaryByIdUseCase.kt`: 특정 ID의 일기 조회
  - `SaveDiaryUseCase.kt`: 새 일기 저장
  - `UpdateDiaryUseCase.kt`: 기존 일기 수정
  - `DeleteDiaryUseCase.kt`: 일기 삭제
- **Model**
  - `Diary.kt`: 일기 도메인 모델

#### Data Layer (core/data)
- **Repository**
  - `DiaryRepository.kt`: 일기 데이터 접근 인터페이스
  - `DiaryRepositoryImpl.kt`: 일기 데이터 접근 구현체
- **DataSource**
  - `DiaryLocalDataSource.kt`: 로컬 데이터 소스 인터페이스

#### Database Layer (core/database)
- **Entity**
  - `DiaryEntity.kt`: 일기 데이터베이스 엔티티
- **DAO**
  - `DiaryDao.kt`: 일기 데이터베이스 접근 인터페이스
- **DataSource 구현체**
  - `DiaryLocalDataSourceImpl.kt`: 로컬 데이터 소스 구현체

## 데이터 흐름

1. 사용자가 UI에서 작업 요청 (작성, 조회, 수정, 삭제)
2. ViewModel에서 적절한 UseCase 호출
3. UseCase는 Repository 인터페이스 메서드 호출 
4. Repository는 DataSource를 통해 데이터 접근
5. DataSource는 Room DB DAO를 통해 실제 데이터 조작
6. 결과가 같은 경로로 역순으로 전달되어 UI에 표시

## 사용 기술

- Jetpack Compose: UI 구성
- Room: 로컬 데이터베이스
- Hilt: 의존성 주입
- Flow: 반응형 데이터 스트림
- Coroutines: 비동기 처리

## 화면 구성

### 일기 목록 화면 (DiaryScreen)
- 작성된 모든 일기 목록 표시
- 각 항목은 날짜와 내용 미리보기 제공
- FAB(Floating Action Button)을 통한 새 일기 작성 기능
- 일기 항목 길게 누르기로 삭제 기능 제공
- 일기 항목 클릭 시 해당 일기 수정 화면으로 이동

### 일기 작성/수정 화면 (WritingScreen)
- 일기 내용 작성/수정 영역
- 작성 모드와 수정 모드 구분
- 저장 기능
- 뒤로가기 기능

## 사용 방법

### 일기 작성
1. 일기 목록 화면에서 우측 하단의 + 버튼 클릭
2. 일기 내용 작성
3. 저장 버튼 클릭

### 일기 수정
1. 일기 목록에서 수정하려는 일기 항목 클릭
2. 일기 내용 수정
3. 저장 버튼 클릭

### 일기 삭제
1. 일기 목록에서 삭제하려는 항목을 길게 누름
2. 삭제 확인 대화상자에서 '삭제' 선택 