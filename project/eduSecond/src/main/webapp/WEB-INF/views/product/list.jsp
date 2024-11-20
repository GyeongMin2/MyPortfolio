<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }
        .container {
            display: flex;
            flex-direction: column;
            gap: 20px;
            max-width: 1200px;
            margin: auto;
        }
        .search-bar {
            margin: 0 auto;
            width: 100%;
            max-width: 1200px;
            margin-top: 4%;
            display: flex;
            justify-content: flex-end;
        }
        .main {
            display: grid;
            grid-template-columns: repeat(5, 1fr); /* 한 줄에 5개의 칸을 만듦 */
            gap: 20px; /* 카드 간 간격 설정 */
            padding: 20px;
        }
        .card {
            width: 100%;
            height: 350px; /* 카드의 높이를 일정하게 설정 */
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
            background-color: #fff;
        }
        .card-body {
            padding: 15px;
        }
        .card img {
            width: 100%;
            height: 150px;
            object-fit: cover;
        }
        .pagination-container {
            text-align: center;
            padding: 20px;
        }
        p{
            line-height: 0.8;
        }
    </style>
</head>
<body>
<%@include file="../main/header.jsp" %>
<div class="container">
    <!-- 검색 영역 -->
    <div class="search-bar">
        <form class="d-flex" role="search" action="/product/list" method="GET">
            <select name="searchCategory" class="form-select" style="width: 120px;">
                <option value="productName" ${searchCategory == 'productName' ? 'selected' : ''}>상품명</option>
                <option value="sellerId" ${searchCategory == 'sellerId' ? 'selected' : ''}>판매자</option>
            </select>
            <input class="form-control me-2" type="search" name="searchValue" value="${searchValue}" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
    </div>
    <!-- 메인 상품 목록 영역 -->
    <c:if test="${not empty message}">
        ${message}
    </c:if>
    <div class="main">
        <c:if test="${not empty pList}">
            <c:forEach var="dto" items="${pList}" varStatus="loop" begin="0" end="9">
                <a href="/product/view?productId=${dto.productId}" style="text-decoration: none; color: inherit;">
                    <div class="card">
                        <c:if test="${not empty dto.thumbnail}">
                            <img src="${dto.thumbnail.imagePath}"
                                 alt="상품 썸네일"
                                 class="card-img-top"
                                 style="height: 150px; object-fit: cover;">
                        </c:if>
                        <div class="card-body">
                            <p class="card-text">${dto.productName}</p>
                            <p class="card-text">가격: ${dto.price}원</p>
                            <p class="card-text">판매상태: <c:if test="${dto.productStatus.equals('AVAILABLE')}">판매중</c:if></p>
                            <p class="card-text">조회수: ${dto.viewCount}</p>
                            <p class="card-text">등록일: ${dto.formatRegDate}</p>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </c:if>
            <button type="button" class="btn btn-primary" onclick="javascript:location.href='/product/regist'">작성하기</button>

    </div>
    <!-- 페이징 영역 -->
    <div class="pagination-container">
        <%@ include file="../common/paging.jsp"%>
    </div>
</div>
<%@include file="../main/footer.jsp" %>
</body>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</html>

