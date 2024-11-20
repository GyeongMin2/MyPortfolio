let currentIndex = 0;

function showSlide(index) {
  const slides = document.querySelector(".slides");
  const slideWidth = document.querySelector(".slider").offsetWidth;
  slides.style.transform = `translateX(${-slideWidth * index}px)`;
  currentIndex = index;
}

// --- 수능 날짜 계산 영역
const dDay = new Date("2024-11-14");

const today = new Date();

const timeDifference = dDay.getTime() - today.getTime();

const daysRemaining = Math.ceil(timeDifference / (1000 * 60 * 60 * 24));

document.getElementById("dDayCount").textContent = `D-${daysRemaining}`;
// 수능 날짜 계산 영역 ---

// --- 로그인 팝업

document.addEventListener("DOMContentLoaded", () => {
  const loginPopupButton = document.querySelector(".loginPopupButton");
  const loginContainer = document.querySelector(".loginContainer");

  function closePopup() {
    loginContainer.innerHTML = "";
    loginContainer.style.display = "none";
  }

  loginPopupButton.addEventListener("click", () => {
    loginContainer.style.display = "block";
    loginContainer.innerHTML = `
            <div class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <h2 class="modal-title">로그인</h2>
                    <form id="loginForm" method="post">
                        <input type="radio" name="userType" value="student" id="studentRadio" checked> 학생
                        <input type="radio" name="userType" value="teacher" id="teacherRadio"> 선생님 <br><br>
  
                        <label for="userId" class="login-label">아이디</label>
                        <input type="text" id="userId" name="userId" class="login-input" required><br><br>
  
                        <label for="userPwd" class="login-label">비밀번호</label>
                        <input type="password" id="userPwd" name="userPwd" class="login-input" required><br><br>
  
                        <input type="checkbox" id="userIdSave" class="login-checkbox"> 아이디 저장<br><br>
  
                        <button type="submit" class="login-button">로그인</button>
                    </form>
                    <button type="button" class="login-button" style="margin-top:20px;" onclick="goToRegister()">회원가입</button>
                </div>
            </div>
        `;

    const studentRadio = document.getElementById("studentRadio");
    const teacherRadio = document.getElementById("teacherRadio");
    const loginForm = document.getElementById("loginForm");

    loginForm.action = "/member/common/login.do";

    studentRadio.addEventListener("click", () => {
      loginForm.action = "/member/common/login.do";
    });

    teacherRadio.addEventListener("click", () => {
      loginForm.action = "/member/teacher/login.do";
    });

    var closeBtn = document.querySelector(".close");
    closeBtn.addEventListener("click", closePopup);

    window.addEventListener("click", (e) => {
      if (e.target === loginContainer) {
        closePopup();
      }
    });
  });
});

function goToRegister() {
  window.location.href = "/goto.do?page=register";
}

// 로그인 팝업 ---



