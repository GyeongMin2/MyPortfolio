<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Title</title>
  <style>
    /* Navbar */
    .navbar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20px;
      background-color: #fff;
      border-bottom: 1px solid #ddd;
      min-width: 1000px;
    }

    .logo {
      display: flex;
      align-items: center;
    }

    .logo img {
      width: 40px;
      height: auto;
      margin-right: 10px;
    }

    .logo span {
      font-weight: bold;
      font-size: 1.2rem;
      color: #333;
    }

    .navbar nav ul {
      list-style: none;
      display: flex;
      gap: 20px;
      margin: 0;
      padding: 0;
    }

    .navbar nav ul li a {
      text-decoration: none;
      color: #000000;
      font-weight: bold;
      font-size: 21px;
    }

    .auth-links {
      display: flex;
      gap: 10px;
    }

    .auth-links a {
      text-decoration: none;
      color: #000000;
    }

    /* Dropdown menu for smaller screens */
    .menu-toggle {
      display: none;
      font-size: 24px;
      cursor: pointer;
    }

    .navbar nav ul {
      flex-direction: row;
    }

    @media (max-width: 1000px) {
      .menu-toggle {
        display: block;
      }

      .navbar nav ul {
        display: none; /* Hide by default */
        flex-direction: column;
        gap: 0;
        background-color: #ffffff;
        position: absolute;
        top: 60px;
        left: 0;
        width: 100%;
        padding: 10px;
        border-top: 1px solid #ffffff;
      }

      .navbar nav ul.show {
        display: flex;
        z-index: 1000;
      }

      .navbar nav ul li {
        padding: 10px 0;
      }
    }
  </style>
  <script>
    function toggleMenu() {
      const navUl = document.querySelector(".navbar nav ul");
      navUl.classList.toggle("show");
    }
  </script>
</head>
<body>

<header>
  <div class="navbar">
    <div class="logo">
      <a href="/main/main">
        <img src="<%= request.getContextPath() %>/resources/images/mainbanner/logo.png" alt="IT Shop Logo">
      </a>
      <span style="font-size: 13px;">선생님들을 위한 중고거래 사이트 </span><span style="margin-left: 5px;">당신마켓</span>
    </div>
    <!-- Menu Toggle for Small Screens -->
    <span class="menu-toggle" onclick="toggleMenu()">☰</span>
    <nav>
      <ul>
        <li><a href="/footer/intro">회사소개</a></li>
        <li><a href="/product/list">상품</a></li>
        <li><a href="/es/mypage/productList">내상품</a></li>
        <li><a href="/message/list">셀파톡</a></li>
        <li><a href="/notice/noticelist">공지사항</a></li>
      </ul>
    </nav>
    <div class="auth-links">
      <!--로그인 전-->
      <c:if test="${empty sessionScope.memberInfo}" var="logout">
        <a href="/login/login">로그인</a> | <a href="/login/agreeregist">회원가입</a>
      </c:if>

      <!--로그인 시-->
      <c:if test="${not empty sessionScope.memberInfo}" var="login">
        <a href="/es/mypage/myInfo">${sessionScope.memberInfo.userName}의 마이페이지</a>| <a href="/main/logout">로그아웃</a>
      </c:if>
    </div>
  </div>
</header>

</body>
</html>
