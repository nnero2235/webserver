package me.nnero.http;

import com.google.common.base.Strings;
import me.nnero.http.exception.ServerErrorException;
import me.nnero.util.Constants;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: NNERO
 * Time: 2017/10/22 15:47
 **/
public class ServletHttpResponse implements HttpResponse {

    private OutputStream socketOutputStream;

    private List<Cookie> cookies;

    private Map<String,String> headerMap;

    private StatusCode statusCode;

    private long contentLength;

    private MimeType mimeType;

    private PrintWriter printWriter;

    private Charset contentEncoding;

    private boolean isWroteMetaData;

    public ServletHttpResponse(OutputStream os){
        this.socketOutputStream = os;
        this.headerMap = new HashMap<>();
        this.statusCode = StatusCode.OK;
        this.mimeType = MimeType.GENERAL;
        this.contentEncoding = Charset.defaultCharset();
    }

    @Override
    public void setStatusCode(int code) {
        this.statusCode = StatusCode.parse(code);
    }

    @Override
    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    @Override
    public String getHeader(String name) {
        return headerMap.get(name);
    }

    @Override
    public void addHeader(String name, String value) {
        if(name == null || value == null){
            throw new NullPointerException("header name or value can't be null");
        }
        headerMap.put(name,value);
    }

    @Override
    public void setContentLength(long length) {
        contentLength = length;
    }

    @Override
    public long getContentLength() {
        return contentLength;
    }

    @Override
    public void setContentType(String type) {
        mimeType = MimeType.parse(type);
    }

    @Override
    public String getContentType() {
        return mimeType.type();
    }

    @Override
    public PrintWriter getWriter() {
        if(!isWroteMetaData){
            writeAndFlushResponseLineAndHeader();
            isWroteMetaData = true;
        }
        if(printWriter == null) {
            printWriter = new PrintWriter(socketOutputStream);
        }
        return printWriter;
    }

    @Override
    public void addCookie(Cookie cookie) {
        if(cookies == null){
            cookies = new ArrayList<>();
        }
        cookies.add(cookie);
    }

    @Override
    public OutputStream getOutputStream() {
        if(!isWroteMetaData){
            writeAndFlushResponseLineAndHeader();
            isWroteMetaData = true;
        }
        return socketOutputStream;
    }

    @Override
    public void setContentEncoding(Charset charset) {
        this.contentEncoding = charset;
    }

    //when user invoke  getOutputStream() or getWriter() , will trigger this method to write meta data to client
    private void writeAndFlushResponseLineAndHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(statusCode.desc()).append("\r\n");//response line
        sb.append("Server: ").append(Constants.SERVER_NAME).append("\r\n"); // server header
        //content-type
        sb.append("Content-Type: ").append(mimeType.type())
                .append(";charset=").append(contentEncoding.displayName()).append("\r\n");
        //content-length
        if(contentLength != 0){
            sb.append("Content-Length: ").append(contentLength).append("\r\n");
        }
        sb.append("Connection: close\r\n"); //now can't support keep-alive
        //cookies
        if(cookies != null){
            for(Cookie cookie : cookies){
                setCookieHeader(sb,cookie);
            }
        }
        for(Map.Entry<String,String> entry : headerMap.entrySet()){
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        sb.append("\r\n");
        try {
            socketOutputStream.write(sb.toString().getBytes(contentEncoding));
        } catch (IOException e) {
            throw new ServerErrorException("response write error: ioexception: "+e.getMessage());
        }
    }

    private void setCookieHeader(StringBuilder sb,Cookie cookie){
        sb.append("Set-Cookie: ").append(cookie.getName())
                .append("=")
                .append(cookie.getValue())
                .append(";");
        if(!Strings.isNullOrEmpty(cookie.getComment())){
            sb.append("comment=").append(cookie.getComment()).append(";");
        }
        sb.append("Max-Age=").append(cookie.getMaxAge()).append(";");
        sb.append("Domain=").append(cookie.getDomain()).append(";");
        sb.append("Path=").append(cookie.getPath()).append(";");
        if(cookie.getSecure() != -1){
            sb.append(cookie.getSecure()).append(";");
        }
        sb.append("\r\n");
    }
}
