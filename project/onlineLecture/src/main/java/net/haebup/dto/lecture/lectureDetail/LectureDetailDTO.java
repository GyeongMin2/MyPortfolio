package net.haebup.dto.lecture.lectureDetail;

public class LectureDetailDTO {
	/**
	 * -- haebup.TBL_LECTURE_DETAIL definition
	 * 
	 * CREATE TABLE `TBL_LECTURE_DETAIL` (
	 * `lecture_detail_idx` int NOT NULL AUTO_INCREMENT COMMENT '강의 상세 아이디',
	 * `lecture_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
	 * NOT NULL COMMENT '강의 코드',
	 * `lecture_detail_content` text CHARACTER SET utf8mb4 COLLATE
	 * utf8mb4_general_ci NOT NULL COMMENT '강의 상세 내용',
	 * PRIMARY KEY (`lecture_detail_idx`),
	 * KEY `fk_lecture_detail_lecture` (`lecture_code`) USING BTREE
	 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
	 * 
	 * ALTER TABLE `TBL_LECTURE_DETAIL` cloumn 추가
	 * ALTER TABLE `TBL_LECTURE_DETAIL` ADD COLUMN `lecture_detail_file_path` varchar(200) NOT NULL COMMENT '강의 상세 파일 경로' AFTER `lecture_detail_content`;
	 * ALTER TABLE `TBL_LECTURE_DETAIL` ADD COLUMN `lecture_detail_file_name` bigint NOT NULL COMMENT '강의 상세 파일 사이즈' AFTER `lecture_detail_file_path`;
	 */
	private int lectureDetailIdx; // 강의 상세 아이디
	private String lectureCode; // 강의 코드
	private String lectureDetailContent; // 강의 상세 내용
	private String lectureDetailFilePath; // 강의 상세 파일 경로
	private String lectureDetailFileName; // 강의 상세 파일 이름
	private long lectureDetailFileSize; // 강의 상세 파일 사이즈

	public LectureDetailDTO() {
		super();
	}

	public int getLectureDetailIdx() {
		return lectureDetailIdx;
	}

	public void setLectureDetailIdx(int lectureDetailIdx) {
		this.lectureDetailIdx = lectureDetailIdx;
	}

	public String getLectureCode() {
		return lectureCode;
	}

	public void setLectureCode(String lectureCode) {
		this.lectureCode = lectureCode;
	}

	public String getLectureDetailContent() {
		return lectureDetailContent;
	}

	public void setLectureDetailContent(String lectureDetailContent) {
		this.lectureDetailContent = lectureDetailContent;
	}

	public String getLectureDetailFilePath() {
		return lectureDetailFilePath;
	}

	public void setLectureDetailFilePath(String lectureDetailFilePath) {
		this.lectureDetailFilePath = lectureDetailFilePath;
	}

	public String getLectureDetailFileName() {
		return lectureDetailFileName;
	}

	public void setLectureDetailFileName(String lectureDetailFileName) {
		this.lectureDetailFileName = lectureDetailFileName;
	}

	public long getLectureDetailFileSize() {
		return lectureDetailFileSize;
	}

	public void setLectureDetailFileSize(long lectureDetailFileSize) {
		this.lectureDetailFileSize = lectureDetailFileSize;
	}
}
