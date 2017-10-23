package me.nnero.util;

import java.io.*;

/**
 * Author: NNERO
 * Time : 15:40 2017/10/23
 */
public class ResourceLoader {

    private static volatile ResourceLoader sLoader;

    private String classPath;

    private ResourceLoader() {
        classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
    }

    public static ResourceLoader getLoader() {
        if (sLoader == null) {
            synchronized (ResourceLoader.class) {
                if (sLoader == null) {
                    sLoader = new ResourceLoader();
                }
            }
        }
        return sLoader;
    }

    public String loadAsString(String fileLocation) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(classPath + File.separator + fileLocation));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
