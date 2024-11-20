<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 강의 목록</title>
    <link rel="stylesheet" href="/stylegroup/main/styles.css" />
    <style>
        .lecture-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .lecture-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .lecture-table th, .lecture-table td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }
        .lecture-table th {
            background-color: #f5f5f5;
            font-weight: bold;
        }
        .lecture-table tr:hover {
            background-color: #f9f9f9;
        }
        .student-list-btn {
            padding: 6px 12px;
            background-color: #008CD6;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }
        .student-list-btn:hover {
            background-color: #0073b7;
        }
    </style>
</head>
<body>
    <div class="boxContainer">
        <!-- 헤더 포함 -->
        <jsp:include page="../../common/commonArea/header.jsp" />
        
        <main class="lecture-container">
            <h2>내 강의 목록</h2>
            <table class="lecture-table">
                <thead>
                    <tr>
                        <th>강의 코드</th>
                        <th>강의명</th>
                        <th>수강생 관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="lecture" items="${lectures}">
                        <tr>
                            <td>${lecture.lectureCode}</td>
                            <td>${lecture.lectureName}</td>
                            <td>
                                <a href="/teacher/lecture/students.do?lectureCode=${lecture.lectureCode}" 
                                   class="student-list-btn">수강생 목록</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>
        
        <!-- 푸터 포함 -->
        <jsp:include page="../../common/commonArea/footer.jsp" />
    </div>
</body>
</html>
