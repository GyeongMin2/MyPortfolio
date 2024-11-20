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
<link rel="stylesheet" href="/stylegroup/teacher/teacherList.css" />
<!-- link 태그 // -->
</head>
<body>
	<div class="boxContainer">
		<!-- // 상단 이미지 -->
		<jsp:include page="../commonArea/pageTopImageArea.jsp" />
		<!-- 상단 이미지 // -->
		
		<!-- // navbar 영역 -->
		<jsp:include page="../commonArea/header.jsp" />
		<!-- navbar 영역 // -->

		<!-- // 메인 콘텐츠 영역 -->
      <main>
        <h1 class="textCenter" style="margin-bottom: 30px">선생님</h1>
        <div class="ccenter">
          <div class="mainBox">
            <h3>과목별 선생님</h3>
            <p class="lectureContainerInfo">
              과목별로 선생님을 확인할 수 있습니다.
            </p>
            <div class="lectureContainer">
              <div class="lectureMenuBar">
                <div
                  class="lectureMenuBarItem ${subject == 'kor' ? 'active' : ''}"
                  onclick="showContent(event, 'content1')"
                >
                  국어
                </div>
                <div
                  class="lectureMenuBarItem ${subject == 'math' ? 'active' : ''}"
                  onclick="showContent(event, 'content2')"
                >
                  수학
                </div>
                <div
                  class="lectureMenuBarItem ${subject == 'eng' ? 'active' : ''}"
                  onclick="showContent(event, 'content3')"
                >
                  영어
                </div>
                <div
                  class="lectureMenuBarItem ${subject == 'soc' ? 'active' : ''}"
                  onclick="showContent(event, 'content4')"
                >
                  사탐
                </div>
                <div
                  class="lectureMenuBarItem ${subject == 'sci' ? 'active' : ''}"
                  onclick="showContent(event, 'content5')"
                >
                  과탐
                </div>
              </div>
              <div>
                <div id="content1" class="content ${subject == 'kor' ? 'active' : ''}">
                  <div class="teacherGroupArea">
                    <a href="/gotoTeacherInfo.do?teacherId=user_kor1">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/%EA%B5%AD%EC%96%B4_%EA%B6%8C%EC%88%9C%EA%B7%9C_1.png"
                          alt="권순구 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>권순구 선생님</h3>
                          <p>권순구의 정의</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_kor2">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/665_%EC%86%90%EC%9C%A0%EB%B9%88_A%ED%98%95_200x275.png"
                          alt="손유빈 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>손유빈 선생님</h3>
                          <p>문학의 감상</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_kor3">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/2020-09-07.png"
                          alt="엄은나 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>엄은나 선생님</h3>
                          <p>문학의 감상</p>
                        </div>
                      </div>
                    </a>
                  </div>
                </div>

                <!-- 수학 -->
                <div id="content2" class="content ${subject == 'math' ? 'active' : ''}">
                  <div class="teacherGroupArea">
                    <a href="/gotoTeacherInfo.do?teacherId=user_math1">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/%EC%88%98%ED%95%99_%EB%B0%95%EC%A0%95%EC%9D%80_Img13.png"
                          alt="박정은 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>박정은 선생님</h3>
                          <p>함수의 극한</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_math2">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/354_%EC%A0%84%EC%A4%80%ED%99%8D_Img13.png"
                          alt="전준홍 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>전준홍 선생님</h3>
                          <p>경우의 수 구하기</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_math3">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/kanghyonh_Img13.png"
                          alt="강력한 형제 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>강력한 형제 선생님</h3>
                          <p>함수의 극한</p>
                        </div>
                      </div>
                    </a>
                  </div>
                </div>

                <!-- 영어 -->
                <div id="content3" class="content ${subject == 'eng' ? 'active' : ''}">
                  <div class="teacherGroupArea">
                  	<a href="/gotoTeacherInfo.do?teacherId=user_eng1">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/%EA%B3%A0%EB%93%B1%EC%9B%B9_%EC%84%A0%EC%83%9D%EB%8B%98_%EC%98%81%EC%96%B4_%EA%B9%80%EC%97%B0%ED%95%98.jpg"
                          alt="김연하 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>김연하 선생님</h3>
                          <p>영어의 응용</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_eng2">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/356_%EA%B6%8C%EC%98%A4%EB%A5%9C_img13.jpg"
                          alt="권오륜 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>권오륜 선생님</h3>
                          <p>영어의 기초</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_eng3">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/336_%EC%9C%A4%EB%B9%88_Img13.jpg"
                          alt="윤빈 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>윤빈 선생님</h3>
                          <p>영어의 응용</p>
                        </div>
                      </div>
                    </a>
                  </div>
                </div>

                <!-- 사회 -->
                <div id="content4" class="content ${subject == 'soc' ? 'active' : ''}">
                  <div class="teacherGroupArea">
                    <a href="/gotoTeacherInfo.do?teacherId=user_soc1">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/401_%EA%B9%80%EB%8F%84%ED%98%95_img13.png"
                          alt="한소유 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>한소유 선생님</h3>
                          <p>사회탐구의 기초</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_soc2">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/361_%EC%B0%A8%EB%AA%A9%EC%96%B8_Img13.png"
                          alt="차목언 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>차목언 선생님</h3>
                          <p>사회 문제 해결</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_soc3">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/%EC%82%AC%ED%9A%8C_%EB%AC%B8%EB%B3%91%EC%9D%BC.png"
                          alt="문병일 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>문병일 선생님</h3>
                          <p>사회 문제 해결</p>
                        </div>
                      </div>
                    </a>
                  </div>
                </div>

                <!-- 과학 -->
                <div id="content5" class="content ${subject == 'sci' ? 'active' : ''}">
                  <div class="teacherGroupArea">
                    <a href="/gotoTeacherInfo.do?teacherId=user_sci1">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/%EA%B3%BC%ED%95%99%ED%83%90%EA%B5%AC_%ED%99%A9%EC%9C%A0%ED%99%94.jpg"
                          alt="황유하 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>황유하 선생님</h3>
                          <p>물리 탐구</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_sci2">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/%EC%84%A0%EC%83%9D%EB%8B%98%EB%A9%94%EC%9D%B8_%EA%B3%BC%ED%95%99-%EC%A0%95%EA%B5%AC%EB%A5%9C.png"
                          alt="정구륜 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>정구륜 선생님</h3>
                          <p>화학 기초</p>
                        </div>
                      </div>
                    </a>
                    <a href="/gotoTeacherInfo.do?teacherId=user_sci3">
                      <div class="teacherCard">
                        <img
                          src="https://high.milkt.co.kr/UploadFile/LecturerImg/%EA%B3%BC%ED%95%99%ED%83%90%EA%B5%AC_%EB%A7%88%EC%A7%84%ED%98%B8.jpg"
                          alt="마진호 선생님"
                          class="teacherImage"
                        />
                        <div class="cardContent">
                          <h3>마진호 선생님</h3>
                          <p>화학 기초</p>
                        </div>
                      </div>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
      <!-- 메인 콘텐츠 영역 // -->

		<!-- // 푸터 영역 -->
		<jsp:include page="../commonArea/footer.jsp" />
		<!-- 푸터 영역 // -->
		
		<script src="/jsgroup/teacher/teacherList.js"></script>
	</div>
</body>
</html>
