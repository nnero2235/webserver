package me.nnero.http;

import java.io.OutputStream;
import java.util.Map;

/**
 * Author: NNERO
 * Time: 2017/10/22 15:47
 **/
public class ServletHttpResponse implements HttpResponse {

    private OutputStream socketOutputStream;

    public ServletHttpResponse(OutputStream os){
        this.socketOutputStream = os;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public Map<String, String> getHeaderMap() {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public void addHeader(String name, String value) {

    }

    @Override
    public void setContentLength(long length) {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ResponseWriter getWriter() {
        return null;
    }

    @Override
    public void addCookie(Cookie cookie) {

    }
}
