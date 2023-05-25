# Study-Deposit 
### 돈을 걸고 하는 스터디
### 서울과학기술대학교 졸업작품(캡스톤 디자인) - 1인작품

## 📝 &nbsp; 다운로드 및 링크
https://www.studydeposit.kro.kr
https://drive.google.com/file/d/1o8I6u0qbQwziXqAcpZhf6aeU_zpd6FSo/view?usp=sharing (apk 파일)

## 📝 &nbsp; Fuctions
#### 1️⃣ 자신이 원하는 스터디를 개설하고 보증금을 걸 수 있다.
#### 2️⃣ 만약 정해진 출석기간을 지키지 못하는경우, (제출한 보증금/보증금은 스터디 참여한 회원+개발자 수)로 분배된다.
#### 3️⃣ 출석시간 규칙은 스터디 방장이 수정가능
#### 4️⃣ 각 스터디방에서는 자유롭게 이야기할 수 있는 채팅기능 제공
#### 5️⃣ 보증금 충전을 위한 카카오페이 결제 서비스
#### 6️⃣ 보증금을 자신의 계좌로 옮길 수 있는 계좌이체 기능


## 📑 &nbsp; About Project


### 로그인/회원가입

![kakaklogin](https://github.com/gyuturn/study-deposit/assets/87477702/69b1b949-3b89-4c67-bae4-06d33188961a)

- 카카오 로그인만을 회원가입으로 가능함
- 추후 카카오페이로 포인트 충전을 위해 카카오로 회원을 통일
- 익명의 닉네임을 받음

<br><br>
### 스터디방 목록(메인 홈)
<img width="143" alt="스크린샷 2023-04-09 오후 2 51 35" src="https://user-images.githubusercontent.com/87477702/230756814-5114cbb0-14ba-42d1-a1c7-67c170babbca.png">

- 현재 진행중인 스터디방 목록 제공
- 메인 홈

<br><br>
#### 스터디방 입장
![studyroom_enter](https://github.com/gyuturn/study-deposit/assets/87477702/dc54a9b7-0009-4ff2-95b9-7b3e5e798306)
![study_enter_error](https://github.com/gyuturn/study-deposit/assets/87477702/3a072582-5871-4210-9fea-3c68e29e967d)
 
- 회원이 스터디방에 입장하려고 하는 UI
- 회원이 현재 자신이 가지고 있는 포인트가 입장
- 보증금보다 높아야 입장가능
- 입장시에 해당 보증금만큼 자신의 포인트가 차감

<br><br>
#### 스터디방 생성
![studyroom_post](https://github.com/gyuturn/study-deposit/assets/87477702/4f060bd1-9b62-4ef3-9e6e-11201b29a8c7)
- 스터디 방 생성
- 제목, 출석날짜, 참여인원 , 보증금, 해시태그 등을 입력해야함

<br><br>
#### 해시태그(스터디방 생성)
![hashtag](https://github.com/gyuturn/study-deposit/assets/87477702/bc6b3615-257c-492b-8452-20d8e4d9ff14)
- 해시태그 등록/조회
- 해시태그 이미 존재할 시 해시태그 선택가능

<br><br>
#### 스터디방 출석
![attendance](https://github.com/gyuturn/study-deposit/assets/87477702/b26fb422-3e86-4d69-ae6b-2d3fc7504329)
![attendance_Already](https://github.com/gyuturn/study-deposit/assets/87477702/76ce8d5c-105a-4f14-9dd2-0e4ae64da6b7)
![attendance_late](https://github.com/gyuturn/study-deposit/assets/87477702/b33c0e91-60d6-48c0-be77-605ff0507a08)

- 포함된 스터디방에서는 매일 정해진 시간마다 출석버튼을 눌려야함
- 만약 누르지 않으면 결석처리 되며, 방장이 정한 출석제한(예를들어 3일)이 초과되면 보증금을 받지 못하고 퇴출
- 다른 유저의 출석현황을 볼 수 있음
<br><br>

#### 스터디방 정보확인
![studyroom_info](https://github.com/gyuturn/study-deposit/assets/87477702/87109857-e904-4b70-b2ed-30e34e50ca32)
- 해당 스터디방의 정보를 간략히 볼 수 있음
- 방과 관련된 정보(제목, 카테고리, 스터디 가간, 인원, 보증금)관련 제도 등을 확인할 수 있음

<br><br>
#### 마이페이지
<img width="161" alt="스크린샷 2023-05-25 오후 11 32 33" src="https://github.com/gyuturn/study-deposit/assets/87477702/ca0b3a30-c253-477c-a2b7-db2c68b2d78d">

- 마이페이지에서는 회원정보 수정, 스터디방 조회,포인트 충전 및 환전, 로그아웃 기능이 있음
- 포인트 충전은 카카오페이로 가능
- 회원정보수정, 포인트 환전, 로그아웃은 미구현
<br><br>
#### 자신이 속한 스터디방 확인 및 입장(마이페이지)
![mystudyrooms](https://github.com/gyuturn/study-deposit/assets/87477702/2d2cd97f-0852-4d24-8552-911b60794a4c)
- 자신이 속한 스터디방을 확인할 수 있음

<br><br>
#### 포인트 충전(카카오페이)
![kakaopay](https://github.com/gyuturn/study-deposit/assets/87477702/97529ef2-bb6f-4934-a9e9-6b8599f79828)
- 포인트 충전 및 환전은 카카오페이를 통해 진행 가능
- 현재는 테스트버전이기에 가상계좌를 I’m port(PG)에 등록하여 기능구현



<br><br>
#### 계좌이체(포인트->현금 전환), 채팅(현재 기능 구현중)



## 📑 &nbsp; ERD

<img width="956" alt="스크린샷 2023-04-09 오후 3 01 49" src="https://user-images.githubusercontent.com/87477702/230757206-9b5442b6-dfc1-4ba8-90c5-7f4cc81dd631.png">


## 📑 &nbsp; Project 관리
#### <a href="https://github.com/gyuturn/study-deposit/issues">Github Issues</a> <br>
#### <a href="https://github.com/users/gyuturn/projects/2">Github Project 칸판보드</a> <br>

## 📱 &nbsp; 기술스택


FE : &nbsp; 
<img src="https://img.shields.io/badge/Vue.js-4FC08D?style=flat-square&logo=Vue&logoColor=white"/>
<img src="https://img.shields.io/badge/vuetify-1867C0?style=flat-square&logo=Vue&logoColor=white"/>

BE : &nbsp; <img src="https://img.shields.io/badge/Java-A8B9CC?style=flat&logo=openjdk&logoColor=white"/> <img src="https://img.shields.io/badge/Spring-5cb230?style=flat&logo=spring&logoColor=white"/> <img src="https://img.shields.io/badge/NGINX-009639?style=flat&logo=NGINX&logoColor=white"/> <img src="https://img.shields.io/badge/REDIS-DC382D?style=flat&logo=REDIS&logoColor=white"/> <img src="https://img.shields.io/badge/mysql-4479A1?style=flat&logo=mysql&logoColor=white"/>  <img src="https://img.shields.io/badge/docker-2496ED?style=flat&logo=docker&logoColor=white"/> 


## 📑 &nbsp; 아키텍쳐
<img width="796" alt="스크린샷 2023-04-09 오후 3 23 37" src="https://user-images.githubusercontent.com/87477702/230757973-609a5c3b-53c0-4150-aaa3-8144fc7ec14a.png">



<p align="right">(<a href="#readme-top">back to top</a>)</p>

<br>

##  👨‍👩‍👧‍👦 &nbsp; Contributors

|```FE/BE``` 김규민|
|:-:|
|<img src="https://user-images.githubusercontent.com/74173976/216749262-407cb3a3-f74b-4021-aea3-99cda8540be7.png" width=130>|
|[@gyuturn](https://github.com/gyuturn)|


<p align="right">(<a href="#readme-top">back to top</a>)</p>
