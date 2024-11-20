document.addEventListener("DOMContentLoaded", () => {
  const valMsg2 = document.querySelector(".valMsg2");
  const valMsg3 = document.querySelector(".valMsg3");
  const valMsg4 = document.querySelector(".valMsg4");
  const valMsg5 = document.querySelector(".valMsg5");
  const valMsg6 = document.querySelector(".valMsg6");
  const valMsg8 = document.querySelector(".valMsg8");

  function setMessage(msgElement, message) {
    msgElement.textContent = message;
    msgElement.style.color = "red";
  }

  function validateUserId() {
    const userId = document.getElementById("userId").value;
    const userIdRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]{4,12}$/;
    if (userId === "") {
      valMsg1.textContent = "";
      return false;
    } else if (!userIdRegex.test(userId)) {
      setMessage(
        valMsg1,
        "아이디는 영문 + 숫자 조합 4 ~ 12자리여야 하며, 영문이 포함되어야 합니다."
      );
      return false;
    }
    valMsg1.textContent = "성공";
    valMsg1.style.color = "#008cd6";
    return true;
  }

  function validateUserPwd() {
    const userPwd = document.getElementById("userPwd").value;
    const userPwdRegex =
      /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{8,20}$/;
    if (userPwd === "") {
      valMsg2.textContent = "";
      return false;
    } else if (!userPwdRegex.test(userPwd)) {
      setMessage(
        valMsg2,
        "비밀번호는 영문 + 숫자 + 특수문자 조합 8 ~ 20자리여야 합니다."
      );
      return false;
    }
    valMsg2.textContent = "성공";
    valMsg2.style.color = "#008cd6";
    return true;
  }

  function validateUserPwdCheck() {
    const userPwd = document.getElementById("userPwd").value;
    const userPwdCheck = document.getElementById("userPwdCheck").value;
    if (userPwdCheck === "") {
      valMsg3.textContent = "";
      return false;
    } else if (userPwd !== userPwdCheck) {
      setMessage(valMsg3, "비밀번호가 일치하지 않습니다.");
      return false;
    }
    valMsg3.textContent = "성공";
    valMsg3.style.color = "#008cd6";
    return true;
  }

  function validateUserName() {
    const userName = document.getElementById("userName").value;
    const userNameRegex = /^[가-힣]{2,10}$/;
    if (userName === "") {
      valMsg4.textContent = "";
      return false;
    } else if (!userNameRegex.test(userName)) {
      setMessage(valMsg4, "이름은 2 ~ 10자리 한글만 가능합니다.");
      return false;
    }
    valMsg4.textContent = "성공";
    valMsg4.style.color = "#008cd6";
    return true;
  }

  function validateUserEmail() {
    const userEmail = document.getElementById("userEmail").value;
    const userEmailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (userEmail === "") {
      valMsg5.textContent = "";
      return false;
    } else if (!userEmailRegex.test(userEmail)) {
      setMessage(valMsg5, "올바른 이메일 형식이 아닙니다.");
      return false;
    }
    valMsg5.textContent = "성공";
    valMsg5.style.color = "#008cd6";
    return true;
  }

  function validateUserPhone() {
    const userPhone = document.getElementById("userPhone").value;
    const userPhoneRegex = /^[0-9]{10,11}$/;
    if (userPhone === "") {
      valMsg6.textContent = "";
      return false;
    } else if (!userPhoneRegex.test(userPhone)) {
      setMessage(valMsg6, "전화번호는 숫자만 입력 가능합니다.");
      return false;
    }
    valMsg6.textContent = "성공";
    valMsg6.style.color = "#008cd6";
    return true;
  }

  function validateUserNickname() {
    const userNickname = document.getElementById("userNickname").value;
    const userNicknameRegex = /^[a-zA-Z가-힣0-9]{2,10}$/;
    if (userNickname === "") {
      valMsg8.textContent = "";
      return false;
    } else if (!userNicknameRegex.test(userNickname)) {
      setMessage(
        valMsg8,
        "닉네임은 2 ~ 10자리 영문, 숫자 또는 한글만 가능합니다."
      );
      return false;
    }
    valMsg8.textContent = "성공";
    valMsg8.style.color = "#008cd6";
    return true;
  }

  document.getElementById("userId").oninput = validateUserId;
  document.getElementById("userPwd").oninput = validateUserPwd;
  document.getElementById("userPwdCheck").oninput = validateUserPwdCheck;
  document.getElementById("userName").oninput = validateUserName;
  document.getElementById("userEmail").oninput = validateUserEmail;
  document.getElementById("userPhone").oninput = validateUserPhone;
  document.getElementById("userNickname").oninput = validateUserNickname;

  document.querySelector("form").addEventListener("submit", (e) => {
    if (
      !validateUserId() ||
      !validateUserPwd() ||
      !validateUserPwdCheck() ||
      !validateUserName() ||
      !validateUserEmail() ||
      !validateUserPhone() ||
      !validateUserNickname()
    ) {
      e.preventDefault();
    }
  });
});
