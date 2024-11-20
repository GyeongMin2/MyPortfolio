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
<!-- link 태그 // -->
</head>
<style>
.slider {
  position: relative;
  width: 100%;
  height: 395px;
  overflow: hidden;
  background-color:#008CD6;
  border-radius:15px
}

.slides {
  display: flex;
  transition: transform 0.5s ease-in-out;
}

.slide {
  min-width: 100%;
  box-sizing: border-box;
  padding: 20px;
  color: white;
  display:flex;
  justify-content: center;
  align-items: center
}

.nav {
  margin-top: 20px;
  display:flex;
  justify-content: center;
  position:relative;
  bottom:100px;
}

.nav-btn {
  background-color: #00c3d7;
  color: white;
  border: none;
  padding: 10px 20px;
  margin: 5px;
  cursor: pointer;
}

</style>

<body>
	<c:if test="${not empty msg}">
    	<script>
    		alert('${msg}');
    	</script>
    </c:if>
	<div class="boxContainer">
		<!-- // 상단 이미지 -->
		<jsp:include page="common/commonArea/pageTopImageArea.jsp" />
		<!-- 상단 이미지 // -->
		
		<!-- // navbar 영역 -->
		<jsp:include page="common/commonArea/header.jsp" />
		<!-- navbar 영역 // -->

		<!-- // 메인 콘텐츠 영역 -->
		<main>
			<div>
				<div class="swiper2 mySwiper underSlide">
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<a href="#"><img src="/public/mainSlider_1.png" alt="" /></a>
						</div>
						<div class="swiper-slide">
							<a href="#"><img src="/public/mainSlider_2.png" alt="" /></a>
						</div>
					</div>
				</div>
			</div>
			<div class="cheerUp">
				<div>
					<p>"수험생 여러분들! 수능 화이팅입니다!"</p>
					<span>수험 공부는 스마트 해법과 함께!</span>
				</div>
			</div>
			<div class="mainContainer1">
				<div class="sliderContainer">
					<div class="swiper mySwiper">
						<div class="swiper-wrapper">
							<div class="swiper-slide">
								<a href="#"><img
									src="https://high.milkt.co.kr/UploadFile/Banner/6b4e2b7e-462b-4940-b321-33253b7db1f920241022090551.jpg"
									alt="" /></a>
							</div>
							<div class="swiper-slide">
								<a href="#"><img
									src="https://high.milkt.co.kr/UploadFile/Banner/03b5b0f1-65f5-405c-b84d-084055ee307920241002112028.jpg"
									alt="" /></a>
							</div>
							<div class="swiper-slide">
								<a href="#"><img
									src="https://high.milkt.co.kr/UploadFile/Banner/ddb12eb2-1a52-429c-b2f1-e9a42f5fd54120240903085826.jpg"
									alt="" /></a>
							</div>
							<div class="swiper-slide">
								<a href="#"><img
									src="https://high.milkt.co.kr/UploadFile/Banner/06598473-c03b-40d6-a5ba-f5ed6ba9abb620240926124434.jpg"
									alt="" /></a>
							</div>
							<div class="swiper-slide">
								<a href="#"><img
									src="https://high.milkt.co.kr/UploadFile/Banner/8aecd4aa-431b-44d1-b730-cf81af60edac20240709091850.jpg"
									alt="" /></a>
							</div>
						</div>
						<div class="swiper-pagination"></div>
					</div>
				</div>
					<div class="sliderContainer">
					<div>
						<div class="slider">
					      <div class="slides">
					        <div class="slide">
					        <div>
					          <h2>수능 전 과목 강좌</h2>
					          <p>스마트 천재에만 있습니다.</p>
					        </div>
					        </div>
					        <div class="slide">
					        <div>
					          <h2>파이널 의대 국어 수학</h2>
					          <p>전문적인 파이널 강의</p>
					        </div>
					        </div>
					        <div class="slide">
					        <div>
					          <h2>국수영사과</h2>
					          <p>최고의 강사진과 함께하세요</p>
					        </div>
					        </div>
					      </div>
					    </div>
					    <div class="nav">
					      <button class="nav-btn" onclick="showSlide(0)">스마트 천재</button>
					      <button class="nav-btn" onclick="showSlide(1)">파이널 강의</button>
					      <button class="nav-btn" onclick="showSlide(2)">최고의 강사진</button>
					    </div>
					</div>
				</div>
			</div>
			<div class="boardGroup">
				<!-- 게시판 요약 섹션 -->
				<div class="container">
					<!-- 체험 후기 섹션 -->
					<div class="box review">
						<h2>체험 후기</h2>
						<div class="user-info">
							<img src="/public/channels4_profile.jpg" alt="User Image" />
							<div>
								<strong>26살 한덕용 학습생</strong> <span>(duk****)</span>
							</div>
						</div>
						<p>저는 프론트엔드 개발자가 되고 싶었습니다. 그런데 사실 ui 작업도 그리 싫어하는 편이 아니긴 합니다.
							아니, 나름 좋아합니다. 그런데 짧은 시간 안에 이 사이트 디자인을 다 짜려니 좀 벅차네요. 지금 새벽
							세시입니다. 살려주세요.</p>
					</div>

					<!-- 공지사항 섹션 -->
					<div class="box notice">
						<h2>공지사항</h2>
						<ul>
							<li><a href="#">[강좌] 2028 수능
									통합사회... <span>24.10.22</span>
							</a></li>
							<li><a href="#">[일반] AI 트레이닝 앰스트롱
									2학... <span>24.10.15</span>
							</a></li>
							<li><a href="#">[이벤트] AI 트레이닝 앰스트롱
									2학... <span>24.10.11</span>
							</a></li>
						</ul>
					</div>

					<!-- 고객센터 섹션 -->
					<div class="box notice" style="display:flex; justify-content: center">
						<img src="/public/userProblemCenter.png">
					</div>
				</div>
			</div>
		</main>
		<!-- 메인 콘텐츠 영역 // -->

		<!-- // 푸터 영역 -->
		<jsp:include page="common/commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
	</div>

	<script src="/jsgroup/main/script.js"></script>

	<!-- Swiper JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

	<!-- Initialize Swiper -->
	<script>
      var swiper = new Swiper(".mySwiper", {
        pagination: {
          el: ".swiper-pagination",
          dynamicBullets: true,
        },
        autoplay: {
          delay: 5000,
        },
      });
    </script>
</body>
</html>
