<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>My Post and Comment List</title>
</head>
<body>
<h1>내 게시글 목록</h1>

<!-- 게시글 목록 -->
<table border="1">
    <tr>
        <th>게시글 타입</th>
        <th>게시글 제목</th>
        <th>게시글 내용</th>
        <th>작성일</th>
    </tr>
    <c:forEach var="post" items="${postList}">
        <tr>
            <td>${post.postType}</td>
            <td>${post.postTitle}</td>
            <td>${post.postContent}</td>
            <td>${post.postRegdate}</td>
        </tr>
    </c:forEach>
</table>

<!-- 게시글 페이징 -->
<div>
    <c:if test="${postPagination.totalPages > 1}">
        <c:if test="${postPagination.hasFirstPage()}">
            <a href="?postPageNo=1&commentPageNo=${commentPagination.pageNo}"><< </a>
        </c:if>
        <c:if test="${postPagination.hasPreviousBlock()}">
            <a href="?postPageNo=${postPagination.blockStartPage - 1}&commentPageNo=${commentPagination.pageNo}">< </a>
        </c:if>

        <c:forEach var="i" begin="${postPagination.blockStartPage}" end="${postPagination.blockEndPage}">
            <c:choose>
                <c:when test="${i == postPagination.pageNo}">
                    <strong>${i}</strong> 
                </c:when>
                <c:otherwise>
                    <a href="?postPageNo=${i}&commentPageNo=${commentPagination.pageNo}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${postPagination.hasNextBlock()}">
            <a href="?postPageNo=${postPagination.blockEndPage + 1}&commentPageNo=${commentPagination.pageNo}"> ></a>
        </c:if>
        <c:if test="${postPagination.hasLastPage()}">
            <a href="?postPageNo=${postPagination.totalPages}&commentPageNo=${commentPagination.pageNo}"> >></a>
        </c:if>
    </c:if>
</div>

<h1>내 댓글 목록</h1>

<!-- 댓글 목록 -->
<table border="1">
    <tr>
        <th>댓글 유형</th>
        <th>원문글</th>
        <th>내용</th>
        <th>작성일</th>
    </tr>
   <c:forEach var="comment" items="${commentList}">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${comment.boardComment != null}">${comment.board.boardType}</c:when>
                    <c:when test="${comment.qnaComment != null}">${comment.qna.qnaType}</c:when>
                </c:choose>
            </td>
             <td>
                <c:choose>
                    <c:when test="${comment.boardComment != null}"><a href="/gotoPostDetail.do?idx=${comment.board.boardIdx}">${comment.board.boardTitle}</a></c:when>
                    <c:when test="${comment.qnaComment != null}"><a href="/gotoQnaDetail.do?idx=${comment.qna.qnaIdx}">${comment.qna.qnaTitle}</a></c:when>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${comment.boardComment != null}">${comment.boardComment.commentContent}</c:when>
                    <c:when test="${comment.qnaComment != null}">${comment.qnaComment.qnaCommentContent}</c:when>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${comment.boardComment != null}">${comment.boardComment.commentRegdate}</c:when>
                    <c:when test="${comment.qnaComment != null}">${comment.qnaComment.commentRegdate}</c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>

<!-- 댓글 페이징 -->
<div>
    <c:if test="${commentPagination.totalPages > 1}">
        <c:if test="${commentPagination.hasFirstPage()}">
            <a href="?commentPageNo=1&postPageNo=${postPagination.pageNo}"><<</a>
        </c:if>
        <c:if test="${commentPagination.hasPreviousBlock()}">
            <a href="?commentPageNo=${commentPagination.blockStartPage - 1}&postPageNo=${postPagination.pageNo}">< </a>
        </c:if>

        <c:forEach var="i" begin="${commentPagination.blockStartPage}" end="${commentPagination.blockEndPage}">
            <c:choose>
                <c:when test="${i == commentPagination.pageNo}">
                    <strong>${i}</strong> 
                </c:when>
                <c:otherwise>
                    <a href="?commentPageNo=${i}&postPageNo=${postPagination.pageNo}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${commentPagination.hasNextBlock()}">
            <a href="?commentPageNo=${commentPagination.blockEndPage + 1}&postPageNo=${postPagination.pageNo}"> ></a>
        </c:if>
        <c:if test="${commentPagination.hasLastPage()}">
            <a href="?commentPageNo=${commentPagination.totalPages}&postPageNo=${postPagination.pageNo}"> >></a>
        </c:if>
    </c:if>
</div>

</body>
</html>