package me.nnero.http;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Author: NNERO
 * Time : 19:38 2017/10/20
 */
@Slf4j
public class RequestBody {

    private InputStream is;

    private long contentLength;

    private String contentType;

    public RequestBody(InputStream is,long contentLength,String contentType){
        this.is = is;
        this.contentLength = contentLength;
        this.contentType= contentType;
    }



    public InputStream getInputStream(){
        return is;
    }

    /**
     * read into buffer
     * @param buffer
     * @param offset
     * @param length
     * @return
     * @throws IOException
     */
    public int read(byte[] buffer,int offset,int length) throws IOException {
        return is.read(buffer,offset,length);
    }

    /**
     * may cause out of memory
     * @return
     */
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int length = -1;
        byte[] buffer = new byte[2048];
        while (true){
            if(contentLength != -1 && bos.size() >= contentLength){
                break;
            } else if(contentLength == -1 && length == 0){
                break;
            }
            length = is.read(buffer);
            bos.write(buffer,0,length);
        }
        return bos.toByteArray();
    }

    public String string(Charset charset) throws IOException {
        return new String(getBytes(),charset);
    }

    public String string() throws IOException {
        return string(Charset.forName("utf8"));
    }

}
