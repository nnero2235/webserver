package nnero.http;

import nnero.core.StaticResourceProcessor;
import nnero.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Author: NNERO
 * Time: 2017/10/6 15:22
 **/
public class HttpResponse implements HttpServletResponse{

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private OutputStream os;

    private PrintWriter writer;

    private List<Cookie> cookies;

    private int status;

    private Map<String,String> headerMap;

    private String contentType = "text/html";

    private long contentLength;

    public HttpResponse(OutputStream os){
        this.os = os;
        cookies = new ArrayList<>();
        headerMap = new HashMap<>();
    }

    public void write404() throws IOException {
        getWriter().write(Constants.NOT_FOUND);
        getWriter().flush();
    }

    public void writeCommon() throws IOException {
        getWriter().write(Constants.CODE_MSG_MAP.get(Constants.CODE_OK));
        getWriter().write("Server: "+Constants.SERVER_NAME+"\r\n");
        getWriter().write("Content-Type: text/html\r\n");
        getWriter().write("\r\n");
    }

    @Override
    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    @Override
    public boolean containsHeader(String name) {
        return headerMap.containsKey(name);
    }

    @Override
    public String encodeURL(String url) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String url) {
        return null;
    }

    @Override
    public String encodeUrl(String url) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return null;
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
    }

    @Override
    public void sendError(int sc) throws IOException {
    }

    @Override
    public void sendRedirect(String location) throws IOException {
    }

    @Override
    public void setDateHeader(String name, long date) {
    }

    @Override
    public void addDateHeader(String name, long date) {
    }

    @Override
    public void setHeader(String name, String value) {
        headerMap.put(name,value);
    }

    @Override
    public void addHeader(String name, String value) {
        headerMap.put(name,value);
    }

    @Override
    public void setIntHeader(String name, int value) {
    }

    @Override
    public void addIntHeader(String name, int value) {
    }

    @Override
    public void setStatus(int sc) {
        status = sc;
    }

    @Override
    public void setStatus(int sc, String sm) {
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getHeader(String name) {
        return headerMap.get(name.toLowerCase());
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return "utf-8";
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if(writer == null) {
            writer = new PrintWriter(os, true);
        }
        return writer;
    }

    @Override
    public void setCharacterEncoding(String charset) {
    }

    @Override
    public void setContentLength(int len) {
        contentLength = len;
    }

    @Override
    public void setContentLengthLong(long len) {
        contentLength = len;
    }

    @Override
    public void setContentType(String type) {
        contentType = type;
    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {
    }

    @Override
    public void resetBuffer() {
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
