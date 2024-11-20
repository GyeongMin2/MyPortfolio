<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>자료실 게시글 작성</title>
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
            margin-bottom: 20px;
        }
        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            font-size: 16px;
        }
        .navbar a:hover {
            background-color: #555;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], textarea, input[type="file"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 200px;
            resize: vertical;
        }
        .file-input {
            margin-top: 10px;
        }
        .button-group {
            text-align: center;
            margin-top: 20px;
        }
        .submit-btn {
            background-color: #333;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .submit-btn:hover {
            background-color: #555;
        }
        .cancel-btn {
            background-color: #666;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-left: 10px;
        }
        .cancel-btn:hover {
            background-color: #888;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />

    <div class="container">
        <h1>자료실 게시글 작성</h1>
        <form action="postWrite.do" enctype="multipart/form-data" method="post">
            <input type="hidden" name="type" value="${boardType}"/>
            <input type="hidden" name="boardWriter" value="${sessionScope.user.userId}">
            
            <div class="form-group">
                <label for="boardTitle">제목</label>
                <input type="text" id="boardTitle" name="boardTitle" required>
            </div>
            
            <div class="form-group">
                <label for="boardContent">내용</label>
                <textarea id="boardContent" name="boardContent" required></textarea>
            </div>
            
            <div class="file-input">
                <label for="file">첨부파일</label>
                <input type="file" id="file" name="file" multiple>
            </div>
            
            <div class="button-group">
                <button type="submit" class="submit-btn">등록</button>
                <button type="button" class="cancel-btn" onclick="history.back()">취소</button>
            </div>
        </form>
    </div>
</body>
</html>
