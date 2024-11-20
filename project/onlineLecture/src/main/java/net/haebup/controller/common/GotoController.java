package net.haebup.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dao.member.MemberDAO;
import java.sql.SQLException;

import java.io.IOException;
// import java.io.PrintWriter;

@WebServlet("/goto.do")
public class GotoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// @Override
	// public void init() throws ServletException {
	// 	MemberDTO memberDto = new MemberDTO();
	// }
	// 페이지 이동
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// String pageName= req.getParameter("page_name");
		// String pathName = req.getParameter("path_name");
		
		// String path = "WEB-INF/" + pathName + "/" + pageName + ".jsp";
		// req.getRequestDispatcher(path).forward(req, res);

		String pageName = req.getParameter("page");
		String type = req.getParameter("type");  
		String category = req.getParameter("category");  
		String teacherId = req.getParameter("teacherId");  
		System.out.println("goto"+pageName);
		MemberDTO memberDto = (MemberDTO) req.getSession().getAttribute("user");
		
		switch(pageName){
			case "register":
				req.getRequestDispatcher("/WEB-INF/common/member/register.jsp").forward(req, res);
				break;
			case "registerSelect":
				req.getRequestDispatcher("/WEB-INF/common/member/registerSelectPage.jsp").forward(req, res);
				break;
			case "modify":
				if (memberDto == null) {
					
					req.setAttribute("msg", "로그인 후 이용 가능합니다");
					req.setAttribute("url", "/main.do"); 
					req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
					return;
				}else{
					try {
						memberDto = new MemberDAO().getUserInfo(memberDto.getUserId());
						req.setAttribute("modifyUser", memberDto);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					req.getRequestDispatcher("/WEB-INF/common/member/modify.jsp").forward(req, res);
				}
				break;
			case "mypage":
				req.getRequestDispatcher("/WEB-INF/common/myPage/myPage.jsp").forward(req, res);
				break;
			case "user/qna/write":
				    req.setAttribute("qnaType", type);
				    req.setAttribute("category", category);
				    req.setAttribute("teacherId", teacherId);

					if(teacherId != null) {
						req.getRequestDispatcher("/WEB-INF/common/inquiy/write.jsp?teacherId="+teacherId).forward(req, res);
					}
					if(category != null) {
						req.getRequestDispatcher("/WEB-INF/common/inquiy/write.jsp?category="+category).forward(req, res);
					} else {
					req.getRequestDispatcher("/WEB-INF/common/inquiy/write.jsp").forward(req, res);
					}
					break;

				// 작성권한체크
			case "post/write":
				  	req.setAttribute("qnaType", type);
				    req.setAttribute("category", category);
				    System.out.println("카ㅔ확인"+category);
				    req.setAttribute("teacherId", teacherId);
		        if (type.equals("N")) {
		            if (memberDto != null) {
		                String userType = memberDto.getUserType();
		                if (userType.equals("A")) {
		                    req.setAttribute("boardType", type);
		                    req.getRequestDispatcher("/WEB-INF/common/post/write.jsp").forward(req, res);
		                } else {
		                    req.setAttribute("msg", "공지사항 작성 권한이 없습니다.");
		                    req.setAttribute("url", "javascript:history.back();"); 
		                    req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
		                }
		            } else {
		                req.setAttribute("msg", "로그인이 필요합니다.");
		                req.setAttribute("url", "/main.do"); 
		                req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
		            }
		        } else if(type.equals("C")){
		        	 if (memberDto != null) {
			                String userType = memberDto.getUserType();
			                if (userType.equals("T")||userType.equals("A")) {
			                    req.setAttribute("boardType", type);
			                    req.getRequestDispatcher("/WEB-INF/common/post/write.jsp").forward(req, res);
			                } else {
			                    req.setAttribute("msg", "공지사항 작성 권한이 없습니다.");
			                    req.setAttribute("url", "javascript:history.back();"); 
			                    req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
			                }
			            } else {
			                req.setAttribute("msg", "로그인이 필요합니다.");
			                req.setAttribute("url", "/main.do"); 
			                req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
			            }
		        }else if (type.equals("R")) {
		            if (memberDto != null) {
		                String userType = memberDto.getUserType();
		                if (userType.startsWith("S")) {
		                    req.setAttribute("boardType", type);
		                    req.getRequestDispatcher("/WEB-INF/common/post/write.jsp").forward(req, res);
		                } else {
		                    req.setAttribute("msg", "작성 권한이 없습니다. 강의 후기는 학생만 작성 가능합니다");
		                    req.setAttribute("url", "javascript:history.back();");
		                    req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
		                }
		            } else {
		                req.setAttribute("msg", "로그인이 필요합니다.");
		                req.setAttribute("url", "/main.do"); 
		                req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
		            }
		        }else if (type.equals("D")) 
		        	 if (memberDto != null) {
			                String userType = memberDto.getUserType();
			                if (userType.equals("T")||userType.equals("A")) {
			                    req.setAttribute("boardType", type);
			                    req.getRequestDispatcher("/WEB-INF/common/post/write.jsp").forward(req, res);
			                } else {
			                    req.setAttribute("msg", "자료실 작성 권한이 없습니다.");
			                    req.setAttribute("url", "javascript:history.back();"); 
			                    req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
			                }
			            } else {
			                req.setAttribute("msg", "로그인이 필요합니다.");
			                req.setAttribute("url", "/main.do"); 
			                req.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(req, res);
			            }
		        else {
		            req.setAttribute("category", category);
		            req.setAttribute("boardType", type);
		            req.getRequestDispatcher("/WEB-INF/common/post/write.jsp").forward(req, res);
		        }
		        break;
		        
			case "admin/notice/write":
				req.getRequestDispatcher("/WEB-INF/admin/notice/write.jsp").forward(req, res);
				break;
			case "admin/filePost/write":
				req.getRequestDispatcher("/WEB-INF/admin/filePost/write.jsp").forward(req, res);
				break;
			case "admin/login":
				req.getRequestDispatcher("/WEB-INF/admin/adminLogin.jsp").forward(req, res);
			default:
				req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, res);
				break;
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}