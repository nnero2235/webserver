package me.nnero.http;

import java.nio.charset.Charset;

/**
 * Author: NNERO
 * Time : 19:38 2017/10/20
 */
public class RequestBody {

    private byte[] rawData;

    public RequestBody(byte[] data){
        this.rawData = data;
    }

    public String string(Charset charset){
        if(rawData != null){
            return new String(rawData,charset);
        }
        return null;
    }

    public String string(){
        return string(Charset.forName("utf8"));
    }

    public byte[] getRaw(){
        return rawData;
    }
}
