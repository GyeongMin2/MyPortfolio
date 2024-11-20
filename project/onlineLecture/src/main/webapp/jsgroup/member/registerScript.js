document.addEventListener("DOMContentLoaded", () => {
  const selectBtn = document.querySelector(".select-btn");

  selectBtn.addEventListener("click", () => {
    const userType = document.querySelector(
      'input[name="userType"]:checked'
    ).value;

    if (userType === "student") {
      window.location.href = "/member/common/gotoRegister.do";
    } else if (userType === "teacher") {
      window.location.href = "/member/teacher/gotoRegister.do";
    }
  });
});
