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
<link rel="stylesheet" href="/stylegroup/member/modifyMember.css" />
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
      <main class="ccenter">
        <div class="mainContainer2">
          <div class="modifyContent">
            <div>
              <div class="modifyMenu modifyMemInfo">
                <h2>학습생 정보</h2>
              </div>
              <div class="modifyMenu modifyMemInfo">
                <a href="/member/common/gotoModify.do" class="modifyPage"
                  >개인정보 수정</a
                >
              </div>

              <div class="modifyMenu modifyMemInfo">
                <a href="/member/common/delete.do">탈퇴</a>
              </div>
              <div class="modifyMenu modifyMemInfo" style="height: auto">
                <img src="/public/userProblemCenter.png" />
              </div>
              <div
                class="modifyMenu modifyMemInfo"
                style="height: 284px; border-bottom: none"
              ></div>
            </div>
            <div class="modifyInputArea">
              <form action="/member/common/modify.do" method="post">
                <h2 class="ccenter">개인 정보 수정</h2>
                <div>
                  <label for="userId" class="inputLabel">아이디:</label>
                  <input
                    type="text"
                    name="userId"
                    id="userId"
                    placeholder="아이디"
                    class="inputField"
                    required
                    value="${sessionScope.user.userId}"
                    disabled
                  /><br />
                </div>
                <button class="idDuplicateCheck">아이디 중복 체크</button>

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
                <span class="valMsg2 valArea" style="font-size:14px"></span>

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
                <span class="valMsg3 valArea" style="font-size:14px"></span>

                <div>
                  <label for="userName" class="inputLabel">이름:</label>
                  <input
                    type="text"
                    name="userName"
                    id="userName"
                    placeholder="이름"
                    class="inputField"
                    required
                    value="${sessionScope.user.userNickname}"
                    disabled
                  /><br />
                </div>
                <span class="valMsg4 valArea" style="font-size:14px"></span>

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
                <span class="valMsg5 valArea" style="font-size:14px"></span>

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
                <span class="valMsg6 valArea" style="font-size:14px"></span>

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
                <span class="valMsg8 valArea" style="font-size:14px"></span>

                <div class="center">
                  <button type="submit" class="submitButton">
                    정보 수정하기
                  </button>
                </div>
              </form>
            </div>
            <div class="rightImageArea">
              <img src="/public/noAreaImage1.png" alt="" />
            </div>
          </div>
        </div>
      </main>
      <!-- 메인 콘텐츠 영역 // -->

		<!-- // 푸터 영역 -->
		<jsp:include page="../commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
		<script src="/jsgroup/member/modifyMember.js"></script>
	</div>
</body>
</html>
