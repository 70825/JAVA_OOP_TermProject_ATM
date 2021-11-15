# 💰 JAVA OOP 텀 프로젝트 - ATM 구현
## 📢 구현 세부사항
### 0. 공통 사항
- 입금 / 출금 / 송금에서는 취소 버튼을 누르면 모든 과정을 취소하고 다시 선택지를 고를 수 있어야함
- (제한 조건)입금 / 출금 / 송금 / 잔고 조회에서는 입금, 출금, 송금, 취소, 확인 버튼을 누른 후에 다음 단계로 넘어갈 수 있도록 함
- 정기 예금 계좌는 입출금 계좌를 상속 받아서 사용해야함
### 1. 로그인 기능
- 데이터베이스에 저장된 계좌 번호를 입력해야 다음 단계로 넘어갈 수 있어야함
- 계좌 번호에 맞는 비밀번호를 입력해야 ATM 기기에 로그인할 수 있어야함
### 2. 입금 기능
- 지폐를 넣은 수만큼 입금을 할 수 있어야함
- 코드에서는 지폐를 넣는 유무를 확인할 수 없으므로 지폐의 개수를 입력하는 것으로 대체함
### 3. 출금 기능
- ATM 기기 안에 있는 지폐 수를 넘겨서 출금할 수 없어야함
- 계좌에 있는 돈보다 많은 금액을 출금할 수 없어야함
### 4. 송금 기능
- 존재하는 송금 계좌로 송금할 수 있어야함
- 계좌에 있는 돈보다 많은 금액을 송금할 수 없어야함
### 5. 잔고 조회
- 고객 이름, 계좌 번호, 통장 종류, 잔액, (정기 예금 계좌일 경우) 에금 만기 날짜를 보여줘야함
### 6. 로그아웃
- 로그아웃 버튼을 누르면 새로운 계좌에 로그인할 수 있어야함
### 7. 시스템 종료
- 시스템 종료 버튼을 누르면 트랜잭션 로그 열람 기능에 접속할 수 있어야함
- 트랜잭션 로그 열람을 하지 않거나, 트랜잭션 로그 열람 후 종료 버튼을 누르면 프로그램이 완전히 종료되어야함


## ⚙ 클래스 구조
- [ ] MVC 패턴 적용
![image](https://user-images.githubusercontent.com/79046106/141721935-2a811c99-ca9d-45c0-9872-9397fa16bbf4.png)

## 🎞 실행 화면
### 초기 화면
<img src="https://user-images.githubusercontent.com/79046106/141777319-5f0e3a64-6ae3-4a0c-9864-8e3c0d9b5629.png" width="500" height="350"/>

### 입금 기능
<img src="https://user-images.githubusercontent.com/79046106/141777675-c14036e7-8a91-4469-b8ae-f0df114d54bc.png" width="500" height="350"/>

### 출금 기능
<img src="https://user-images.githubusercontent.com/79046106/141777819-e63d389d-2ea2-4906-bebf-5020c8395c7f.png" width="500" height="350"/>

### 송금 기능
<img src="https://user-images.githubusercontent.com/79046106/141778081-71baa984-1a0c-4dea-b82c-4f34aaffcc49.png" width="500" height="350"/>

### 잔고 조회 기능
<img src="https://user-images.githubusercontent.com/79046106/141777944-d1c23cb0-9f8b-4e7c-b87d-f8edcb2c84d0.png" width="500" height="350"/>

### 시스템 종료
<img src="https://user-images.githubusercontent.com/79046106/141778239-430c755b-87d0-4b78-8624-94f81afc83fb.png" width="500" height="350"/>

## 🙍‍♂️ 만든 사람
- 70825(https://github.com/70825)
- rano_del(https://github.com/reno-del)

  
## 🏁 맡은 역할  
70825
- [ ] MVC 패턴으로 코드 수정
- [ ] 고객 정보 GUI를 제외한 모든 코드 작성에 관여


rano_del
- [ ] Databse 생성자 데이터 값 추가
- [ ] Controller 코드 수정
- [ ] 출금 GUI, 고객 정보 GUI 코드 작성
