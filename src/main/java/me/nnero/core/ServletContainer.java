package me.nnero.core;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import me.nnero.http.HttpRequest;
import me.nnero.http.HttpResponse;
import me.nnero.http.exception.NotFoundException;
import me.nnero.http.exception.ServerErrorException;
import me.nnero.servlet.Servlet;
import me.nnero.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Author: NNERO
 * Time : 16:40 2017/10/23
 */
@Slf4j
public class ServletContainer implements Container {

    private ServletRegistry registry;

    public ServletContainer(ServletRegistry registry){
        this.registry = registry;
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException{
        String path = request.getPath();
        String className = registry.getClassName(path);
        log.info("process---> path:"+path+" className: "+className);
        if(!Strings.isNullOrEmpty(className)){
            String classPath = ResourceLoader.getLoader().getClassPath();
            String classRepo = new URL("file",null,classPath + File.separator).toString();
            URL realURL = new URL(classRepo);
            URLClassLoader classLoader = new URLClassLoader(new URL[]{realURL},Thread.currentThread().getContextClassLoader());
            try {
                Class<?> clazz = classLoader.loadClass(className);
                Servlet servlet = (Servlet) clazz.newInstance();
                servlet.service(request,response);
            } catch (ClassNotFoundException e) {
                throw new ServerErrorException(e.getMessage());
            } catch (IllegalAccessException e) {
                throw new ServerErrorException(e.getMessage());
            } catch (InstantiationException e) {
                throw new ServerErrorException(e.getMessage());
            } catch (NoClassDefFoundError e){
                throw new ServerErrorException(e.getMessage());
            }
        } else {
            throw new NotFoundException(path+" not found!");
        }
    }
}
