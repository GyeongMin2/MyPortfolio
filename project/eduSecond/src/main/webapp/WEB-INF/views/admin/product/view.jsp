<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>상품 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/adminHeader.jsp" %>
    
    <div class="container mt-4">
        <h2 class="mb-4">상품 상세 정보</h2>
        
        <div class="row">
            <div class="col-md-6">
                <!-- 이미지 캐러셀 -->
                <div id="productImageCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <c:forEach items="${product.images}" var="image" varStatus="status">
                            <div class="carousel-item ${status.first ? 'active' : ''}">
                                <img src="${image.imagePath}" class="d-block w-100" alt="상품 이미지">
                            </div>
                        </c:forEach>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#productImageCarousel" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#productImageCarousel" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
            
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${product.productName}</h5>
                        <p class="card-text">
                            <strong>판매자:</strong> ${product.sellerId}<br>
                            <strong>가격:</strong> ${product.price}원<br>
                            <strong>수량:</strong> ${product.quantity}개<br>
                            <strong>상태:</strong> ${product.quality}<br>
                            <strong>판매상태:</strong> ${product.productStatus}<br>
                            <strong>조회수:</strong> ${product.viewCount}<br>
                            <strong>등록일:</strong> ${product.regDate}<br>
                        </p>
                        <hr>
                        <h6>상품설명:</h6>
                        <p class="card-text">${product.productDesc}</p>
                        
                        <div class="mt-3">
                            <a href="/admin/product/list" class="btn btn-secondary">목록으로</a>
                            <a href="/admin/product/delete?productId=${product.productId}" 
                               class="btn btn-danger"
                               onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
