package net.haebup.controller.file;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/file/*")
public class FileServingController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        // 경로 정보가 없으면 404 에러 반환
        if (pathInfo == null || pathInfo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 경로 정보 디코딩
        pathInfo = URLDecoder.decode(pathInfo, StandardCharsets.UTF_8);
        
        // 실제 경로 계산
        String realPath = request.getServletContext().getRealPath("/uploads") 
                       + pathInfo.replace("/uploads", "");
        
        // 파일 객체 생성
        File file = new File(realPath);

        // 파일이 존재하지 않으면 404 에러 반환
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 파일 컨텐츠 유형 설정
        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);

        Files.copy(file.toPath(), response.getOutputStream());
    }
} 