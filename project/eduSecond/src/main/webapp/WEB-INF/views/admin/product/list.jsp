<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>상품 관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../common/adminHeader.jsp" %>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>상품 관리</h2>

        <!-- 검색 폼 -->
        <form class="d-flex gap-2" method="get" action="/admin/product/list">
            <select name="searchCategory" class="form-select" style="width: 120px;">
                <option value="productName" ${searchCategory == 'productName' ? 'selected' : ''}>상품명</option>
                <option value="sellerId" ${searchCategory == 'sellerId' ? 'selected' : ''}>판매자</option>
            </select>
            <input type="text" name="searchValue" value="${searchValue}" class="form-control" placeholder="검색어 입력">
            <button type="submit" class="btn btn-dark">검색</button>
        </form>
    </div>

    <!-- 상품 목록 테이블 -->
    <table class="table table-hover">
        <thead class="table-dark">
        <tr>
            <th>상품ID</th>
            <th>썸네일</th>
            <th>상품명</th>
            <th>판매자</th>
            <th>가격</th>
            <th>조회수</th>
            <th>등록일</th>
            <th>관리</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.productId}</td>
                <td>
                    <c:if test="${not empty product.thumbnail}">
                        <img src="${product.thumbnail.imagePath}"
                             alt="상품 썸네일"
                             style="width: 50px; height: 50px; object-fit: cover;">
                    </c:if>
                </td>
                <td>
                    <a href="/admin/product/view?productId=${product.productId}">
                            ${product.productName}
                    </a>
                </td>
                <td>${product.sellerId}</td>
                <td>${product.price}</td>
                <td>${product.viewCount}</td>
                <td>${product.regDate}</td>
                <td>
                    <a href="/admin/product/delete?productId=${product.productId}"
                       class="btn btn-sm btn-outline-danger"
                       onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 -->
    <%@ include file="../../common/paging.jsp" %>
</div>
</body>
</html>