<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../common/adminHeader.jsp" %>
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>회원 관리</h2>
            
            <!-- 검색 폼 -->
            <form class="d-flex gap-2" method="get" action="/admin/member/list">
                <select name="searchCategory" class="form-select" style="width: 120px;">
                    <option value="userId" ${searchCategory == 'userId' ? 'selected' : ''}>아이디</option>
                    <option value="userName" ${searchCategory == 'userName' ? 'selected' : ''}>이름</option>
                    <option value="userEmail" ${searchCategory == 'userEmail' ? 'selected' : ''}>이메일</option>
                </select>
                <input type="text" name="searchValue" value="${searchValue}" class="form-control" placeholder="검색어 입력">
                <button type="submit" class="btn btn-dark">검색</button>
            </form>
        </div>

        <!-- 알림 메시지 -->
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- 회원 목록 테이블 -->
        <table class="table table-hover">
            <thead class="table-dark">
                <tr>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>전화번호</th>
                    <th>가입일</th>
                    <th>상태</th>
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
                        <td>${member.regDate}</td>
                        <td>
                            <c:choose>
                                <c:when test="${member.enabled eq 'Q'}">
                                    <span class="badge bg-warning">탈퇴 신청</span>
                                </c:when>
                                <c:when test="${member.enabled eq 'Y'}">
                                    <span class="badge bg-success">활성</span>
                                </c:when>
                                <c:when test="${member.enabled eq 'N'}">
                                    <span class="badge bg-danger">비활성</span>
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${member.enabled eq 'Q'}">
                                    <a href="/admin/member/withdrawal/list" 
                                       class="btn btn-sm btn-warning">
                                        탈퇴 관리
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/admin/member/status?userId=${member.userId}&enabled=${member.enabled eq 'Y' ? 'N' : 'Y'}" 
                                       class="btn btn-sm btn-outline-dark"
                                       onclick="return confirm('회원 상태를 변경하시겠습니까?')">
                                        ${member.enabled eq 'Y' ? '비활성화' : '활성화'}
                                    </a>
                                </c:otherwise>
                            </c:choose>
                            <a href="/admin/member/delete?userId=${member.userId}" 
                               class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('정말 삭제하시겠습니까?')">
                                삭제
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- 페이징 -->
        <%@ include file="../../common/paging.jsp" %>
    </div>
</body>
</html>
