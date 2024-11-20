document.addEventListener("DOMContentLoaded", () => {
  const myLectureButton = document.getElementById("myLectureButton");

myLectureButton.addEventListener("click", () => {
    window.location.href = "/mypage/common/gotoMyLecture.do";
  });
});

// const searchOption = document.getElementById("searchOption");
// const searchInput = document.getElementById("searchInput");

// searchOption.addEventListener("change", function () {
//   const selectedOption = searchOption.value;

//   if (selectedOption === "board_title") {
//     searchInput.placeholder = "제목";
//   } else if (selectedOption === "board_writer") {
//     searchInput.placeholder = "아이디";
//   } else if (selectedOption === "board_content") {
//     searchInput.placeholder = "내용";
//   } else if (selectedOption === "board_title_content") {
//     searchInput.placeholder = "제목 + 내용";
//   }
// });

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

document.addEventListener("DOMContentLoaded", () => {
  const commentSummaries = document.querySelectorAll(".commentSummary");
  const maxLength = 30;

  commentSummaries.forEach((comment) => {
    const originalText = comment.textContent.trim();
    if (originalText.length > maxLength) {
      comment.textContent = originalText.slice(0, maxLength) + "...";
    }
  });
});

// 게시글 유형 바꾸기
document.addEventListener("DOMContentLoaded", function () {
    const postTypeElements = document.querySelectorAll("td.postType");

    postTypeElements.forEach((element) => {
        const postType = element.dataset.type; // dataset에서 postType을 가져옴

        let labelHtml = "";
        switch (postType) {
            case "P":
                labelHtml = '<span class="label free">자유게시판</span>';
                break;
            case "D":
                labelHtml = '<span class="label file">자료실</span>';
                break;
            case "N":
                labelHtml = '<span class="label notice">공지사항</span>';
                break;
            case "C":
                labelHtml = '<span class="label lecNotice">강의공지</span>';
                break;
            case "R":
                labelHtml = '<span class="label review">수강후기</span>';
                break;
			case "G":
				labelHtml = '<span class="label review">1대1 QnA</span>';
				break;
			case "T":
				labelHtml = '<span class="label review">선생님 QnA</span>';
				break;
            default:
                labelHtml = '<span class="label unknown">알 수 없음</span>';
                break;
        }
        element.innerHTML = labelHtml;
    });
});

// 댓글 유형 바꾸기
document.addEventListener("DOMContentLoaded", function () {
    const commentTypeElements = document.querySelectorAll("td.commentType");

    commentTypeElements.forEach((element) => {
        const type = element.dataset.type; // dataset에서 type을 가져옴

        let labelHtml = "";
        switch (type) {
            case "P":
                labelHtml = '<span class="label free">자유게시판</span>';
                break;
            case "D":
                labelHtml = '<span class="label file">자료실</span>';
                break;
            case "N":
                labelHtml = '<span class="label notice">공지사항</span>';
                break;
            case "C":
                labelHtml = '<span class="label lecNotice">강의공지</span>';
                break;
            case "R":
                labelHtml = '<span class="label review">수강후기</span>';
                break;
            default:
                labelHtml = '<span class="label unknown">알 수 없음</span>';
                break;
        }
        element.innerHTML = labelHtml;
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const lectureCodeElements = document.querySelectorAll("td.lectureCode");

    lectureCodeElements.forEach((element) => {
		// 세 글자 뜯어오기
        const code = element.dataset.code.substring(0, 3);

        let labelHtml = "";
        switch (code) {
            case "KOR":
                labelHtml = '<span class="label kor">국어</span>';
                break;
            case "MAT":
                labelHtml = '<span class="label mat">수학</span>';
                break;
            case "ENG":
                labelHtml = '<span class="label eng">영어</span>';
                break;
            case "SOC":
                labelHtml = '<span class="label soc">사회</span>';
                break;
            case "SCI":
                labelHtml = '<span class="label sci">과학</span>';
                break;
            default:
                labelHtml = '<span class="label unknown">알 수 없음</span>';
                break;
        }
        element.innerHTML = labelHtml; // 해당 요소에 HTML 추가
    });
});
