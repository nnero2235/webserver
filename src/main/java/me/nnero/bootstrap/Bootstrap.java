package me.nnero.bootstrap;

import lombok.extern.slf4j.Slf4j;
import me.nnero.core.DefaultHttpConnector;
import me.nnero.core.HttpConnector;
import me.nnero.core.ServletContainer;
import me.nnero.core.ServletRegistry;

/**
 * Author: NNERO
 * Time : 17:58 2017/10/23
 */
@Slf4j
public class Bootstrap {

    public static void boot(){
        log.info("BootStrap start!");
        String host = "127.0.0.1";
        int port = 8081;
        ServletRegistry registry = new ServletRegistry();
        registry.initRegistry();
        ServletContainer container = new ServletContainer(registry);
        HttpConnector httpConnector = new DefaultHttpConnector(host,port,container);
        httpConnector.startAndListen();
    }

    public static void main(String[] args) {
        boot();
    }
}
