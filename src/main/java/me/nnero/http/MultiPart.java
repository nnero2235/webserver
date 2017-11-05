package me.nnero.http;

import me.nnero.http.exception.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: NNERO
 * Time: 2017/10/28 22:43
 * Content-Type: multipart/form-data
 **/
public class MultiPart {

    private InputStream is;

    private String boundary;

    public MultiPart(String contentType, InputStream is) throws IOException {
        if(!contentType.startsWith(MimeType.MULTI_PART.type())){
            throw new RuntimeException("content-type is not multipart/form-data");
        }
        int index = contentType.lastIndexOf("=");
        if(index == -1){
            throw new BadRequestException("multipart/form-data: boundary is null");
        }
        boundary = contentType.substring(index+1);
        this.is = is;
        checkBoundary();
    }

    public Part nextPart() throws IOException {
        return new Part(is,boundary);
    }

    private void checkBoundary() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();
        if(!boundary.equals(line)){
            throw new BadRequestException("boundary is wrong!,this is:"+line+" but real is: "+boundary);
        }
    }
}
