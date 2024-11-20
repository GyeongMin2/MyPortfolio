/**
 * 
 */
function validateForm() {
  const title = document.getElementById("postTitle").value.trim();
  const content = document.getElementById("postContent").value.trim();

  if (title === "" || content === "") {
    alert("제목과 내용을 모두 입력해 주세요.");
    return false;
  }
  return true;
}

function updateCharCount(type) {
  const titleInput = document.getElementById("postTitle");
  const contentTextarea = document.getElementById("postContent");
  const titleCharCountDisplay = document.getElementById("titleCharCount");
  const contentCharCountDisplay = document.getElementById("contentCharCount");
  const maxTitleChars = 30;
  const maxContentChars = 3000;

  if (type === "title") {
    const currentTitleLength = titleInput.value.length;
    titleCharCountDisplay.textContent = `${currentTitleLength}/${maxTitleChars}`;
    titleCharCountDisplay.classList.toggle(
      "overLimit",
      currentTitleLength > maxTitleChars
    );
  } else if (type === "content") {
    const currentContentLength = contentTextarea.value.length;
    contentCharCountDisplay.textContent = `${currentContentLength}/${maxContentChars}`;
    contentCharCountDisplay.classList.toggle(
      "overLimit",
      currentContentLength > maxContentChars
    );
  }
}

function validateForm() {
  const title = document.getElementById("postTitle").value.trim();
  const content = document.getElementById("postContent").value.trim();
  const maxTitleChars = 30;
  const maxContentChars = 3000;

  if (title === "" || content === "") {
    alert("제목과 내용을 모두 입력해 주세요.");
    return false;
  }

  if (title.length > maxTitleChars) {
    alert("제목은 30자 이하로 작성해 주세요.");
    return false;
  }

  if (content.length > maxContentChars) {
    alert("내용은 3000자 이하로 작성해 주세요.");
    return false;
  }

  return true;
}
