<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/stylegroup/main/styles.css" />
<link rel="stylesheet" href="/stylegroup/main/loginStyle.css" />
<meta charset="UTF-8">
<title>chunjaeHaebup</title>
</head>
<body>
<%
Date now = new Date();
%>
    <!-- // navbar 영역 -->
    <div class="center">
        <div class="navTop">
            <div class="navTopContent">
                <a href="https://www.chunjae.co.kr/#/main" target="_blank">천재교육</a><span>|</span>
                <a href="https://www.milkt.co.kr/HME/sellpa" target="_blank">T셀파</a>
                <span>|</span> <a href="https://mall.chunjae.co.kr/#/main/main"
                    target="_blank">천재교육 쇼핑몰</a> <span>|</span> <a
                    href="https://www.genia.academy/" target="_blank">천재IT교육센터</a>
            </div>
            <div class="navTopContent">
                <span class="dDay">2025학년도 수능 <strong id="dDayCount">수능날짜가
                        동적으로 바뀜</strong></span> <span>|</span> <a href="#">협업기업</a><span>|</span> <a
                    href="/gotoQnaList.do?type=G
            ">고객센터</a>
			</div>
		</div>
	</div>
	<div class="navMainBoundary"></div>
	<div class="menuArea">
		<div class="center">
			<div>
				<nav class="navbarContainer">
					<div class="navbarMain">
						<div>
							<a href="/main.do">
								<img src="/public/logo.png" alt="logo" class="logoImg" />
							</a>
						</div>
						<div style="width: 200px"></div>
						<ul class="navbarGroup">
							<li>브랜드 소개</li>
							<li>강의 목록</li>
							<li>이벤트</li>
							<li>게시판</li>
							<li>선생님</li>
							<li>자료실</li>
						</ul>
						<div class="submenu">
							<ul>
								<li class="submenuContentGroup1"><a href="https://www.smarthb-math.co.kr/about/about.php">브랜드 소개</a><a
									href="https://github.com/TheLastOnlineLecture/onlineLecture">개발 Github</a></li>
								<li class="submenuContentGroup2">
									<a href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=KOR">국어</a>
									<a href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=MATH">수학</a>
									<a href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=ENG">영어</a>
									<a href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=SOC">사회</a>
									<a href="${pageContext.request.contextPath}/lecture/common/lectureList.do?filterBy=subject&filterValue=SCI">과학</a>
								</li>
								<li class="submenuContentGroup3"><a href="https://high.milkt.co.kr/DalJa/Attend/frm_Attend_Event.aspx?menucode=110900">출석체크 이벤트</a> <a
									href="https://high.milkt.co.kr/DalJa/Tchallenge/frm_Tchallenge_Default.aspx?MenuCode=112000">스마트 천재 챌린지</a> <a href="https://high.milkt.co.kr/DalJa/TPlay/frm_TPlay.aspx?MenuCode=110211">T-Play 이벤트</a></li>
								<li class="submenuContentGroup4"><a
									href="/gotoPostList.do?type=P">자유게시판</a>
									<a href="/gotoPostList.do?type=N">공지사항</a>
								</li>
								<li class="submenuContentGroup5"><a href="/teacherList.do?subject=kor">국어</a> <a
									href="/teacherList.do?subject=math">수학</a> <a href="/teacherList.do?subject=eng">영어</a><a
									href="/teacherList.do?subject=soc">사회</a> <a href="/teacherList.do?subject=sci">과학</a></li>
								<li class="submenuContentGroup6">
									<!-- 근데 여기가 사이트 자료실인가 선생님 자료실인가? --> <a href="/gotoPostList.do?type=D">사이트 자료실</a>
								</li>
							</ul>
						</div>
						<div class="userButton">
							<button class="loginBtn">
								<img src="/public/loginBtn.png" alt="" />
							</button>
							<c:choose>
								<c:when test="${not empty sessionScope.user}">
									<div class="dropdown-content">
										<div class="loginArea">
											<p>${sessionScope.user.userNickname}님</p>
										</div>
										<span id="changeLogin"></span>
										<c:choose>
											<c:when test="${sessionScope.user.userType == 'T'}">
												<a href="/teacherMyLectureList.do">내 강의실</a>
												<a href="<c:url value='/myStudyRoom/common/gotoWriteDetail.do' />">마이페이지</a>
												<a href="/common/login/logout.do">로그아웃</a>
											</c:when>
											<c:otherwise>
												<a href="/mypage/common/gotoMyLecture.do">나의 강의실</a>
												<a href="/payments/user/gotoPayments.do">장바구니 목록</a>
												<a href="<c:url value='/myStudyRoom/common/gotoWriteDetail.do' />">마이페이지</a>
												<a href="/common/login/logout.do">로그아웃</a>
											</c:otherwise>
										</c:choose>
									</div>
								</c:when>
								<c:otherwise>
									<div class="dropdown-content">
										<div class="loginArea">
											<button class="loginPopupButton">Login</button>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</nav>
			</div>
		</div>
	</div>
	<div class="loginContainer"></div>
	<div class="navMainBoundary"></div>
	<script src="/jsgroup/main/script.js?now=<%=now.getTime()%>"></script>
	<!-- navbar 영역 // -->

</body>
</html>
