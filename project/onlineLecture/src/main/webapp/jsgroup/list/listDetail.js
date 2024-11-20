/**
 * 
 */
function toggleBottomBar() {
  const bottomBar = document.getElementById("bottomBar");
  if (bottomBar.style.display === "none" || bottomBar.style.display === "") {
    bottomBar.style.display = "block";
  } else {
    bottomBar.style.display = "none";
  }
}

function updateCharCount() {
  const textarea = document.getElementById("commentContent");
  const charCountDisplay = document.getElementById("charCount");
  const maxChars = 1000;

  const currentLength = textarea.value.length;

  if (currentLength > maxChars) {
    charCountDisplay.classList.add("overLimit");
  } else {
    charCountDisplay.classList.remove("overLimit");
  }

  charCountDisplay.textContent = `${currentLength}/${maxChars}`;
}

function validateComment() {
  const commentContent = document.getElementById("commentContent").value.trim();
  const maxChars = 1000;

  if (commentContent === "") {
    alert("댓글 내용을 입력해 주세요.");
    return false;
  }

  if (commentContent.length > maxChars) {
    alert("댓글은 1000자 이하로 작성할 수 있습니다.");
    return false;
  }

  return true;
}

// 동적으로 바뀌게 하기
const titleElement = document.querySelector(".boardTitle");
const boardType = titleElement.getAttribute("data-board-type");
console.log("boardType:", boardType);
document.addEventListener("DOMContentLoaded", () => {
  const labelElements = document.querySelector(".boardTitle");

  switch (boardType) {
    case "P":
      titleElement.textContent = "자유게시판";
      labelElements.forEach((label) => {
        label.textContent = "자유게시판";
        label.className = "label free";
      });
      break;
    case "D":
      titleElement.textContent = "자료실";
      labelElements.forEach((label) => {
        label.textContent = "자료실";
        label.className = "label file";
      });
      break;
    case "N":
      titleElement.textContent = "공지사항";
      labelElements.forEach((label) => {
        label.textContent = "공지사항";
        label.className = "label notice";
      });
      break;
    case "C":
      titleElement.textContent = "강의 공지";
      labelElements.forEach((label) => {
        label.textContent = "강의 공지";
        label.className = "label lecNotice";
      });
      break;
    case "R":
      titleElement.textContent = "수강후기";
      labelElements.forEach((label) => {
        label.textContent = "수강후기";
        label.className = "label review";
      });
      break;
    default:
      titleElement.textContent = "error";
      labelElements.forEach((label) => {
        label.textContent = "error";
        label.className = "label";
      });
  }
});

function confirmDelete() {
  if (confirm("정말 삭제하시겠습니까?")) {
    document.getElementById("postFrm").submit();
  } else {
    alert("삭제가 취소되었습니다.");
    return false;
  }
}
