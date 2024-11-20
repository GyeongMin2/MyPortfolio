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
<link rel="stylesheet" href="/stylegroup/myPage/myPage.css" />
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
        <h1 class="textCenter" style="margin-bottom: 30px">마이페이지</h1>
        <div class="ccenter">
          <div class="myPageMenu">
            <div class="myPageMenuGroup">
              <div class="myPageMenuGroupContent">
                <img src="/public/myprofile.png" alt="" /> &nbsp;내 프로필&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="/goto.do?page=modify" style="font-size:10px;">회원 수정▶</a></span>
              </div>
              
              <c:choose>
              	<c:when test="${sessionScope.user.userType != 'T'}">
              
              <div class="myPageMenuGroupContent">
                <img src="/public/myplan.png" alt="" /> &nbsp;학습 현황
              </div>
              <div class="myPageMenuGroupContent">
                <img src="/public/event.png" alt="" /> &nbsp;이벤트
              </div>
              
              	</c:when>
              </c:choose>
            </div>
            <div class="myPageMenuGroup">
              <div class="myPageContent">
                <div class="ccenter">
                  <img
                    src="/public/channels4_profile.jpg"
                    alt=""
                    class="myProfileImg"
                  />
                  <div class="myProFile">
                    <h3>${sessionScope.user.userNickname}님</h3>
                    <p>등급 : <span>${ sessionScope.user.userType }</span></p>
                  </div>
                </div>
              </div>
              
              <c:choose>
				  <c:when test="${sessionScope.user.userType != 'T'}">
				    <div>
				      <div class="myPageMenuGroup">
				        <div class="myPageContent">
				          <div class="gang">
				            <p>현재 수강중인<br />강의 개수</p>
				            <span>${lectureTotalCount}</span>
				          </div>
				        </div>
				      </div>
				    </div>
				    <div>
				      <div class="myPageMenuGroup">
				        <div class="myPageContent" style="background-color: #6e46ff">
				          <div>
				            <img src="/public/myPageEvent.jpg" alt="" width="100%" />
				          </div>
				        </div>
				      </div>
				    </div>
				  </c:when>
				</c:choose>
            </div>
          </div>
        </div>
        <div class="navMainBoundary" style="margin: 20px 0 20px 0"></div>
          <c:choose>
              	<c:when test="${sessionScope.user.userType != 'T'}">
              	
        	<div class="ccenter">
          <div class="myStudyVideo">
            <div class="studyCardUpBar">
              <h3>현재 수강중인 강좌</h3>
              <button id="myLectureButton">나의 강의실로 이동▶</button>
            </div>
            <div class="myStudyArea">
              <div class="myStudyVideoList">
                <!-- 아무것도 없으면 -->
                <!-- <div class="noStudyVideo">현재 수강중인 강의가 없습니다.</div> -->
                <c:forEach var="lec" items="${lectureList}">
                <a
                  href="/lecture/common/lectureDetail.do?lectureCode=${lec.lectureCode}"
                  class="studyCardLink"
                >
                  <div class="studyCard">
                    <h4>${lec.lectureName}</h4>
                    <p>${lec.lectureCode}</p>
                    <p>시작한 날</p>
                    <p>${lec.lectureRegdate}</p>
                    <p>${lec.teacherName} 선생님</p>
                  </div>
                </a>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>
              	</c:when>
          </c:choose>
        <div class="ccenter">
          <div class="myStudyVideo" style="margin: 50px 0 50px 0">
            <div class="myStudyVideo marginTop100">
              <h3>내가 작성한 글</h3>
              <div>
                <table class="boardTable">
                  <thead>
                    <tr>
                      <th>분류</th>
                      <th>제목</th>
                      <th>등록일</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="board" items="${postList}">
                    <tr>
                      <td class="postType" data-type="${board.postType}"><span>${board.postType}</span></td>
                      <td class="titleSummary">
                        <a href="/gotoPostDetail.do?idx=${board.postIdx}">${board.postTitle}</a>
                      </td>
                      <td>${board.postRegdate}</td>
                    </tr>
                    </c:forEach>
                  </tbody>
                </table>
                <div class="pagination">
                  <c:if test="${postPagination.totalPages > 1}">
			        <c:if test="${postPagination.hasFirstPage()}">
			            <a href="?postPageNo=1&commentPageNo=${commentPagination.pageNo}"><< </a>
			        </c:if>
			        <c:if test="${postPagination.hasPreviousBlock()}">
			            <a href="?postPageNo=${postPagination.blockStartPage - 1}&commentPageNo=${commentPagination.pageNo}">< </a>
			        </c:if>
			
			        <c:forEach var="i" begin="${postPagination.blockStartPage}" end="${postPagination.blockEndPage}">
			            <c:choose>
			                <c:when test="${i == postPagination.pageNo}">
			                    <strong>${i}</strong> 
			                </c:when>
			                <c:otherwise>
			                    <a href="?postPageNo=${i}&commentPageNo=${commentPagination.pageNo}">${i}</a>
			                </c:otherwise>
			            </c:choose>
			        </c:forEach>
			
			        <c:if test="${postPagination.hasNextBlock()}">
			            <a href="?postPageNo=${postPagination.blockEndPage + 1}&commentPageNo=${commentPagination.pageNo}"> ></a>
			        </c:if>
			        <c:if test="${postPagination.hasLastPage()}">
			            <a href="?postPageNo=${postPagination.totalPages}&commentPageNo=${commentPagination.pageNo}"> >></a>
			        </c:if>
			    </c:if>
                </div>
              </div>
            </div>
            <div class="myStudyVideo marginTop100">
              <h3>내가 작성한 댓글</h3>
              <div>
              <table class="boardTable">
				    <tr>
				        <th>댓글 유형</th>
				        <th>원문글</th>
				        <th>내용</th>
				        <th>작성일</th>
				    </tr>
				   <c:forEach var="comment" items="${commentList}">
				        <tr>
				            <td class="commentType" data-type="${comment.board.boardType}">
				                <c:choose>
				                    <c:when test="${comment.boardComment != null}"><span class="label free">${comment.board.boardType}</span></c:when>
				                    <c:when test="${comment.qnaComment != null}"><span class="label qna">${comment.qna.qnaType}</span></c:when>
				                </c:choose>
				            </td>
				             <td>
				                <c:choose>
				                    <c:when test="${comment.boardComment != null}"><a href="/gotoPostDetail.do?idx=${comment.board.boardIdx}">${comment.board.boardTitle}</a></c:when>
				                    <c:when test="${comment.qnaComment != null}"><a href="/gotoQnaDetail.do?idx=${comment.qna.qnaIdx}">${comment.qna.qnaTitle}</a></c:when>
				                </c:choose>
				            </td>
				            <td class="commentSummary">
				                <c:choose>
				                    <c:when test="${comment.boardComment != null}">${comment.boardComment.commentContent}</c:when>
				                    <c:when test="${comment.qnaComment != null}">${comment.qnaComment.qnaCommentContent}</c:when>
				                </c:choose>
				            </td>
				            <td>
				                <c:choose>
				                    <c:when test="${comment.boardComment != null}">${comment.boardComment.commentRegdate}</c:when>
				                    <c:when test="${comment.qnaComment != null}">${comment.qnaComment.commentRegdate}</c:when>
				                </c:choose>
				            </td>
				        </tr>
				    </c:forEach>
				</table>
                <div class="pagination">
                  <!-- "[" + pageNum + "]" 형식으로 들어올 예정 -->
                  <c:if test="${commentPagination.totalPages > 1}">
			        <c:if test="${commentPagination.hasFirstPage()}">
			            <a href="?commentPageNo=1&postPageNo=${postPagination.pageNo}"><<</a>
			        </c:if>
			        <c:if test="${commentPagination.hasPreviousBlock()}">
			            <a href="?commentPageNo=${commentPagination.blockStartPage - 1}&postPageNo=${postPagination.pageNo}">< </a>
			        </c:if>
			
			        <c:forEach var="i" begin="${commentPagination.blockStartPage}" end="${commentPagination.blockEndPage}">
			            <c:choose>
			                <c:when test="${i == commentPagination.pageNo}">
			                    <strong>${i}</strong> 
			                </c:when>
			                <c:otherwise>
			                    <a href="?commentPageNo=${i}&postPageNo=${postPagination.pageNo}">${i}</a>
			                </c:otherwise>
			            </c:choose>
			        </c:forEach>
			
			        <c:if test="${commentPagination.hasNextBlock()}">
			            <a href="?commentPageNo=${commentPagination.blockEndPage + 1}&postPageNo=${postPagination.pageNo}"> ></a>
			        </c:if>
			        <c:if test="${commentPagination.hasLastPage()}">
			            <a href="?commentPageNo=${commentPagination.totalPages}&postPageNo=${postPagination.pageNo}"> >></a>
			        </c:if>
			    </c:if>
                </div>
              </div>
            </div>
            <c:choose>
				  <c:when test="${sessionScope.user.userType != 'T'}">
				  	<div class="myStudyVideo marginTop100">
		              <h3>나의 결제 목록</h3>
		              <div>
		                <table class="boardTable">
		                  <thead>
		                    <tr>
		                      <th>과목</th>
		                      <th>강의 제목</th>
		                      <th>강의 코드</th>
		                      <th>구매일</th>
		                      <th>금액</th>
		                    </tr>
		                  </thead>
		                  <tbody>
		                    <c:forEach var="payList" items="${paymentList}">
			                    <tr>
			                      <td class="lectureCode" data-code="${payList.lectureCode}"><span class="label kor">
			                      ${payList.lectureCode}
			                      </span></td>
			                      <td class="titleSummary">
			                        <a href="/lecture/common/lectureDetail.do?lectureCode=${payList.lectureCode}">${payList.lectureName}</a>
			                      </td>
			                      <td>${payList.lectureCode}</td>
			                      <td>${payList.paymentDate}</td>
			                      <td>${payList.lecturePrice}원</td>
			                    </tr>
			                </c:forEach>
		                  </tbody>
		                </table>
		              </div>
		            </div>
				  </c:when>
            </c:choose>
            
            <div class="myStudyVideo marginTop100">
              <h3>강좌 프로모션</h3>
              <img src="/public/mainSlider.png" alt="" />
            </div>
          </div>
        </div>
        </div>
      </main>
		<!-- 메인 콘텐츠 영역 // -->
		
		
		<!-- // 푸터 영역 -->
		<jsp:include page="../commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
		<script src="/jsgroup/myPage/myPage.js"></script>
		
	</div>
</body>
</html>
