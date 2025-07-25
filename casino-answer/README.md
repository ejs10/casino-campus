# 🎰 카지노 캠퍼스 - 포커 게임 프로젝트

## 프로젝트 소개
Java OOP를 학습하기 위한 포커 게임 구현 프로젝트입니다.

## 구현 목표
- 5 Card Draw 포커 게임
- 4명의 플레이어, 100라운드 자동 진행

## 프로젝트 구조
```
src/main/java/
├── game/
│   ├── components/     # 카드, 덱, 핸드
│   ├── participants/   # 플레이어, 딜러
│   └── management/     # 게임 운영
└── Main.java          # 시작점
```

## 실행 방법

### IntelliJ IDEA 사용
1. 프로젝트를 IntelliJ IDEA에서 열기
2. Gradle 프로젝트로 자동 인식됨
3. `Main.java` 파일에서 실행

### 터미널에서 실행
```bash
# 빌드
./gradlew build

# 실행
./gradlew run

# 테스트
./gradlew test
```

## 구현 순서
1. Card → Deck → Hand
2. Player → Dealer
3. Casino (메인 게임)

## 참고
- 각 인터페이스의 JavaDoc을 읽고 구현하세요
- 테스트 코드를 통해 구현을 검증하세요
- 완성된 예제는 `../casino/` 폴더 참고