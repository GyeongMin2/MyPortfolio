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
        }

        .privacy-policy {
            max-width: 1500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            display: flex;
            gap: 20px;
        }

        .left-side {
            width: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .carousel {
            width: 100%;
            max-width: 400px;
            height: auto;
        }

        .carousel-item {
            align-content: center;
        }

        .right-side {
            width: 50%;
        }

        .profile-container {
            display: flex;
            max-width: 1200px;
            margin: 20px 0;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
            overflow: hidden;
        }

        .profile-image img {
            width: 100%;
            height: auto;
            object-fit: cover;
        }

        .profile-name {
            font-size: 18px;
            font-weight: bold;
            margin-top: 10px;
        }

        .profile-info {
            width: 65%;
            padding: 0 20px;
        }

        .profile-title {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .info-details {
            margin-top: 10%;
        }

        .seller-page-link {
            margin-bottom: 10px;
        }

        .seller-page-link > a {
            font-size: 18px;
            color: black;
            text-decoration: none;
        }

        .button-container {
            display: flex;
            gap: 10px;
            margin-top: 10px;
        }

        .info {
            margin-top: 20px;
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin: 20px 0;
        }

        ul li {
            margin-bottom: 10px;
        }

        .no-image {
            text-align: center;
            margin: 10%;
            font-size: 18px;
            color: #888;
        }
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<%@ include file="../main/header.jsp" %>
<section class="privacy-policy">
    <div class="left-side">
        <c:if test="${not empty dto.images}">
            <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <c:forEach var="image" items="${dto.images}" varStatus="status">
                        <button type="button" data-bs-target="#carouselExampleIndicators"
                                data-bs-slide-to="${status.index}"
                                class="${status.first ? 'active' : ''}" aria-current="${status.first ? 'true' : ''}"
                                aria-label="Slide ${status.index + 1}"></button>
                    </c:forEach>
                </div>
                <div class="carousel-inner">
                    <c:forEach var="image" items="${dto.images}" varStatus="status">
                        <div class="carousel-item ${status.first ? 'active' : ''}">
                            <img src="${image.imagePath}" class="d-block w-100" alt="Product Image ${status.index + 1}">
                        </div>
                    </c:forEach>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </c:if>
        <c:if test="${empty dto.images}">
            <div class="no-image">
                준비된 이미지가 없습니다.
            </div>
        </c:if>
    </div>
    <div class="right-side">
        <div class="content">
            <div class="wish">
                <c:choose>
                    <c:when test="${isLiked}">
                        <img  src="../../../resources/images/heart/heartRed.png"
                             id="heartImageRed" height="30px" width="30px" onclick="Heart()">
                    </c:when>
                    <c:otherwise>
                        <img  src="../../../resources/images/heart/heart.png" id="heartImage"
                             height="30px" width="30px" onclick="Heart()">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="profile-container">
                <div class="profile-card">
                    <div class="profile-image">
                        <img src="/resources/images/seller/woman.png" alt="User Profile">
                    </div>
                    <h3 class="profile-name">${dto.sellerId}</h3>
                </div>
                <div class="profile-info">
                    <h2 class="profile-title">${member.userName}</h2>
                    <div class="info-details">
                        <div class="seller-page-link">
                            <a href="seller/sellerpage?userId=${dto.sellerId}">➡️ 판매자 페이지 보러가기</a>
                        </div>
                        <div class="button-container">
                            <c:if test="${memberInfo.userId ne dto.sellerId}">
                                <a href="#" class="btn btn-primary" onclick="createChatRoom()">1:1 문의하기</a>
                                <a href="/es/payment/view?productId=${dto.productId}" class="btn btn-primary"
                                   data-bs-toggle="modal" data-bs-target="#purchaseModal">구매하기</a>
                            </c:if>
                            <c:if test="${memberInfo.userId eq dto.sellerId}">
                                <a href="/product/delete?productId=${dto.productId}" class="btn btn-danger">삭제하기</a>
                                <a href="/product/update?productId=${dto.productId}" class="btn btn-warning">수정하기</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <div class="info">
                <ul>
                    <li>상품명: ${dto.productName}</li>
                    <li>상세 내용: ${dto.productDesc}</li>
                    <li>가격: ${dto.price}원</li>
                    <li>구매 가능 수량: ${dto.quantity}</li>
                    <li>품질: ${dto.quality}</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="modal fade" id="purchaseModal" tabindex="-1" aria-labelledby="purchaseModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="purchaseModalLabel">구매 방법을 선택해주세요</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body justify-content-center">
                    <button type="button" class="btn btn-primary"
                            onclick="javascript:location.href='/es/payment/view?productId=${dto.productId}'">바로구매
                    </button>
                    <button type="button" class="btn btn-primary"
                            onclick="location.href='/es/payment/view_1?productId=${dto.productId}'">직거래
                    </button>
                </div>
            </div>
        </div>
    </div>
</section>
<h2 style="margin-left: 3%">후기</h2>
<section class="privacy-policy">
    <table class="table table-bordered">
        <thead class="table-light">
        <tr>
            <th>작성자</th>
            <th>내용</th>
            <th>별점</th>
            <th>등록일</th>
        </tr>
        </thead>
        <c:if test="${not empty reviewList}">
            <c:forEach var="dto" items="${reviewList}" varStatus="status">
                <tbody>
                <td>${dto.writerId}</td>
                <td>${dto.content}</td>
                <td>${dto.rating}</td>
                <td>${dto.formatRegDate}</td>
                </tbody>
            </c:forEach>
        </c:if>
    </table>
</section>
<%@include file="../main/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
<script>
    function Heart() {
        const heartImageRed = document.getElementById("heartImageRed");
        const heartImage = document.getElementById("heartImage");
        const productId = "${dto.productId}"
        const userId = "${userId}"
        if (heartImageRed) {
            // 현재 빨간 하트인 경우 (좋아요를 취소하는 경우)
            window.location.href = "/es/like/delete?productId=" + productId + "&userId=" + userId;
        } else if (heartImage) {
            // 현재 빈 하트인 경우 (좋아요를 추가하는 경우)
            window.location.href = "/es/like/insert?productId=" + productId + "&userId=" + userId;
        }
    }

    function createChatRoom() {
        const productId = ${dto.productId};
        const sellerId = '${dto.sellerId}';  // 판매자 ID
        const buyerId = '${sessionScope.memberInfo.userId}';  // 현재 로그인한 사용자 ID

        if (!buyerId) {
            alert('로그인이 필요한 서비스입니다.');
            window.location.href = '/login/login';
            return;
        }

        if (sellerId === buyerId) {
            alert('자신의 상품입니다.');
            return;
        }

        fetch('/message/room/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                productId: productId,
                sellerId: sellerId,
                buyerId: buyerId
            })
        })
            .then(response => response.json())
            .then(data => {
                window.location.href = '/message/chatting?roomId=' + data.roomId;
            })
            .catch(error => {
                console.error('Error:', error);
                alert('채팅방 생성에 실패했습니다.');
            });
    }
</script>