# 백엔드 개발자 포트폴리오

##### **최종 업데이트**: 2024년 11월 20일

***

## 👋 소개
안녕하세요! 백엔드 개발자를 목표로 하는 [강경민]입니다.
- 서버 개발과 시스템 아키텍처에 관심이 많습니다
- 클린 코드와 테스트 작성을 중요하게 생각합니다
- 코드 리뷰를 통해 성장하는 것을 중요하게 생각합니다

## 기술 스택
### 언어
![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white) 
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white) 

### 프레임워크 & 라이브러리
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33D?style=flat-square&logo=Spring_Boot&logoColor=white) 

### ORM & 데이터 처리
![JPA](https://img.shields.io/badge/JPA-6DB33F?style=flat-square&logo=Spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=Hibernate&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-000000?style=flat-square)

### 데이터베이스
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=MariaDB&logoColor=white)

### 프로토콜 & 통신
![WebSocket](https://img.shields.io/badge/WebSocket-000000?style=flat-square&logo=WebSocket&logoColor=white)

### 서버 & WAS
![Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?style=flat-square&logo=ApacheTomcat&logoColor=black)
![Node.js](https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=Node.js&logoColor=white)

### 인프라 & 도구
![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=AmazonAWS&logoColor=white)
![Azure](https://img.shields.io/badge/Azure-0089D6?style=flat-square&logo=MicrosoftAzure&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat-square&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white)
![letsEncrypt](https://img.shields.io/badge/Let's_Encrypt-003A70?style=flat-square&logo=Let%27sEncrypt&logoColor=white)

### OS
![Linux](https://img.shields.io/badge/Linux-FCC624?style=flat-square&logo=Linux&logoColor=black)
![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=flat-square&logo=Ubuntu&logoColor=white)

***
## 📝 주요 프로젝트
### 1.🥄[TSPOON]
> 이 프로젝트는 회원가입, 로그인, 게시판 CRUD, 쪽지 기능 등을 포함하고 있습니다.

**역할**: 백엔드 개발
**기술 스택**: Java, Servlet, JSP, MySQL, Tomcat
**주요 기능**:
- 회원가입, 로그인, 로그아웃, 자동 로그인
  - Ajax 통신을 통해 아이디 중복체크 비동기 처리
  - 비밀번호 암호화 처리 (SHA-256, Salt)
  - 자동 로그인 처리 (쿠키)
- 게시판 CRUD
  - 쪽지및 게시판 불러오기 시 ajax 통신을 통해 비동기 처리
- 쪽지
  - 쪽지 쓰기, 쪽지 읽기, 쪽지 삭제
  - 프로시저를 이용한 논리적인 쪽지 삭제 및 물리적인 삭제 분리

**성과**:
- 보안성(세션기반 인증, sql 인젝션 방지, xss 방지)
- 확장성 (모듈화된 구조, MVC 패턴적용)
- 데이터 무결성 (트랜잭션 처리, 사용자 권한 검증)

+ [프레젠테이션](project/TSPOON/TSPOON_porject.pdf)
+ [주요 소스코드](project/TSPOON/TSPOON_project.md)
+ [github](https://github.com/GyeongMin2/MyPortfolio/tree/main/project/TSPOON/src/main/java)

***

### 2.🎥[onlineLecture]
> 이 프로젝트는 온라인 강의 플랫폼으로, 강의 관리, 사용자 인증 등의 기능을 포함하고 있습니다.

**역할**: 백엔드개발 및 공통 모듈 설계 및 DB설계 및 DDL관리, 홈서버 구축 및 배포
**기술 스택**: Java, Servlet, JSP, MySQL, Tomcat, SSL 배포
**주요 기능**:
- 회원가입, 로그인, 강의 구매, 결제, 관리자 페이지등 다양한 기능 구현
- 관리자 페이지에서 강의 관리, 회원 관리등 기능구현
- DB 설계 및 DDL 관리 SSH를 통한 DB접속 및 관리
- 홈서버 구축 및 배포 및 SSL 인증서를 Let's Encrypt를 통해 설정하여 웹 애플리케이션의 보안을 강화

**성과**:
- 홈서버 구축 및 배포
  - SSL 인증서를 Let's Encrypt를 통해 설정하여 웹 애플리케이션의 보안을 강화
  - 포트포워딩 설정을 통해 웹 애플리케이션 배포
- 데이터베이스 관리
  - DB 설계 및 DDL 관리
  - SSH를 통한 DB접속 및 관리
- 공통 모듈(유틸) 설계
  - 모듈화된 구조, MVC 패턴적용
  - 클린 코드 작성
- 첫 협업 프로젝트 경험
  - 의사소통 및 협업 능력 향상

+ [github](https://github.com/TheLastOnlineLecture/onlineLecture)
+ [주요 소스코드](project/onlineLecture/onlineLecture_project.md)

***

### 3.🎒[eduSecond]
>이 프로젝트는 중고 거래 플랫폼으로, 중고 물품 거래 및 채팅 기능을 포함하고 있습니다.

**역할**: 백엔드 개발
**기술 스택**: Java, Servlet, JSP, MySQL, Tomcat, Spring, WebSocket
**주요 기능**:
- 회원가입, 로그인, 중고 물품 등록,수정,삭제 기능 구현
- 중고물품 거래 프로세스 구현
- 채팅 기능
  - 채팅방 생성, 채팅방 입장, 채팅방 퇴장, 채팅 기능
  - WebSocket과 http 하이브리드 통신 구현
- 관리자 페이지
  - 중고 물품 관리, 회원 관리 기능 구현

**성과**:
- Spring MVC,Web을 활용한 확장성 있는 웹 애플리케이션 구현
- MyBatis를 활용한 데이터베이스 처리
- 실시간 1:1 채팅 시스템 구현
  - Spring WebSocket을 활용한 실시간 양방향 통신 구현
  - WebSocket과 HTTP를 결합한 하이브리드 통신으로 성능 최적화
  - 채팅 메시지 영구 저장 및 이전 대화 내역 조회 기능 구현
  - 읽지 않은 메시지 알림 기능 구현
- 관리자 페이지 시스템 설계 및 구현
  - 트랜잭션 관리를 통한 데이터 정합성 보장
    - 회원 탈퇴 시 관련 데이터(게시글, 댓글, 채팅 등) 일괄 처리
    - 실패 시 롤백 처리로 데이터 일관성 유지
  - 필터를 통한 관리자 인증 및 권한 검증

+ [github](https://github.com/eduSecond)
+ [주요 소스코드](project/eduSecond/eduSecond_project.md)
+ [프레젠테이션](project/eduSecond/eduSecond_project.pdf)

## 📈 기술적 성과
- **홈서버 구축 및 배포**
  - SSL 인증서를 Let's Encrypt를 통해 설정하여 웹 애플리케이션의 보안을 강화
  - 포트포워딩 설정을 통해 웹 애플리케이션 배포
- **Restful API 설계 및 구현**
  - 웹 애플리케이션에서 필요한 데이터를 효과적으로 전달하고 처리하기 위한 API 설계 및 구현
- **크론탭과 스케쥴러를 통한 리소스 모니터링**
  - java와 크론탭을 통해 리소스 모니터링 및 로그 저장


## 🎓 교육
- 명지전문대학 | 소프트웨어 콘텐츠학과 (2024년 8월)
- 천재교육 | java fullstack 개발자 과정 (2024년 7월 ~ 2025년 1월)
- 상원고등학교 (2020년 1월)

## 📑 자격증 & 수상
- 자격증명 (취득연도)
- 수상 내역

## 📞 연락처
- 이메일: kgmmsw101@gmail.com
- GitHub: [GyeongMin2](https://github.com/GyeongMin2)

## 🧑‍💻 토이 프로젝트

### 1.🧑‍💻[javaSimpleBoard](https://github.com/GyeongMin2/javaSimpleBoard)
> java, jdbc,mysql을 이용한 cli기반 게시판
+[주요 소스코드](toyProject/javaSimpleBoard_project.md)

### 2.🧑‍💻[javaPlayground](https://github.com/GyeongMin2/javaPlayground)
> java, jdbc, mysql을 이용한 리소스모니터 및 저수준 소켓통신 구현 (server,client)
+[주요 소스코드](toyProject/javaPlayground_project.md)
