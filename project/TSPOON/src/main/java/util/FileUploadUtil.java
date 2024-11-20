package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.ServletException;

public class FileUploadUtil {

    private static final String PROFILE_UPLOAD_DIR = "uploads/profiles";
    private static final String MESSAGE_UPLOAD_DIR = "uploads/messages";

    public static String uploadProfilePicture(HttpServletRequest request, String fieldName) throws IOException, ServletException {
        return uploadFile(request, fieldName, PROFILE_UPLOAD_DIR);
    }

    public static String uploadMessageAttachment(HttpServletRequest request, String fieldName) throws IOException, ServletException {
        return uploadFile(request, fieldName, MESSAGE_UPLOAD_DIR);
    }

    private static String uploadFile(HttpServletRequest request, String fieldName, String subDir) throws IOException, ServletException {
        String uploadPath = request.getServletContext().getRealPath("/" + subDir);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    
        Part filePart = request.getPart(fieldName);
        if (filePart == null) {
            System.out.println("파일 업로드 실패 : " + fieldName);
            return null;
        }
    
        String fileName = getSubmittedFileName(filePart);
        
        if (fileName != null && !fileName.isEmpty()) {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            String filePath = uploadPath + File.separator + uniqueFileName;
            
            filePart.write(filePath);
            return subDir + "/" + uniqueFileName; // 웹 경로 반환
        }
        return null;
    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
