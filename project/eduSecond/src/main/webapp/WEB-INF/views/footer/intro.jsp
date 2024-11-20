<%--
  Created by IntelliJ IDEA.
  User: full5-4
  Date: 2024-11-11
  Time: 오후 8:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: Arial, sans-serif;
      background-color: #f5f5f5;
      color: #333;
      text-align: center;
    }

    .tab-container {
      margin-top: 20px;
    }

    .tab {
      display: flex;
      justify-content: center;
      border-bottom: 2px solid #ddd;
    }

    .tab-button {
      padding: 10px 20px;
      font-size: 16px;
      border: none;
      background-color: #fff;
      cursor: pointer;
      outline: none;
      transition: background-color 0.3s;
    }

    .tab-button.active {
      background-color: #ff3d3d;
      color: #fff;
    }

    .tab-content {
      display: none;
      padding: 20px;
    }

    .tab-content.active {
      display: block;
    }

    .section-title {
      font-size: 24px;
      margin: 30px 0;
      color: #333;
    }

    .banner {
      position: relative;
      margin: 20px auto;
      width: 80%;
      max-width: 800px;
    }

    .banner img {
      width: 100%;
      border-radius: 5px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }

    .banner-text {
      position: absolute;
      bottom: 20px;
      left: 20px;
      color: #fff;
      text-shadow: 0px 2px 4px rgba(0, 0, 0, 0.6);
    }

    .banner-text h2 {
      font-size: 36px;
      margin-bottom: 5px;
    }

    .banner-text p {
      font-size: 16px;
    }

    .content {
      margin: 40px auto;
      width: 80%;
      max-width: 800px;
      text-align: left;
      color: #333;
    }

    .content h3 {
      font-size: 20px;
      color: #444;
      margin-top: 30px;
    }

    .content p {
      font-size: 16px;
      line-height: 1.6;
      margin-top: 10px;
    }
    /*성장을 보여주는 그래프 부분*/

    .timeline {
      text-align: center;
      padding: 50px 20px;
    }

    .timeline h2 {
      font-size: 24px;
      margin-bottom: 40px;
    }

    .timeline-container {
      display: flex;
      justify-content: space-around;
      align-items: flex-start;
      position: relative;
      max-width: 1200px;
      margin: 0 auto;
      padding-top: 40px;
    }

    .timeline-item {
      position: relative;
      text-align: center;
      width: 20%;
    }

    .timeline-item .circle {
      width: 10px;
      height: 10px;
      background-color: rgba(0, 0, 0, 0.66);
      border-radius: 50%;
      margin: 0 auto 10px auto;
    }

    .timeline-item .year {
      font-size: 18px;
      color: rgba(0, 0, 0, 0.66);
      font-weight: bold;
      margin-bottom: 10px;
    }

    .timeline-item img {
      width: 150px;
      height: auto;
      margin-bottom: 10px;
    }

    .timeline-item p {
      font-size: 14px;
    }

    .timeline::after {
      content: "";
      position: absolute;
      top: 20px;
      left: 10%;
      width: 80%;
      height: 2px;
      background-color: #ff4a4a;
      z-index: -1;
    }


    /*하단 배너*/
    .description {
      background-image: url('<%= request.getContextPath() %>/resources/images/introbanner/bannerintro.png');
      background-size: cover;
      background-position: center;
      color: #fff;
      padding: 60px 20px;
      text-align: center;
      position: relative;
    }

    .description::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.6); /* 어두운 배경 오버레이 */
      z-index: 1;
    }

    .description-content {
      position: relative;
      z-index: 2;
      max-width: 800px;
      margin: 0 auto;
      line-height: 1.8;
      font-size: 16px;
    }

    .description-content p {
      margin-bottom: 20px;
    }

    .description-content strong {
      color: #000000; /* 강조된 연도 색상 */
    }


    .organization-chart {
      text-align: center;
      padding: 50px 20px;
    }

    .organization-chart h2 {
      font-size: 24px;
      margin-bottom: 40px;
    }

    .chart-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 35px;
    }

    .ceo {
      background-color: #fff;
      color: #000000;
      border: 2px solid rgba(0, 0, 0, 0.66);
      border-radius: 50%;
      width: 100px;
      height: 100px;
      line-height: 100px;
      font-weight: bold;
      margin-bottom: 20px;
    }

    .department-container {
      display: flex;
      justify-content: center;
      gap: 40px;
      flex-wrap: wrap;
    }

    .department {
      background-color: #f2f2f2;
      color: #333;
      padding: 20px;
      border-radius: 25px;
      position: relative;
      width: 180px;
      text-align: center;
    }

    .sub-department {
      background-color: rgba(0, 0, 0, 0.66);
      color: #fff;
      margin-top: 10px;
      padding: 10px;
      border-radius: 15px;
    }

    .management-vision {
      text-align: center;
      padding: 60px 20px;
      background-color: #f9f9f9;
    }

    .management-vision p {
      max-width: 800px;
      margin: 0 auto 20px auto;
      line-height: 1.6;
      font-size: 16px;
    }

    .management-vision h3 {
      font-size: 20px;
      margin: 40px 0 20px;
    }

    .management-vision h4 {
      font-size: 18px;
      margin: 20px 0;
    }

    .vision-container {
      display: flex;
      justify-content: center;
      gap: 20px;
      flex-wrap: wrap;
    }

    .vision-item {
      padding: 15px 30px;
      border-radius: 25px;
      font-weight: bold;
      border: 2px solid rgb(0, 0, 0);
      color: rgba(0, 0, 0, 0.66);
      background-color: #fff;
      transition: background-color 0.3s, color 0.3s;
    }

    .vision-item:hover {
      background-color: rgba(0, 0, 0, 0.66);
      color: #fff;
    }


  </style>
