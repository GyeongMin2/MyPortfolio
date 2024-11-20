<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>강의 상세 내용</title>
    <link rel="stylesheet" href="<c:url value="/stylegroup/main/styles.css" />" />
    <style>
        .lecture-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .lecture-header {
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 2px solid #008CD6;
        }
        .lecture-title {
            font-size: 28px;
            color: #333;
            margin-bottom: 10px;
        }
        .lecture-info {
            color: #666;
            font-size: 14px;
        }
        .detail-list {
            margin-top: 20px;
        }
        .detail-item {
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .detail-content {
            font-size: 16px;
            line-height: 1.6;
            margin-bottom: 15px;
        }
        .file-info {
            background: #f5f5f5;
            padding: 10px;
            border-radius: 4px;
            font-size: 14px;
            color: #666;
        }
        .file-link {
            color: #008CD6;
            text-decoration: none;
        }
        .file-link:hover {
            text-decoration: underline;
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
        <jsp:include page="../commonArea/pageTopImageArea.jsp" />
        <jsp:include page="../commonArea/header.jsp" />
        
        <main class="ccenter">
            <div class="lecture-container">
                <c:forEach var="lecture" items="${lectureDTOs}">
                    <div class="lecture-header">
                        <h1 class="lecture-title">${lecture.lectureName}</h1>
                        <div class="lecture-info">
                            <span>강사: ${lecture.teacherName}</span> |
                            <span>수강 기한: ${lecture.lectureLimitDate}까지</span>
                        </div>
                    </div>
                    <div>
                    	<a href="/student/lecture/viewReportCard.do?lectureCode=${lecture.lectureCode}">성적표 보기</a>
	                    <a href="/gotoPostList.do?type=D&category=${lecture.lectureCode}">강의실의 자료실</a>
						<a href="/gotoQnaList.do?type=T&category=${lecture.lectureCode}">강의실의 qna</a>
                    </div>
                    <div class="detail-list">
                        <c:forEach var="detail" items="${lecture.lectureDetails}">
                            <div class="detail-item">
                                <div class="detail-content">${detail.lectureDetailContent}</div>
                                <c:if test="${not empty detail.lectureDetailFileName}">
                                    <div class="file-info">
                                        <span>첨부파일:</span>
                                        <a href="${detail.lectureDetailFilePath}" class="file-link">
                                            ${detail.lectureDetailFileName}
                                        </a>
                                        <span>(${detail.lectureDetailFileSize} bytes)</span>
                                    </div>
                                </c:if>
                                <a href="/lecture/common/watchLecture.do?lectureCode=${lecture.lectureCode}&detailIdx=${detail.lectureDetailIdx}" 
                                   class="watch-btn">강의 시청하기</a>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
                
                <a href="/mypage/common/gotoMyLecture.do" class="back-btn">목록으로 돌아가기</a>
            </div>
        </main>
        
        <jsp:include page="../commonArea/footer.jsp" />
    </div>
</body>
</html>
