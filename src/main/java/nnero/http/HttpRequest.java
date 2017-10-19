package nnero.http;

import com.google.common.base.Strings;
import nnero.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.Principal;
import java.util.*;

/**
 * Author: NNERO
 * Time: 2017/10/6 15:22
 **/
public class HttpRequest implements HttpServletRequest{

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private BufferedReader reader;

    private String uriPath;

    private String queryString;

    private String requestBody;

    private String schema;

    private Cookie[] cookies;

    private int port;

    private String method;

    private Map<String,String> headerMap;

    private Map<String,String> paramMap;

    public HttpRequest(InputStream inputStream,int port){
        reader = new BufferedReader(new InputStreamReader(inputStream));
        headerMap = new HashMap<>();
        paramMap = new HashMap<>();
        this.port = port;
    }

    private void parseRequestLine(String requestLine){
        String[] parts = requestLine.split(" ");
        method = parts[0];
        uriPath = parts[1];
        if("get".equalsIgnoreCase(method)) {
            int index = uriPath.indexOf("?");
            if (index != -1) {
                queryString = uriPath.substring(index + 1);
                uriPath = uriPath.substring(0, index);
            }
        }
        schema = parts[2];
    }

    //需要用的时候才解析
    private void parseRequestParams(){
        if(!Strings.isNullOrEmpty(queryString)) {
            String[] paramStrings = queryString.split("\\&");
            for (String s : paramStrings) {
                String[] paramKV = s.split("=");
                if (paramKV.length == 2) {
                    paramMap.put(paramKV[0], paramKV[1]);
                } else {
                    paramMap.put(paramKV[0], "");
                }
            }
        }
    }

    private void parseHeader(String line){
        String[] headerString = line.split(":");
        if(headerString.length == 2){
            String name = headerString[0].trim().toLowerCase();
            String value = headerString[1].trim().toLowerCase();
            if("cookie".equalsIgnoreCase(name)){ //解析cookie
                String[] cookieValues = value.split(";");
                cookies = new Cookie[cookieValues.length];
                int index = 0;
                for(String cookieV : cookieValues){
                    String[] realCookie = cookieV.split("=");
                    if(realCookie.length >= 2) {
                        Cookie cookie = new Cookie(realCookie[0], realCookie[1]);
                        cookies[index] = cookie;
                        index++;
                    }
                }
            }
            headerMap.put(name,value);
        }
    }

    public void parse(){
        //parse inputstream data
        StringBuilder sb = new StringBuilder();
        String line = "";
        int lineNumber = 0;
        boolean isGet = true;
        try {
            while (true){
                lineNumber++;
                line = reader.readLine();
                if(lineNumber == 1){ //parse request line
                    if(Strings.isNullOrEmpty(line)){
                        break;
                    }
                    parseRequestLine(line);
                    isGet = "get".equalsIgnoreCase(method);
                } else if(isGet){
                    if(Strings.isNullOrEmpty(line)){
                        break;
                    }
                    parseHeader(line);
                } else { //post
                    if(Strings.isNullOrEmpty(line)) {
                        int contentLength = Integer.parseInt(headerMap.get("content-length"));
                        char[] buffer = new char[contentLength];
                        reader.read(buffer);
                        requestBody = new String(buffer);
                        queryString = requestBody;
                        break;
                    }
                    parseHeader(line);
                }
                sb.append(line).append("\r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return cookies;
    }

    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return headerMap.get(name.toLowerCase());
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String name) {
        return 0;
    }

    @Override
    public String getMethod(){
        return method;
    }

    @Override
    public String getPathInfo() {
        return uriPath;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if("jsessionid".equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public String getRequestURI() {
        return uriPath;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {

    }

    @Override
    public void logout() throws ServletException {

    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return "utf-8";
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
    }

    @Override
    public int getContentLength() {
        try {
            return Integer.parseInt(headerMap.get("content-length"));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public long getContentLengthLong() {
        try {
            return Long.parseLong(headerMap.get("content-length"));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public String getContentType() {
        return headerMap.get("content-type");
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        if(paramMap.isEmpty()){
            parseRequestParams();
        }
        return paramMap.get(s);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return schema;
    }

    @Override
    public String getScheme() {
        return schema;
    }

    @Override
    public String getServerName() {
        return Constants.SERVER_NAME;
    }

    @Override
    public int getServerPort() {
        return port;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return reader;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {
    }

    @Override
    public void removeAttribute(String s) {
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }
}
