package me.nnero.http;

/**
 * Author: NNERO
 * Time: 2017/10/21 22:41
 **/
public enum StatusCode {

    OK(200,"200 ok"),
    REDIRECT(302,"302 redirect"),
    NOT_MODIFY(304,"304 not modify"),
    BAD_REQUEST(400,"400 bad request"),
    FORBIDDEN(403,"403 forbidden"),
    NOT_FOUND(404,"404 not found"),
    SERVER_ERROR(500,"500 server error");


    private int code;

    private String desc;

    StatusCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int code(){
        return code;
    }

    public String desc(){
        return desc;
    }
}
