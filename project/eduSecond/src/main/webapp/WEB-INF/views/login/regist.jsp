<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<%@include file="../main/header.jsp"%>

<div class="container mt-5 w-50" style="margin-bottom:20px;">
  <div class="card shadow-sm">
    <div class="card-body">
      <h1 class="text-center mb-4">회원가입</h1>
      <form id="registrationForm" method="post" action="/login/regist" >
        <div class="mb-3">
          <label for="userId" class="form-label">아이디</label>
          <input type="text" id="userId" name="userId" class="form-control" placeholder="영문, 숫자 포함 6~20자 입력해주세요.">
          <c:if test="${errors != null && errors.hasFieldErrors('userId')}">
            <span class="text-danger">${errors.getFieldError('userId').defaultMessage}</span>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="password" class="form-label">비밀번호</label>
          <input type="password" id="password" name="userPw" class="form-control" placeholder="영문, 숫자, 특수문자(@#$%^&+=) 포함 8~20자 입력해주세요.">
          <c:if test="${errors != null && errors.hasFieldErrors('userPw')}">
            <span class="text-danger">${errors.getFieldError('userPw').defaultMessage}</span>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="confirmPassword" class="form-label">비밀번호 확인</label>
          <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="비밀번호를 다시 한번 입력해주세요.">
          <p class="text-danger" id="confirmPasswordError" style="display: none;">비밀번호가 일치하지 않습니다.</p>
        </div>

        <div class="mb-3">
          <label for="name" class="form-label">이름</label>
          <input type="text" id="name" name="userName" class="form-control" placeholder="이름을 입력해주세요.">
          <c:if test="${errors != null && errors.hasFieldErrors('userName')}">
            <span class="text-danger">${errors.getFieldError('userName').defaultMessage}</span>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="phone" class="form-label">휴대전화번호</label>
          <input type="text" id="phone" name="userPhone" class="form-control" placeholder="000-0000-0000">
          <c:if test="${errors != null && errors.hasFieldErrors('userPhone')}">
            <span class="text-danger">${errors.getFieldError('userPhone').defaultMessage}</span>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="email" class="form-label">이메일</label>
          <input type="email" id="email" name="userEmail" class="form-control" placeholder="@ 기호 다음에는 알파벳과 숫자로 이루어진 도메인이 오게 해주세요">
          <c:if test="${errors != null && errors.hasFieldErrors('userEmail')}">
            <span class="text-danger">${errors.getFieldError('userEmail').defaultMessage}</span>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="birth" class="form-label">생년월일</label>
          <input type="date" value="${member.userBirth}" name="userBirth" id="birth" class="form-control">
          <c:if test="${errors != null && errors.hasFieldErrors('userBirth')}">
            <span class="text-danger">${errors.getFieldError('userBirth').defaultMessage}</span>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="userPostcode" class="form-label">우편번호</label>
          <div class="input-group">
            <input type="text" id="userPostcode" name="userPostcode" class="form-control" readonly required>
            <button type="button" onclick="goZip()" class="btn btn-secondary">우편번호 찾기</button>
          </div>
          <c:if test="${errors != null && errors.hasFieldErrors('userPostcode')}">
            <span class="text-danger">${errors.getFieldError('userPostcode').defaultMessage}</span>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="userAddr" class="form-label">주소</label>
          <input type="text" id="userAddr" name="userAddress" class="form-control" readonly required>
          <c:if test="${errors != null && errors.hasFieldErrors('userAddress')}">
            <span class="text-danger">${errors.getFieldError('userAddress').defaultMessage}</span>
          </c:if>
        </div>

        <button type="submit" class="btn btn-dark w-100">회원가입</button>
      </form>
    </div>
  </div>
</div>

<%@include file="../main/footer.jsp"%>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
  function goZip() {
    // 팝업 창을 명시적으로 열기
    var postcodeWindow = window.open('', '_blank', 'width=500,height=600');

    new daum.Postcode({
      oncomplete: function (data) {
        var roadAddr = data.roadAddress; // 도로명 주소
        var extraRoadAddr = ""; // 참고 항목

        // 법정동명이 있을 경우 추가
        if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
          extraRoadAddr += data.bname;
        }
        // 건물명이 있고, 공동주택일 경우 추가
        if (data.buildingName !== "" && data.apartment === "Y") {
          extraRoadAddr += (extraRoadAddr !== "" ? ", " + data.buildingName : data.buildingName);
        }
        // 참고항목이 있을 경우 괄호까지 추가
        if (extraRoadAddr !== "") {
          extraRoadAddr = " (" + extraRoadAddr + ")";
        }

        // 우편번호와 주소 정보를 해당 필드에 넣음
        document.getElementsByName("userPostcode")[0].value = data.zonecode;
        document.getElementsByName("userAddress")[0].value = roadAddr + extraRoadAddr;

        // 팝업 창 닫기
        postcodeWindow.close();
      },
      // 팝업 창 내부에 띄우기
      width: '100%',
      height: '100%'
    }).embed(postcodeWindow.document.body);
  }

  function validatePasswordMatch() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var errorMessage = document.getElementById("confirmPasswordError");

    if (password !== confirmPassword) {
      errorMessage.style.display = "block";
      return false;
    } else {
      errorMessage.style.display = "none";
      return true;
    }
  }

  document.getElementById("confirmPassword").addEventListener("keyup", function() {
    validatePasswordMatch();
  });
</script>

</body>
</html>