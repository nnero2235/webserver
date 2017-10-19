package nnero.core;

import nnero.http.HttpRequest;
import nnero.http.HttpResponse;
import nnero.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Author: NNERO
 * Time: 2017/10/7 14:37
 **/
public class ServletProcessor {

    private static final Logger  logger = LoggerFactory.getLogger(ServletProcessor.class);

    private static final String SERVLET_DIR = "\\target\\classes\\nnero\\servlet\\";

    public ServletProcessor(){}

    public void process(HttpRequest request, HttpResponse response) throws IOException, ServletException {
        int index = request.getPathInfo().lastIndexOf("/");
        String servletName = "nnero.servlet."+request.getPathInfo().substring(index+1);
        File classPath = new File(Constants.WEB_ROOT+SERVLET_DIR);
        String classRepo = new URL("file",null,classPath.getCanonicalPath()+File.separator).toString();
        URL realURL = new URL(classRepo);

        URLClassLoader classLoader = new URLClassLoader(new URL[]{realURL},Thread.currentThread().getContextClassLoader());
        try {
            Class<?> clazz = classLoader.loadClass(servletName);
            Servlet servlet = (Servlet) clazz.newInstance();
            response.writeCommon(); //写入响应行 和 公共头
            servlet.service(request,response);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (NoClassDefFoundError e){
            logger.error(e.getMessage());
            response.write404();
        }
    }
}
