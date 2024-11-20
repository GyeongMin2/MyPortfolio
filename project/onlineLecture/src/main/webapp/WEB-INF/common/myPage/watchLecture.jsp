<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${lecture.lectureName} - 강의 시청</title>
    <link rel="stylesheet" href="<c:url value='/stylegroup/main/styles.css' />" />
    <style>
        .lecture-container {
            width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .lecture-header {
            margin-bottom: 30px;
        }
        .lecture-content {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .video-container {
            margin: 20px 0;
            position: relative;
            padding-bottom: 56.25%;
            height: 0;
            overflow: hidden;
        }
        .video-container iframe {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }
        .back-btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #008CD6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 20px;
        }
        .back-btn:hover {
            background-color: #0073b1;
        }
    </style>
</head>
<body>
    <div class="boxContainer">
        <jsp:include page="../commonArea/pageTopImageArea.jsp" />
        <jsp:include page="../commonArea/header.jsp" />
        
        <main class="ccenter">
            <div class="lecture-container">
                <div class="lecture-header">
                    <h1>${lecture.lectureName}</h1>
                    <p>강사: ${lecture.teacherName}</p>
                </div>
                
                <div class="lecture-content">
                    <div class="video-container">
                        <!-- 실제 비디오 플레이어나 컨텐츠가 들어갈 자리 -->
                        <c:if test="${not empty detail.lectureDetailFilePath}">
						    <div class="video-container">
						        <iframe 
								    width="100%" 
								    height="315" 
								    src="https://www.youtube.com/embed/RqyCiGRvwns" 
								    frameborder="0" 
								    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
								    allowfullscreen>
								</iframe>

						    </div>
						</c:if>
                    </div>
                    
                    <div class="content-description">
                        ${detail.lectureDetailContent}
                    </div>
                    
                    <c:if test="${not empty detail.lectureDetailFileName}">
                    </c:if>
                </div>
                
                <a href="/mypage/common/gotoLectureDetailList.do?lectureCode=${lecture.lectureCode}" 
                   class="back-btn">강의 목록으로 돌아가기</a>
            </div>
        </main>
        
        <jsp:include page="../commonArea/footer.jsp" />
    </div>
</body>
</html>