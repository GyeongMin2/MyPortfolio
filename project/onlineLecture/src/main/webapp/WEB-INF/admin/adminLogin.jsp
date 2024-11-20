<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 로그인</title>
    <link rel="stylesheet" href="<c:url value='/stylegroup/main/styles.css' />">
    <style>
        .login-container {
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .login-form {
            display: flex;
            flex-direction: column;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .submit-btn {
            background-color: #333;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .submit-btn:hover {
                background-color: #555;
        }
        .error-message {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>관리자 로그인</h2>
        <c:if test="${not empty msg}">
            <p class="error-message">${msg}</p>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/member/admin/login.do" method="post" class="login-form">
            <div class="form-group">
                <label for="userId">아이디:</label>
                <input type="text" id="userId" name="userId" required>
            </div>
            <div class="form-group">
                <label for="userPwd">비밀번호:</label>
                <input type="password" id="userPwd" name="userPwd" required>
            </div>
            <button type="submit" class="submit-btn">로그인</button>
        </form>
    </div>
</body>
</html>

