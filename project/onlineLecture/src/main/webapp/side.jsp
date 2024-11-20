<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div>
<ul>
<li><a href="gotoPostList.do?type=P">자유게시판</a></li>
<li><a href="gotoPostList.do?type=D">자료실</a> </li>
<li><a href="gotoPostList.do?type=N">공지사항</a> </li>
<li><a href="gotoPostList.do?type=C">강의공지</a>  </li>
<li><a href="gotoPostList.do?type=R">수강후기</a> </li>
<li><a href="gotoQnaList.do?type=G">1:1QNA</a> </li>
<!-- 파라미터로 선생님id -->
<li><a href="gotoQnaList.do?type=T&teacherId=teacher_eng2">선생님의 QNA</a> </li>
<!-- 파라미터로 강의코드받기 -->
<li><a href="gotoQnaList.do?type=T&category=ENG001">강의실의 QNA</a></li>
<li><a href="/myStudyRoom/common/gotoWriteDetail.do">내 댓글& 글</a></li>
</ul>

</div>

</body>
</html>