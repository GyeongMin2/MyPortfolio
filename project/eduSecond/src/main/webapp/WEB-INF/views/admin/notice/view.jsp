<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>공지사항 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/adminHeader.jsp" %>
    
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h2>${notice.title}</h2>
            </div>
            <div class="card-body">
                <div class="mb-3">
                    <strong>작성자:</strong> ${notice.adminId}<br>
                    <strong>조회수:</strong> ${notice.viewCount}<br>
                    <strong>등록일:</strong> ${notice.regDate}<br>
                    <strong>수정일:</strong> ${notice.modDate}
                </div>
                <hr>
                <div class="content">
                    ${notice.content}
                </div>
                
                <div class="mt-4">
                    <a href="/admin/notice/list" class="btn btn-secondary">목록으로</a>
                    <a href="/admin/notice/modify?noticeId=${notice.noticeId}" class="btn btn-warning">수정</a>
                    <a href="/admin/notice/delete?noticeId=${notice.noticeId}" 
                       class="btn btn-danger"
                       onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
