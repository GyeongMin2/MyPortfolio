<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원 정보 수정</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .navbar {
            background-color: #333;
            overflow: hidden;
        }
        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            font-size: 18px;
        }
        .navbar a:hover {
            background-color: #575757;
        }
        .container {
            padding: 20px;
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-top: 10px;
            font-weight: bold;
        }
        input, select {
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        input[type="submit"], a.button {
            margin-top: 20px;
            padding: 10px;
            background-color: #333;
            color: white;
            border: none;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            border-radius: 4px;
        }
        input[type="submit"]:hover, a.button:hover {
            background-color: #575757;
        }
        .button-group {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="container">
        <h1>회원 정보 수정</h1>
        <form action="${pageContext.request.contextPath}/admin/member/memberModify.do" method="post">
            <input type="hidden" name="userId" value="${member.userId}">
            
            <label for="userName">이름:</label>
            <input type="text" id="userName" name="userName" value="${member.userName}" readonly>
            
            <label for="userNickname">닉네임:</label>
            <input type="text" id="userNickname" name="userNickname" value="${member.userNickname}" required>
            
            <label for="userEmail">이메일:</label>
            <input type="email" id="userEmail" name="userEmail" value="${member.userEmail}" required>
            
            <label for="userPhone">전화번호:</label>
            <input type="tel" id="userPhone" name="userPhone" value="${member.userPhone}" required>
            
            <label for="userType">회원 유형:</label>
            <select id="userType" name="userType">
                <c:forEach var="type" items="${memberTypes}">
                    <option value="${type.userType}" ${member.userType eq type.userType ? 'selected' : ''}>${type.userTypeName}</option>
                </c:forEach>
            </select>
            
            <label for="mileage">마일리지:</label>
            <input type="number" id="mileage" name="mileage" value="${member.mileage}" required>
            
            <div class="button-group">
                <input type="submit" value="수정">
                <a href="${pageContext.request.contextPath}/admin/member/memberList.do" class="button">취소</a>
            </div>
        </form>
    </div>
</body>
</html>
