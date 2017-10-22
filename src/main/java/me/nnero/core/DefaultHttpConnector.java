package me.nnero.core;

import lombok.extern.slf4j.Slf4j;
import me.nnero.http.ServletHttpRequest;
import me.nnero.http.ServletHttpResponse;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Author: NNERO
 * Time: 2017/10/22 15:29
 **/
@Slf4j
public class DefaultHttpConnector implements HttpConnector{

    private String host;

    private int port;

    private volatile boolean shutdown;

    private ConnectionPool pool;

    public DefaultHttpConnector(String host,int port,Container container){
        this.host = host;
        this.port = port;
        this.pool = new DefaultConnectionPool(Integer.MAX_VALUE);
        this.pool.setContainer(container);
    }

    @Override
    public void startAndListen() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port,0, InetAddress.getByName(host));
            while (!shutdown){
                Socket clientSocket = serverSocket.accept();
                pool.asyncProcessSocket(clientSocket);
            }
        } catch (IOException e) {
            log.error("",e);
        } finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    log.error("",e);
                }
            }
        }
    }

    @Override
    public void close() {
        shutdown = true;
    }

}
