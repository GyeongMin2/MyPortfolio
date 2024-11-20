<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .admin-navbar {
            background-color: #212529;
            padding: 1rem 0;
        }
        
        .admin-navbar .navbar-brand {
            color: #fff;
            font-weight: bold;
            font-size: 1.5rem;
        }
        
        .admin-navbar .nav-link {
            color: rgba(255,255,255,.75);
            margin: 0 1rem;
            transition: color 0.3s ease;
        }
        
        .admin-navbar .nav-link:hover,
        .admin-navbar .nav-link.active {
            color: #fff;
        }
        
        .admin-navbar .navbar-text {
            color: rgba(255,255,255,.75);
        }
        
        .admin-info {
            background-color: #f8f9fa;
            padding: 0.5rem 0;
            border-bottom: 1px solid #dee2e6;
        }
    </style>
</head>
<body>
    <nav class="admin-navbar navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="/admin/dashboard">당신마켓 관리자</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#adminNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="adminNavbar">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/dashboard' ? 'active' : ''}" 
                           href="/admin/dashboard">대시보드</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${requestScope['javax.servlet.forward.servlet_path'].startsWith('/admin/product') ? 'active' : ''}" 
                           href="/admin/product/list">상품관리</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${requestScope['javax.servlet.forward.servlet_path'].startsWith('/admin/member/list') ? 'active' : ''}" 
                           href="/admin/member/list">회원관리</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${requestScope['javax.servlet.forward.servlet_path'].startsWith('/admin/notice') ? 'active' : ''}" 
                           href="/admin/notice/list">공지사항</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${requestScope['javax.servlet.forward.servlet_path'].startsWith('/admin/member/withdrawal') ? 'active' : ''}" 
                           href="/admin/member/withdrawal/list">탈퇴관리</a>
                    </li>
                </ul>
                
                <div class="d-flex align-items-center">
                    <span class="navbar-text me-3">${sessionScope.adminId}님</span>
                    <a href="/admin/logout" class="btn btn-outline-light btn-sm">로그아웃</a>
                </div>
            </div>
        </div>
    </nav>
    
    <div class="admin-info">
        <div class="container">
            <small>현재 페이지: 
                <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/admin/dashboard'}">대시보드</c:when>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'].startsWith('/admin/product')}">상품관리</c:when>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'].startsWith('/admin/member/list')}">회원관리</c:when>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'].startsWith('/admin/notice')}">공지사항</c:when>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'].startsWith('/admin/member/withdrawal')}">탈퇴관리</c:when>
                </c:choose>
            </small>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
