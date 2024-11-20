<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>공지사항 관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/adminHeader.jsp" %>
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>공지사항 관리</h2>
            
            <div class="d-flex gap-2">
                <form class="d-flex gap-2" method="get" action="/admin/notice/list">
                    <select name="searchCategory" class="form-select" style="width: 120px;">
                        <option value="title" ${searchCategory == 'title' ? 'selected' : ''}>제목</option>
                        <option value="content" ${searchCategory == 'content' ? 'selected' : ''}>내용</option>
                    </select>
                    <input type="text" name="searchValue" value="${searchValue}" class="form-control" placeholder="검색어 입력">
                    <button type="submit" class="btn btn-dark">검색</button>
                </form>
                <a href="/admin/notice/register" class="btn btn-primary">공지사항 등록</a>
            </div>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <table class="table table-hover">
            <thead class="table-dark">
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>등록일</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${notices}" var="notice">
                    <tr>
                        <td>${notice.noticeId}</td>
                        <td>
                            <a href="/admin/notice/view?noticeId=${notice.noticeId}">
                                ${notice.title}
                            </a>
                        </td>
                        <td>${notice.adminId}</td>
                        <td>${notice.viewCount}</td>
                        <td>${notice.regDate}</td>
                        <td>
                            <a href="/admin/notice/delete?noticeId=${notice.noticeId}" 
                               class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <%@ include file="../../common/paging.jsp" %>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
