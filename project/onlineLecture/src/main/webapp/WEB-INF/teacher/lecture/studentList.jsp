<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>수강생 목록</title>
    <link rel="stylesheet" href="/stylegroup/main/styles.css" />
    <style>
        .student-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: white;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f0f0f0;
        }
        .back-btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #666;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .back-btn:hover {
            background-color: #444;
        }
    </style>
</head>
<body>
    <div class="boxContainer">
        <jsp:include page="../../common/commonArea/header.jsp" />
        
        <main class="student-container">
            <a href="/teacherMyLectureList.do" class="back-btn">← 강의 목록으로</a>
            <h2>수강생 목록</h2>
            <table>
                <thead>
                    <tr>
                        <th>학생 ID</th>
                        <th>이름</th>
                        <th>닉네임</th>
                        <th>이메일</th>
                        <th>결제일</th>
                        <th>수강 시작일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="student" items="${students}">
                        <tr>
                            <td>${student.userId}</td>
                            <td>${student.userName}</td>
                            <td>${student.userNickname}</td>
                            <td>${student.userEmail}</td>
                            <td>${student.paymentDate}</td>
                            <td>${student.lectureStartDate}</td>
                            <td>
                                <a href="/teacher/lecture/reportCard.do?lectureCode=${student.lectureCode}&userId=${student.userId}">
                                    성적표 관리
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
        
        <jsp:include page="../../common/commonArea/footer.jsp" />
    </div>
</body>
</html>
