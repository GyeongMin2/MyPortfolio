<%--
  Created by IntelliJ IDEA.
  User: full5-4
  Date: 2024-11-12
  Time: 오전 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 약관</title>
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
    }

    .terms-section {
      max-width: 800px;
      margin: 50px auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }

    .terms-section h1 {
      font-size: 28px;
      margin-bottom: 20px;
      text-align: center;
    }

    .terms-content h2 {
      font-size: 20px;
      margin-top: 20px;
      margin-bottom: 10px;
      color: #000000;
    }

    .terms-content p {
      margin-bottom: 10px;
      text-align: justify;
    }

    .terms-content ol, .terms-content ul {
      margin: 10px 0 20px 20px;
    }

    .terms-content li {
      margin-bottom: 5px;
    }

  </style>
</head>
<body>
<%@ include file="../main/header.jsp"%>

<section class="terms-section">
  <h1>이용약관</h1>
  <div class="terms-content">
    <h2>제 1 조 (목적)</h2>
    <p>본 약관은 "회사"가 제공하는 서비스의 이용과 관련하여 "회사"와 회원 간의 권리, 의무 및 책임 사항을 규정함을 목적으로 합니다.</p>

    <h2>제 2 조 (용어의 정의)</h2>
    <p>이 약관에서 사용하는 주요 용어의 정의는 다음과 같습니다.</p>
    <ol>
      <li>"서비스"란 "회사"가 제공하는 모든 온라인 서비스를 의미합니다.</li>
      <li>"회원"이란 "회사"와 서비스 이용 계약을 체결하고 이용 자격을 부여받은 자를 의미합니다.</li>
      <li>"아이디(ID)"란 회원 식별과 서비스 이용을 위하여 회원이 설정하고 회사가 승인한 고유한 문자와 숫자의 조합을 의미합니다.</li>
    </ol>

    <h2>제 3 조 (약관의 게시와 개정)</h2>
    <p>1. "회사"는 본 약관의 내용을 회원이 쉽게 알 수 있도록 "서비스" 초기 화면에 게시합니다.</p>
    <p>2. "회사"는 "약관의 규제에 관한 법률", "정보통신망 이용촉진 및 정보보호 등에 관한 법률" 등을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다.</p>

    <h2>제 4 조 (서비스의 제공 및 변경)</h2>
    <p>1. "회사"는 다음과 같은 업무를 수행합니다:</p>
    <ul>
      <li>정보 제공 서비스</li>
      <li>컨텐츠 제공 서비스</li>
      <li>기타 "회사"가 정하는 서비스</li>
    </ul>
    <p>2. "회사"는 서비스의 일부 또는 전부를 변경할 수 있으며, 이러한 변경 사항은 회원에게 공지합니다.</p>

    <h2>제 5 조 (서비스 이용의 제한 및 중지)</h2>
    <p>"회사"는 다음 각 호에 해당하는 경우 서비스 제공을 제한하거나 중지할 수 있습니다.</p>
    <ul>
      <li>설비 점검, 교체, 고장 및 통신 두절 등의 부득이한 사유가 있는 경우</li>
      <li>회원이 "회사"의 서비스 운영을 방해하는 경우</li>
      <li>기타 중대한 이유로 인해 서비스 제공이 불가능하다고 판단되는 경우</li>
    </ul>

    <h2>제 6 조 (회원 가입)</h2>
    <p>1. 회원으로 가입하고자 하는 자는 "회사"가 정한 가입 양식에 따라 회원 정보를 기입하고 가입을 신청해야 합니다.</p>
    <p>2. "회사"는 전 항과 같이 회원으로 가입할 것을 신청한 자가 다음 각 호에 해당하지 않는 한 회원으로 등록합니다:</p>
    <ul>
      <li>가입 신청자가 이전에 회원 자격을 상실한 적이 있는 경우</li>
      <li>기타 회원으로 등록하는 것이 "회사"의 기술상 현저히 지장이 있다고 판단되는 경우</li>
    </ul>

    <h2>제 7 조 (회원 탈퇴 및 자격 상실)</h2>
    <p>1. 회원은 "회사"에 언제든지 탈퇴를 요청할 수 있으며, "회사"는 즉시 회원 탈퇴를 처리합니다.</p>
    <p>2. 회원이 다음 각 호에 해당하는 경우, "회사"는 회원 자격을 제한하거나 상실시킬 수 있습니다:</p>
    <ul>
      <li>가입 신청 시 허위 내용을 등록한 경우</li>
      <li>다른 사람의 서비스 이용을 방해하거나 그 정보를 도용하는 경우</li>
    </ul>
  </div>
</section>

<%@ include file="../main/footer.jsp"%>
</body>
</html>
