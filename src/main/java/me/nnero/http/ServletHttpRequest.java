package me.nnero.http;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import me.nnero.http.exception.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: NNERO
 * Time: 2017/10/22 15:43
 **/
@Slf4j
public class ServletHttpRequest implements HttpRequest{

    private InputStream socketInputStream;

    private String clientIP;

    private Map<String,String> requestParamMap;

    private String queryString;

    private Map<String,String> headerMap;

    private List<Cookie> cookies;

    private String method;

    private MimeType mimeType;

    private String path;

    private RequestBody requestBody;

    private long contentLength;

    private String protocol;

    public ServletHttpRequest(InputStream is,String clientIP){
        socketInputStream = is;
        this.clientIP = clientIP;
        headerMap = new HashMap<>();
        contentLength = -1; //means  client doesn't set header : content-length
    }

    private void parseRequestLine(String requestLine){
        try {
            String[] parts = requestLine.split(" ");
            method = parts[0];
            path = parts[1];
            if("GET".equals(method)) {
                int index = path.indexOf("?");
                if (index != -1) {
                    queryString = path.substring(index + 1);
                    path = path.substring(0, index);
                }
            }
            protocol = parts[2];
        } catch (Exception e) {
            throw new BadRequestException("request line: "+requestLine+" is broken");
        }
    }

    //when need then parse
    //only GET method
    // if POST method see @RequestBody class
    private void parseRequestParams(){
        if(!Strings.isNullOrEmpty(queryString)) {
            requestParamMap = new HashMap<>();
            String[] paramStrings = queryString.split("&");
            for (String s : paramStrings) {
                String[] paramKV = s.split("=");
                if (paramKV.length == 2) {
                    requestParamMap.put(paramKV[0], paramKV[1]);
                } else {
                    requestParamMap.put(paramKV[0], "");
                }
            }
        }
    }

    private void parseHeader(String line){
        int splitIndex = line.indexOf(":");
        if(splitIndex != -1){
            String name = line.substring(0,splitIndex).trim().toLowerCase();
            String value = line.substring(splitIndex+1).trim().toLowerCase();
            if("cookie".equalsIgnoreCase(name)){ //parse cookie
                String[] cookieValues = value.split(";");
                cookies = new ArrayList<>();
                for(String cookieV : cookieValues){
                    String[] realCookie = cookieV.split("=");
                    if(realCookie.length >= 2) {
                        Cookie cookie = new Cookie(realCookie[0], realCookie[1]);
                        cookies.add(cookie);
                    }
                }
            } else if("accept".equalsIgnoreCase(name)){ //parse mimetype
                mimeType = MimeType.parse(value);
            } else if("content-length".equalsIgnoreCase(name)){//parse content-length
                try {
                    contentLength = Long.parseLong(value);
                } catch (NumberFormatException e) {
                }
            }
            headerMap.put(name,value);
        } else {
            throw new BadRequestException("header :"+line+" is broken!");
        }
    }

    public void parse(){
        //parse inputstream data
        String line = null;
        int lineNumber = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socketInputStream));
            while (true){
                lineNumber++;
                line = reader.readLine();
                if(lineNumber == 1) { //parse request line
                    parseRequestLine(line);
                }
                //reach end of header area
                if(lineNumber != 1 && Strings.isNullOrEmpty(line)){
                    if("GET".equals(method)){ //means parse is over
                        break;
                    } else if("POST".equals(method)){ //means needing to parse requestBody
                        requestBody = new RequestBody(socketInputStream,contentLength);
                        break;
                    } else {
                        break;
                    }
                }
                parseHeader(line);
            }
        } catch (IOException e) {
            log.error("request parse error ",e);
        }
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public Map<String, String> getParameterMap() {
        if(requestParamMap == null){
            parseRequestParams();
        }
        return requestParamMap;
    }

    @Override
    public String getParameter(String name) {
        if(name == null){
            throw new NullPointerException("name can't be null");
        }
        if(requestParamMap == null){
            parseRequestParams();
            if(requestParamMap == null){ //if null then parse means no params
                return null;
            }
        }
        return requestParamMap.get(name.toLowerCase());
    }

    @Override
    public String getHeader(String name) {
        if(name == null){
            throw new NullPointerException("name can't be null");
        }
        return headerMap.get(name.toLowerCase());
    }

    @Override
    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getHost() {
        return getHeader("host");
    }

    @Override
    public String getClientIPAddress() {
        return clientIP;
    }

    @Override
    public List<Cookie> getCookies() {
        return cookies;
    }

    @Override
    public String getJSessionId() {
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("jsessionid".equalsIgnoreCase(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public MimeType getMimeType() {
        return mimeType;
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public RequestBody getRequestBody() {
        return requestBody;
    }

    @Override
    public long getContentLength() {
        return contentLength;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }
}
