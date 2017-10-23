package me.nnero.core;

import lombok.extern.slf4j.Slf4j;
import me.nnero.http.ErrorHandler;
import me.nnero.http.ServletHttpRequest;
import me.nnero.http.ServletHttpResponse;
import me.nnero.http.StatusCode;
import me.nnero.http.exception.BadRequestException;
import me.nnero.http.exception.NotFoundException;
import me.nnero.http.exception.ServerErrorException;

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
        ServletHttpRequest request = null;
        ServletHttpResponse response = null;
        try {
            request = new ServletHttpRequest(clientSocket.getInputStream(),clientIP);
            response = new ServletHttpResponse(clientSocket.getOutputStream());
            request.parse();
            container.process(request,response);
        } catch (IOException e) {
            log.error("",e);
        } catch (BadRequestException e){
            log.error("",e);
            ErrorHandler.getHandler().handle(StatusCode.BAD_REQUEST,e.getMessage(),response);
        } catch (NotFoundException e){
            log.error("",e);
            ErrorHandler.getHandler().handle(StatusCode.NOT_FOUND,e.getMessage(),response);
        } catch (ServerErrorException e){
            log.error("",e);
            ErrorHandler.getHandler().handle(StatusCode.SERVER_ERROR,e.getMessage(),response);
        }finally {
            close();
        }
    }
}
