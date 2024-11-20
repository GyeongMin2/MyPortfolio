<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>관리자 대시보드</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2 class="mb-4">관리자 대시보드</h2>
        
        <!-- 통계 카드 -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">총 상품 수</h5>
                        <p class="card-text display-4">${productCount}</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">총 회원 수</h5>
                        <p class="card-text display-4">${memberCount}</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- 관리 메뉴 -->
        <div class="row mb-4">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">상품 관리</h5>
                        <p class="card-text">상품 목록 조회 및 관리</p>
                        <a href="/admin/product/list" class="btn btn-primary">바로가기</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">회원 관리</h5>
                        <p class="card-text">회원 목록 조회 및 관리</p>
                        <a href="/admin/member/list" class="btn btn-primary">바로가기</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">공지사항 관리</h5>
                        <p class="card-text">공지사항 등록 및 관리</p>
                        <a href="/admin/notice/list" class="btn btn-primary">바로가기</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- 로그아웃 -->
        <div class="text-end">
            <a href="/admin/logout" class="btn btn-secondary">로그아웃</a>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>