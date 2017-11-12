package me.nnero.http.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Author: NNERO
 * Time: 2017/11/11 22:24
 * wrapper for outputstream
 * in addition to implements chunked protocol
 **/
public class ServletOutputStream extends OutputStream {

    private OutputStream socketOutputStream;

    private byte[] chunkedBuffer;

    private int bufferSize;

    private boolean usedChunked = true;

    public ServletOutputStream(OutputStream socketOutputStream){
        chunkedBuffer= new byte[4096];
        this.socketOutputStream = socketOutputStream;
    }

    public void setChunkedEnabled(boolean enabled){
        this.usedChunked = enabled;
    }

    @Override
    public void write(int b) throws IOException {
        if(usedChunked){
            if (bufferSize + 1 >= chunkedBuffer.length) {
                innerFlush();
                chunkedBuffer[0] = (byte) b;
                bufferSize += 1;
            } else {
                chunkedBuffer[bufferSize+1] = (byte) b;
                bufferSize += 1;
            }
        } else {
            socketOutputStream.write(b);
        }
    }

    @Override
    public void write(byte[] buf) throws IOException {
        write(buf,0,buf.length);
    }

    @Override
    public void write(byte[] buf,int offset,int length) throws IOException {
        if(usedChunked) {
            int size = length - offset;
            if (bufferSize + size >= chunkedBuffer.length) {
                int restSize = size - bufferSize;
                System.arraycopy(buf, offset, chunkedBuffer, bufferSize, restSize);
                bufferSize += restSize;
                innerFlush();
                System.arraycopy(buf, offset + restSize, chunkedBuffer, bufferSize, length - restSize);
                bufferSize += length - restSize;
            } else {
                System.arraycopy(buf, offset, chunkedBuffer, bufferSize, length);
                bufferSize += length;
            }
        } else {
            socketOutputStream.write(buf,offset,length);
        }
    }

    private void innerFlush() throws IOException {
        String hexSizeStr = Integer.toHexString(bufferSize)+"\r\n";
        socketOutputStream.write(hexSizeStr.getBytes(Charset.forName("utf8")));
        socketOutputStream.write(chunkedBuffer,0,bufferSize);
        socketOutputStream.write("\r\n".getBytes(Charset.forName("utf8")));
        socketOutputStream.flush();
        bufferSize = 0;
    }

    @Override
    public void flush() throws IOException {
        if(usedChunked) {
            innerFlush();
            socketOutputStream.write("0\r\n".getBytes(Charset.forName("utf8")));
            socketOutputStream.flush();
        } else {
            socketOutputStream.flush();
        }
    }

}
