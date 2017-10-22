package me.nnero.http;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Author: NNERO
 * Time: 2017/10/22 15:43
 **/
public class ServletHttpRequest implements HttpRequest{

    private InputStream socketInputStream;

    private String clientIP;

    public ServletHttpRequest(InputStream is,String clientIP){
        socketInputStream = is;
        this.clientIP = clientIP;
    }

    public void parse(){

    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public Map<String, String> getParameterMap() {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Map<String, String> getHeaderMap() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public String getClientIPAddress() {
        return null;
    }

    @Override
    public List<Cookie> getCookies() {
        return null;
    }

    @Override
    public String getJSessionId() {
        return null;
    }

    @Override
    public MimeType getMimeType() {
        return null;
    }

    @Override
    public String method() {
        return null;
    }

    @Override
    public RequestBody getRequestBody() {
        return null;
    }
}
