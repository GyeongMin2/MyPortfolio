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
				<form action="postWrite.do" enctype="multipart/form-data" method="post" onsubmit="return validateForm()">
					<input type="hidden" name="category" value="${category}"/>
					<input type="hidden" name="type" class="postForm" value="${boardType}"/>
					<input type="hidden" name="boardWriter" value="${sessionScope.user.userId}">
					
					<div class="formGroup">
					
					<label for="postTitle" class="postLabel">제목</label>
					<input type="text" id="postTitle" class="postInput" name="boardTitle" oninput="updateCharCount('title')" placeholder="제목을 입력하세요" required>
					<span id="titleCharCount" class="charCount">0/30</span>
					</div>
					<div class="formGroup">
					<label for="postContent" class="postLabel">내용</label>
					<textarea id="postContent" name="boardContent" class="postTextarea" placeholder="내용을 입력하세요" oninput="updateCharCount('content')" required></textarea>
					
					<span id="contentCharCount" class="charCount">0/3000</span>
           			</div>
           			
           			<div class="formActions">
					<button type="submit" class="submitBtn">등록</button>
					<button
		                type="button"
		                class="cancelBtn"
		                onclick="window.location.href='/post/common/gotoPostList.do'"
		              >
		                취소
		            </button>
		            </div>
					<div class="file-input">
						<label for="file">첨부파일</label>
						<input type="file" id="file" name="file">
					</div>
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
