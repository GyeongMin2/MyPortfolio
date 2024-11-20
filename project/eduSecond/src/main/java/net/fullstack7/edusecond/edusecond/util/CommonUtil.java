package net.fullstack7.edusecond.edusecond.util;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CommonUtil {

    public int getStringToInt(String str, int defaultValue) {
        return(StringUtils.isNumeric(str) ? Integer.parseInt(str) : defaultValue);
    }
    public void setCookiesInfo(
            HttpServletRequest req,
            HttpServletResponse res,
            String path, int maxAge, String Domain,
            String cName, String cValue
    ){
      Cookie cookies = new Cookie(cName, cValue);
        cookies.setPath(path);
        cookies.setMaxAge(maxAge);
        cookies.setDomain(Domain != null ? Domain : "/");
        res.addCookie(cookies);
    }
    public String getCookieValue(HttpServletRequest req, String cName) {
        Cookie[] cookies = req.getCookies(); //요청 헤더에 있는 모든 쿠키 조회 값=이름
        if (cookies != null) {
            for (Cookie c : cookies) {
                String cookieName = c.getName();
                String cookieValue = c.getValue();
                if (cookieName.equals(cName))  return cookieValue;
            }
        }
        return "";
    }

    public static String makeUuid() {
        return UUID.randomUUID().toString();
    }
}
