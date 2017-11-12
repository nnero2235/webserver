package me.nnero.http;

import me.nnero.http.io.ServletOutputStream;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Author: NNERO
 * Time : 19:39 2017/10/20
 */
public interface HttpResponse {

    void setStatusCode(int code);

    Map<String,String> getHeaderMap();

    String getHeader(String name);

    void addHeader(String name,String value);

    void setContentLength(long length);

    long getContentLength();

    void setContentType(String type);

    String getContentType();

    PrintWriter getWriter();

    void addCookie(Cookie cookie);

    ServletOutputStream getOutputStream();

    void setContentEncoding(Charset charset);
}
