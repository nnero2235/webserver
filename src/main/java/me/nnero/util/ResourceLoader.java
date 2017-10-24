package me.nnero.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.Iterator;

/**
 * Author: NNERO
 * Time : 15:40 2017/10/23
 */
@Slf4j
public class ResourceLoader {

    private static volatile ResourceLoader sLoader;

    private String classPath;

    private ResourceLoader() {
//        classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
//        classPath = System.getProperty("user.dir");
        classPath = this.getClass().getResource("/").getPath();
        log.debug("classPath: "+classPath);
    }

    public static ResourceLoader getLoader() {
        if (sLoader == null) {
            synchronized (ResourceLoader.class) {
                if (sLoader == null) {
                    sLoader = new ResourceLoader();
                }
            }
        }
        return sLoader;
    }

    public String loadAsString(String fileLocation) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(classPath + File.separator + fileLocation));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * special the web.xml to parse
     * @param callback
     * @throws DocumentException
     */
    public void loadWebXMLConfigFile(WebXMLConfigCallback callback) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(classPath+ File.separator + "web.xml"));
        Element root = document.getRootElement();
        if("servlets".equals(root.getName())){
            Iterator it = root.elementIterator();
            while (it.hasNext()){
                Element servletElement = (Element) it.next();
                if("servlet".equals(servletElement.getName())){
                    Element className = servletElement.element("className");
                    Element path = servletElement.element("path");
                    if(className != null && path != null){
                        callback.process(className.getTextTrim(),path.getTextTrim());
                    }
                } else {
                    throw new DocumentException("broken doc : child must be named 'servlet'");
                }
            }
        } else {
            throw new DocumentException("broken doc : root must be named 'servlets'");
        }
    }

    public String getClassPath(){
        return classPath;
    }

    public interface WebXMLConfigCallback {
        void process(String className,String path);
    }
}
