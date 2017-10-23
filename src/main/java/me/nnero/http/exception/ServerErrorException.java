package me.nnero.http.exception;

/**
 * Author: NNERO
 * Time : 15:32 2017/10/23
 */
public class ServerErrorException extends RuntimeException {

    public ServerErrorException(String msg){
        super(msg);
    }
}
