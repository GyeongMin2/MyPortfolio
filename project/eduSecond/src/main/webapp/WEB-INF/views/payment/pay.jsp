<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

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
            background-color: whitesmoke;
            padding: 15px;
            color: black;
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
            margin: 2%;
            margin-left: 10%;
            padding: 20px;
            flex-grow: 1;
            border: 2px solid lightgrey;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: white;
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
        .error-message {
            color : red;
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
            <li>판매자 아이디 : ${dto.sellerId}</li>
            <li>상품명 : ${dto.productName}</li>
            <li>가격 : ${dto.price} 원</li>
        </ul>
    </div>

    <div class="content col-7">
        <form action="/es/payment/pay" method="post" >
            <div class="mb-3">
                <label for="name" class="form-label">받는 사람</label>
                <input name="recipientName" class="form-control" id="name" />
                <c:if test="${errors != null && errors.hasFieldErrors('recipientName')}">
                    <span class="error-message">${errors.getFieldError('recipientName').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="tel" class="form-label">전화번호</label>
                <input name="recipientPhone" class="form-control" id="tel" />
                <c:if test="${errors != null && errors.hasFieldErrors('recipientPhone')}">
                    <span class="error-message">${errors.getFieldError('recipientPhone').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">이메일</label>
                <input name="recipientEmail" type="email" class="form-control" id="email" placeholder="name@example.com" />
                <c:if test="${errors != null && errors.hasFieldErrors('recipientEmail')}">
                    <span class="error-message">${errors.getFieldError('recipientEmail').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="zipcode" class="form-label">우편번호</label>
                <input name="shippingPostcode" class="form-control" id="zipcode" />
                <input type="button" class="btn btn-secondary" onclick="goZip()" value="우편번호 찾기" />
                <c:if test="${errors != null && errors.hasFieldErrors('shippingPostcode')}">
                    <span class="error-message">${errors.getFieldError('shippingPostcode').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="addr" class="form-label">주소</label>
                <input name="shippingAddress" class="form-control" id="addr" />
                <c:if test="${errors != null && errors.hasFieldErrors('shippingAddress')}">
                    <span class="error-message">${errors.getFieldError('shippingAddress').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="paymentCompany" class="form-label">결제회사</label>
                <input name="paymentCompany" class="form-control" id="paymentCompany" />
                <c:if test="${errors != null && errors.hasFieldErrors('paymentCompany')}">
                    <span class="error-message">${errors.getFieldError('paymentCompany').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="orderQuantity" class="form-label">결제수량(최대 : ${dto.quantity})</label>
                <input type="number" class="form-control" id="orderQuantity" name="orderQuantity" max="${dto.quantity}">
                <c:if test="${errors != null && errors.hasFieldErrors('orderQuantity')}">
                    <span class="error-message">${errors.getFieldError('orderQuantity').defaultMessage}</span>
                </c:if>
            </div>
            <input type="hidden" value="${dto.price}" name="unitPrice">
            <input type="hidden" value="${dto.productId}" name="productId">
            <input type="hidden" value="${dto.quantity}" name="totalQuantity">
            <button type="submit" class="btn btn-primary">결제하기</button>
        </form>
    </div>

</div>
<%@include file="../main/footer.jsp"%>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function goZip() {
        // 팝업 창을 명시적으로 열기
        var postcodeWindow = window.open('', '_blank', 'width=500,height=600');

        new daum.Postcode({
            oncomplete: function (data) {
                var roadAddr = data.roadAddress; // 도로명 주소
                var extraRoadAddr = ""; // 참고 항목

                // 법정동명이 있을 경우 추가
                if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가
                if (data.buildingName !== "" && data.apartment === "Y") {
                    extraRoadAddr += (extraRoadAddr !== "" ? ", " + data.buildingName : data.buildingName);
                }
                // 참고항목이 있을 경우 괄호까지 추가
                if (extraRoadAddr !== "") {
                    extraRoadAddr = " (" + extraRoadAddr + ")";
                }

                // 우편번호와 주소 정보를 해당 필드에 넣음
                document.getElementsByName("shippingPostcode")[0].value = data.zonecode;
                document.getElementsByName("shippingAddress")[0].value = roadAddr + extraRoadAddr;

                // 팝업 창 닫기
                postcodeWindow.close();
            },
            // 팝업 창 내부에 띄우기
            width: '100%',
            height: '100%'
        }).embed(postcodeWindow.document.body);
    }
</script>

</body>
</html>
