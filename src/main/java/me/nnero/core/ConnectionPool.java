package me.nnero.core;

import java.net.Socket;

/**
 * Author: NNERO
 * Time: 2017/10/22 16:03
 **/
public interface ConnectionPool {

    void asyncProcessSocket(Socket socket);

    void setContainer(Container container);

    void shutdown(); // don't accept connections
}