</head>
<body>
<%@include file="../main/header.jsp"%>

<%--<div class="tab-container">--%>
<%--  <div class="tab">--%>
<%--    <button class="tab-button active" onclick="showTab('company')">회사소개</button>--%>
<%--    <button class="tab-button" onclick="showTab('brand')">브랜드소개</button>--%>
<%--  </div>--%>
<%--</div>--%>

<section id="company" class="tab-content active">
  <h1 class="section-title">회사소개</h1>

  <div class="banner">
    <img src="<%= request.getContextPath() %>/resources/images/introbanner/intro2.png">
  </div>

  <div class="content">
    <h3>믿음을 주는</h3>
    <p>당신마켓은 누구나 손쉽게 교사가 필요한 물품들을 중고거래를 하도록 혁신적인 시스템을 만들고 고객을 위한 가치창출과 도전정신으로 미래를 선도하는 기업으로 성장하겠습니다.</p>

    <h3>당신을 위한</h3>
    <p>국내 최대 중고 매매 시스템과 업계 최고의 노하우를 가지고 고객에게 무한한 신뢰와 감동을 서비스하겠습니다. 한달 평균 약 2,400건 중고거래를 하고 있습니다. 2020년 기준 총 약 270,000명의 고객이 당신마켓을 이용하고 있습니다.</p>
  </div>
</section>


<section class="timeline">
  <h2>연혁</h2>
  <hr>
  <div class="timeline-container">
    <div class="timeline-item">
      <div class="circle"></div>
      <div class="year">2011</div>
      <img src="<%= request.getContextPath() %>/resources/images/introbanner/intromin2.png" alt="2011 Image">
      <p>교사 중고거래 플렛폼 전문회사 설립</p>
    </div>
    <div class="timeline-item">
      <div class="circle"></div>
      <div class="year">2014</div>
      <img src="<%= request.getContextPath() %>/resources/images/mainbanner/logo.png" alt="2014 Image">
      <p>중고 거래전문 당신마켓 상호 변경</p>
    </div>
    <div class="timeline-item">
      <div class="circle"></div>
      <div class="year">2018</div>
      <img src="<%= request.getContextPath() %>/resources/images/introbanner/intromin3.png" alt="2018 Image">
      <p>수도권 지점 확대</p>
    </div>
    <div class="timeline-item">
      <div class="circle"></div>
      <div class="year">2020</div>
      <img src="<%= request.getContextPath() %>/resources/images/introbanner/intromin4.png" alt="2020 Image">
      <p>270,000명의 고객 이용</p>
    </div>
  </div>
</section>

<section class="description">
  <div class="description-content">
    <p><strong>2011년</strong> 현재는 국내 최대의 선생님 들을 위한 중고 거래 시스템회사로 변화하고 있습니다.</p>
    <p><strong>2014년</strong> 본격적인 중고거래를 위한 전문회사를 설립하고 꾸준하게 지점을 확대하고 있으며</p>
    <p><strong>2016년부터 2020년까지</strong> 총 5년 연속 대한민국 고객만족도 브랜드 “중고 교과서 판매” 부문을 수상했습니다.</p>
    <p><strong>2020년</strong> 투명한 중고거래를 위한 매입 시스템을 개편하고 고객에게 편리한 중고거래 서비스를 제공하고 있습니다.</p>
    <p>앞으로도 국내 최고의 선생님을 위한 중고 거래 플랫폼을 구축하기 위해 더욱 노력하겠습니다.</p>
  </div>
