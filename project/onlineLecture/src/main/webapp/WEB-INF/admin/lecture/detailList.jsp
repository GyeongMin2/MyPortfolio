<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>강의 상세 내용 관리</title>
    <style>
        .detail-item {
            border: 1px solid #ddd;
            margin: 10px 0;
            padding: 15px;
        }
        .detail-content {
            margin: 10px 0;
        }
        .file-info {
            color: #666;
            font-size: 0.9em;
        }
        .form-container {
            display: none;
            margin: 20px 0;
            padding: 15px;
            border: 1px solid #ccc;
        }
        .form-group {
            margin: 10px 0;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    <h2>강의 상세 내용 관리</h2>
    <button onclick="toggleAddForm()">새 내용 추가</button>
    
    <!-- 추가 폼 -->
    <div id="addForm" class="form-container">
        <form action="${pageContext.request.contextPath}/admin/lecture/detail/manage" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="lectureCode" value="${lectureCode}">
            
            <div class="form-group">
                <label for="content">내용</label>
                <textarea name="content" id="content" required></textarea>
            </div>
            
            <div class="form-group">
                <label for="fileName">파일명</label>
                <input type="text" name="fileName" id="fileName">
            </div>
            
            <div class="form-group">
                <label for="filePath">파일 경로</label>
                <input type="text" name="filePath" id="filePath">
            </div>
            
            <div class="form-group">
                <label for="fileSize">파일 크기</label>
                <input type="number" name="fileSize" id="fileSize" value="0">
            </div>
            
            <button type="submit">추가</button>
            <button type="button" onclick="toggleAddForm()">취소</button>
        </form>
    </div>

    <!-- 상세 내용 목록 -->
    <div id="detailList">
        <c:forEach var="detail" items="${details}">
            <div class="detail-item">
                <div class="detail-content">${detail.lectureDetailContent}</div>
                <div class="file-info">
                    파일: ${detail.lectureDetailFileName} (${detail.lectureDetailFileSize} bytes)
                </div>
                <form action="${pageContext.request.contextPath}/admin/lecture/detail/manage" 
                      method="post" style="display: inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="lectureCode" value="${lectureCode}">
                    <input type="hidden" name="detailIdx" value="${detail.lectureDetailIdx}">
                    <button type="button" onclick="editDetail(this.form)">수정</button>
                    <button type="submit" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
                </form>
            </div>
        </c:forEach>
    </div>

    <script>
        function toggleAddForm() {
            const form = document.getElementById('addForm');
            form.style.display = form.style.display === 'none' ? 'block' : 'none';
        }

        function editDetail(form) {
            const detailItem = form.closest('.detail-item');
            const content = detailItem.querySelector('.detail-content').textContent;
            
            // 수정 폼으로 변경
            detailItem.innerHTML = `
                <form action="${pageContext.request.contextPath}/admin/lecture/detail/manage" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="lectureCode" value="${lectureCode}">
                    <input type="hidden" name="detailIdx" value="${form.detailIdx.value}">
                    
                    <div class="form-group">
                        <label for="content">내용</label>
                        <textarea name="content" required>${content}</textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="fileName">파일명</label>
                        <input type="text" name="fileName" value="${detail.lectureDetailFileName}">
                    </div>
                    
                    <div class="form-group">
                        <label for="filePath">파일 경로</label>
                        <input type="text" name="filePath" value="${detail.lectureDetailFilePath}">
                    </div>
                    
                    <div class="form-group">
                        <label for="fileSize">파일 크기</label>
                        <input type="number" name="fileSize" value="${detail.lectureDetailFileSize}">
                    </div>
                    
                    <button type="submit">저장</button>
                    <button type="button" onclick="location.reload()">취소</button>
                </form>
            `;
        }

        // 메시지 표시
        <c:if test="${not empty message}">
            alert('${message}');
        </c:if>
    </script>
</body>
</html>

