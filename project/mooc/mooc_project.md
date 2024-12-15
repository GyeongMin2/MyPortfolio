## 주요 소스코드

## 환경 설정
- **JDK 버전** : 17 이상
- **MySQL 버전** : 9.0.1
- **MariaDB 버전** : 10.11.1

## 주요 의존성 및 버전
- **Spring Boot** : 3.3.6
- **QueryDSL** : 5.1.0
- **commons-io** : 2.11.0
- **JUnit** : 5.10.2
- **MyBatis** : 3.0.3

### 파일 업로드/다운로드 시스템

#### 1. 파일 설정 (FileConfig.java)
Spring의 리소스 핸들러를 설정하여 업로드된 파일 접근 경로를 지정합니다.
```java
@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Value("${file.upload.path}")
    private String uploadPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/")
        .addResourceLocations("file:" + uploadPath + "/");
    }
}
```
#### 2. 파일 확장자 제한 (FileConstants.java)
허용된 파일 확장자를 상수로 정의하여 보안 강화
```java
public class FileConstants {
    public static final String[] ALLOWED_VIDEO_EXTENSIONS = {"mp4","mov"};
    public static final String[] ALLOWED_DOCUMENT_EXTENSIONS = {
        "pdf", "ppt", "pptx", "xls", "xlsx", "doc", "docx"
    };
    public static final String[] ALLOWED_IMAGE_EXTENSIONS = {
        "jpg", "jpeg", "png", "gif"
    };
}
```
#### 3. 파일 업로드 JavaScript 처리
```javascript
async function uploadContent(formData) {
    try {
        const response = await fetch('/api/teacher/contents', {
            method: 'POST',
            body: formData
        });
    if (!response.ok) {
    const error = await response.json();
        throw new Error(error.message);
        }
        return await response.json();
    } catch (error) {
        console.error('Upload failed:', error);
        throw error;
    }
}

function previewContent(input) {
    const file = input.files[0];
    if (!file) return;
    const reader = new FileReader();
    const previewContainer = input.nextElementSibling;
    const videoPreview = previewContainer.querySelector('video');
    const imagePreview = previewContainer.querySelector('img');
    reader.onload = function(e) {
        if (file.type.startsWith('video/')) {
            videoPreview.classList.remove('d-none');
            imagePreview.classList.add('d-none');
            videoPreview.src = e.target.result;
        } else {
            imagePreview.classList.remove('d-none');
            videoPreview.classList.add('d-none');
            imagePreview.src = e.target.result;
        }
    };
    reader.readAsDataURL(file);
}
```

### 강좌 관리 시스템

#### 1. 강좌 CRUD 기능

##### 강좌 생성 및 수정
```java
public Course createCourse(CourseCreateDTO dto, Teacher teacher) throws IOException {
    Subject subject = subjectRepository.findBySubjectId(dto.getSubjectId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과목입니다."));
    
    String thumbnailPath = fileUploadUtil.uploadImageFile(dto.getThumbnail(), "thumbnails");
    
    Course course = Course.builder()
        .title(dto.getTitle())
        .subject(subject)
        .weeks(dto.getWeeks())
        .learningTime(dto.getLearningTime())
        .language(dto.getLanguage())
        .description(dto.getDescription())
        .isCreditBank(dto.getIsCreditBank())
        .thumbnail(thumbnailPath.replace("\\", "/"))
        .teacher(teacher)
        .status("DRAFT")
        .viewCount(0)
        .createdAt(LocalDateTime.now())
        .build();
    
    return courseRepository.save(course);
}
```
##### 강좌 조회
```java
@Transactional(readOnly = true)
public CourseDetailDTO getCourseWithContents(int courseId) {
    Course course = courseRepository.findById(courseId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));
    
    List<LectureDTO> lectureDTOs = course.getLectures().stream()
        .map(lecture -> {
            // 강의 콘텐츠와 퀴즈 정보 매핑
            ...
        })
        .collect(Collectors.toList());
    
    return CourseDetailDTO.builder()
        .courseId(course.getCourseId())
        .title(course.getTitle())
        .description(course.getDescription())
        .thumbnail(course.getThumbnail())
        // ... 기타 필드 매핑
        .lectures(lectureDTOs)
        .build();
}
```
#### 2. 권한 관리 시스템
```java
  //권한 체크
  //CourseService.java
  public boolean checkAuthority(int id, Teacher teacher, String type) {
      switch (type) {
          case "course":
              return courseRepository.findById(id).get().getTeacher().getTeacherId().equals(teacher.getTeacherId());
          case "lecture":
              return lectureRepository.findById(id).get().getCourse().getTeacher().getTeacherId().equals(teacher.getTeacherId());
          case "content":
              return lectureContentRepository.findById(id).get().getLecture().getCourse().getTeacher().getTeacherId().equals(teacher.getTeacherId());
          case "quiz":
              return quizRepository.findById(id).get().getLecture().getCourse().getTeacher().getTeacherId().equals(teacher.getTeacherId());
          default:
              log.info("알맞은 권한 체크 타입이 아닙니다.");
              return false;
      }
  }
  //CourseEnrollmentServiceImpl.java
  public boolean checkAuthority(String memberId, int courseId) {
      Member member = memberRepository.findByMemberId(memberId)
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
      Course course = courseRepository.findById(courseId)
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));
      // 수강중인 강의인지 확인
      return courseEnrollmentRepository.findByCourseAndMember(course, member).isPresent();
  }
```

#### 주요 기능
- **강좌 관리**
  - 강좌 생성, 수정, 삭제, 조회 기능
  - 강좌 상태 관리 (DRAFT/PUBLISHED)
  - 섬네일 이미지 업로드 처리
  
- **강의 콘텐츠 관리**
  - RESTful API 기반의 강의 콘텐츠 조회 등록 수정 삭제 처리
  - 비디오/문서 파일 업로드 처리
  - 퀴즈 생성 및 관리

- **권한 관리**
  - 강사별 강좌 접근 권한 확인
  - 콘텐츠 수정/삭제 권한 검증
  
- **파일 처리**
  - 동영상/문서/이미지 등 다양한 파일 형식 지원
  - 파일 업로드 시 확장자 검증을 통한 보안 강화
  - 파일 저장 경로 관리

- **트랜잭션 관리**
  - 읽기 전용 트랜잭션 최적화
  - 데이터 정합성 보장

### Class Diagram & ERD
![classDiagram](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/mooc/mooc_classdiagram.png)

![erd](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/mooc/mooc_ERD.png)

### api 명세서
[@api 문서 markdown](https://github.com/KmoocProject/mooc/blob/main/project_note/api/api.md)
![api-image-1](https://github.com/KmoocProject/mooc/blob/main/project_note/api/api1.png)
![api-image-2](https://github.com/KmoocProject/mooc/blob/main/project_note/api/api2.png)
