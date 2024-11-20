package net.fullstack7.edusecond.edusecond.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonDateUtil {
    public CommonDateUtil() {}
    //LocalDateTime --> DateTime  문자열 형태로 반환(패턴에 따라, 기본형 :yyyy-MM-dd HH:mm:ss)
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

}
