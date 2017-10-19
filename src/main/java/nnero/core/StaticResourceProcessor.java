package nnero.core;

import nnero.http.HttpRequest;
import nnero.http.HttpResponse;
import nnero.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Author: NNERO
 * Time: 2017/10/7 13:56
 **/
public class StaticResourceProcessor {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceProcessor.class);

    private static volatile StaticResourceProcessor sProcessor;

    private StaticResourceProcessor(){}

    public static StaticResourceProcessor getStaticProcessor(){
        synchronized (StaticResourceProcessor.class){
            if(sProcessor == null){
                synchronized (StaticResourceProcessor.class){
                    sProcessor = new StaticResourceProcessor();
                }
            }
        }
        return sProcessor;
    }

    public void process(HttpRequest request, HttpResponse response){
        FileReader fr = null;
        try {
            File staticFile = new File(Constants.WEB_ROOT + request.getPathInfo());
            logger.info(staticFile.getAbsolutePath());
            if (staticFile.exists()) {
                response.writeCommon();
                fr = new FileReader(staticFile);
                char[] buffer = new char[2048];
                int length = 0;
                while((length =  fr.read(buffer)) != -1){
                    response.getWriter().write(buffer,0,length);
                }
                response.getWriter().flush();
            } else {
                response.write404();
            }
        } catch (IOException e){
            logger.error(e.getMessage());
        } finally {
            if(fr != null){
                try {
                    fr.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