</section>


<section class="organization-chart">
  <h2>조직도</h2>
  <hr>
  <div class="chart-container">
    <div class="ceo">CEO</div>
    <div class="department-container">
      <div class="department">경영지원본부</div>
      <div class="department">시스템 사업본부
        <div class="sub-department">교육운영팀</div>
        <div class="sub-department">기술지원팀</div>
      </div>
      <div class="department">직영점 운영본부
        <div class="sub-department">직영점</div>
      </div>
      <div class="department">지점 운영본부
        <div class="sub-department">지점 운영</div>
      </div>
      <div class="department">마케팅 운영본부
        <div class="sub-department">온라인마케팅</div>
      </div>
    </div>
  </div>
</section>

<section class="management-vision">
  <p>
    내면에 책임을 다하는 문화, 스스로 경쟁하고 선택하는 본인 전략들을 빠르고 신속히 판단과 업무로 속도감 있는 조직체계를 갖춘 기업입니다. 관리 중심에서 일 중심으로 변화하고 고객에게 믿음과 신뢰감을 주는 고객만족을 목표로 하는 회사입니다.
  </p>
  <h3>경영이념</h3>
  <hr style="margin-bottom: 25px;">
  <div class="banner">
    <img src="<%= request.getContextPath()%>/resources/images/introbanner/footbanner.png" alt="IT Shop Cityscape Banner">
  </div>
  <p>
    저희 당신마켓은 "고객중심을 바탕으로 최고의 신뢰와 정직함으로 서비스를 제공하며 함께 성장한다."라는 경영이념이 있습니다.
    당신마켓은 빠르게 성장하고 있는 사이트로 새로운 멀티 플랫폼을 항상 관련된 온/오프라인 채널을 통해 세계적인 중고 거래 플랫폼으로 도약하고자 노력하고 있으며, 중고거래 및 서비스를 추가한 수입을 확장하여 더 많은 기업들에게 도움을 주려고 합니다.
  </p>
  <h4>Vision</h4>
  <div class="vision-container">
    <div class="vision-item">고객중심</div>
    <div class="vision-item">내실경영</div>
    <div class="vision-item">지속성장</div>
    <div class="vision-item">기술혁신</div>
    <div class="vision-item">사회공헌</div>
  </div>
</section>

<section class="timeline">
  <h2>찾아오는 길</h2>
  <hr>
  <div class="banner">
    <div id="map" style="width:100%;height:400px;"></div>
  </div>


  <p>
    도로명 : 서울 금천구 벚꽃로 266 마리오아울렛3관 지번 : 가산동 60-20 우편번호 : 08511
  </p>
  <p style="text-align: left">
    17가산디지털단지역 3번 출구에서 247m
  </p>

</section>
<%--<section id="brand" class="tab-content">--%>
<%--  <h1 class="section-title">브랜드소개</h1>--%>
<%--  <p>브랜드 소개 내용이 여기에 표시됩니다.</p>--%>
<%--</section>--%>
<%@ include file="../main/footer.jsp"%>

<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=iaof4vxmpl"></script>
<script>
  var mapOptions = {
    center: new naver.maps.LatLng(37.4787, 126.8866),
    zoom: 15
  };

  var map = new naver.maps.Map('map', mapOptions);

  // Create custom marker with arrow icon
  var marker = new naver.maps.Marker({
    position: new naver.maps.LatLng(37.4787, 126.8866), // Replace with your desired marker coordinates
    map: map,
    icon: {
      url: 'https://path-to-your-arrow-icon.png', // Replace with the URL of your arrow icon
      size: new naver.maps.Size(50, 50),          // Adjust the icon size
      scaledSize: new naver.maps.Size(50, 50),     // Scale to fit the icon
      origin: new naver.maps.Point(0, 0),
      anchor: new naver.maps.Point(25, 50)         // Adjust the anchor point to position the arrow correctly
    }
  });
</script>
<script>
  function showTab(tabId) {
    const tabs = document.querySelectorAll('.tab-content');
    const buttons = document.querySelectorAll('.tab-button');

    tabs.forEach(tab => {
      tab.classList.remove('active');
    });

    buttons.forEach(button => {
      button.classList.remove('active');
    });

    document.getElementById(tabId).classList.add('active');
    document.querySelector(`.tab-button[onclick="showTab('${tabId}')"]`).classList.add('active');
  }
</script>


</body>
</html>
