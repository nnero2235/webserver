package me.nnero.core;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: NNERO
 * Time: 2017/10/22 16:05
 **/
@Slf4j
public class DefaultConnectionPool implements ConnectionPool {

    private static final long ALIVE_TIME = 4 * 1000;

    private static final IgnoreHandler defaultHandler = new IgnoreHandler();

    private ExecutorService threadPool;

    private Container container;

    public DefaultConnectionPool(int maxThreads){
        if(maxThreads <= 0){
            throw new RuntimeException("max threads must be  > 0");
        }
        threadPool = new ThreadPoolExecutor(0,maxThreads,ALIVE_TIME,
                TimeUnit.SECONDS, new SynchronousQueue<>(),defaultHandler);
    }

    public void setContainer(Container container){
        this.container = container;
    }

    @Override
    public void asyncProcessSocket(Socket socket) {
        threadPool.execute(new Connection(socket,container));
    }

    @Override
    public void shutdown() {
        threadPool.shutdown();
    }

    private static class IgnoreHandler implements RejectedExecutionHandler {

        private AtomicInteger ignoreCount;

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.warn("Task :"+r.toString()+" is ignored");
            ignoreCount.incrementAndGet();
        }

        public int getIgnoreCount(){
            return ignoreCount.get();
        }
    }
}
