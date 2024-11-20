package net.haebup.utils.fileUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import java.io.FileInputStream;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class FileIOUtil {
    // 업로드 디렉토리 경로 상수
    private static final String BOARD_UPLOAD_DIR = "uploads/board";
    private static final String LECTURE_NOTICE_UPLOAD_DIR = "uploads/lecture/notice";
    private static final String LECTURE_DETAIL_UPLOAD_DIR = "uploads/lecture/detail";
    private static final int BUFFER_SIZE = 4096; // 버퍼 크기

    public static String uploadBoardAttachment(HttpServletRequest request, String fieldName)
            throws IOException, ServletException {
        return uploadFile(request, fieldName, BOARD_UPLOAD_DIR);
    }

    public static String uploadProfilePicture(HttpServletRequest request, String fieldName)
            throws IOException, ServletException {
        return uploadFile(request, fieldName, LECTURE_NOTICE_UPLOAD_DIR);
    }

    public static String uploadMessageAttachment(HttpServletRequest request, String fieldName)
            throws IOException, ServletException {
        return uploadFile(request, fieldName, LECTURE_DETAIL_UPLOAD_DIR);
    }

    // 파일 업로드 처리
    private static String uploadFile(HttpServletRequest request, String fieldName, String subDir)
            throws IOException, ServletException {
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + subDir;

        // 업로드 디렉토리 생성
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // 파일 파트 가져오기
        Part filePart = request.getPart(fieldName);
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = getSubmittedFileName(filePart);
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            String filePath = uploadPath + File.separator + uniqueFileName;
            
            // 파일 저장
            filePart.write(filePath);
            
            // 웹 접근 경로 반환
            return "/file/uploads/" + subDir + "/" + uniqueFileName;
        }
        return null;
    }

    // 파일 다운로드 처리
    public static void downloadFile(HttpServletResponse response, String filePath, String fileName) 
            throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("파일이 존재하지 않습니다: " + filePath);
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentLength((int) file.length());

        try (FileInputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }
    }

    private static String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}