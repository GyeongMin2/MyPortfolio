<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 성적표</title>
    <link rel="stylesheet" href="<c:url value="/stylegroup/main/styles.css" />" />
    <style>
        .container {
            padding: 20px;
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        
        .lecture-info {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #f5f5f5;
            border-radius: 5px;
        }
        
        .lecture-name {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }
        
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
        
        .back-btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #666;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 20px;
        }
        
        .back-btn:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <div class="boxContainer">
        <jsp:include page="../../common/commonArea/pageTopImageArea.jsp" />
        <jsp:include page="../../common/commonArea/header.jsp" />
        
        <main class="ccenter">
            <div class="container">
                <div class="lecture-info">
                    <h2 class="lecture-name">${lectureName}</h2>
                </div>
                
                <table>
                    <thead>
                        <tr>
                            <th>평가 항목</th>
                            <th>점수</th>
                            <th>등록일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="report" items="${reportCards}">
                            <tr>
                                <td>${report.reportCardName}</td>
                                <td>${report.score}</td>
                                <td>${report.reportCardRegdate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <a href="/mypage/common/gotoMyLecture.do" class="back-btn">목록으로 돌아가기</a>
            </div>
        </main>
        
        <jsp:include page="../../common/commonArea/footer.jsp" />
    </div>
</body>
</html>
