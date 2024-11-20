<%-- Created by IntelliJ IDEA.
     User: full5-4
     Date: 2024-11-11
     Time: 오후 1:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>당신 마켓</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: Arial, sans-serif;
      line-height: 1.6;
      background-color:black;
    }


    /* Hero Section */
    .hero {
      color: #fff;
      text-align: center;
      position: relative;
      height: 100%;
    }

    .hero-content h1 {
      font-size: 2.5rem;
      line-height: 1.2;
    }

    .hero-content p {
      margin-top: 20px;
      font-size: 1rem;
    }

    .hero-content .highlight {
      font-size: 1.2rem;
      font-weight: bold;
      color: #ff3d3d;
      text-decoration: underline;
    }

    .tags {
      margin-top: 20px;
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      justify-content: center;
    }

    .tags span {
      background-color: #333;
      color: #fff;
      padding: 8px 12px;
      border-radius: 20px;
      font-size: 0.9rem;
    }

    /* Floating Button */
    .floating-button {
      position: fixed;
      bottom: 20px;
      right: 20px;
      background-color: #ff0404;
      color: #fff;
      padding: 15px 20px;
      border-radius: 50px;
      box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.3);
      text-align: center;
      height: 80px;
    }

    .floating-button a {
      color: #fff;
      text-decoration: none;
      font-weight: bold;
      font-size: 1rem;
    }

    /* Main Banner Style */
    .main-banner {
      position: relative;
      width: 100%;
      height: 800px;
      overflow: hidden;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .main-banner img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      position: absolute;
      top: 0;
      left: 0;
      opacity: 0;
      transition: opacity 1s ease-in-out;
    }

    .main-banner img.active {
      opacity: 1;
    }

    /* Banner Navigation (Arrows) */
    .banner-nav {
      position: absolute;
      top: 50%;
      width: 100%;
      display: flex;
      justify-content: space-between;
      transform: translateY(-50%);
    }

    .banner-nav button {
      background-color: rgba(0, 0, 0, 0.5);
      border: none;
      color: white;
      font-size: 24px;
      padding: 10px;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    .banner-nav button:hover {
      background-color: rgba(0, 0, 0, 0.8);
    }

    /* Banner Dots (Indicators) */
    .banner-dots {
      position: absolute;
      bottom: 20px;
      width: 100%;
      display: flex;
      justify-content: center;
      gap: 10px;
    }

    .banner-dots span {
      width: 12px;
      height: 12px;
      background-color: white;
      border-radius: 50%;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    .banner-dots span.active {
      background-color: #007bff;
    }

    .process-section {
      text-align: center;
      padding: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background-color: white;
      border-radius: 5px;
      margin-top: 20px;
    }

    .process-section h2 {
      font-size: 24px;
      color: #333;
      margin-bottom: 30px;
    }

    .highlight {
      color: #ff4a4a;
    }

    .process-container {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 20px;
      flex-wrap: wrap; /* Allows wrapping on smaller screens */

    }

    .process-step {
      text-align: center;
      width: 150px;
    }

    .process-icon {
      width: 40px;
      height: 40px;
      margin-bottom: 15px;
    }

    .process-step h3 {
      font-size: 16px;
      font-weight: bold;
      color: #333;
      margin-bottom: 10px;
    }

    .process-step p {
      font-size: 14px;
      color: #666;
      line-height: 1.5;

    }

    .arrow {
      font-size: 24px;
      color: #e0e0e0;
    }

    .purchase-status {
      text-align: center;
      padding: 20px;
      width: 80%;
      max-width: 800px;
      margin: 0 auto; /* Centers the element horizontally */
      display: flex;
      flex-direction: column;
      align-items: center; /* Centers children horizontally */
    }

    .purchase-status h2 {
      font-size: 24px;
      color: #ff0000;
      margin-bottom: 20px;
    }

    .status-container {
      background-color: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      width: 800px;
    }

    .status-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px 0;
      border-bottom: 1px solid #e0e0e0;
      flex-wrap: wrap; /* Allows items to wrap on smaller screens */
    }

    .status-item:last-child {
      border-bottom: none;
    }

    .item-info {
      display: flex;
      align-items: center;
      flex: 1;
    }

    .item-icon {
      width: 30px;
      height: 30px;
      margin-right: 10px;
    }

    .user-info {
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .user-info span {
      font-size: 14px;
      color: #666;
    }

    .status-button {
      padding: 5px 15px;
      font-size: 14px;
      border: none;
      border-radius: 15px;
      cursor: pointer;
      color: #fff;
    }

    .status-button.pending {
      background-color: #ff4a4a;
    }

    .status-button.completed {
      background-color: #333;
    }

    .more-button-container {
      text-align: center;
      margin-top: 15px;
    }

    .more-button {
      padding: 10px 20px;
      font-size: 16px;
      color: #fff;
      background-color: #333;
      border: none;
      border-radius: 20px;
      cursor: pointer;
    }

    /* Responsive Media Queries */
    @media (max-width: 768px) {
      .navbar nav ul {
        flex-direction: column;
        gap: 10px;
      }

      .hero-content h1 {
        font-size: 2rem;
      }

      .process-container {
        flex-direction: column;
        gap: 20px;
      }

      .process-step {
        width: 100%;
      }

      .purchase-status {
        width: 90%;
        max-width: 100%;
      }

      .status-item {
        flex-direction: column;
        align-items: flex-start;
      }

      .floating-button {
        bottom: 10px;
        right: 10px;
        padding: 10px 15px;
        font-size: 0.9rem;
      }
    }

    @media (max-width: 480px) {
      .navbar {
        flex-direction: column;
        text-align: center;
      }

      .logo span {
        font-size: 1rem;
      }

      .hero-content h1 {
        font-size: 1.5rem;
      }

      .tags span {
        font-size: 0.8rem;
        padding: 6px 10px;
      }

      .process-section h2,
      .purchase-status h2 {
        font-size: 20px;
      }

      .more-button {
        font-size: 14px;
        padding: 8px 16px;
      }

      .status-item {
        flex-direction: column;
        gap: 10px;
      }
    }


    .icon {
      width: 50px;
      height: 50px;
      background-color: #ff0202;
      display: block;
      margin: auto; /* Center the icon inside the container */
    }

    .speech-bubble {
      position: absolute;
      top: -30px; /* Move the bubble above the icon */
      left: -10px; /* Move the bubble to the left */
      width: 50px;
      height: 40px;
      background-color: white;
      border-radius: 20px; /* Round the corners to make it look like a bubble */
      display: flex;
      justify-content: center;
      align-items: center;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
      z-index: 1; /* Ensure the bubble is above other elements */
    }

    .speech-bubble::after {
      content: '';
      position: absolute;
      bottom: -6px; /* Position the tail at the bottom of the bubble */
      left: 15px; /* Adjust the position of the tail */
      width: 0;
      height: 0;
      border-left: 6px solid transparent;
      border-right: 6px solid transparent;
      border-top: 6px solid white; /* Match the bubble's background color */
    }

    .notification-number {
      font-size: 14px;
      font-weight: bold;
      color: black;
    }


  </style>
</head>
<body>
<%@include file="header.jsp"%>
<c:if test="${not empty errorMessage}">
  <script>
    alert("${errorMessage}");
  </script>
</c:if>
<section class="hero">
  <div class="hero-content">
    <div class="main-banner" id="mainBanner">
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/banner1.png" class="active">
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/banner4.png">
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/banner6.png">
      <div class="banner-dots" id="bannerDots"></div>
    </div>

    <p class="highlight" style="text-decoration-line: none;">실시간 중고시세를 확인해 보세요.</p>
    <div class="tags">
      <span># 초등 교과서</span>
      <span># 중등 교과서</span>
      <span># 고등 교과서</span>
      <span># 고등 수학</span>
      <span># 중등 국어</span>
    </div>
  </div>
  <div class="floating-button">
    <a href="/message/list">
        <c:if test="${not empty unreadCount}">
              <div class="speech-bubble">
                <span class="notification-number">${unreadCount}</span>
              </div>
        </c:if>
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/selltalk.png" alt="셀파톡" class="icon">
    </a>
  </div>
</section>

<section class="process-section">
  <h2>당신마켓의 <span class="highlight">중고 거래 절차</span>를 고객님께 알려드립니다.</h2>
  <div class="process-container">
    <div class="process-step">
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/proc1.png" alt="매각 접수 아이콘" class="process-icon">
      <h3>원하는 상품 찜</h3>
      <p>증고 거래를 원하는 상품 찜하기</p>
    </div>
    <div class="arrow">&#10140;</div>
    <div class="process-step">
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/proc2.png" alt="제품 회수 아이콘" class="process-icon">
      <h3>채팅 하기</h3>
      <p>상품을 판매하는 판매자와 채팅하기</p>
    </div>
    <div class="arrow">&#10140;</div>
    <div class="process-step">
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/proc3.png" alt="제품 검사 아이콘" class="process-icon">
      <h3>약속 잡기</h3>
      <p>판매자와 약속 잡기(직거래 or 택배거래)</p>
    </div>
    <div class="arrow">&#10140;</div>
    <div class="process-step">
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/proc4.png" alt="보상 지급 아이콘" class="process-icon">
      <h3>구매 완료</h3>
      <p>원하는 상품을 받아보기</p>
    </div>
  </div>
</section>

<section class="purchase-status">
  <h2>중고 물품 전체 리스트</h2>
  <div class="status-container">

    <c:if test="${not empty pList}">
      <c:forEach var="dto" items="${pList}" varStatus="loop" begin="0" end="5">
        <a href="/product/view?productId=${dto.productId}" style="text-decoration: none; color: inherit;">
          <div class="status-item">

            <div class="item-info">
              <span>${dto.productName}</span>
            </div>
            <div class="user-info">
              <span>가격 : ${dto.price}원</span>
              <span>등록일 : ${dto.formatRegDate}</span>
              <button class="status-button pending" onclick="location.href='/product/view?productId=${dto.productId}'">상세보기</button>
            </div>

          </div>
        </a>
      </c:forEach>
    </c:if>
  </div>

  </div>
  <div class="more-button-container">
    <button class="more-button" onclick="location.href='/product/list'">more &#10140;</button>
  </div>
</section>

<%@include file="footer.jsp"%>


<script>
  const images = document.querySelectorAll('.main-banner img');
  const dotsContainer = document.getElementById('bannerDots');
  const prevBtn = document.getElementById('prevBtn');
  const nextBtn = document.getElementById('nextBtn');
  let currentIndex = 0;

  // Generate indicator dots
  images.forEach((_, index) => {
    const dot = document.createElement('span');
    dot.addEventListener('click', () => showSlide(index));
    dotsContainer.appendChild(dot);
  });

  const dots = document.querySelectorAll('.banner-dots span');
  updateDots();

  // Slide navigation function
  function showSlide(index) {
    images[currentIndex].classList.remove('active');
    dots[currentIndex].classList.remove('active');
    currentIndex = (index + images.length) % images.length;
    images[currentIndex].classList.add('active');
    dots[currentIndex].classList.add('active');
  }

  function updateDots() {
    dots.forEach((dot, index) => {
      dot.classList.toggle('active', index === currentIndex);
    });
  }

  // Auto slide
  function autoSlide() {
    showSlide(currentIndex + 1);
  }

  let slideInterval = setInterval(autoSlide, 3000);

  // Prev/Next button event listeners
  prevBtn.addEventListener('click', () => {
    clearInterval(slideInterval);
    showSlide(currentIndex - 1);
    slideInterval = setInterval(autoSlide, 3000);
  });

  nextBtn.addEventListener('click', () => {
    clearInterval(slideInterval);
    showSlide(currentIndex + 1);
    slideInterval = setInterval(autoSlide, 3000);
  });
</script>

</body>
</html>
