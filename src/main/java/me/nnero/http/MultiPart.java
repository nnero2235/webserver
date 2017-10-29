package me.nnero.http;

import me.nnero.http.exception.BadRequestException;

import java.io.InputStream;
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

    private List<Part> parts;

    public MultiPart(String contentType, InputStream is){
        if(!contentType.startsWith(MimeType.MULTI_PART.type())){
            throw new RuntimeException("content-type is not multipart/form-data");
        }
        int index = contentType.lastIndexOf("=");
        if(index == -1){
            throw new BadRequestException("multipart/form-data: boundary is null");
        }
        boundary = contentType.substring(index+1);
        this.is = is;
        parts = new ArrayList<>();
    }

    public Part nextPart(){
        return new Part(is,boundary);
    }
}
