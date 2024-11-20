<%@page import="java.util.Date"%>
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
<%
Date now = new Date();
%>
<!-- // link태그 -->
<link rel="stylesheet" href="/stylegroup/main/styles.css?now=<%=now.getTime()%>" />
<link rel="icon" href="/public/channels4_profile.jpg" type="image/png" />
<!-- link 태그 // -->
</head>

<style>
/* 사이드 바 시작 */

.sidebar {
  position: fixed;
  bottom: 0px;
  left: 50px;
  width: 60px;
  height: 350px;
  background-color: #008cd6;
  color: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-top-left-radius: 40px;
  border-top-right-radius: 40px;
  transform: translateY(100%);
  transition: transform 0.3s ease, background-color 0.3s ease, opacity 0.5s ease;
  opacity: 0;
  overflow: hidden; /* 이미지가 영역에 맞게 */
  z-index:90;
}

.sidebar.visible {
  transform: translateY(0);
  background-color: #008cd6;
  opacity: 1;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.sidebar ul li {
  margin: 10px 0;
}

.sidebar ul li a {
  color: #fff;
  text-decoration: none;
  font-size: 14px;
}

.sidebar ul li a:hover {
  color: #ddd;
}

.toggleButton {
  position: fixed;
  bottom: 0px;
  left: 50px;
  width: 60px;
  height: 60px;
  background-color: #008cd6;
  color: white;
  border: none;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: background-color 0.3s ease;
  overflow: hidden;
  z-index: 500;
}

.toggleButton:hover {
  background-color: #008acc;
}

.hiddenToggleBottom {
  position: fixed;
  bottom: 0px;
  left: 44px;
  width: 66px;
  height: 80px;
  overflow: hidden;
  background-color: white;
  z-index: 15;
}

li {
	text-align:center;
	color:white;
}

li a {
	font-size:12px;
}

/* 사이드 바 끝 */
</style>

<body>
	<c:choose>
		<c:when test="${not empty sessionScope.user}">
	        <div class="sideBarContainer">
	            <div class="sidebar visible">
	                <ul>
	                <c:choose>
						<c:when test="${sessionScope.user.userType != 'T'}">
	                    <li><a href="/mypage/common/gotoMyLecture.do"><img src="/public/myLecture.png" width="70%" /></a></li>
	                    <li><a href="/payments/user/gotoPayments.do"><img src="/public/cart.png" width="70%" /></a></li>
	                    <li><a href="<c:url value='/myStudyRoom/common/gotoWriteDetail.do' />"><img src="/public/myPage.png" width="70%" /></a></li>
	                    <li><a href="/common/login/logout.do"><img src="/public/logout.png" width="70%" /></a></li>
	                    <li></li>
	                    </c:when>
	                    <c:otherwise>
	                    <li><a href="/teacherMyLectureList.do"><img src="/public/myLecture.png" width="70%" /></a></li>
						<li><a href="<c:url value='/myStudyRoom/common/gotoWriteDetail.do' />"><img src="/public/myPage.png" width="70%" /></a></li>
						<li><a href="/common/login/logout.do"><img src="/public/logout.png" width="70%" /></a></li>
						<li></li>
	                    </c:otherwise>
	                </c:choose>
	                </ul>
	            </div>
	            <button class="toggleButton">메뉴</button>
	        </div>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
	<script>
	document.addEventListener("DOMContentLoaded", () => {
		  const sidebar = document.querySelector(".sidebar");
		  const toggleButton = document.querySelector(".toggleButton");
	
		  toggleButton.addEventListener("click", () => {
		    sidebar.classList.toggle("visible");
		  });
		});
	</script>
</body>
</html>
