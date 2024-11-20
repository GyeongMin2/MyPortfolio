package net.fullstack7.edusecond.edusecond.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.BeansException;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommonFileUtil implements ApplicationContextAware {
    private static final String UPLOAD_PATH = "uploads/products/";
    private static final String EXTERNAL_PATH = "/home/gyeongmini/uploads/products/";  
    private static ServletContext servletContext;
    private static final Logger log = LogManager.getLogger(CommonFileUtil.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            servletContext = applicationContext.getBean(ServletContext.class);
            // 외부 디렉토리 생성
            File uploadDir = new File(EXTERNAL_PATH);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            log.info("외부 업로드 디렉토리 생성: {}", EXTERNAL_PATH);
        } catch (BeansException e) {
            log.error("초기화 실패: ", e);
            throw new RuntimeException("파일 업로드 유틸 초기화 실패", e);
        }
    }

    public static List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> uploadedFilePaths = new ArrayList<>();
        
        log.info("파일 업로드 시작 - CommonFileUtil");
        log.info("업로드 경로: {}", EXTERNAL_PATH);
        
        File uploadDir = new File(EXTERNAL_PATH);
        log.info("디렉토리 상태:");
        log.info("- 존재여부: {}", uploadDir.exists());
        log.info("- 쓰기권한: {}", uploadDir.canWrite());
        
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                log.warn("빈 파일이 전달됨");
                continue;
            }

            String originalFileName = file.getOriginalFilename();
            log.info("처리할 파일: {}", originalFileName);
            
            if (originalFileName != null && !originalFileName.isEmpty()) {
                String uniqueFileName = generateUniqueFileName(originalFileName);
                String fullPath = EXTERNAL_PATH + uniqueFileName;
                
                File destinationFile = new File(fullPath);
                try {
                    log.info("파일 저장 시도: {}", fullPath);
                    file.transferTo(destinationFile);
                    
                    if (destinationFile.exists()) {
                        log.info("파일 저장 성공: {}", fullPath);
                        log.info("- 파일크기: {}", destinationFile.length());
                        log.info("- 권한: r={}, w={}", destinationFile.canRead(), destinationFile.canWrite());
                        
                        String webPath = "/" + UPLOAD_PATH + uniqueFileName;
                        uploadedFilePaths.add(webPath);
                        log.info("웹 경로 추가: {}", webPath);
                    } else {
                        log.error("파일이 저장되지 않음: {}", fullPath);
                    }
                } catch (IOException e) {
                    log.error("파일 저장 실패: ", e);
                    throw e;
                }
            }
        }
        
        log.info("총 {} 개 파일 처리 완료", uploadedFilePaths.size());
        return uploadedFilePaths;
    }

    public static boolean deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        File file = new File(EXTERNAL_PATH + fileName);
        boolean result = file.exists() && file.delete();
        
        if (result) {
            log.info("파일 삭제 완료: {}", file.getPath());
        } else {
            log.warn("파일 삭제 실패: {}", file.getPath());
        }
        
        return result;
    }

    public static String renameFile(String oldFileName, String newFileName) throws IOException {
        String oldName = oldFileName.substring(oldFileName.lastIndexOf("/") + 1);
        File oldFile = new File(EXTERNAL_PATH + oldName);
        
        String uniqueNewFileName = generateUniqueFileName(newFileName);
        File newFile = new File(EXTERNAL_PATH + uniqueNewFileName);

        if (oldFile.exists() && oldFile.renameTo(newFile)) {
            log.info("파일 이름 변경 완료: {} -> {}", oldFile.getPath(), newFile.getPath());
            return "/" + UPLOAD_PATH + uniqueNewFileName;
        } else {
            log.error("파일 이름 변경 실패: {}", oldFile.getPath());
            throw new IOException("파일명을 변경할 수 없습니다.");
        }
    }

    private static String generateUniqueFileName(String originalFileName) {
        String extension = "";
        int index = originalFileName.lastIndexOf(".");
        if (index > 0) {
            extension = originalFileName.substring(index);
        }
        return UUID.randomUUID().toString() + extension;
    }
}