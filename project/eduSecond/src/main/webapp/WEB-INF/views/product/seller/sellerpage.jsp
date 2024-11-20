<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>판매자 정보</title>
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


        .profile-info {
            flex: 1;
            margin-left: 15px;
        }

        .profile-name {
            font-size: 18px;
            font-weight: bold;
        }


        .product-container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
        }

        .product-grid {
            max-width: 1200px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            gap: 20px;
        }

        .product-card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 15px;
            text-align: center;
        }

        .product-card img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            border-radius: 5px;
        }

        .product-title {
            font-size: 16px;
            margin: 10px 0 5px;
        }

        .product-price {
            font-size: 14px;
            color: #ff4500;
            font-weight: bold;
        }

        .product-details {
            font-size: 12px;
            color: #777;
        }

        .product-info {
            font-size: 12px;
            color: #999;
        }
        .review-section {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
        }

        .review-card {
            border-bottom: 1px solid #ddd;
            padding: 20px 0;
        }

        .review-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }


        .user-info {
            flex: 1;
            margin-left: 15px;
        }

        .username {
            font-weight: bold;
            font-size: 16px;
            margin-bottom: 5px;
        }

        .rating {
            font-size: 14px;
            color: #FFD700;
        }

        .tag {
            background-color: #f0f0f0;
            border: none;
            padding: 5px 10px;
            font-size: 12px;
            color: #555;
            border-radius: 4px;
            cursor: default;
            margin-top: 5px;
        }

        .review-time {
            font-size: 12px;
            color: #999;
        }

        .review-content {
            margin-top: 10px;
            font-size: 14px;
            color: #555;
            line-height: 1.6;
        }
        /* Styles for the Review Section Heading */
        .review-heading {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }

        .review-heading hr {
            border: none;
            border-top: 2px solid #ddd;
            margin: 10px 0 20px;
            width: 100%;
        }
        /* styles.css */


        .profile-container {
            display: flex;
            max-width: 1200px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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

        .profile-card {
            width: 35%;
            background-color: #f2f2f2;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
        }

        .profile-image {
            background-color: #ddd;
            border-radius: 50%;
            width: 80px;
            height: 80px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 10px;
        }

        .profile-image img {
            width: 100%;
            border-radius: 50%;
        }

        .profile-name {
            font-size: 18px;
            font-weight: bold;
            margin-top: 10px;
        }

        .profile-rating {
            color: #FFD700;
            font-size: 16px;
            margin-top: 5px;
        }

        .profile-stats {
            font-size: 14px;
            color: #777;
            margin-top: 10px;
        }

        .follow-button {
            margin-top: 15px;
            padding: 8px 20px;
            font-size: 14px;
            color: #333;
            background-color: transparent;
            border: 1px solid #333;
            border-radius: 4px;
            cursor: pointer;
        }

        .profile-info {
            width: 70%;
            padding: 0 20px;
        }

        .profile-title {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .info-details {
            display: flex;
            gap: 15px;
            font-size: 14px;
            color: #555;
            margin-bottom: 10px;
        }

        .info-item {
            display: flex;
            align-items: center;
        }

        .verification-status {
            font-size: 14px;
            color: #FFA500;
            font-weight: bold;
            margin-top: 10px;
        }

        .report-link {
            display: inline-block;
            margin-top: 15px;
            font-size: 14px;
            color: #999;
            text-decoration: none;
        }

        .report-link:hover {
            text-decoration: underline;
        }


    </style>
</head>
<body>
<%@ include file="../../main/header.jsp"%>

<section class="privacy-policy">
    <div class="profile-container">
        <div class="profile-card">
            <div class="profile-image">
                <img src="/resources/images/seller/woman.png" alt="User Profile">
            </div>
            <h3 class="profile-name">${member.userName}</h3>
            <c:if test="${not empty StarAvg}">
            <div class="rating" data-rating="${StarAvg.starAvg}"></div>
            <div>(${StarAvg.starAvg})</div>
            </c:if>
            <c:if test="${empty StarAvg}">
                <div>(아직 등록된 리뷰가 없습니다)</div>
            </c:if>

        </div>

        <div class="profile-info">
            <h2 class="profile-title">${member.userName}</h2>
            <div class="info-details">
                <span class="info-item">📪이메일 :${member.userEmail}</span>
            </div>
        </div>
    </div>

    <div class="product-container">
        <div class="review-heading">
            <p style="margin-left: 25px">판매 리스트</p>
            <hr style="max-width: 1200px">
        </div>
        <div class="product-grid">
            <!-- Product card template
            <c:if test="${not empty list}">
                <p>!상품이 없습니다</p>
            </c:if> -->
            <c:if test="${not empty list}">
                <c:forEach var="dto" items="${list}" varStatus="loop" begin="0" end="9">
                    <a href="/product/view?productId=${dto.productId}" style="text-decoration: none; color: inherit;">
                        <div class="product-card">
                            <!--<img src="product1.jpg" alt="Product Image">-->
                            <h3 class="product-title">${dto.productName}</h3>
                            <p class="product-price">가격 : ${dto.price}</p>
                            <!--<p class="product-details"></p>-->
                            <p class="product-info">조회수 : ${dto.viewCount}</p>
                        </div>
                        <!-- Repeat product-card for other products -->
                    </a>
                </c:forEach>
            </c:if>
        </div>
    </div>


    <div class="review-section">
        <div class="review-heading">
            <p style="margin-left: 25px">리뷰</p>
            <hr style="max-width: 1200px">
        </div>
        <c:if test="${not empty ReviewList}">
            <c:forEach var="dto" items="${ReviewList}" varStatus="loop" begin="0" end="9">
                <div class="review-card">
                    <div class="review-header">
                        <img src="/resources/images/seller/man.png" alt="User Profile" class="profile-image">
                        <div class="user-info">
                            <p class="username">${dto.writerId}</p>
                            <!-- Rating container with data-rating -->
                            <div class="rating" data-rating="${dto.rating}"></div>
                        </div>
                        <span class="review-time">${dto.regDate}</span>
                    </div>
                    <p class="review-content">🎁 상품 : ${dto.productId} ${dto.productName}</p>
                    <p class="review-content">${dto.content}</p>
                </div>
            </c:forEach>
        </c:if>
    </div>

    <div class="banner">
        <img src="/resources/images/introbanner/banneri.gif">
    </div>
</section>

<%@include file="../../main/footer.jsp"%>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Select all rating elements
        const ratings = document.querySelectorAll(".rating");

        // Iterate through each rating element
        ratings.forEach((ratingElement) => {
            // Get the rating value from the data attribute
            const rating = parseInt(ratingElement.getAttribute("data-rating"), 10);

            // Clear current content (if any)
            ratingElement.textContent = "";

            // Add ⭐ based on the rating value
            for (let i = 0; i < rating; i++) {
                ratingElement.textContent += "⭐";
            }
        });
    });


</script>


</body>
</html>
