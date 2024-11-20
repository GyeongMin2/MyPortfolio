<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>강의 수정</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .container {
            padding: 20px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        input[type="text"], input[type="number"], input[type="date"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #333;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #575757;
        }
    </style>
</head>
<body>
    <!-- 공통 헤더 include -->
    <jsp:include page="../common/header.jsp" />
    
    <div class="container">
        <h1>강의 수정</h1>
        <form action="${pageContext.request.contextPath}/admin/lecture/manage" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="lectureCode" value="${lecture.lectureCode}">
            
            <label for="lectureName">강의명</label>
            <input type="text" id="lectureName" name="lectureName" value="${lecture.lectureName}" required>
            
            <label for="teacherId">선생님 ID</label>
            <input type="text" id="teacherId" name="teacherId" value="${lecture.teacherId}" required>
            
            <button type="submit">수정하기</button>
        </form>
    </div>
</body>
</html>
