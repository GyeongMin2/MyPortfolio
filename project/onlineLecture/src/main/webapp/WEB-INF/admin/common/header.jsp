<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    /* 네비게이션 바 스타일 */
    .navbar {
        background-color: #333;
        overflow: hidden;
        margin-bottom: 20px;
    }
    .navbar a {
        float: left;
        display: block;
        color: white;
        text-align: center;
        padding: 14px 20px;
        text-decoration: none;
        font-size: 16px;
    }
    .navbar a:hover {
        background-color: #555;
    }
    /* 컨테이너 스타일 */
    .container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
        background-color: white;
    }
</style>

<!-- 네비게이션 바 -->
<div class="navbar">
    <a href="${pageContext.request.contextPath}/admin/member/memberList.do">회원 관리</a>
    <a href="${pageContext.request.contextPath}/admin/lecture/manage?action=list">강의 관리</a>
    <a href="${pageContext.request.contextPath}/admin/gotoTotalPostList.do">게시판 관리</a>
</div> 