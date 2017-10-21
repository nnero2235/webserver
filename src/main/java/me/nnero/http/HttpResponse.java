package me.nnero.http;

import java.util.Map;

/**
 * Author: NNERO
 * Time : 19:39 2017/10/20
 */
public interface HttpResponse {

    int getCode();

    Map<String,String> getHeaderMap();

    String getHeader(String name);

    void addHeader(String name,String value);

    void setContentLength(long length);

    int getContentLength();

    void setContentType(String type);

    String getContentType();

    ResponseWriter getWriter();

    void addCookie(Cookie cookie);
}
