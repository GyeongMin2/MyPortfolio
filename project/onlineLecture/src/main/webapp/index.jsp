<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chunjaeHaebup</title>
</head>
<body>
	<a href="<c:url value='/main.do' />" >ㅎㅇ</a>
	<a href="<c:url value='/lecture/common/lectureList.do' />" >장바구니</a>
	<a href="<c:url value='/payments/user/gotoPayments.do' />" >결제창</a>
	<a href="<c:url value='/lecture/common/lectureList.do' />" >강의 리스트</a>
	<a href="<c:url value='/payments/user/gotoPayments.do' />" >선생님 상세</a>
	
<%-- 	<a href="<c:url value='/main.do?e=main' />" >ㅎㅇ</a> --%>	
<%@ include file="/side.jsp" %>

</body>
</html>