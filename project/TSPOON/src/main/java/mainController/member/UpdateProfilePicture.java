package mainController.member;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import dao.ProfileDAO;
import dto.profile.ProfilePictureDTO;
import dto.member.MemberDTO;
import util.FileUploadUtil;

@WebServlet("/updateProfilePicture.do")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 5 * 5 // 25 MB
)
public class UpdateProfilePicture extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            MemberDTO user = (MemberDTO) session.getAttribute("user");

            if (user == null) {
                throw new ServletException("로그인이 필요합니다.");
            }

            Part filePart = request.getPart("pic");
            
            if (filePart == null) {
                System.out.println("파일 업로드 실패 : ");
                for (Part part : request.getParts()) {
                    System.out.println(part.getName() + ": " + part.getSize() + " bytes");
                }
                throw new ServletException("파일이 선택되지 않았습니다.");
            }

            String fileName = getSubmittedFileName(filePart);
            String contentType = filePart.getContentType();

            if (!isValidImageFile(fileName, contentType)) {
                throw new ServletException("지원하지 않는 파일 형식입니다. JPG, PNG, JPEG 파일만 업로드 가능합니다.");
            }

            String uploadedFilePath = FileUploadUtil.uploadProfilePicture(request, "pic");

            if (uploadedFilePath == null) {
                throw new ServletException("파일 업로드에 실패했습니다.");
            }

            ProfilePictureDTO profilePicture = new ProfilePictureDTO();
            profilePicture.setUserId(user.getUserId());
            profilePicture.setFileName(uploadedFilePath.substring(uploadedFilePath.lastIndexOf("/") + 1));
            profilePicture.setFilePath(uploadedFilePath);
            profilePicture.setFileSize(filePart.getSize());

            ProfileDAO profileDAO = new ProfileDAO();
            profileDAO.updateProfilePicture(profilePicture);

            // 세션 업데이트
            session.setAttribute("profilePath", uploadedFilePath);

            out.print("{\"success\": true, \"message\": \"프로필 사진이 성공적으로 업데이트되었습니다.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"프로필 사진 업데이트 중 오류 발생: " + e.getMessage() + "\"}");
        }
    }

    private boolean isValidImageFile(String fileName, String contentType) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) &&
               (contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/jpg"));
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
