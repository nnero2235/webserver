package me.nnero.http;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import me.nnero.http.exception.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: NNERO
 * Time: 2017/10/29 22:06
 **/
@Slf4j
public class Part {

    private InputStream is;

    private byte[] boundaryBytes;

    private byte[] boundaryBuf;

    private boolean isOver;

    private Map<String, String> partInfoMap;

    private int matchCount;

    private boolean valid;

    public Part(InputStream is, String boundary) throws IOException {
        this.boundaryBytes = boundary.getBytes();
        this.is = is;
        this.boundaryBuf = new byte[256];
        this.valid = true;
        decodePartInfo();
    }

    private void decodePartInfo() throws IOException {
        String line = null;
        partInfoMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.is));
        while (true) {
            line = reader.readLine();
            if(Strings.isNullOrEmpty(line)){//null part
                valid = false;
                return;
            }
            if ("\r\n".equals(line)) {
                break;
            }
            String[] strArr = line.split(";");
            for (String s : strArr) {
                int index = s.lastIndexOf(":");
                if (index == -1) {
                    index = s.lastIndexOf("=");
                }
                if (index != -1) {
                    String key = line.substring(0, index).trim().toLowerCase();
                    String value = line.substring(index + 1).trim().toLowerCase();
                    partInfoMap.put(key, value);
                }
            }
        }
    }

    public String getPartInfo(String name) {
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        return partInfoMap.get(name);
    }

    public boolean hasData(){
        return valid;
    }

    //return -1 when reach end
    public int readData(byte[] buf) throws IOException {
        if (buf == null) {
            throw new NullPointerException("buf is null");
        }

        if(!valid){
            log.warn("this part is invalided!");
            return -1;
        }

        if (isOver) {
            byte br = (byte) is.read();
            byte bn = (byte) is.read();
            if (br == '\r' && bn == '\n') {
                return -1;
            } else {
                 throw new BadRequestException("Error boundary lack \\r\\n");
            }
        }

        if (matchCount != 0) {
            System.arraycopy(boundaryBuf, 0, buf, 0, matchCount);
        }

        int length = 0;
        matchCount = 0;

        while (true) {
            if (length == buf.length) {
                return length;
            }
            byte b = (byte) is.read();
            if (b == boundaryBytes[matchCount]) {
                boundaryBuf[matchCount] = b;
                matchCount++;
                if (matchCount == boundaryBytes.length) {
                    isOver = true;
                    return length;
                }
            } else {
                if (matchCount != 0) {
                    if (matchCount + length > buf.length) {
                        return length;
                    } else {
                        System.arraycopy(boundaryBuf, 0, buf, length, matchCount);
                        length += matchCount;
                    }
                }
                matchCount = 0;
                buf[length] = b;
                length++;
            }
        }
    }

}
