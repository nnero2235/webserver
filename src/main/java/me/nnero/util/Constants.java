package me.nnero.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: NNERO
 * Time : 14:23 2017/10/23
 */
public class Constants {

    public static final String SERVER_NAME = "NNERO";

    public static final String ERROR_PAGE_END = "</h1></p></body></html>";

    public static final String SERVER_ERROR_PAGE_END = "</p></body></html>";

    public static final Map<Integer,String> PAGE_START_MAP = new HashMap<>();

    static {
        PAGE_START_MAP.put(400,"<html><title>400 Bad Request</title><body><p><h1>");
        PAGE_START_MAP.put(404,"<html><title>404 Not Found</title><body><p><h1>");
        PAGE_START_MAP.put(500,"<html><title>500 Server Error</title><body><p>");
    }
}
