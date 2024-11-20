<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>탈퇴 신청 회원 관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/adminHeader.jsp" %>
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>탈퇴 신청 회원 관리</h2>
            
            <!-- 검색 폼 -->
            <form class="d-flex gap-2" method="get" action="/admin/member/withdrawal/list">
                <select name="searchCategory" class="form-select" style="width: 120px;">
                    <option value="userId" ${searchCategory == 'userId' ? 'selected' : ''}>아이디</option>
                    <option value="userName" ${searchCategory == 'userName' ? 'selected' : ''}>이름</option>
                </select>
                <input type="text" name="searchValue" value="${searchValue}" class="form-control" placeholder="검색어 입력">
                <button type="submit" class="btn btn-dark">검색</button>
            </form>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <table class="table table-hover">
            <thead class="table-dark">
                <tr>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>전화번호</th>
                    <th>신청일</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${members}" var="member">
                    <tr>
                        <td>${member.userId}</td>
                        <td>${member.userName}</td>
                        <td>${member.userEmail}</td>
                        <td>${member.userPhone}</td>
                        <td>${member.modifyDate}</td>
                        <td>
                            <form action="/admin/member/withdrawal/process" method="post" style="display:inline;">
                                <input type="hidden" name="userId" value="${member.userId}">
                                <button type="submit" name="action" value="approve" 
                                        class="btn btn-sm btn-danger"
                                        onclick="return confirm('정말 탈퇴를 승인하시겠습니까?')">
                                    승인
                                </button>
                                <button type="submit" name="action" value="reject" 
                                        class="btn btn-sm btn-secondary"
                                        onclick="return confirm('탈퇴를 거절하시겠습니까?')">
                                    거절
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <!-- 페이징 영역 -->
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <!-- 이전 페이지 블록 버튼 -->
                <c:if test="${paging.prevBlock}">
                    <li class="page-item">
                        <a class="page-link" href="?pageNo=${paging.startBlockPage - 1}&searchCategory=${param.searchCategory}&searchValue=${param.searchValue}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <!-- 현재 페이지 블록 버튼들 -->
                <c:forEach begin="${paging.startBlockPage}" end="${paging.endBlockPage}" var="i">
                    <li class="page-item ${i == param.pageNo ? 'active' : ''}">
                        <a class="page-link" href="?pageNo=${i}&searchCategory=${param.searchCategory}&searchValue=${param.searchValue}">${i}</a>
                    </li>
                </c:forEach>

                <!-- 다음 페이지 블록 버튼 -->
                <c:if test="${paging.nextBlock}">
                    <li class="page-item">
                        <a class="page-link" href="?pageNo=${paging.endBlockPage + 1}&searchCategory=${param.searchCategory}&searchValue=${param.searchValue}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</body>
</html>
