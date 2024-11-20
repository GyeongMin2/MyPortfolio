<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 관리</title>
    <style>
        /* 테이블 스타일 */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: white;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #f0f0f0;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f5f5f5;
        }

        /* 검색 폼 스타일 */
        .search-container {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .search-container select,
        .search-container input[type="text"] {
            padding: 8px;
            margin: 0 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-container button {
            padding: 8px 15px;
            background-color: #333;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-container button:hover {
            background-color: #555;
        }

        /* 버튼 스타일 */
        .action-buttons {
            margin: 20px 0;
        }
        .action-buttons button {
            padding: 8px 15px;
            margin-right: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .write-btn {
            background-color: #4CAF50;
            color: white;
        }
        .delete-btn {
            background-color: #f44336;
            color: white;
        }

        /* 페이징 스타일 */
        .pagination {
            display: flex;
            justify-content: center;
            margin: 20px 0;
            gap: 5px;
        }
        .pagination a {
            color: #333;
            padding: 8px 12px;
            text-decoration: none;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: white;
        }
        .pagination a:hover {
            background-color: #f5f5f5;
        }
        .pagination a.active {
            background-color: #333;
            color: white;
            border-color: #333;
        }

        /* 컨테이너 스타일 */
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="container">
        <h1>게시판 관리</h1>
        
        <form action="${pageContext.request.contextPath}/admin/gotoTotalPostList.do" method="get" class="search-container">
            <label for="type">타입:</label>
            <select name="type" id="type">
                <option value="" ${param.type == '' ? 'selected' : ''}>전체</option>
                <option value="P" ${param.type == 'P' ? 'selected' : ''}>일반 게시글</option>
                <option value="D" ${param.type == 'D' ? 'selected' : ''}>자료실</option>
                <option value="N" ${param.type == 'N' ? 'selected' : ''}>공지사항</option>
                <option value="C" ${param.type == 'C' ? 'selected' : ''}>강의 공지</option>
                <option value="R" ${param.type == 'R' ? 'selected' : ''}>강의 리뷰</option>
            </select>

            <label for="searchType">검색 조건:</label>
            <select name="searchType" id="searchType">
                <option value="category" ${param.searchType == 'category' ? 'selected' : ''}>강의코드</option>
                <option value="title" ${param.searchType == 'title' ? 'selected' : ''}>제목</option>
                <option value="writer" ${param.searchType == 'writer' ? 'selected' : ''}>작성자</option>
                <option value="title_content" ${param.searchType == 'title_content' ? 'selected' : ''}>제목+내용</option>
            </select>

            <label for="keyword">검색어:</label>
            <input type="text" name="keyword" id="keyword" value="${param.keyword}" />

            <button type="submit">검색</button>
        </form>

        <div class="action-buttons">
            <button class="write-btn" onclick="location.href='${pageContext.request.contextPath}/goto.do?page=post/write&type=N'">공지사항 작성</button>
            <button class="delete-btn" onclick="deleteSelected()">선택 삭제</button>
        </div>

        <table>
            <thead>
                <tr>
                    <th><input type="checkbox" id="allCheck" onclick="allChecks(this)"></th>
                    <th>번호</th>
                    <th>타입</th>
                    <th>카테고리</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>등록일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="board" items="${boardList}">
                    <tr>
                        <td><input type="checkbox" name="boardCheck" value="${board.boardIdx}"></td>
                        <td>${board.boardIdx}</td>
                        <td>
                            <c:choose>
                                <c:when test="${board.boardType == 'P'}">일반</c:when>
                                <c:when test="${board.boardType == 'D'}">자료실</c:when>
                                <c:when test="${board.boardType == 'N'}">공지</c:when>
                                <c:when test="${board.boardType == 'C'}">강의공지</c:when>
                                <c:when test="${board.boardType == 'R'}">리뷰</c:when>
                            </c:choose>
                        </td>
                        <td>${board.boardCategory}</td>
                        <td><a href="${pageContext.request.contextPath}/gotoPostDetail.do?idx=${board.boardIdx}">${board.boardTitle}</a></td>
                        <td>${board.boardWriter}</td>
                        <td>${board.boardRegdate}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty boardList}">
                    <tr>
                        <td colspan="7" style="text-align:center;">검색 결과가 없습니다.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <div class="pagination">
            <c:if test="${pagination.hasFirstPage()}">
                <a href="${pageContext.request.contextPath}/admin/gotoTotalPostList.do?pageNo=1&type=${param.type}&searchType=${param.searchType}&keyword=${param.keyword}&category=${param.category}"><<</a>
            </c:if>
            
            <c:if test="${pagination.hasPreviousBlock()}">
                <a href="${pageContext.request.contextPath}/admin/gotoTotalPostList.do?pageNo=${pagination.blockStartPage - 1}&type=${param.type}&searchType=${param.searchType}&keyword=${param.keyword}&category=${param.category}"><</a>
            </c:if>

            <c:forEach var="i" begin="${pagination.blockStartPage}" end="${pagination.blockEndPage}">
                <a href="${pageContext.request.contextPath}/admin/gotoTotalPostList.do?pageNo=${i}&type=${param.type}&searchType=${param.searchType}&keyword=${param.keyword}&category=${param.category}" 
                   class="${pagination.currentPage == i ? 'active' : ''}">${i}</a>
            </c:forEach>

            <c:if test="${pagination.hasNextBlock()}">
                <a href="${pageContext.request.contextPath}/admin/gotoTotalPostList.do?pageNo=${pagination.blockEndPage + 1}&type=${param.type}&searchType=${param.searchType}&keyword=${param.keyword}&category=${param.category}">></a>
            </c:if>
            
            <c:if test="${pagination.hasLastPage()}">
                <a href="${pageContext.request.contextPath}/admin/gotoTotalPostList.do?pageNo=${pagination.totalPages}&type=${param.type}&searchType=${param.searchType}&keyword=${param.keyword}&category=${param.category}">>></a>
            </c:if>
        </div>
    </div>

    <script>
        function allChecks(checkbox) {
            const checkboxes = document.querySelectorAll('input[name="boardCheck"]');
            checkboxes.forEach((item) => {
                item.checked = checkbox.checked;
            });
        }
        
        function deleteSelected() {
            const checkboxes = document.querySelectorAll('input[name="boardCheck"]:checked');
            const idx = Array.from(checkboxes).map(checkbox => checkbox.value);
            
            if (idx.length === 0) {
                alert('삭제할 게시글을 선택해주세요.');
                return;
            }
            
            if (confirm('선택한 게시글을 삭제하시겠습니까?')) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '${pageContext.request.contextPath}/admin/deleteList.do';
                
                const input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'idx';
                input.value = idx.join(',');
                
                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>