<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            background-color: #f9f9f9;
            color: #333;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .privacy-policy {
            max-width: 1500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        .mainpage {
            display: flex;
            flex-grow: 1;
            min-height: calc(100vh); /* header와 footer를 제외한 높이 설정 */
        }

        .sidebar {
            background-color: #444444;
            padding: 15px;
            color: #fff;
            height: 100%;
        }

        .sidebar ul {
            list-style: none;
            padding: 0;
        }

        .sidebar ul li {
            margin-bottom: 10px;
        }

        .sidebar ul li a {
            color: #fff;
            text-decoration: none;
        }

        .sidebar ul li a:hover {
            text-decoration: underline;
        }

        .content {
            padding: 20px;
            flex-grow: 1;
        }
        .no-image, .image-container {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 100px;
            height: 100px;
            text-align: center;
        }

        .no-image {
            font-weight: bold;
        }

        .search-bar {
            margin: 0 auto;
            width: 100%;
            max-width: 1200px;
            margin-bottom: 4%;
            display: flex;
            justify-content: flex-end;
        }
        td, th, tr {
            vertical-align: middle;
            text-align: center;
        }
        span > a{
            text-decoration-line: none;
            color: black;
            font-weight: bolder;
        }
        td > a{
            text-decoration-line: none;
            color: black;
        }


    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../main/header.jsp"%>
<div class="mainpage">
    <div class="sidebar col-2">
        <ul class="list-unstyled">
            <li><a href="/es/mypage/myInfo">내 정보</a></li>
            <li><a href="/es/mypage/wishList">찜 목록</a></li>
            <li><a href="/es/mypage/orderList">거래 내역</a></li>
            <li><a href="/es/mypage/productList">내 상품</a></li>
            <li><a href="/message/list">셀파톡</a></li>
        </ul>
    </div>
    <div class="content col-10">
        <span><a href="/es/mypage/productList">판매중 상품</a></span> &nbsp; &nbsp; <span><a href="/es/mypage/productList_1">판매 완료 상품</a></span>
        <div class="search-bar">
            <form class="d-flex" role="search" action="/es/mypage/productList_1" method="GET">
                <select name="searchCategory" class="form-select" style="width: 120px;">
                    <option value="productName" ${searchCategory == 'productName' ? 'selected' : ''}>상품명</option>
                    <option value="sellerId" ${searchCategory == 'sellerId' ? 'selected' : ''}>판매자</option>
                </select>
                <input class="form-control me-2" type="search" name="searchValue" value="${searchValue}" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">상품명</th>
                    <th scope="col">가격</th>
                    <th scope="col">상태</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty soldOutList}">
                    <c:forEach var="dto" items="${soldOutList}" >
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty dto.thumbnail}">
                                        <div class="image-container">
                                            <img src="${dto.thumbnail.imagePath}"
                                                 alt="상품 썸네일"
                                                 style="max-width: 100%; max-height: 100%; object-fit: cover;">
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-image">준비된 이미지가 없습니다.</div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${dto.productName}</td>
                            <td>${dto.price}원</td>
                            <td>${dto.productStatus}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty soldOutList}">
                    <tr>
                        <td colspan="4">존재하는 상품이 없습니다.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        <%@ include file="../common/paging.jsp"%>
    </div>
</div>
<%@include file="../main/footer.jsp"%>
</body>
</html>
