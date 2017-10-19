import nnero.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

/**
 * Author: NNERO
 * Time: 2017/10/7 13:32
 **/
public class httpTest {

    private String requestString = "";

    @Before
    public void prepare(){
        StringBuilder sb = new StringBuilder();
        sb.append("POST /index.jsp http/1.1\r\n");
        sb.append("Accept: application/json\r\n");
        sb.append("Host: localhost\r\n");
        sb.append("Refer: www.baidu.com\r\n");
        sb.append("Cookie: jsessionid=122345;mock=je;\r\n");
        sb.append("\r\n");
        sb.append("name=maru&age=12\r\n");
        requestString = sb.toString();
    }

    @Test
    public void testHttpRequest(){
        InputStream is = new ByteArrayInputStream(requestString.getBytes());

        HttpRequest request = new HttpRequest(is,8080);
        request.parse();

        System.out.println(request.getPathInfo());
        System.out.println(request.getMethod());
        System.out.println(request.getParameter("name"));
        System.out.println(request.getParameter("age"));
        System.out.println(request.getParameter("gaga"));
        System.out.println(request.getHeader("accept"));
        System.out.println(request.getHeader("Host"));
        System.out.println(request.getQueryString());

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            System.out.println(cookie.getName()+":"+cookie.getValue());
        }
    }
}
