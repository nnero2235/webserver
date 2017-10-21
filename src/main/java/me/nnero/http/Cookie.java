package me.nnero.http;

import com.google.common.base.Strings;

/**
 * Author: NNERO
 * Time : 19:31 2017/10/20
 */
public class Cookie {

    private String name;

    private String value;

    private String path;

    private String domain;

    private int maxAge;

    private String comment;

    private int version;

    private int secure;

    public Cookie(String name,String value){
        this.name = name;
        this.value = value;
        this.maxAge = -1;// never expired
        this.version = 1;//default
        this.secure = -1; //do not write to client
        this.domain = "*"; //every domain will bring this cookie
        this.path = "/"; //for all request
        this.comment = "";//nothing
    }


    public String getSetCookieHeaderString(){
        StringBuilder sb = new StringBuilder(100);
        sb.append(name)
                .append("=")
                .append(value)
                .append(";")
                .append("Domain")
                .append("=")
                .append(domain)
                .append(";")
                .append("Path")
                .append("=")
                .append(path)
                .append(";")
                .append("Version")
                .append("=")
                .append(version)
                .append(";")
                .append("Max-Age")
                .append("=")
                .append(maxAge)
                .append(";");
        if(!Strings.isNullOrEmpty(comment)){
            sb.append("Comment")
                    .append("=")
                    .append(comment);
        }
        if(secure != -1){
            sb.append(secure)
                    .append(";");
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSecure() {
        return secure;
    }

    public void setSecure(int secure) {
        this.secure = secure;
    }
}
