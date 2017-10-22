package me.nnero.core;

import lombok.extern.slf4j.Slf4j;
import me.nnero.http.ServletHttpRequest;
import me.nnero.http.ServletHttpResponse;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Author: NNERO
 * Time: 2017/10/22 15:58
 **/
@Slf4j
public class Connection implements Runnable{

    private Socket clientSocket;

    private Container container;

    public Connection(Socket clientSocket,Container container){
        this.clientSocket = clientSocket;
        this.container = container;
    }

    public void close(){
        try {
            if(clientSocket != null){
                clientSocket.close();
            }
        } catch (IOException e) {
            log.error("",e);
        }
    }

    @Override
    public void run() {
        InetAddress clientAddress = clientSocket.getInetAddress();
        String clientIP = clientAddress.getCanonicalHostName();
        try {
            ServletHttpRequest request = new ServletHttpRequest(clientSocket.getInputStream(),clientIP);
            request.parse();
            ServletHttpResponse response = new ServletHttpResponse(clientSocket.getOutputStream());
            container.process(request,response);
        } catch (IOException e) {
            log.error("",e);
        } finally {
            close();
        }
    }
}
