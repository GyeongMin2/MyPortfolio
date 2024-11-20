package net.haebup.controller.board.file;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.utils.fileUtil.FileIOUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/downloadFile.do")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String filePath = URLDecoder.decode(request.getParameter("filePath"), "UTF-8");
		String fileName = URLDecoder.decode(request.getParameter("fileName"), "UTF-8");
		
		System.out.println("filePath: " + filePath);
		System.out.println("fileName: " + fileName);

		try {
			FileIOUtil.downloadFile(response, filePath, fileName);
		} catch (IOException e) {
			System.out.println("오류확인 : "+e.getMessage());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
