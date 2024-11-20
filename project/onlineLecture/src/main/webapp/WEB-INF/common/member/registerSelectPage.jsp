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
<link rel="stylesheet"
	href="<c:url value="/stylegroup/main/styles.css" />" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<link rel="icon" href="/public/channels4_profile.jpg" type="image/png" />
<link rel="stylesheet" href="/stylegroup/member/registerStyle.css" />
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
			<div class="register-main">
				<div class="register-container">
					<img src="/public/logo.png" alt="Logo" class="register-logo" />

					<h1 class="register-title">회원가입</h1>
					<p class="register-subtitle">가입 유형을 선택해주세요.</p>

					<div class="register-user-type">
						<div class="user-type-option student">
							<img src="/public/student.png" alt="학생" class="user-image" /> <label
								class="radio-container" style="display: block"> <input
								type="radio" name="userType" value="student" checked /> <span
								class="checkmark"></span> 학생
							</label>
						</div>

						<div class="user-type-option teacher">
							<img src="/public/teacher.png" alt="선생님" class="user-image" /> <label
								class="radio-container" style="display: block"> <input
								type="radio" name="userType" value="teacher" /> <span
								class="checkmark"></span> 선생님
							</label>
						</div>
					</div>

					<button class="select-btn">가입 진행</button>
				</div>
			</div>
		</main>
		<!-- 메인 콘텐츠 영역 // -->

		<!-- // 푸터 영역 -->
		<jsp:include page="../commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
		<script src="/jsgroup/member/registerScript.js"></script>
	</div>
</body>
</html>
