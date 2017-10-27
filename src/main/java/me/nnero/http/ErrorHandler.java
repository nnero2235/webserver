package me.nnero.http;

import lombok.extern.slf4j.Slf4j;
import me.nnero.util.Constants;
import me.nnero.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Author: NNERO
 * Time : 11:52 2017/10/23
 */
@Slf4j
public class ErrorHandler {

    private static volatile ErrorHandler sHandler;

    private ErrorHandler() {
    }

    public static ErrorHandler getHandler() {
        if (sHandler == null) {
            synchronized (ErrorHandler.class) {
                if (sHandler == null) {
                    sHandler = new ErrorHandler();
                }
            }
        }
        return sHandler;
    }

    public void handle(StatusCode statusCode, String msg, ServletHttpResponse response) {
        if (statusCode.equals(StatusCode.BAD_REQUEST)) {
            writeResponse(StatusCode.BAD_REQUEST.code(),StatusCode.BAD_REQUEST.desc(), msg, response);
        } else if (statusCode.equals(StatusCode.NOT_FOUND)) {
            writeResponse(StatusCode.NOT_FOUND.code(),StatusCode.NOT_FOUND.desc(), msg, response);
        } else {
            writeResponse(StatusCode.SERVER_ERROR.code(),StatusCode.SERVER_ERROR.desc(), msg, response);
        }
    }

    private void writeResponse(int code,String desc, String msg, ServletHttpResponse response) {
        response.setContentEncoding(Charset.forName("UTF-8"));
        response.setContentType("text/html");
        response.setStatusCode(code);
        PrintWriter pw = response.getWriter();
        pw.print(Constants.PAGE_START_MAP.get(code));
        pw.print(desc);
        pw.print(": ");
        pw.print(msg);
        pw.print(code == 500 ? Constants.SERVER_ERROR_PAGE_END : Constants.ERROR_PAGE_END);
        pw.flush();
        pw.close();
    }
}
