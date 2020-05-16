package dsk.otus.softwarearchitect.task5.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Common {

    public static String getSessionId(HttpServletRequest request) {
        if (request == null) return null;
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("session_id")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
