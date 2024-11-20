<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>스마트 해법</title>

<!-- // link태그 -->
<link rel="stylesheet" href="<c:url value="/stylegroup/main/styles.css" />" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<link rel="icon" href="/public/channels4_profile.jpg" type="image/png" />
<link rel="stylesheet" href="/stylegroup/member/registerGo.css" />
<!-- link 태그 // -->
</head>
<body>
	<div class="boxContainer">
		<!-- // 상단 이미지 -->
		<jsp:include page="../commonArea/pageTopImageArea.jsp" />
		<!-- 상단 이미지 // -->
		
		<!-- // navbar 영역 -->
		<jsp:include page="../commonArea/header.jsp" />
		<!-- navbar 영역 // -->

		<!-- // 메인 콘텐츠 영역 -->
      <main>
        <div class="mainContainer">
          <div class="mainContent">
            <div>
              <div class="ccenter">
                <img src="/public/logo.png" alt="logo" />
              </div>
              <h1 class="textCenter">학생가입</h1>
              <div class="textCenter ehddml">
                가입 정보 입력 및 약관에 동의해주세요.
              </div>
              <form action="/member/common/register.do" method="post">
                <div>
                  <label for="userId" class="inputLabel">아이디:</label>
                  <input
                    type="text"
                    name="userId"
                    id="userId"
                    placeholder="아이디"
                    class="inputField"
                    required
                    oninput="validateUserId()"
                  /><br />
                </div>
                <button class="idDuplicateCheck">아이디 중복 체크</button>
                <span class="valMsg1 valArea"></span>

                <div>
                  <label for="userPwd" class="inputLabel">비밀번호:</label>
                  <input
                    type="password"
                    name="userPwd"
                    id="userPwd"
                    placeholder="비밀번호"
                    class="inputField"
                    required
                    oninput="validateUserPwd()"
                  /><br />
                </div>
                <span class="valMsg2 valArea"></span>

                <div>
                  <label for="userPwdCheck" class="inputLabel"
                    >비밀번호 확인:</label
                  >
                  <input
                    type="password"
                    name="userPwdCheck"
                    id="userPwdCheck"
                    placeholder="비밀번호"
                    class="inputField"
                    required
                    oninput="validateUserPwdCheck()"
                  /><br />
                </div>
                <span class="valMsg3 valArea"></span>

                <div>
                  <label for="userName" class="inputLabel">이름:</label>
                  <input
                    type="text"
                    name="userName"
                    id="userName"
                    placeholder="이름"
                    class="inputField"
                    required
                    oninput="validateUserName()"
                  /><br />
                </div>
                <span class="valMsg4 valArea"></span>

                <div>
                  <label for="userEmail" class="inputLabel">이메일:</label>
                  <input
                    type="email"
                    name="userEmail"
                    id="userEmail"
                    placeholder="이메일"
                    class="inputField"
                    required
                    oninput="validateUserEmail()"
                  /><br />
                </div>
                <span class="valMsg5 valArea"></span>

                <div>
                  <label for="userPhone" class="inputLabel">전화번호:</label>
                  <input
                    type="tel"
                    name="userPhone"
                    id="userPhone"
                    placeholder="전화번호"
                    class="inputField"
                    required
                    oninput="validateUserPhone()"
                  /><br />
                </div>
                <span class="valMsg6 valArea"></span>

                <div>
                  <label for="userBirth" class="inputLabel">생년월일:</label>
                  <input
                    type="date"
                    name="userBirth"
                    id="userBirth"
                    placeholder="생년월일"
                    class="inputField"
                    required
                  /><br />
                </div>
                <span class="valMsg7 valArea"></span>

                <div>
                  <label for="userNickname" class="inputLabel">닉네임:</label>
                  <input
                    type="text"
                    name="userNickname"
                    id="userNickname"
                    placeholder="닉네임"
                    class="inputField"
                    required
                    oninput="validateUserNickname()"
                  /><br />
                </div>
                <span class="valMsg8 valArea"></span>

                <div class="center">
                  <button type="submit" class="submitButton">회원가입</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </main>

      <!-- 메인 콘텐츠 영역 // -->

		<!-- // 푸터 영역 -->
		<jsp:include page="../commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
		<script src="/jsgroup/member/registerGo.js"></script>
	</div>
</body>
</html>
