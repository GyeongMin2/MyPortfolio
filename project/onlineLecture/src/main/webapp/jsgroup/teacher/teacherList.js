/**
 * 
 */
function showContent(e, contentId) {
  const contents = document.querySelectorAll(".content");
  const menuItems = document.querySelectorAll(".lectureMenuBarItem");

  contents.forEach((content) => {
    content.classList.remove("active");
  });

  menuItems.forEach((item) => {
    item.classList.remove("active");
  });

  document.getElementById(contentId).classList.add("active");
  e.target.classList.add("active");
}

document.addEventListener("DOMContentLoaded", () => {
  const commentSummaries = document.querySelectorAll(".titleSummary");
  const maxLength = 15;

  commentSummaries.forEach((comment) => {
    const originalText = comment.textContent.trim();
    if (originalText.length > maxLength) {
      comment.textContent = originalText.slice(0, maxLength) + "...";
    }
  });
});
