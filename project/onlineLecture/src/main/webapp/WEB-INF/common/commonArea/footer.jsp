<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/stylegroup/main/styles.css" />

<meta charset="UTF-8">
<title>footer</title>
</head>
<body>
	<footer class="footerContainer">
		<div class="footerContent">
			<img src="/public/logo.png" alt="로고" id="footerLogo" />
			<div>
				<p class="footerLink">
					<a href="#" style="padding-left: 0px">회사소개</a> | <a href="#">이용약관</a>
					| <a href="#">개인정보처리방침</a> | <a href="#">스마트해법수학 학습관</a>
				</p>
				<p>(주)스마트 해법 대표자 : 한덕용, 조수진, 강경민 사업자등록번호 : 119-81-78787 부가통신사업
					신고 번호 : 016590</p>
				<p>통신판매신고서 : 제2016-서울금천-1306호 주소 : 서울 금천구 가산로9길 23 11층 (가산동)
					대표번호 : 010-8904-4623</p>
				<p style="color: rgb(153, 153, 153)">Copyright © CHUNJAETEXTBOOK
					Corp. All Rights Reserved</p>
			</div>
			<img src="/public/footerRight.png" alt="가맹문의" />
		</div>
		<div>
		<jsp:include page="../commonArea/sideBar.jsp" />
		</div>
	</footer>
</body>
</html>