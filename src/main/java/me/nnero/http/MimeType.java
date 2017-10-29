package me.nnero.http;

import com.google.common.base.Strings;

/**
 * Author: NNERO
 * Time : 19:32 2017/10/20
 */
public enum MimeType {
    GENERAL("*/*"),
    APPLICATION("application/*"),
    APPLICATION_JSON("application/json"),
    TEXT("text/*"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    IMAGE("image/*"),
    IMAGE_PNG("image/png"),
    IMAGE_JPG("image/jpg"),
    MULTI_PART("multipart/form-data");

    private String type;

    MimeType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }

    public static MimeType parse(String mimeTypeStr) {
        if (Strings.isNullOrEmpty(mimeTypeStr)) {
            return GENERAL;
        } else if (GENERAL.type.equalsIgnoreCase(mimeTypeStr)) {
            return GENERAL;
        } else if (APPLICATION.type.equalsIgnoreCase(mimeTypeStr)) {
            return APPLICATION;
        } else if (APPLICATION_JSON.type.equalsIgnoreCase(mimeTypeStr)) {
            return APPLICATION_JSON;
        } else if (TEXT.type.equalsIgnoreCase(mimeTypeStr)) {
            return TEXT;
        } else if (TEXT_HTML.type.equalsIgnoreCase(mimeTypeStr)) {
            return TEXT_HTML;
        } else if (TEXT_PLAIN.type.equalsIgnoreCase(mimeTypeStr)) {
            return TEXT_PLAIN;
        } else if (IMAGE.type.equalsIgnoreCase(mimeTypeStr)) {
            return IMAGE;
        } else if (IMAGE_PNG.type.equalsIgnoreCase(mimeTypeStr)) {
            return IMAGE_PNG;
        } else if (IMAGE_JPG.type.equalsIgnoreCase(mimeTypeStr)) {
            return IMAGE_JPG;
        } else if(MULTI_PART.type.equalsIgnoreCase(mimeTypeStr)){
            return MULTI_PART;
        }
        return GENERAL;
    }
}
