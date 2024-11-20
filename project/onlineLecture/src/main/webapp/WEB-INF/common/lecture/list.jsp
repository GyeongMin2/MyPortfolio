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
<style>
	a:hover {
	  text-decoration: underline;
	}
</style>
<script>
    <c:if test="${message != null}">
        alert('${message}');
    </c:if>
    function addToCart(lectureCode) {
        fetch('${pageContext.request.contextPath}/lecture/user/insertCart.do', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'lectureCode=' + encodeURIComponent(lectureCode)
        })
        .then(response => response.json())
        .then(result => {
            alert(result.message);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와의 통신 중 오류가 발생했습니다.');
            });
        }
</script>
<!-- // link태그 -->
<link rel="stylesheet"
	href="<c:url value="/stylegroup/main/styles.css" />" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<link rel="icon" href="/public/channels4_profile.jpg" type="image/png" />
<link rel="stylesheet" href="/stylegroup/payment/payment.css" />
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
			<div class="menuContainer3">
				<div class="paymentContent">
					<div>
						<div class="paymentMenu paymentMemInfo">
							<h2>강의 리스트</h2>
						</div>

						<!-- 과목별 링크 -->
						<div class="paymentMenu paymentMemInfo" style="height: 300px">
							<ul>
								<li><a
									href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=KOR">국어</a></li>
								<li><a
									href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=MATH">수학</a></li>
								<li><a
									href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=ENG">영어</a></li>
								<li><a
									href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=SOC">사회</a></li>
								<li><a
									href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=SCI">과학</a></li>
							</ul>
						</div>
						<div class="paymentMenu paymentMemInfo" style="height: auto">
							<img src="/public/userProblemCenter.png" />
						</div>
						<div class="paymentMenu paymentMemInfo"
							style="height: 284px; border-bottom: none"></div>
					</div>
					<div>
						<div class="paymentArea">
							<h2 style="margin-bottom: 30px">강의 찾기</h2>


							<h3>강의 목록</h3>
							<div class="navMainBoundary" style="margin-bottom: 20px"></div>
								<div class="paymentList">
									<table class="paymentTable">
										<!-- 정렬 및 필터링 폼 -->
										<form action="${pageContext.request.contextPath}/lecture/common/lectureList.do" method="get">
											<select name="sortBy">
													<option value="price_asc"
														${sortBy == 'price_asc' ? 'selected' : ''}>가격 낮은순</option>
													<option value="price_desc"
														${sortBy == 'price_desc' ? 'selected' : ''}>가격
														높은순</option>
													<option value="date_desc"
														${sortBy == 'date_desc' ? 'selected' : ''}>최신순</option>
													<option value="date_asc"
														${sortBy == 'date_asc' ? 'selected' : ''}>오래된순</option>
												</select> <select name="filterBy">
													<option value="">필터 선택</option>
													<option value="title"
														${filterBy == 'title' ? 'selected' : ''}>강의명 검색</option>
													<option value="teacher"
														${filterBy == 'teacher' ? 'selected' : ''}>선생님별
														검색</option>
												</select> <input type="text" name="filterValue"
													value="${filterValue}" placeholder="필터 값 입력"> <input
													type="submit" value="적용">
											</form>
											
										<c:if test="${not empty sessionScope.user}">
										<form action="${pageContext.request.contextPath}/lecture/user/insertMultCart.do" method="post">
											<thead>
												<tr>
													<th><input type="checkbox" id="selectAll" /></th>
													<th>강의코드</th>
													<th>강좌명</th>
													<th>가격</th>
													<th>등록일</th>
													<th>강사명</th>
													<th>장바구니</th>
												</tr>
											</thead>
											<c:forEach var="lecture" items="${lectures}">
												<tbody>
													<tr>
														<td><input type="checkbox" name="selectedLectures"
															class="lectureCheckbox" value="${lecture.lectureCode}"
															data-price="${lecture.lecturePrice}"></td>
														<td>${lecture.lectureCode}</td>
														<td><a href="${pageContext.request.contextPath}/lecture/common/lectureDetail.do?lectureCode=${lecture.lectureCode}">${lecture.lectureName}</a></td>
														<td><span class="payValue">${lecture.lecturePrice}</span>원</td>
														<td>${lecture.lectureRegdate}</td>
														<td><a
															href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=teacher&filterValue=${lecture.teacherName}">${lecture.teacherName}</a></td>
														<td>
								                            <button type="button" onclick="addToCart('${lecture.lectureCode}')" style="width:100%; height:100%; display:flex; justify-content: center; align-items:center; border:none; background-color:white; cursor:pointer; text-decoration:underline;">장바구니에 담기</button>
								                        </td>
													</tr>
												</tbody>
												</c:forEach>
												<input type="submit" value="선택한 강의 장바구니에 담기">
											</form>
											<div class="totalPrice">
												<span>총 장바구니에 담은 강의 금액 : <span
													id="totalPayValue">0</span>원
												</span>
											</div>
										</c:if>
										<c:if test="${empty sessionScope.user}">
									        <!-- 강의 목록 (비로그인 사용자용) -->
									        <table class="paymentTable">
									            <tr>
									                <th>강의 코드</th>
									                <th>강의명</th>
									                <th>가격</th>
									                <th>등록일</th>
									                <th>선생님</th>
									            </tr>
									            <c:forEach var="lecture" items="${lectures}">
									                <tr>
									                    <td>${lecture.lectureCode}</td>
									                    <td><a href="${pageContext.request.contextPath}/lecture/common/lectureDetail.do?code=${lecture.lectureCode}">${lecture.lectureName}</a></td>
									                    <td>${lecture.lecturePrice}원</td>
									                    <td>${lecture.lectureRegdate}</td>
									                    <td><a href="${pageContext.request.contextPath}/gotoTeacherInfo.do?teacherId=${lecture.teacherId}">${lecture.teacherName}</a></td>
									                </tr>
									            </c:forEach>
									        </table>
									    </c:if>
									</table>
									<!-- 페이징 -->
								    <div>
								        <div style="width:100%; display:flex; justify-content: space-around;">
									        <c:if test="${currentPage > 1}">
									            <a href="${pageContext.request.contextPath}/lecture/common/lectureList.do?page=${currentPage - 1}&sortBy=${sortBy}&filterBy=${filterBy}&filterValue=${filterValue}">이전</a>
									        </c:if>
									        <c:forEach begin="1" end="${totalPages}" var="i">
									            <c:choose>
									                <c:when test="${i == currentPage}">
									                    <strong style="color:#008CD6">[${i}]</strong>
									                </c:when>
									                <c:otherwise>
									                    <a href="${pageContext.request.contextPath}/lecture/common/lectureList.do?page=${i}&sortBy=${sortBy}&filterBy=${filterBy}&filterValue=${filterValue}">[${i}]</a>
									                </c:otherwise>
									            </c:choose>
									        </c:forEach>
								        
								        <c:if test="${currentPage < totalPages}">
								            <a href="${pageContext.request.contextPath}/lecture/common/lectureList.do?page=${currentPage + 1}&sortBy=${sortBy}&filterBy=${filterBy}&filterValue=${filterValue}">다음</a>
								        </c:if>
								        </div>
								    </div>
								</div>
						</div>
					</div>
					<div style="margin-top: 20px">
						<img src="/public/noAreaImage1.png" alt="" />
					</div>
				</div>
			</div>
		</main>
		<!-- 메인 콘텐츠 영역 // -->

		<!-- // 푸터 영역 -->
		<jsp:include page="../commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
	</div>
	<script src="/jsgroup/payment/payment.js"></script>
</body>
</html>
