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
    .container {
      flex: 1;
      background-color: white;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      padding: 20px;
      max-width: 1200px;
      margin: 0 auto;
    }

    h1 {
      font-size: 24px;
      margin-bottom: 20px;
      border-bottom: 2px solid #000000;
      padding-bottom: 5px;
    }

    .star-rating {
      display: flex;
      gap: 5px;
      cursor: pointer;
    }

    .star {
      font-size: 40px;
      color: #ddd;
      transition: color 0.3s;
    }

    .star.selected {
      color: #ffc107;
    }

    .form-group {
      margin-bottom: 15px;
    }

    .form-group input,
    .editor {
      width: 100%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }

    .buttons {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }

    .save-button {
      padding: 10px 20px;
      border-radius: 4px;
      cursor: pointer;
    }

    .save-button {
      background-color: #000000;
      color: white;
      border: none;
    }

    .result {
      margin-top: 10px;
      font-weight: bold;
      color: #000000;
    }

    .action-buttons {
      margin-top: 20px;
      text-align: right;
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
    <ul>
      <li><a href="/es/mypage/myInfo">내 정보</a></li>
      <li><a href="/es/mypage/wishList">찜 목록</a></li>
      <li><a href="/es/mypage/orderList">거래 내역</a></li>
      <li><a href="/es/product/productList">내 상품</a></li>
      <li><a href="/es/mypage/messageList">셀파톡</a></li>
    </ul>
  </div>

  <div class="content col-10">
    <div class="container">
      <h1>리뷰</h1>
      <form method="POST"  action="/es/review/reviewOk">
        <div class="star-rating">
          <span class="star" data-value="1">&#9733;</span>
          <span class="star" data-value="2">&#9733;</span>
          <span class="star" data-value="3">&#9733;</span>
          <span class="star" data-value="4">&#9733;</span>
          <span class="star" data-value="5">&#9733;</span>
        </div>
        <div class="result">별점을 선택해주세요.</div>

        <!-- Hidden input to store selected rating -->
        <input type="hidden" name="rating" id="rating" value="">

        <div class="form-group">
          <label for="content">내용</label>
          <textarea id="content" class="editor" name="content" placeholder="내용을 입력해주세요..." required></textarea>
        </div>

        <div class="buttons">
          <input type="submit" class="save-button" value="등록">
        </div>
        <input type="hidden" value="${param.orderId}" name="orderId">
      </form>
    </div>
  </div>
</div>

<%@include file="../main/footer.jsp"%>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    const stars = document.querySelectorAll('.star');
    const result = document.querySelector('.result');
    let selectedRating = 0;

    stars.forEach((star) => {
      star.addEventListener('click', function() {
        selectedRating = parseInt(star.getAttribute('data-value'));
        updateStars(selectedRating);
        updateResultText(selectedRating); // 별점 선택 시 result 영역 업데이트
        document.getElementById('rating').value = selectedRating; // 선택한 별점 hidden input에 설정
      });

      star.addEventListener('mouseover', function() {
        const hoverValue = parseInt(star.getAttribute('data-value'));
        updateStars(hoverValue);
      });

      star.addEventListener('mouseout', function() {
        updateStars(selectedRating);
      });
    });

    function updateStars(rating) {
      stars.forEach((star, index) => {
        if (index < rating) {
          star.classList.add('selected');
        } else {
          star.classList.remove('selected');
        }
      });
    }

    function updateResultText(rating) {
      if (rating > 0) {
        result.textContent = '선택한 별점: ' + rating + '개';
      } else {
        result.textContent = '별점을 선택해주세요.';
      }
    }

    function handleCancel() {
      if (confirm('정말로 취소하시겠습니까?')) {
        document.querySelector('form').reset();
        selectedRating = 0; // 별점 초기화
        updateStars(0);
        updateResultText(0); // 결과 텍스트 초기화
      }
    }

    window.handleCancel = handleCancel; // handleCancel을 전역으로 노출하여 onclick 이벤트에서 사용 가능하도록 함
  });
</script>
</body>
</html>