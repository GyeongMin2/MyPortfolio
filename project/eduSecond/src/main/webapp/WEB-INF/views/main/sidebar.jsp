<%--
  Created by IntelliJ IDEA.
  User: full5-4
  Date: 2024-11-11
  Time: 오후 2:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>eBook Sidebar</title>
  <style>
    /* Sidebar */
    .sidebar {
      width: 50vw; /* 50% of viewport width */
      height: 100vh; /* Full viewport height */
      background-color: rgba(0, 0, 0, 0.5);
      border-radius: 10px;
      color: #ffffff;
      padding: 20px;
      position: fixed;
      right: 0;
      top: 0;
      overflow-y: auto;
    }
    

    .sidebar-title {
      font-size: 20px;
      margin-bottom: 10px;
    }

    .search-input {
      width: 100%;
      padding: 10px;
      border: none;
      border-radius: 5px;
      margin-bottom: 20px;
      background-color: #444;
      color: #fff;
    }

    .selection-search h3 {
      font-size: 18px;
      margin-bottom: 10px;
    }

    .dropdown {
      margin-bottom: 15px;
    }

    .dropdown label {
      font-size: 14px;
      display: block;
      margin-bottom: 5px;
    }

    .dropdown select {
      width: 100%;
      padding: 10px;
      border: none;
      border-radius: 5px;
      background-color: #555;
      color: #fff;
    }

    .estimated-price {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 16px;
      padding: 10px;
      background-color: #333;
      border-radius: 5px;
      margin-bottom: 20px;
    }

    .price {
      font-weight: bold;
    }

    .buttons {
      display: flex;
      justify-content: space-between;
    }

    .apply-button,
    .live-consult-button {
      flex: 1;
      padding: 10px;
      border: none;
      border-radius: 5px;
      font-size: 14px;
      cursor: pointer;
      color: #fff;
    }

    .apply-button {
      background-color: #333;
      margin-right: 10px;
    }

    .live-consult-button {
      background-color: #b88a43;
    }

    .notice {
      font-size: 12px;
      color: #f00;
      margin-top: 20px;
      text-align: left;
    }
  </style>
</head>
<body>
<div class="sidebar">
  <h2 class="sidebar-title">빠른검색</h2>
  <input type="text" class="search-input" placeholder="판매하실 모델명을 검색해 보세요.">

  <div class="selection-search">
    <h3>선택검색</h3>

    <div class="dropdown">
      <label for="product-group">판매하실 제품군을 선택하세요.</label>
      <select id="product-group">
        <option>선택</option>
      </select>
    </div>

    <div class="dropdown">
      <label for="brand">브랜드</label>
      <select id="brand">
        <option>선택</option>
      </select>
    </div>

    <div class="dropdown">
      <label for="product-category">제품분류</label>
      <select id="product-category">
        <option>선택</option>
      </select>
    </div>

    <div class="dropdown">
      <label for="product-name">제품명</label>
      <select id="product-name">
        <option>선택</option>
      </select>
    </div>

    <div class="estimated-price">
      <span>예상매입가</span>
      <span class="price">0원</span>
    </div>

    <div class="buttons">
      <button class="apply-button">매각신청하기</button>
      <button class="live-consult-button">실시간 상담</button>
    </div>
  </div>

  <p class="notice">🔴 찾으시는 모델명이 검색이 안되면 실시간상담을 이용하세요.</p>
</div>
</body>
</html>