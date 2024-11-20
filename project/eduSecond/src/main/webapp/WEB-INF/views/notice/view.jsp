<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>공지사항</title>
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
    }

    .privacy-policy {
      max-width: 1500px;
      margin: 50px auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }
    .container {
      width: 100%;
      height: 500px;
      background-color: #fff;
      padding: 20px;
      border: 1px solid #ddd;
      border-radius: 5px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .title {
      font-size: 24px;
      text-align: center;
      margin-bottom: 10px;
    }

    .meta-info {
      text-align: right;
      font-size: 14px;
      color: #666;
      margin-bottom: 20px;
    }

    .description {
      font-size: 16px;
      margin-bottom: 20px;
      border-bottom: 1px solid #ddd;
      padding-bottom: 10px;
    }

    .attachment-section {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 14px;
      margin-bottom: 20px;
      padding-bottom: 10px;
      border-bottom: 1px solid #ddd;
    }

    .content {
      font-size: 14px;
      line-height: 1.6;
      margin-bottom: 20px;
    }

    .content p {
      margin-bottom: 10px;
    }

    .content img {
      width: 100%;
      max-width: 400px;
      margin: 20px 0;
    }

    .back-button {
      display: block;
      width: 100%;
      padding: 10px;
      background-color: #333;
      color: #fff;
      border: none;
      font-size: 16px;
      cursor: pointer;
      text-align: center;
      border-radius: 5px;
      transition: background-color 0.3s;
    }

    .back-button:hover {
      background-color: #555;
    }
    .banner {
      position: relative;
      margin: 20px auto;
      width: 80%;
      max-width: 1200px;
    }

    .banner img {
      width: 100%;
      border-radius: 5px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }

  </style>
</head>
<body>
<%@ include file="../main/header.jsp"%>

<section class="privacy-policy">
  <div class="container">
    <h1 class="title">공지사항</h1>
    <div class="meta-info">
      <span style="display: block; text-align: left;">작성자 : ${notice.adminId}</span> | <span>${dUtil.localDateTimeToString(notice.regDate, 'yyyy-MM-dd')}</span> | <span>조회수 : ${notice.viewCount}</span>
    </div>

    <p class="description">${notice.title}</p>
    <!--
    <div class="attachment-section">
      <strong>첨부파일</strong>
      <span>첨부파일 없음</span>
    </div>
    -->
    <div class="content">
        ${notice.content}
      </p>
    </div>

    <button class="back-button" onclick="location.href='/notice/noticelist'">목록</button>
  </div>
  <div class="banner">
    <img src="/resources/images/introbanner/banneri.gif">
  </div>
</section>

<%@include file="../main/footer.jsp"%>


</body>
</html>
