package net.haebup.controller.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dao.member.MemberDAO;
import net.haebup.dto.lecture.LectureDTO;
import net.haebup.dao.lecture.LectureDAO;
import java.sql.SQLException;

import java.io.IOException;
import java.util.List;

@WebServlet("/gotoTeacherInfo.do")
public class GotoTeacherInfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String teacherId = req.getParameter("teacherId");
        MemberDAO memberDao = new MemberDAO();
        LectureDAO lectureDao = new LectureDAO();
        try {
            MemberDTO memberDto = memberDao.getUserInfo(teacherId);
            List<LectureDTO> lectures = lectureDao.getLectureListByTeacherId(teacherId);
            req.setAttribute("teacherInfo", memberDto);
            req.setAttribute("lectures", lectures);
            
            // 각 선생님 ID에 따른 JSP 페이지 분기
            String jspPath;
            
            switch (teacherId) {
                // 국어 선생님
                case "user_kor1":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_kor1.jsp";
                    break;
                case "user_kor2":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_kor2.jsp";
                    break;
                case "user_kor3":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_kor3.jsp";
                    break;
                    
                // 수학 선생님
                case "user_math1":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_math1.jsp";
                    break;
                case "user_math2":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_math2.jsp";
                    break;
                case "user_math3":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_math3.jsp";
                    break;
                    
                // 영어 선생님
                case "user_eng1":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_eng1.jsp";
                    break;
                case "user_eng2":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_eng2.jsp";
                    break;
                case "user_eng3":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_eng3.jsp";
                    break;
                    
                // 사회 선생님
                case "user_soc1":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_soc1.jsp";
                    break;
                case "user_soc2":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_soc2.jsp";
                    break;
                case "user_soc3":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_soc3.jsp";
                    break;
                    
                // 과학 선생님
                case "user_sci1":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_sci1.jsp";
                    break;
                case "user_sci2":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_sci2.jsp";
                    break;
                case "user_sci3":
                    jspPath = "/WEB-INF/common/teacherInfo/teacherInfo_user_sci3.jsp";
                    break;
                    
                default:
                    jspPath = "main.do";
            }
            
            req.getRequestDispatcher(jspPath).forward(req, res);
        } catch (SQLException e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
        }
    }
}
