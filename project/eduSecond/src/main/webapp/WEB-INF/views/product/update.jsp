<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>상품 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .image-preview {
            position: relative;
            width: 150px;
            height: 150px;
            margin: 10px;
            display: inline-block;
        }
        .image-preview img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .delete-button {
            position: absolute;
            top: 5px;
            right: 5px;
            background: rgba(255, 0, 0, 0.7);
            color: white;
            border: none;
            border-radius: 50%;
            width: 25px;
            height: 25px;
            cursor: pointer;
        }
        .main-badge {
            position: absolute;
            top: 5px;
            left: 5px;
            background: rgba(0, 0, 255, 0.7);
            color: white;
            padding: 2px 8px;
            border-radius: 10px;
            font-size: 12px;
        }
        .image-controls {
            position: absolute;
            bottom: 5px;
            left: 5px;
            right: 5px;
            background: rgba(255, 255, 255, 0.8);
            padding: 5px;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .main-image-radio {
            margin-right: 5px;
        }
        .main-image-label {
            margin: 0;
            font-size: 12px;
            color: #333;
        }
    </style>
</head>
<body>
<%@ include file="../main/header.jsp"%>
<div class="container mt-5">
    <h2>상품 수정</h2>
    <form action="/product/updateOk" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
        <input type="hidden" name="productId" value="${dto.productId}">
        
        <div class="mb-3">
            <label for="productName" class="form-label">상품명</label>
            <input type="text" class="form-control" id="productName" name="productName" value="${dto.productName}">
        </div>
        
        <div class="mb-3">
            <label for="productDesc" class="form-label">상품 설명</label>
            <textarea class="form-control" id="productDesc" name="productDesc" rows="5">${dto.productDesc}</textarea>
        </div>
        
        <div class="mb-3">
            <label for="price" class="form-label">가격</label>
            <input type="number" class="form-control" id="price" name="price" value="${dto.price}">
        </div>
        
        <div class="mb-3">
            <label for="quantity" class="form-label">수량</label>
            <input type="number" class="form-control" id="quantity" name="quantity" value="${dto.quantity}">
        </div>
        
        <div class="mb-3">
            <label for="quality" class="form-label">상품 상태</label>
            <select class="form-control" id="quality" name="quality">
                <option value="NEW" ${dto.quality == 'NEW' ? 'selected' : ''}>새상품</option>
                <option value="LIKE_NEW" ${dto.quality == 'LIKE_NEW' ? 'selected' : ''}>거의 새것</option>
                <option value="GOOD" ${dto.quality == 'GOOD' ? 'selected' : ''}>양호</option>
                <option value="FAIR" ${dto.quality == 'FAIR' ? 'selected' : ''}>사용감 있음</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="productStatus" class="form-label">판매 상태</label>
            <select class="form-control" id="productStatus" name="productStatus">
                <option value="AVAILABLE" ${dto.productStatus == 'AVAILABLE' ? 'selected' : ''}>판매중</option>
                <option value="RESERVED" ${dto.productStatus == 'RESERVED' ? 'selected' : ''}>예약중</option>
                <option value="SOLD" ${dto.productStatus == 'SOLD' ? 'selected' : ''}>판매완료</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">이미지 수정</label>
            <div class="d-flex mb-2">
                <div class="form-check me-3">
                    <input class="form-check-input" type="radio" name="imageUpdateType" id="keepImages" value="keep" checked>
                    <label class="form-check-label" for="keepImages">
                        기존 이미지 유지
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="imageUpdateType" id="updateImages" value="update">
                    <label class="form-check-label" for="updateImages">
                        이미지 새로 등록
                    </label>
                </div>
            </div>
            
            <!-- 기존 이미지 표시 영역 -->
            <div id="currentImages">
                <c:forEach items="${images}" var="image">
                    <div class="image-preview">
                        <img src="${image.imagePath}" alt="상품 이미지">
                        <c:if test="${image.isMain == 'Y'}">
                            <span class="badge bg-primary">대표</span>
                        </c:if>
                    </div>
                </c:forEach>
            </div>

            <!-- 새 이미지 업로드 영역 -->
            <div id="newImageUpload" style="display: none;">
                <input type="file" class="form-control" id="files" name="files" multiple accept="image/*" disabled>
                <small class="text-muted">1~4개의 이미지를 선택하세요. 첫 번째 이미지가 대표 이미지가 됩니다.</small>
                <div id="imagePreview" class="mt-2"></div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">수정하기</button>
        <a href="/product/view?productId=${dto.productId}" class="btn btn-secondary">취소</a>
    </form>
</div>
<%@include file="../main/footer.jsp"%>

<script>
document.querySelectorAll('input[name="imageUpdateType"]').forEach(radio => {
    radio.addEventListener('change', function() {
        const newImageUpload = document.getElementById('newImageUpload');
        const filesInput = document.getElementById('files');
        
        if (this.value === 'update') {
            newImageUpload.style.display = 'block';
            filesInput.disabled = false;
            filesInput.required = true;
        } else {
            newImageUpload.style.display = 'none';
            filesInput.disabled = true;
            filesInput.required = false;
            filesInput.value = '';
            document.getElementById('imagePreview').innerHTML = '';
        }
    });
});

// 파일 선택 시 미리보기
document.getElementById('files').addEventListener('change', function(e) {
    const files = Array.from(e.target.files);
    if (files.length < 1 || files.length > 4) {
        alert('1~4개의 이미지를 선택해주세요.');
        this.value = '';
        return;
    }
    
    const preview = document.getElementById('imagePreview');
    preview.innerHTML = '';
    
    files.forEach((file, index) => {
        if (!file.type.startsWith('image/')) {
            alert('이미지 파일만 업로드 가능합니다.');
            this.value = '';
            return;
        }
        
        const reader = new FileReader();
        reader.onload = function(e) {
            const div = document.createElement('div');
            div.className = 'image-preview';
            
            // 이미지 엘리먼트 생성
            const img = document.createElement('img');
            img.src = e.target.result;
            img.alt = '미리보기';
            div.appendChild(img);
            
            // 대표 이미지 배지 추가 (첫 번째 이미지인 경우)
            if (index === 0) {
                const badge = document.createElement('span');
                badge.className = 'badge bg-primary';
                badge.textContent = '대표';
                div.appendChild(badge);
            }
            
            preview.appendChild(div);
        };
        reader.readAsDataURL(file);
    });
});

function validateForm() {
    const imageUpdateType = document.querySelector('input[name="imageUpdateType"]:checked').value;
    if (imageUpdateType === 'update') {
        const files = document.getElementById('files').files;
        if (files.length < 1 || files.length > 4) {
            alert('1~4개의 이미지를 선택해주세요.');
            return false;
        }
    }
    return true;
}
</script>
</body>
</html>
