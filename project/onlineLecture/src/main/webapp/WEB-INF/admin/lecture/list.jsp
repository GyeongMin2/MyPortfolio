<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>강의 목록</title>
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
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <div class="container">
        <h1>강의 목록</h1>
        <div style="margin-bottom: 20px;">
            <a href="${pageContext.request.contextPath}/admin/lecture/manage?action=new" class="btn-add" style="
                padding: 10px 20px;
                background-color: #4CAF50;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                display: inline-block;">
                새 강의 등록
            </a>
        </div>
        <table>
            <thead>
                <tr>
                    <th>강의 코드</th>
                    <th>강의명</th>
                    <th>가격</th>
                    <th>등록일</th>
                    <th>선생님</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="lecture" items="${lectures}">
                    <tr>
                        <td>${lecture.lectureCode}</td>
                        <td>${lecture.lectureName}</td>
                        <td>${lecture.lecturePrice}원</td>
                        <td>${lecture.lectureRegdate}</td>
                        <td>${lecture.teacherName}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/lecture/manage?action=edit&lectureCode=${lecture.lectureCode}">수정</a>
                            <a href="${pageContext.request.contextPath}/admin/lecture/detail/manage?action=list&lectureCode=${lecture.lectureCode}">상세내용</a>
                            <button onclick="return confirm('정말 삭제하시겠습니까?') && deleteLecture('${lecture.lectureCode}')">삭제</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <script>
        function deleteLecture(lectureCode) {
            if(confirm('정말로 이 강의를 삭제하시겠습니까?')) {
                fetch('${pageContext.request.contextPath}/admin/lecture/manage', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'action=delete&lectureCode=' + encodeURIComponent(lectureCode)
                })
                .then(response => response.json())
                .then(result => {
                    if(result.success) {
                        alert('강의가 삭제되었습니다.');
                        location.reload();
                    } else {
                        alert('강의 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('요청 처리 중 오류가 발생했습니다.');
                });
            }
        }
    </script>
</body>
</html>
