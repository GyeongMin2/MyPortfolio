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
        .error-message{
            color:red;
        }
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../main/header.jsp"%>

<div class="mainpage">
    <div class="sidebar col-2">
        <p>상품 등록</p>
    </div>

    <div class="content col-7">
        <form action="/product/registOk" method="post" enctype="multipart/form-data">
            <br>
            <div class="mb-3">
                <label for="productName" class="form-label">상품명</label>
                <input type="text" class="form-control" id="productName" name="productName">
                <c:if test="${errors != null && errors.hasFieldErrors('productName')}">
                    <span class="error-message">${errors.getFieldError('productName').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlTextarea1" class="form-label">상품 설명</label>
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="10" name="productDesc"></textarea>
                <c:if test="${errors != null && errors.hasFieldErrors('productDesc')}">
                    <span class="error-message">${errors.getFieldError('productDesc').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">가격</label>
                <input type="number" class="form-control" id="price" name="price">
                <c:if test="${errors != null && errors.hasFieldErrors('price')}">
                    <span class="error-message">${errors.getFieldError('price').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="quantity" class="form-label">수량</label>
                <input type="number"  class="form-control" id="quantity" name="quantity">
                <c:if test="${errors != null && errors.hasFieldErrors('quantity')}">
                    <span class="error-message">${errors.getFieldError('quantity').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="quality" class="form-label">상품 상태</label>
                <select id="quality" name="quality" class="form-select" aria-label="Default select example">
                    <option value="NEW">new</option>
                    <option value="LIKE_NEW">likenew</option>
                    <option value="GOOD">good</option>
                    <option value="FAIR">fair</option>
                </select>
                <c:if test="${errors != null && errors.hasFieldErrors('quality')}">
                    <span class="error-message">${errors.getFieldError('quality').defaultMessage}</span>
                </c:if>
            </div>
            <div class="mb-3">
                <input type="file"  class="form-control" name="files" multiple required>
                <c:if test="${not empty errorMessage}">
                    <span class="error-message">${errorMessage}</span>
                </c:if>
            </div>
            <button type="submit" class="btn btn-primary">등록하기</button>
        </form>
    </div>
</div>
<%@include file="../main/footer.jsp"%>
</body>
</html>