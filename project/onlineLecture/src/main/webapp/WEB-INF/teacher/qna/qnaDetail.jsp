<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3> qna 상세보기 여기서 댓글  </h3>
<form id="qnaFrm" action="deleteQna.do" method="POST">
	<input type="hidden" name="idx" value="${qnaDTO.qnaIdx}"/>
	<input type="hidden" name="type" value="${qnaDTO.qnaType}"/>
	<h2>${boardDTO.boardTitle}</h2>
	<p><strong>작성자:</strong> ${qnaDTO.qndWriter}</p>
	<p><strong>등록일:</strong> ${qnaDTO.qndRegdate}</p>
	<p><strong>내용:</strong></p>
	<div>${qnaDTO.qnaContent}</div>
	<button type="button" onclick="gotoModify()">수정</button>
	<button onclick="gotoDelete()">삭제</button>
</form>
<c:if test="${not empty commentList}">
	<h3>댓글 목록</h3>
	<ul>
	    <c:forEach var="qnaComment" items="${commentQnaList}">
	        <li>${comment.commentContent} - <strong>${comment.userId}</strong> ${comment.commentRegdate}</li>
	    </c:forEach>
	</ul>
</c:if>
<form id="commentFrm" action="qnaCommentWrite.do" method="POST">
    <input type="hidden" name="qnaIdx" value="${qnaDTO.qnaIdx}"/> 
    <textarea name="commentContent" rows="4" cols="50" placeholder="댓글을 입력하세요" required></textarea><br>
    <button type="submit">댓글작성</button>
</form>

<script>
function gotoModify() {
    location.href = "gotoQnaModify.do?idx=${boardDTO.boardIdx}"; 
}
function gotoDelete() {
    if (confirm("정말 삭제하시겠습니까?")) { 
    	document.getElementById("postFrm").submit();
    }
}
</script>
</body>
</html>