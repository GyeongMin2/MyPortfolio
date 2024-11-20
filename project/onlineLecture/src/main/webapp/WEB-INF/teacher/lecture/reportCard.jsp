<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>성적표 관리</title>
    <link rel="stylesheet" href="<c:url value="/stylegroup/main/styles.css" />" />
    <style>
        /* 컨테이너 스타일 */
        .container {
            padding: 20px;
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        
        /* 테이블 스타일 */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f0f0f0;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        
        /* 폼 스타일 */
        .form-group {
            margin: 10px 0;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        /* 버튼 스타일 */
        .action-btn {
            padding: 6px 12px;
            margin: 0 5px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
        }
        .modify-btn {
            background-color: #4CAF50;
            color: white;
        }
        .delete-btn {
            background-color: #f44336;
            color: white;
        }
        .action-btn:hover {
            opacity: 0.8;
        }
        
        /* 추가 폼 영역 */
        .add-form-container {
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 5px;
            border: 1px solid #ddd;
        }
    </style>
</head>
<body>
    <div class="boxContainer">
        <jsp:include page="../../common/commonArea/pageTopImageArea.jsp" />
        <jsp:include page="../../common/commonArea/header.jsp" />
        
        <main class="ccenter">
            <div class="container">
                <h2>성적표 관리</h2>
                
                <!-- 성적표 추가 폼 -->
                <div class="add-form-container">
                    <form action="/teacher/lecture/reportCard.do" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="lectureCode" value="${lectureCode}">
                        <input type="hidden" name="userId" value="${userId}">
                        
                        <div class="form-group">
                            <label for="reportCardName">성적표 이름</label>
                            <input type="text" name="reportCardName" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="score">점수</label>
                            <input type="text" name="score" required>
                        </div>
                        
                        <button type="submit">추가</button>
                    </form>
                </div>
                
                <!-- 성적표 목록 -->
                <table>
                    <thead>
                        <tr>
                            <th>성적표 이름</th>
                            <th>점수</th>
                            <th>등록일</th>
                            <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="report" items="${reportCards}">
                            <tr>
                                <td>${report.reportCardName}</td>
                                <td>${report.score}</td>
                                <td>${report.reportCardRegdate}</td>
                                <td>
                                    <form action="/teacher/lecture/reportCard.do" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="reportCardIdx" value="${report.reportCardIdx}">
                                        <input type="hidden" name="lectureCode" value="${lectureCode}">
                                        <input type="hidden" name="userId" value="${userId}">
                                        <input type="text" name="reportCardName" value="${report.reportCardName}" required>
                                        <input type="text" name="score" value="${report.score}" required>
                                        <button type="submit" class="modify-btn">수정</button>
                                    </form>
                                    <form action="/teacher/lecture/reportCard.do" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="reportCardIdx" value="${report.reportCardIdx}">
                                        <input type="hidden" name="lectureCode" value="${lectureCode}">
                                        <input type="hidden" name="userId" value="${userId}">
                                        <button type="submit" class="delete-btn">삭제</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
        
        <jsp:include page="../../common/commonArea/footer.jsp" />
    </div>
</body>
</html>
ㅎ