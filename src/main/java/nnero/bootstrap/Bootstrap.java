package nnero.bootstrap;

import nnero.http.HttpConnector;

/**
 * Author: NNERO
 * Time: 2017/10/7 14:55
 **/
public class Bootstrap {
    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.run();
//        Thread thread = new Thread(connector);
//        thread.start();
    }
}
