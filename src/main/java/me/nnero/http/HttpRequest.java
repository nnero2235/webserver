package me.nnero.http;

import java.util.List;
import java.util.Map;

/**
 * Author: NNERO
 * Time : 19:27 2017/10/20
 */
public interface HttpRequest {

    String getQueryString();

    Map<String,String> getParameterMap();

    String getParameter(String name);

    String getHeader(String name);

    Map<String,String> getHeaderMap();

    String getPath();

    String getHost();

    String getClientIPAddress();

    List<Cookie> getCookies();

    String getJSessionId();

    MimeType getMimeType();

    String method();

    RequestBody getRequestBody();

    long getContentLength();
}
