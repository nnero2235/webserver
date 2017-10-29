package me.nnero.http;

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
public class Part {

    private InputStream is;

    private String boundary;

    private byte[] boundaryBytes;

    private BufferedReader reader;

    private Map<String,String> partInfoMap;

    private byte[] overFlowData; //buf next more data

    public Part(InputStream is,String boundary) throws IOException {
        this.boundary = boundary;
        this.boundaryBytes = this.boundary.getBytes();
        this.is = is;
        this.reader =  new BufferedReader(new InputStreamReader(this.is));
        decodePartInfo();
    }

    private void decodePartInfo() throws IOException {
        String line = null;
        line = reader.readLine();
        if(!line.equals(boundary)){
            throw new BadRequestException("multipart error: boundary is wrong: "+line+" boundary is: "+boundary);
        }
        partInfoMap = new HashMap<>();
        while (true) {
            line = reader.readLine();
            if("\r\n".equals(line)){
                break;
            }
            String[] strArr = line.split(";");
            for (String s : strArr) {
                int index = s.lastIndexOf(":");
                if(index == -1){
                    index = s.lastIndexOf("=");
                }
                if(index != -1){
                    String key = line.substring(0,index).trim().toLowerCase();
                    String value = line.substring(index+1).trim().toLowerCase();
                    partInfoMap.put(key,value);
                }
            }
        }
    }

    public String getPartInfo(String name){
        if(name == null){
            throw new RuntimeException("get part info name is null");
        }
        return partInfoMap.get(name);
    }

    //return -1 when reach end
    public int readData(byte[] buf) throws IOException {
        int length = is.read(buf);
        int boundaryLength = getDataBoundary(buf,length,boundaryBytes);
        if(boundaryLength != -1){
            return boundaryLength;
        }
        return length;
    }

    byte[] getOverFlowData(){
        return overFlowData;
    }


    //return data boundary length or -1 means not reached
    private int getDataBoundary(byte[] data,int length,byte[] boundary) throws IOException {
        boolean matched = false;
        int matchCount = 0;
        for(int i=0;i<length;i++){
            if(data[i] == boundary[i]){
                matched = true;
                matchCount++;
                if(matchCount == boundary.length -1){
                    if(i < length -1) {
                        overFlowData = new byte[length - i];
                        System.arraycopy(data, length - i + 1, overFlowData, 0, length - i + 1);
                    }
                    return i;
                }
            } else {
                matched = false;
                matchCount = 0;
            }
            if(matched && i == length -1){
                byte[] tmpBuf = new byte[256];
                int len = is.read(tmpBuf);
                for(int j=0;j<len;j++){
                    if(tmpBuf[j] == boundary[i+j]){
                        matchCount++;
                        if(matchCount == boundary.length -1){
                            len = j;
                            break;
                        }
                    }
                }
                overFlowData = new byte[256-len];
                System.arraycopy(tmpBuf,len+1,overFlowData,0,256-len -1);
                return i;
            }
        }
        return -1;
    }
}
