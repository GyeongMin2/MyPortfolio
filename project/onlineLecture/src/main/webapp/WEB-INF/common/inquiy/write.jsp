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
<link rel="stylesheet" href="/stylegroup/list/list.css" />
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
			<div class="postWriteContainer">
          		<h2 class="postTitle">글 작성</h2>
				<form action="qnaWrite.do?type=${qnaType}" method="post" onsubmit="return validateForm()">
					<div class="formGroup">
					
					<input type="hidden" name="type" value="${qnaType}"/>
					<input type="hidden" name="category" value="${category}"/>
					<input type="hidden" name="teacherId" value="${teacherId}"/>
					<!-- 	<label for="boardType">게시판 유형:</label> -->
					<!-- 	<input type="text"  name="boardType" required><br> -->
					
					<label for="postTitle" class="postLabel">제목</label>
					<input type="text" id="postTitle" class="postInput" name="qnaTitle" oninput="updateCharCount('title')" placeholder="제목을 입력하세요" required>
					<span id="titleCharCount" class="charCount">0/30</span>
					</div>
					<div class="formGroup">
					<label for="postContent" class="postLabel">내용</label>
					<textarea id="postContent" name="qnaContent" class="postTextarea" placeholder="내용을 입력하세요" oninput="updateCharCount('content')" required></textarea>
					
					<input type="hidden" name="qnaWriter" value=${sessionScope.user.userId}>
					<span id="contentCharCount" class="charCount">0/3000</span>
           			</div>
           			
           			<div class="formActions">
					<button type="submit" class="submitBtn">등록</button>
					<button
		                type="button"
		                class="cancelBtn"
		                onclick="window.location.href='/gotoQnaList.do'"
		              >
		                취소
		            </button>
		            </div>
					<c:if test="${boardType != 'R' && boardType != 'N' && boardType != 'C'}">
						<input type="file" name="attachedFile">
					</c:if>
				</form>
			</div>
		</main>
		
		
		<!-- // 푸터 영역 -->
		<jsp:include page="../commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
		<script src="/jsgroup/list/listWrite.js"></script>
	</div>
</body>
</html>
