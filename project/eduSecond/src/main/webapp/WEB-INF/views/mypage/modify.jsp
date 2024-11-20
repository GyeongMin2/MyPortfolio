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
        .dashboard h2 {
            font-size: 18px;
        }
        .dashboard p {
            margin: 5px 0;
        }
        .table th {
            width: 150px;
            color: #555;
        }
        .btn-container {
            margin-top: 20px;
        }
        .error-message {
            color: red;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
<%@ include file="../main/header.jsp" %>

<div class="mainpage">
    <!-- 사이드바 -->
    <div class="sidebar col-md-2">
        <ul class="list-unstyled">
            <li><a href="/es/mypage/myInfo">내 정보</a></li>
            <li><a href="/es/mypage/wishList">찜 목록</a></li>
            <li><a href="/es/mypage/orderList">거래 내역</a></li>
            <li><a href="/es/mypage/productList">내 상품</a></li>
            <li><a href="/message/list">셀파톡</a></li>
        </ul>
    </div>

    <!-- 콘텐츠 영역 -->
    <div class="content col-md-10">
        <!-- 내 정보 수정 -->
        <h2 class="mb-4">내 정보 수정</h2>
        <div class="card">
            <div class="card-body">
                <form method="post" action="/es/mypage/modifyOk" onsubmit="return validateForm()">
                    <table class="table table-borderless">
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
                            <td>
                                <input type="email" class="form-control" value="${member.userEmail}" name="userEmail">
                                <c:if test="${errors != null && errors.hasFieldErrors('userEmail')}">
                                    <span class="error-message">${errors.getFieldError('userEmail').defaultMessage}</span>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>전화번호</th>
                            <td>
                                <input type="text" class="form-control" value="${member.userPhone}" name="userPhone">
                                <c:if test="${errors != null && errors.hasFieldErrors('userPhone')}">
                                    <span class="error-message">${errors.getFieldError('userPhone').defaultMessage}</span>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>우편번호</th>
                            <td>
                                <input type="text" id="userPostcode" class="form-control" name="userPostcode" value="${member.userPostcode}" readonly required>
                                <c:if test="${errors != null && errors.hasFieldErrors('userPostcode')}">
                                    <span class="error-message">${errors.getFieldError('userPostcode').defaultMessage}</span>
                                </c:if>
                                <input type="button" class="btn btn-secondary" onclick="goZip()" value="우편번호 찾기" />
                            </td>
                        </tr>
                        <tr>
                            <th>주소</th>
                            <td>
                                <input type="text" class="form-control" id="userAddr" name="userAddress" value="${member.userAddress}" readonly required>
                                <c:if test="${errors != null && errors.hasFieldErrors('userAddress')}">
                                    <span class="error-message">${errors.getFieldError('userAddress').defaultMessage}</span>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>생일</th>
                            <td>
                                <input type="date" class="form-control" value="${member.userBirth}" name="userBirth">
                                <c:if test="${errors != null && errors.hasFieldErrors('userBirth')}">
                                    <span class="error-message">${errors.getFieldError('userBirth').defaultMessage}</span>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>가입일</th>
                            <td>${dUtil.localDateTimeToString(member.regDate, 'yyyy-MM-dd')}</td>
                        </tr>
                    </table>
                    <div class="btn-container text-end">
                        <button type="submit" class="btn btn-primary me-2">등록</button>
                        <button type="button" class="btn btn-secondary" onclick="location.href='/es/mypage/myInfo'">취소</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../main/footer.jsp" %>
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
                document.getElementById("userPostcode").value = data.zonecode;
                document.getElementById("userAddr").value = roadAddr + extraRoadAddr;

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
