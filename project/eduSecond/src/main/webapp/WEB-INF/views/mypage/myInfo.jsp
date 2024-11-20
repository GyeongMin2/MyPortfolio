<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>툴 - 마이페이지</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f9f9f9;
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
    }
    .info-table th {
      width: 150px;
      color: #555;
    }
    .info-table td {
      color: #333;
    }
    .btn-container {
      margin-top: 20px;
    }
    a{
      text-decoration-line: none;
      color: black;
    }

  </style>
</head>
<body>
<%@ include file="../main/header.jsp" %>

<div class="mainpage">
  <div class="sidebar col-md-2">
    <ul class="list-unstyled">
      <li><a href="/es/mypage/myInfo">내 정보</a></li>
      <li><a href="/es/mypage/wishList">찜 목록</a></li>
      <li><a href="/es/mypage/orderList">거래 내역</a></li>
      <li><a href="/es/mypage/productList">내 상품</a></li>
      <li><a href="/message/list">셀파톡</a></li>
    </ul>
  </div>

  <div class="content col-md-10">
    <!-- 대시보드 -->
    <div class="row g-4 mb-4">
      <div class="col-md-4">
        <div class="card h-100">
          <div class="card-body text-center">
            <h5 class="card-title">내가 등록한 상품</h5>
            <p><a href="/es/mypage/productList">판매중: <strong>${mypageDTO.productAvailableCount}</strong></a></p>
            <p><a href="/es/mypage/productList_1">판매완료: <strong>${mypageDTO.productSoldCount}</strong></a></p>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card h-100">
          <div class="card-body text-center">
            <h5 class="card-title">내가 찜한 상품</h5>
            <p><a href="/es/mypage/wishList"><strong>${mypageDTO.wishListCount}</strong>개</a></p>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card h-100">
          <div class="card-body text-center">
            <h5 class="card-title"><a href="/product/seller/sellerpage?userId=${memberInfo.userId}">내 프로필 바로가기</a></h5>
          </div>
        </div>
      </div>
    </div>

    <!-- 내 정보 테이블 -->
    <h2 class="mb-4">내 정보</h2>
    <div class="card">
      <div class="card-body">
        <table class="table info-table">
          <tbody>
          <tr>
            <th>아이디</th>
            <td>${member.userId}</td>
          </tr>
          <tr>
            <th>이름</th>
            <td>${member.userName}</td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>${member.userEmail}</td>
          </tr>
          <tr>
            <th>전화번호</th>
            <td>${member.userPhone}</td>
          </tr>
          <tr>
            <th>주소</th>
            <td>${member.userAddress}</td>
          </tr>
          <tr>
            <th>생일</th>
            <td>${member.userBirth}</td>
          </tr>
          <tr>
            <th>가입일</th>
            <td>${dUtil.localDateTimeToString(member.regDate, 'yyyy-MM-dd')}</td>
          </tr>
          </tbody>
        </table>
        <div class="btn-container text-end">
          <button class="btn btn-primary me-2" onclick="location.href='/es/mypage/modify'">수정</button>
          <c:if test="${member.enabled == 'Y'}">
            <button class="btn btn-danger" onclick="confirmWithdrawal()">탈퇴</button>
          </c:if>
          <c:if test="${member.enabled == 'Q'}">
            <button class="btn btn-danger" onclick="confirmWithdrawalCancel()">탈퇴취소</button>
          </c:if>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="../main/footer.jsp" %>

<script>
function confirmWithdrawal() {
    if (confirm('정말 탈퇴하시겠습니까?\n탈퇴 신청 후 관리자 승인 시 계정이 비활성화됩니다.')) {
        location.href = '/es/mypage/delete';
    }
}
function confirmWithdrawalCancel() {
    if (confirm('탈퇴 신청을 취소하시겠습니까?')) {
        location.href = '/es/mypage/deleteCancel';
    }
}
</script>
</body>
</html>