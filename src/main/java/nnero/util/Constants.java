package nnero.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: NNERO
 * Time: 2017/10/7 13:52
 **/
public class Constants {

    public static final int CODE_OK = 200;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_BAN = 403;
    public static final int CODE_NOT_MODIFY = 304;
    public static final int CODE_REDIRECT = 302;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_SERVER_ERROR = 500;

    public static final Map<Integer,String> CODE_MSG_MAP = new HashMap<>();

    public static final String SERVER_NAME = "WebContainer";

    public static final String WEB_ROOT= "F:\\code\\java\\webserver";

    public static final String NOT_FOUND = "HTTP/1.1 404 File Not Found\r\n"+
            "Content-Type: text/html\r\n"+
            "Content-Length: 48\r\n"+
            "\r\n"+
            "<html>" +
            "<body><h1>404 not Found</h1></body>" +
            "</html>";

    static {
        CODE_MSG_MAP.put(CODE_OK,"HTTP/1.1 200 OK\r\n");
        CODE_MSG_MAP.put(CODE_NOT_FOUND,"HTTP/1.1 404 File Not Found\r\n");
        CODE_MSG_MAP.put(CODE_BAN,"HTTP/1.1 403 Forbidden\r\n");
        CODE_MSG_MAP.put(CODE_NOT_MODIFY,"HTTP/1.1 304 Not Modify\r\n");
        CODE_MSG_MAP.put(CODE_REDIRECT,"HTTP/1.1 302 Redirect\r\n");
        CODE_MSG_MAP.put(CODE_BAD_REQUEST,"HTTP/1.1 400 Bad Request\r\n");
        CODE_MSG_MAP.put(CODE_SERVER_ERROR,"HTTP/1.1 404 Server Error\r\n");
    }
}
