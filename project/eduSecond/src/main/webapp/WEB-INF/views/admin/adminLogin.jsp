<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>관리자 로그인</title>
</head>
<body>
    <h2>관리자 로그인</h2>
    
    <c:if test="${error != null}">
        <div style="color: red;">${error}</div>
    </c:if>
    
    <form action="/admin/login" method="post">
        <div>
            <label for="adminId">관리자 ID</label>
            <input type="text" id="adminId" name="adminId" required>
        </div>
        
        <div>
            <label for="adminPw">비밀번호</label>
            <input type="password" id="adminPw" name="adminPw" required>
        </div>
        
        <button type="submit">로그인</button>
    </form>
</body>
</html>
