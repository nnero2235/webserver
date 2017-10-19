import nnero.core.StaticResourceProcessor;
import nnero.http.HttpRequest;
import nnero.http.HttpResponse;
import org.junit.Test;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Author: NNERO
 * Time: 2017/10/7 14:08
 **/
public class processorTest {

    @Test
    public void staticProcessorTest() throws FileNotFoundException {
        StaticResourceProcessor processor = StaticResourceProcessor.getStaticProcessor();
        PrintWriter pw = new PrintWriter(new File("F:\\code\\java\\webserver\\1.txt"));
//        processor.process();
    }

    @Test
    public void classLoaderTest() throws IOException, ClassNotFoundException,
            IllegalAccessException, InstantiationException, ServletException {
        StringBuilder sb = new StringBuilder();
        sb.append("POST /index.jsp http/1.1\r\n");
        sb.append("Accept: application/json\r\n");
        sb.append("Host: localhost\r\n");
        sb.append("Refer: www.baidu.com\r\n");
        sb.append("Cookie: jsessionid=122345;mock=je;\r\n");
        sb.append("\r\n");
        sb.append("name=maru&age=12\r\n");

        HttpRequest request = new HttpRequest(new ByteArrayInputStream(sb.toString().getBytes()),8080);
        request.parse();
        HttpResponse response = new HttpResponse(new BufferedOutputStream(System.out));

        String classPath = "F:\\code\\java\\webserver\\target\\classes\\nnero\\servlet\\";
        String servletName = "nnero.servlet.MyServlet";
        File file = new File(classPath);
        String repo = new URL("file",null,file.getCanonicalPath()+File.separator).toString();
        System.out.println(repo);
        URL url = new URL(repo);
        URLClassLoader  classLoader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());
        Class<?> clazz = classLoader.loadClass(servletName);
        Servlet servlet = (Servlet) clazz.newInstance();
        servlet.service(request,response);
    }
}
