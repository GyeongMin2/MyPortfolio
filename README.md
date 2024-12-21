# 백엔드 개발자 포트폴리오

> **최종 업데이트**: 2024년 12월 21일

## 소개
안녕하세요! 백엔드 개발자를 목표로 하는 강경민 입니다.
- 서버 개발과 시스템 아키텍처에 관심이 많습니다
- 클린 코드와 테스트 작성을 중요하게 생각합니다
- 코드 리뷰를 통해 성장하는 것을 중요하게 생각합니다

## 기술 스택

### 주요 기술
>실제 프로젝트에서 주도적으로 사용한 기술들입니다.
- **Backend**: ![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33D?style=flat-square&logo=Spring_Boot&logoColor=white)

- **Database**: ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white) ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=MariaDB&logoColor=white)

- **ORM/Mapper**: ![MyBatis](https://img.shields.io/badge/MyBatis-000000?style=flat-square) ![JPA](https://img.shields.io/badge/JPA-6DB33F?style=flat-square&logo=Spring&logoColor=white)

- **Frontend**: ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white)

- **OS & Tools**: ![Linux](https://img.shields.io/badge/Linux-FCC624?style=flat-square&logo=Linux&logoColor=black) ![Git](https://img.shields.io/badge/Git-F05032?style=flat-square&logo=Git&logoColor=white) ![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white) ![WebSocket](https://img.shields.io/badge/WebSocket-000000?style=flat-square&logo=WebSocket&logoColor=white)

### 활용 가능
> 프로젝트에서 사용해본 경험이 있는 기술들입니다.
- **Backend**: ![Node.js](https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=Node.js&logoColor=white)

- **Infrastructure**: ![Nginx](https://img.shields.io/badge/Nginx-009639?style=flat-square&logo=nginx&logoColor=white)

- **Cloud**: ![AWS_RDS](https://img.shields.io/badge/Amazon_RDS-232F3E?style=flat-square&logo=amazonrds&logoColor=white) ![Azure](https://img.shields.io/badge/Azure-0089D6?style=flat-square&logo=MicrosoftAzure&logoColor=white)

## 주요 프로젝트
### 1.[TSPOON]
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

**[프레젠테이션](project/TSPOON/TSPOON_project.pdf)** | **[주요 소스코드 리뷰](project/TSPOON/TSPOON_project.md)** | **[github](https://github.com/GyeongMin2/MyPortfolio/tree/main/project/TSPOON/src/main/java)**

***

### 2.[onlineLecture]
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

**[프레젠테이션](project/onlineLecture/onlinLecture_project.pdf)** | **[주요 소스코드 리뷰](project/onlineLecture/onlineLecture_project.md)** | **[github](https://github.com/TheLastOnlineLecture/onlineLecture)** 

***

### 3.[eduSecond]
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

**[프레젠테이션](project/eduSecond/eduSecond_project.pdf)** | **[주요 소스코드 리뷰](project/eduSecond/eduSecond_project.md)** | **[github](https://github.com/eduSecond)**

***

### 4.[mooc]
> 학점은행제를 지원하는 온라인 교육 플랫폼으로 강좌 관리, 회원 관리, 수강 관리, 관리자 기능, 파일 관리 기능을 포함하고 있습니다.
- K-MOOC 사이트를 참고하여 만든 프로젝트

**역할**: 백엔드 개발

**기술 스택**: Java, javascript, JPA, SpringBoot, MySQL

**주요 기능**:
- 회원 관리 기능
- 강좌 관리 기능
  - 강좌 등록, 수정, 삭제 기능을 Restful API 형식으로 구현
- 수강 관리 기능
  - 수강 신청/취소 및 학습 진도율 관리, 수강완료 처리(80% 이상 수강시)
- 관리자 기능
- 파일 관리 기능
  - 파일 업로드 및 다운로드 기능 구현

**성과**:
- Spring Boot와 JPA를 활용한 확장성 있는 웹 애플리케이션 구현
  - 모듈화된 구조와 계층 분리를 통한 유지보수성 향상
  - JPA를 활용한 효율적인 데이터 접근 계층 구현
- RESTful API 설계 및 구현
  - 강좌 관리 API 설계 및 구현
  - 파일 업로드/다운로드 API 구현
- 트랜잭션 관리 및 데이터 정합성
  - 읽기 전용 트랜잭션을 통한 성능 최적화
  - 강좌 삭제 시 관련 데이터 일괄 처리
- 파일 관리 시스템 구현
  - 다양한 파일 형식(이미지, 비디오, 문서) 지원
  - 파일 확장자 검증을 통한 보안 강화

**[프레젠테이션](project/mooc/mooc_project.pdf)** | **[주요 소스코드 리뷰](project/mooc/mooc_project.md)** | **[github](https://github.com/KmoocProject/mooc)**

***

### 5.[studyShare]
> 오늘의 학습은 학생들을 위한 학습 사이트로 친구 관리, 채팅, 게시물 관리 기능을 포함하고 있습니다.
- 오늘의 학습은 학생들이 배운 내용을 쉽게 정리하고 공유할 수 있도록 도와주는 사이트입니다.

**역할**: 백엔드 개발

**기술 스택**: Java, SpringBoot, Node.js, MySQL, Nginx

**주요 기능**:
- MSA 기반 회원 관리 시스템
  - JWT 토큰 기반 인증
  - 이메일 인증을 통한 회원가입 및 비밀번호 변경
  - 회원 정보 CRUD
- 학습 게시물 관리
  - 게시물 CRUD 및 파일 업로드
  - 자동 썸네일 생성
  - 게시물 좋아요 및 공유 기능
  - 페이징 처리
- 실시간 채팅 시스템
  - STOMP를 이용한 실시간 채팅
  - 1:1 및 그룹 채팅 지원
  - 채팅방 목록 및 대화 내역 조회
- 친구 관리 시스템
  - 친구 추가/삭제
  - 친구 목록 조회
- 관리자 페이지
  - 회원 관리 및 사용자 권한 관리

**성과**:
- MSA 기반 회원 관리 시스템 구현
  - JWT 토큰 기반 인증
  - 이메일 인증을 통한 회원가입 및 비밀번호 변경
- Nginx를 통한 리버스 프록시 설정
  - http/3 프로토콜 사용으로 속도 향상및 포트포워딩 설정
- 이메일 인증
    - 자체 구축한 SMTP 서버(info@gyeongminiya.asia)를 통해 대부분의 이메일 전송을 처리
    - 한국 주요 메일 서비스(네이버, 다음 등)의 스팸 정책으로 인한 전송 실패를 방지하기 위해 Gmail SMTP도 보조적으로 활용
    - 도메인별로 최적의 전송 경로를 자동 선택하여 전달률 극대화
    - Postfix와 Dovecot을 활용한 자체 메일 서버 구축으로 비용 절감 및 완전한 제어 가능
- 인덱싱을 통한 속도 향상
  - 자주 조회되는 테이블에 인덱싱 추가
    - ActiveTokens 테이블에 인덱싱 추가 (해당 테이블은 토큰의 유효성을 검사하는 테이블이라 자주 조회되었음)

**[프레젠테이션](project/studyShare/studyShare_project.pdf)** | **[주요 소스코드 리뷰](project/studyShare/studyShare_project.md)** | **[github](https://github.com/ChunjaeStudyShare/ChunjaeStudyShare)**

***

## 기술적 성과
- **홈서버 구축 및 배포**
  - SSL 인증서를 Let's Encrypt를 통해 설정하여 웹 애플리케이션의 보안을 강화
  - 포트포워딩 설정을 통해 웹 애플리케이션 배포
- **Java와 Spring,SpringBoot를 통한 확장성 있는 웹 애플리케이션 구현**
  - 모듈화된 구조, MVC 패턴적용
- **Restful API 설계 및 구현**
  - 웹 애플리케이션에서 필요한 데이터를 효과적으로 전달하고 처리하기 위한 API 설계 및 구현
- **크론탭과 스케쥴러를 통한 리소스 모니터링**
  - java와 크론탭을 통해 리소스 모니터링 및 로그 저장
- **MSA 기반 회원 관리 시스템 구현**
  - Node.js 서버 (api.gyeongminiya.asia:3443)
    - 회원 인증/인가 전용 API 서버
    - JWT 토큰기반 인증시스템
    - 이메일 인증 서비스
  - Spring Boot 서버 (www.gyeongminiya.asia:8080)
    - 프론트엔드 렌더링
    - 게시물,채팅 등 주요 비즈니스 로직 처리
  - 서비스 연동
    - Nginx 리버스 프록시를 통한 라우팅
    - HTTP/3 프로토콜 지원으로 성능 최적화
    - JWT 토큰 기반 서비스간 인증
- **메일 서버 구축**
  - Postfix와 Dovecot을 활용한 자체 메일 서버 구축으로 비용 절감 및 완전한 제어 가능

## 교육
- 명지전문대학 | 소프트웨어 콘텐츠학과 (2024년 8월)
- 천재교육 | java fullstack 개발자 과정 (2024년 7월 ~ 2025년 1월)

## 연락처
- 이메일: kgmmsw101@gmail.com, info@gyeongminiya.asia
- GitHub: [GyeongMin2](https://github.com/GyeongMin2)

## 토이 프로젝트

### 1.[javaSimpleBoard](https://github.com/GyeongMin2/javaSimpleBoard)
> java, jdbc,mysql을 이용한 cli기반 게시판
+ [주요 소스코드](toyProject/javaSimpleBoard/javaSimpleBoard_project.md)

### 2.[javaPlayground](https://github.com/GyeongMin2/javaPlayground)
> java, jdbc, mysql을 이용한 리소스모니터 및 저수준 소켓통신 구현 (server,client)
+ [주요 소스코드](toyProject/javaPlayground/javaPlayground_project.md)

