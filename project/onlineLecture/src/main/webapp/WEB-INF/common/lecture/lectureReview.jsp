<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
<meta charset="UTF-8">
<title>스마트 해법</title>

<!-- // link태그 -->
<link rel="stylesheet"
	href="<c:url value="/stylegroup/main/styles.css" />" />
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
			<h1 class="textCenter boardBoxTitle" data-board-type="${boardType}">${boardType}</h1>
			<div class="areaDeployment"></div>
			<div class="ccenter">
				<div class="boardBoxMain">
					<div class="boardContainerLeftSide">
						<div class="boardMenuGroup">
							<div class="boardMenu">
								<h3>게시판 모음</h3>
							</div>
							<div class="boardMenu">
								<p>
									<a href="#">수강후기</a>
								</p>
							</div>
						</div>
					</div>
					<div class="boardContainer">
						<div class="boardFilter">
							<form action="/gotoPostList.do" method="get">
								<input type="hidden" name="type" value="${boardType}"/>
								<div class="boardTopBar">
									<div>
										<select id="searchType" name="searchType">
											<option value="title">제목</option>
											<option value="writer">아이디</option>
											<option value="title_content">제목 + 내용</option>
										</select> <input type="text" id="searchKeyword" placeholder="제목" name="searchKeyword"/>
										<button type="submit">검색</button>
									</div>
									<a href="/goto.do?page=post/write&type=${boardType}&category=${category}">글 작성</a>
								</div>
							</form>
						</div>
						<table class="boardTable">
							<thead>
								<tr>
									<th>학년도</th>
									<th>분류</th>
									<th>제목</th>
									<th>등록일</th>
									<th>작성자</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty boardList}">
										<c:forEach var="board" items="${boardList}">
											<tr>
												<td>2024</td>
												<td><span class="label notice">공지사항</span></td>
												<td><a href="gotoPostDetail.do?idx=${board.boardIdx}">${board.boardTitle}</a></td>
												<td>${board.boardRegdate}</td>
												<td>${board.boardWriter}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="5">등록된 게시글 x</td>
										</tr>
									</c:otherwise>
								</c:choose>
								<tr>
					              <td colspan="5">
					                <div class="pagination">
					                  <c:if test="${pagination.hasFirstPage()}">
					                    <a href="?pageNo=1"></a>
					                  </c:if>
					                  <c:if test="${pagination.hasPreviousBlock()}">
					                    <a
					                      href="?pageNo=${pagination.blockStartPage - 1}&type=${boardType}"
					                      ><</a
					                    >
					                  </c:if>
					
					                  <c:forEach
					                    var="i"
					                    begin="${pagination.blockStartPage}"
					                    end="${pagination.blockEndPage}"
					                  >
					                    <a href="?pageNo=${i}&type=${boardType}">${i}</a>
					                  </c:forEach>
					
					                  <c:if test="${pagination.hasNextBlock()}">
					                    <a
					                      href="?pageNo=${pagination.blockEndPage + 1}&type=${boardType}"
					                      >></a
					                    >
					                  </c:if>
					                  <c:if test="${pagination.hasLastPage()}">
					                    <a href="?pageNo=${pagination.totalPages}&type=${boardType}"
					                      >></a
					                    >
					                  </c:if>
					                </div>
					              </td>
					            </tr>
							</tbody>
						</table>
					</div>
					<div class="boardContainerrightSide">
						<img src="/public/noAreaImage1.png" alt="" />
					</div>
				</div>
			</div>
		</main>
		<!-- 메인 콘텐츠 영역 // -->


		<!-- // 푸터 영역 -->
		<jsp:include page="../commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
		<script src="/jsgroup/list/list.js"></script>
	</div>
</body>
</html>