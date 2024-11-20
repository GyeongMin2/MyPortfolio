<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>툴</title>
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

    .mainpage {
      display: flex;
      flex-grow: 1;
      min-height: calc(100vh);
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
      overflow-x: auto;
    }

    .nav-tabs {
      margin-bottom: 20px;
    }

    .table {
      table-layout: fixed;
      width: 100%;
    }
    .table th {
      font-size: 14px;
    }

    .table td, .table th {
      white-space: nowrap;
      overflow: auto;
      max-width: 150px;
    }
    .table td::-webkit-scrollbar {
      display: none;
    }

    .table th::-webkit-scrollbar {
      display: none;
    }

    .search-form {
      margin-left: auto;
      display: flex;
      align-items: center;
    }

    .footer {
      background-color: #f8f8f8;
      padding: 10px;
      text-align: center;
      border-top: 1px solid #ddd;
      width: 100%;
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
    <h2>거래 내역</h2>
    <!-- 거래 내역 탭과 검색 폼 -->
    <div class="d-flex align-items-center">
      <ul class="nav nav-tabs flex-grow-1" id="orderTabs" role="tablist">
        <li class="nav-item" role="presentation">
          <a class="nav-link" id="purchased-tab" href="/es/mypage/orderList" role="tab" aria-controls="purchased" aria-selected="false">내가 구매한 상품</a>
        </li>
        <li class="nav-item" role="presentation">
          <a class="nav-link active" id="sold-tab" data-bs-toggle="tab" href="#sold" role="tab" aria-controls="sold" aria-selected="true">내가 판매한 상품</a>
        </li>
      </ul>
      <form class="search-form" action="/es/mypage/orderList_1" method="get">
        <input class="form-control me-2" type="text" placeholder="Search" aria-label="Search" name="searchValue">
        <input type="hidden" value="productName" name="searchCategory">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
    </div>

    <div class="tab-content" id="orderTabsContent">
      <div class="tab-pane fade show active" id="sold" role="tabpanel" aria-labelledby="sold-tab">
        <!-- 내가 판매한 상품 리스트 -->
        <div>
          <table class="table">
            <thead>
            <tr>
              <th scope="col">상품 사진</th>
              <th scope="col">상품명</th>
              <th scope="col">구매자</th>
              <th scope="col">단위가격/개수</th>
              <th scope="col">총 가격</th>
              <th scope="col">주문 상태</th>
              <th scope="col">배송 상태</th>
              <th scope="col">받는분/배송주소</th>
              <th scope="col">결제번호</th>
              <th scope="col">결제일</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty orderList}">
              <c:forEach var="dto" items="${orderList}" >
                <tr>
                  <td>
                    <c:if test="${not empty dto.imagePath}">
                      <img src="${dto.imagePath}"
                           alt="상품 썸네일"
                           style="width: 50px; height: 50px; object-fit: cover;">
                    </c:if>
                  </td>
                  <td><a href="/product/view?productId=${dto.productId}">${dto.productName}</a></td>
                  <td>${dto.buyerId}</td>
                  <td>${dto.unitPrice}원/${dto.orderQuantity}개</td>
                  <td>${dto.totalPrice}원</td>
                  <c:choose>
                  <c:when test="${dto.orderStatus eq '구매완료' || dto.orderStatus eq '직거래 완료'}">
                    <td style="color:green;"
                  </c:when>
                  <c:otherwise>
                  <td style="color:red;"
                  </c:otherwise>
                  </c:choose>
                  >
                      ${dto.orderStatus}<br>
                    <c:if test="${dto.orderStatus eq '구매대기'}">
                      <button class="btn btn-sm btn-outline-primary" onclick="location.href='/es/payment/confirm?paymentNumber=${dto.paymentNumber}&pageNo=${param.pageNo}'">수락</button>
                      <button class="btn btn-sm btn-outline-danger" onclick="location.href='/es/payment/reject?paymentNumber=${dto.paymentNumber}&pageNo=${param.pageNo}'">거부</button>
                    </c:if>
                  </td>
                  <td>
                      ${dto.deliveryStatus}<br>
                    <c:if test="${dto.orderStatus eq '직거래'}">
                      <button class="btn btn-sm btn-primary" onclick="location.href='/es/payment/direct?paymentNumber=${dto.paymentNumber}&pageNo=${param.pageNo}'">직거래완료</button>
                    </c:if>
                    <c:if test="${dto.orderStatus eq '구매완료' && dto.deliveryStatus eq '배송전'}">
                      <button class="btn btn-sm btn-success" onclick="location.href='/es/payment/startDelivery?paymentNumber=${dto.paymentNumber}&pageNo=${param.pageNo}'">배송시작</button>
                    </c:if>
                  </td>
                  <td>${dto.recipientName}/${dto.shippingAddress}</td>
                  <td>${dto.paymentNumber}</td>
                  <td>${dUtil.localDateTimeToString(dto.regDate, 'yyyy-MM-dd')}</td>
                </tr>
              </c:forEach>
            </c:if>

            </tbody>
          </table>
          <c:if test="${empty orderList}">
            <p>목록이 없습니다</p>
          </c:if>
          <%@ include file="../common/paging.jsp"%>

        </div>
      </div>
    </div>
  </div>
</div>

<%@include file="../main/footer.jsp"%>
</body>
</html>