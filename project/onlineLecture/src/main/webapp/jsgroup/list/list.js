const searchOption = document.getElementById("searchType");
const searchInput = document.getElementById("searchKeyword");

searchOption.addEventListener("change", function() {
	const selectedOption = searchOption.value;

	if (selectedOption === "title") {
		searchInput.placeholder = "제목";
	} else if (selectedOption === "writer") {
		searchInput.placeholder = "아이디";
	} else if (selectedOption === "title_content") {
		searchInput.placeholder = "제목 + 내용";
	}
});

function goToWritePage() {
	location.href = "/inquiry/common/gotoWrite.do";
}

// 나중에 현재 페이지 찾고 색 바꾸기
const paginationButtons = document.querySelectorAll(".pagination button");

paginationButtons.forEach((button) => {
	// [1] 여기에 현재 페이지 들어가기
	if (button.innerText === "[1]") {
		button.classList.add("current-page");
	}
});

// 동적으로 바뀌게 하기
const titleElement = document.querySelector(".textCenter.boardBoxTitle");
const boardType = titleElement.getAttribute("data-board-type");
console.log("boardType:", boardType);
document.addEventListener("DOMContentLoaded", () => {
	const labelElements = document.querySelectorAll(".label");

	switch (boardType) {
		case "P":
			titleElement.textContent = "자유게시판";
			labelElements.forEach(label => {
				label.textContent = "자유게시판";
				label.className = "label free";
			});
			break;
		case "D":
			titleElement.textContent = "자료실";
			labelElements.forEach(label => {
				label.textContent = "자료실";
				label.className = "label file";
			});
			break;
		case "N":
			titleElement.textContent = "공지사항";
			labelElements.forEach(label => {
				label.textContent = "공지사항";
				label.className = "label notice";
			});
			break;
		case "C":
			titleElement.textContent = "강의 공지";
			labelElements.forEach(label => {
				label.textContent = "강의 공지";
				label.className = "label lecNotice";
			});
			break;
		case "R":
			titleElement.textContent = "수강후기";
			labelElements.forEach(label => {
				label.textContent = "수강후기";
				label.className = "label review";
			});
			break;
		case "G":
			titleElement.textContent = "1대1 QnA";
			labelElements.forEach(label => {
				label.textContent = "1대1QnA";
				label.className = "label review";
			});
			break;
		case "T":
			titleElement.textContent = "선생님 QnA";
			labelElements.forEach(label => {
				label.textContent = "선생님QnA";
				label.className = "label review";
			});
			break;
		default:
			titleElement.textContent = "error";
			labelElements.forEach(label => {
				label.textContent = "error";
				label.className = "label";
			});
	}
});

console.log("js 반영 완료");
