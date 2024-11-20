<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>스마트 해법</title>
    <link rel="stylesheet" href="<c:url value="/stylegroup/main/styles.css" />" />
    <style>
        .lecture-list {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: white;
        }
        .lecture-list th, .lecture-list td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        .lecture-list th {
            background-color: #f0f0f0;
            font-weight: bold;
        }
        .lecture-list tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .lecture-list tr:hover {
            background-color: #f5f5f5;
        }
        .no-lectures {
            text-align: center;
            padding: 20px;
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin: 20px 0;
        }
        .lecture-title {
            font-size: 24px;
            margin: 20px 0;
            color: #333;
            text-align:center;
        }
        .start-lecture-btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #008CD6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .start-lecture-btn:hover {
            background-color: #0073b1;
        }
        .lecture-name-link {
            color: #008CD6;
            text-decoration: none;
            font-weight: bold;
        }
        .lecture-name-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="boxContainer">
        <jsp:include page="../commonArea/pageTopImageArea.jsp" />
 
        <jsp:include page="../commonArea/header.jsp" />

        <main>
            <h1 class="lecture-title">내 강의 목록</h1>
            <div class="ccenter">
            <div style="width:1500px">
            <c:if test="${not empty lectureList}">
                <table class="lecture-list">
                    <thead>
                        <tr>
                            <th>강의 코드</th>
                            <th>강의명</th>
                            <th>선생님</th>
                            <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="lecture" items="${lectureList}">
                            <tr>
                                <td>${lecture.lectureCode}</td>
                                <td>
                                    <a href="/mypage/common/gotoLectureDetailList.do?lectureCode=${lecture.lectureCode}" 
                                       class="lecture-name-link">${lecture.lectureName}</a>
                                </td>
                                <td>${lecture.teacherName}</td>
                                <td>
                                    <a href="/mypage/common/gotoLectureDetailList.do?lectureCode=${lecture.lectureCode}" 
                                       class="start-lecture-btn">강의 시작</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty lectureList}">
                <div class="no-lectures">
                    <p>등록된 강의가 없습니다.</p>
                </div>
            </c:if>
            </div>
            </div>
        </main>


        <jsp:include page="../commonArea/footer.jsp" />
    </div>
</body>
</html>
