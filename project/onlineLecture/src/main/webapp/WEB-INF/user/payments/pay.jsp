<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>스마트 해법</title>

<!-- // link태그 -->
<link rel="stylesheet" href="<c:url value="/stylegroup/main/styles.css" />" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
<link rel="icon" href="/public/channels4_profile.jpg" type="image/png" />
<link rel="stylesheet" href="/stylegroup/payment/payment.css" />
<!-- link 태그 // -->
</head>
<body>
	<div class="boxContainer">
		<!-- // 상단 이미지 -->
		<jsp:include page="../../common/commonArea/pageTopImageArea.jsp" />
		<!-- 상단 이미지 // -->
		
		<!-- // navbar 영역 -->
		<jsp:include page="../../common/commonArea/header.jsp" />
		<!-- navbar 영역 // -->

		<!-- // 메인 콘텐츠 영역 -->
      <main class="ccenter">
        <div class="menuContainer3">
          <div class="paymentContent">
            <div>
              <div class="paymentMenu paymentMemInfo">
                <!-- 여긴 동적으로 변할 예정 -->
                <h2>장바구니</h2>
              </div>
              <div class="paymentMenu paymentMemInfo">주문/결제</div>
              <div class="paymentMenu paymentMemInfo" style="height: auto">
                <img src="/public/userProblemCenter.png" />
              </div>
              <div
                class="paymentMenu paymentMemInfo"
                style="height: 284px; border-bottom: none"
              ></div>
            </div>
            <div>
              <div class="paymentArea">
                <h3>주문/결제</h3>
                <div class="navMainBoundary"></div>
                <div class="paymentStep">
                  <img src="/public/paymentStep1.png" alt="" />
                </div>
                <ul class="liGroup">
                  <li>
                    장바구니안에 담긴 내역은 결제편의를 위해 15일 동안 보관이
                    됩니다. (15일 이상 경과된 내역은 자동 삭제)
                  </li>
                  <li>
                    교재와 배송상품을 일괄 신청하시면 배송비를 중복해서 부담하지
                    않으셔도 됩니다.
                  </li>
                  <li>
                    장바구니에서 결제된 학습과정 및 교재와 배송상품은
                    [마이페이지 > 결제정보 > 결제내역] 에서 확인 가능합니다.
                  </li>
                </ul>
                <h3>구매 목록</h3>
                <div class="navMainBoundary" style="margin-bottom: 20px"></div>
                <form action="${pageContext.request.contextPath}/payments/user/payments.do" method="POST" id="paymentForm">
                  <div class="paymentList">
                    <table class="paymentTable">
                      <thead>
                        <tr>
                          <th><input type="checkbox" id="selectAll" checked/></th>
                          <th>강의코드</th>
                          <th>강사명</th>
                          <th>강좌명</th>
                          <th>가격</th>
                          <th>삭제</th>
                        </tr>
                      </thead>
                      <tbody>
                      <c:forEach var="item" items="${cartItems}">
                        <tr>
                          <td>
                            <input
                              type="checkbox"
                              class="lectureCheckbox"
                              name="selectedLectures"
                              data-price="${item.lecturePrice}"
                              value="${item.lectureCode}" onchange="updateTotal()"
                              checked
                            />
                          </td>
                          <td>강의 코드 : ${item.lectureCode}</td>
                          <td>${item.teacherName} 선생님</td>
                          <td>
                            ${item.lectureName}
                          </td>
                          <td>
                          <span class="payValue">${item.lecturePrice}</span>원
                          </td>
                          <td>
                            <button type="button" class="deleteBtn" data-payment-idx="${item.paymentIdx}">
                              삭제
                            </button>
                          </td>
                        </tr>
                        </c:forEach>
                      </tbody>
                    </table>

                    <div class="totalPrice">
                      <span
                        >내 마일리지 : <span id="mileage">${mileage}</span>원</span
                      >
                      <span
                        >총 결제금액 : <span id="totalPayValue">0</span>원</span
                      >
                      <span
                        >결제 후 남은 마일리지 : <span id="afterMileage">0</span>원</span
                      >
                    </div>

                    <div class="actionButtons">
                      <button
                        type="button"
                        class="deleteSelected"
                        onclick="deleteSelected()"
                      >
                        선택 삭제
                      </button>
                      <button type="button" class="viewMore">
                        다른 강좌 더보기
                      </button>
                      <button type="submit" class="paySelected" id="payButton">
                        선택 상품 결제하기
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
            <div style="margin-top: 20px">
              <img src="/public/noAreaImage1.png" alt="" />
            </div>
          </div>
        </div>
      </main>
      <!-- 메인 콘텐츠 영역 // -->

		<!-- // 푸터 영역 -->
		<jsp:include page="../../common/commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
	</div>
	<script src="/jsgroup/payment/payment.js"></script>
</body>
</html>
