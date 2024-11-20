<%--
  Created by IntelliJ IDEA.
  User: full5-4
  Date: 2024-11-11
  Time: 오후 5:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .footer {
            background-color: #333;
            color: #ddd;
            padding: 20px 40px;
            font-size: 14px;
            min-width: 1000px;
        }

        .footer-top {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 20px 0;
        }

        .footer-logo img {
            width: 120px;
        }

        .footer-nav {
            display: flex;
            gap: 20px;
        }

        .footer-nav a {
            color: #ddd;
            text-decoration: none;
        }

        .footer-nav a:hover {
            color: #fff;
        }

        .footer-contact {
            font-size: 18px;
            color: #fff;
            font-weight: bold;
        }

        .footer-contact .phone-icon::before {
            content: "\260E"; /* Unicode character for phone icon */
            margin-right: 8px;
        }

        .footer-divider {
            border-top: 1px solid #555;
            margin: 10px 0;
        }

        .footer-bottom {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
        }

        .footer-bottom p {
            margin: 5px 0;
            font-size: 13px;
            color: #aaa;
        }

        .family-site option {
            background-color: #333;
            color: #ddd;
        }


    </style>
</head>
<body>
<footer class="footer">
    <div class="footer-top">
        <nav class="footer-nav">
            <a href="/footer/intro">회사소개</a>
            <a href="/footer/memberterms">회원약관</a>
            <a href="/footer/personalinfo">개인정보 처리방침</a>
            <a href="#">이메일 수집거부</a>
            <a href="#">고객 센터</a>
        </nav>
        <div class="footer-contact">
            <span class="footer-phone"><i class="phone-icon"></i> 1644-9897</span>
        </div>
    </div>

    <div class="footer-divider"></div>

    <div class="footer-bottom">
        <p>상호명 : 당신마켓 | 대표자 : 풀스택 7기 YB | 사업자등록번호 : 403-05-70864</p>
        <p>주소 : 서울시 금천구 가산디지털로 1319-11 마리오 아울렛 3관 1105호 | 전화 : 1644-9897 | 팩스 : 02-582-9897</p>
        <p>개인정보관리책임자 : 함창훈(haiu@naver.com)</p>
        <p>COPYRIGHT ⓒ 당신마켓. ALL RIGHTS RESERVED.</p>
    </div>
</footer>
</body>
</html>
