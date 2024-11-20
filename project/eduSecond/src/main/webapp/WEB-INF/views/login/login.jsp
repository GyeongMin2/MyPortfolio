<%--
  Created by IntelliJ IDEA.
  User: full5-4
  Date: 2024-11-12
  Time: 오전 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>로그인</title>
  <style>

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: Arial, sans-serif;
      line-height: 1.6;
      background-color: #f9f9f9;
      color: #333;
    }

    .privacy-policy {
      max-width: 800px;
      height: 600px;
      margin: 50px auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }


    h1 {
      text-align: center;
      font-size: 24px;
      margin-bottom: 20px;
    }

    label {
      font-weight: bold;
      margin-top: 10px;
    }

    input[type="text"], input[type="password"] {
      width: 100%;
      padding: 10px;
      margin: 5px 0 10px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    button {
      width: 100%;
      padding: 10px;
      background-color: #333;
      color: #fff;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      cursor: pointer;
      margin-top: 30px;
    }

    button:hover {
      background-color: #555;
    }

    .error-message {
      color: red;
      font-size: 12px;
      display: none;
    }
  </style>
</head>
<body>
<%@include file="../main/header.jsp"%>
<c:if test="${not empty errorMessage}">
  <script>
    alert("${errorMessage}");
  </script>
</c:if>
<div class="privacy-policy">
  <h1>로그인</h1>
  <form method="post" id="registrationForm" onsubmit="return validateForm()">
    <label for="userId">아이디</label>
    <input type="text" id="userId" name="userId" placeholder="아이디를 입력해주세요.">
    <p class="error-message" id="userIdError">아이디를 입력해주세요.</p>

    <label for="password">비밀번호</label>
    <input type="password" id="password" name="password" placeholder="비밀번호를 입력해주세요.">
    <p class="error-message" id="passwordError">비밀번호를 올바르게 입력해주세요.</p>

    <button type="submit">로그인</button>
  </form>

</div>
<%@include file="../main/footer.jsp"%>
<script>
  function validateForm() {
    let isValid = true;

    const userId = document.getElementById('userId').value.trim();
    const password = document.getElementById('password').value;

    // Hide all error messages
    document.querySelectorAll('.error-message').forEach((el) => el.style.display = 'none');

    // Validate User ID
    if (!userId) {
      document.getElementById('userIdError').style.display = 'block';
      isValid = false;
    }

    // Validate Password
    const passwordPattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,20}$/;
    if (!passwordPattern.test(password)) {
      document.getElementById('passwordError').style.display = 'block';
      isValid = false;
    }
    return isValid;
  }
</script>
</body>
</html>
