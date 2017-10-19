package nnero.http;

import nnero.core.ServletProcessor;
import nnero.core.StaticResourceProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: NNERO
 * Time: 2017/10/7 14:52
 **/
public class HttpConnector implements Runnable{

    private static final int PORT = 8080;

    private static final String IP = "127.0.0.1";

    private static final Logger logger = LoggerFactory.getLogger(HttpConnector.class);

    private static final String SHUTDOWN = "/shutdown";

    private volatile boolean shutdown = false;

    public HttpConnector(){
    }

    public void await(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT,1, InetAddress.getByName(IP));
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }

        while (!shutdown){
            InputStream is = null;
            OutputStream os = null;
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();

                HttpRequest request = new HttpRequest(is,PORT);
                request.parse();
                HttpResponse response = new HttpResponse(os);

                if(request.getPathInfo().startsWith("/servlet/")){
                    ServletProcessor processor = new ServletProcessor();
                    processor.process(request,response);
                } else {
                    StaticResourceProcessor processor = StaticResourceProcessor.getStaticProcessor();
                    processor.process(request,response);
                }
                logger.info("http--->finished");
                socket.close();

                shutdown = SHUTDOWN.equals(request.getPathInfo());
            } catch (IOException e) {
                logger.error(e.getMessage());
            } catch (ServletException e){
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        await();
    }
}
