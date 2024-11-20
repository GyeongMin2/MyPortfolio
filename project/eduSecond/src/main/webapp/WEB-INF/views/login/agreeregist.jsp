<%--
  Created by IntelliJ IDEA.
  User: full5-4
  Date: 2024-11-12
  Time: 오전 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>개인 정보 처리 방침</title>
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

    .privacy-policy {
      max-width: 800px;
      margin: 50px auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }

    .privacy-policy h1 {
      font-size: 28px;
      margin-bottom: 20px;
      text-align: center;
    }

    .policy-content h2 {
      font-size: 20px;
      margin-top: 20px;
      margin-bottom: 10px;
      color: #000000;
    }

    .policy-content p {
      margin-bottom: 10px;
      text-align: justify;
    }

    .policy-content ul {
      margin: 10px 0 20px 20px;
    }

    .policy-content li {
      margin-bottom: 5px;
    }

    table {
      width: 100%;
      margin: 20px 0;
      border-collapse: collapse;
    }

    table th, table td {
      border: 1px solid #ddd;
      padding: 10px;
      text-align: left;
    }

    table th {
      background-color: #f2f2f2;
      font-weight: bold;
    }

    table td {
      background-color: #fff;
    }
    /* 약관 동의 체크박스 스타일 */
    .checkbox-container {
      display: flex;
      align-items: center;
      margin-top: 20px;
      font-size: 16px;
    }

    .checkbox-container input[type="checkbox"] {
      width: 20px;
      height: 20px;
      margin-right: 10px;
      accent-color: #333; /* 체크박스 색상 */
    }

    .checkbox-container label {
      color: #333;
      font-weight: normal;
    }

    .checkbox-container label:hover {
      color: #555;
    }

    button {
      margin-top: 20px;
      padding: 10px 20px;
      background-color: #333;
      color: #fff;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      cursor: pointer;
    }

    button:hover {
      background-color: #555;
    }

  </style>
</head>
<body>
<%@ include file="../main/header.jsp"%>

<section class="privacy-policy">
  <form>
  <h1>약관 동의</h1>
    <div class="checkbox-container">
      <input type="checkbox" id="agreeCheckbox">
      <label for="agreeCheckbox">약관 동의</label>
    </div>
  <div class="policy-content">
    <h2>제 1 조 (개인정보의 처리 목적)</h2>
    <p>회사는 다음의 목적을 위하여 개인정보를 처리합니다. 처리한 개인정보는 다음의 목적 이외의 용도로는 사용되지 않으며 이용 목적이 변경될 시에는 사전 동의를 구할 예정입니다.</p>
    <ul>
      <li>서비스 제공에 관한 계약 이행 및 서비스 제공에 따른 요금정산</li>
      <li>회원 관리, 고객 지원</li>
      <li>신규 서비스 개발 및 마케팅 광고 활용</li>
    </ul>

    <h2>제 2 조 (개인정보의 처리 및 보유 기간)</h2>
    <p>회사는 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리 및 보유합니다.</p>

    <h2>제 3 조 (개인정보의 제3자 제공에 관한 사항)</h2>
    <p>회사는 정보주체의 동의가 있거나 법률에 특별한 규정이 있는 경우에만 개인정보를 제3자에게 제공합니다.</p>

    <h2>제 4 조 (개인정보처리의 위탁)</h2>
    <p>회사는 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.</p>
    <table>
      <thead>
      <tr>
        <th>수탁업체명</th>
        <th>위탁업무 내용</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>ABC 업체</td>
        <td>데이터 보관 및 관리</td>
      </tr>
      <tr>
        <td>XYZ 업체</td>
        <td>고객 상담 서비스</td>
      </tr>
      </tbody>
    </table>

    <h2>제 5 조 (정보주체와 법정대리인의 권리·의무 및 행사방법)</h2>
    <p>정보주체는 회사에 대해 언제든지 개인정보 열람, 정정, 삭제, 처리정지 요구 등의 권리를 행사할 수 있습니다.</p>

    <h2>제 6 조 (처리하는 개인정보 항목)</h2>
    <p>회사는 다음의 개인정보 항목을 처리하고 있습니다:</p>
    <ul>
      <li>필수 항목: 성명, 이메일 주소, 전화번호</li>
      <li>선택 항목: 주소, 생년월일</li>
    </ul>

    <h2>제 7 조 (개인정보의 파기)</h2>
    <p>회사는 개인정보 보유기간의 경과, 처리 목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체 없이 해당 개인정보를 파기합니다.</p>

    <h2>제 8 조 (개인정보의 안전성 확보 조치)</h2>
    <p>회사는 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다:</p>
    <ul>
      <li>관리적 조치: 내부관리계획 수립 및 시행, 직원 교육 등</li>
      <li>기술적 조치: 개인정보처리시스템 등의 접근 권한 관리, 보안 프로그램 설치</li>
    </ul>

    <h2>제 9 조 (개인정보 보호책임자)</h2>
    <p>회사는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다:</p>
    <p><strong>개인정보 보호책임자:</strong> 홍길동<br>
      <strong>연락처:</strong> privacy@company.com, 02-123-4567</p>
  </div>
    <br><br>
    <button type="button" onclick="goToRegistration()">회원가입 하러 가기</button>
  </form>
</section>
<%@include file="../main/footer.jsp"%>

<script>
  function goToRegistration() {
    const checkbox = document.getElementById('agreeCheckbox');
    if (!checkbox.checked) {
      alert("약관에 동의하셔야 회원가입이 가능합니다.");
    } else {
      location.href = '/login/regist';
    }
  }
</script>
</body>
</html>
