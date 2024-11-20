<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>새 강의 등록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <h1>새 강의 등록</h1>
        <form action="${pageContext.request.contextPath}/admin/lecture/manage" method="post">
            <input type="hidden" name="action" value="insert">
            
            <div class="form-group">
                <label for="lectureCode">강의 코드:</label>
                <input type="text" id="lectureCode" name="lectureCode" required>
            </div>
            
            <div class="form-group">
                <label for="lectureName">강의명:</label>
                <input type="text" id="lectureName" name="lectureName" required>
            </div>
            
            <div class="form-group">
                <label for="lecturePrice">가격:</label>
                <input type="number" id="lecturePrice" name="lecturePrice" required>
            </div>
            
            <div class="form-group">
                <label for="lectureLimitDate">수강 기간:</label>
                <input type="text" id="lectureLimitDate" name="lectureLimitDate" placeholder="YYYY-MM-DD" required>
            </div>
            
            <div class="form-group">
                <label for="teacherId">강사 ID:</label>
                <input type="text" id="teacherId" name="teacherId" required>
            </div>
            
            <div class="form-group">
                <button type="submit">강의 등록</button>
                <a href="${pageContext.request.contextPath}/admin/lecture/manage?action=list" style="
                    margin-left: 10px;
                    text-decoration: none;
                    color: #666;
                ">취소</a>
            </div>
        </form>
    </div>
</body>
</html> 