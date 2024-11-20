<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>alert</title>
</head>
<body>
    <%
    String message = (String) request.getAttribute("msg");
    String url = (String) request.getAttribute("url");
    %>
    <script>
        alert("<%= message %>");
        window.location.href = "<%= request.getContextPath() + url %>";
    </script>
</body>
</html>
