package org.ng.undp.vdms.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Utility class for manipulating Uri's
 * Created by seyi on 7/16/15.
 */
public class UriUtil {
    public static String  filterPath(String uri, String key) {
        String path = removePath(uri, key);
        if(path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    public static boolean contains(String text, String match) {
        if(text != null) {
            String[] arr = text.split(",");
            return Arrays.asList(arr).contains(match);
        } else{
            return false;
        }
    }

    public static String removePath(String uri, String key) {
        return (uri.replaceAll(key+":[0-9a-zA-Z_\\-\\.,]+", "")).replaceAll("\\/+", "/");
    }

    public static String computePath(String uri, String key, String value) {
        return (uri.replaceAll(key+":[0-9a-zA-Z_\\-\\.,]+", "") + "/" + key + ":" + value).replaceAll("\\/+", "/");
    }

    public static String  getPagedUrl(String uri,  int index) {
        String url = uri.replaceAll("\\&page=\\d*", "").replaceAll("page=\\d*", "");
        if(url.contains("?")) {
            url = url + "&page=" + index;
        } else {
            url = url + "?page=" + index;
        }
        return url.replaceFirst("\\?\\&", "?");
    }


    public static String  getFullPagedUrl(String uri,  int index, String qString) {

        qString = (StringUtils.isEmpty(qString)) ? "" : qString;
        String url = uri + "?" + qString;
        url = url.replaceAll("\\&page=\\d*", "").replaceAll("page=\\d*", "");
        if(url.contains("?")) {
            url = url + "&page=" + index;
        } else {
            url = url + "?page=" + index;
        }
        return url.replaceFirst("\\?\\&", "?");
    }

    public static String  getUnPagedUrl(String uri,  String qString) {
        if(qString != null){
            qString = qString.replaceAll("\\&page=\\d*", "").replaceAll("page=\\d*", "");
        }

        String url = uri;
        System.out.println("QueryString: " + qString);
        if(StringUtils.isNotEmpty(qString)){
            url = uri + "?" + qString;

        }

        //url = url.replaceAll("\\&page=\\d*", "").replaceAll("page=\\d*", "");

        return url.replaceFirst("\\?\\&", "");

    }


    public static String  getLimitUrl(String uri,  int limit) {
        String url = uri.replaceAll("\\&limit=\\d*", "").replaceAll("limit=\\d*", "");
        if(url.contains("?")) {
            url = url + "&limit=" + limit;
        } else {
            url = url + "?limit=" + limit;
        }
        return url.replaceFirst("\\?\\&", "?");
    }

    public static String  getSortUrl(String uri, String sort) {
        String url = uri
                .replaceAll("\\&sort=[a-zA-Z_\\-\\.,]+(.+(DESC|ASC))*", "").replaceAll("sort=[a-zA-Z_\\-\\.,]+(.+(DESC|ASC))*", "")
                .replaceAll("\\&order=[a-zA-Z_\\-\\.,]+(.+(DESC|ASC))*", "").replaceAll("order=[a-zA-Z_\\-\\.,]+(.+(DESC|ASC))*", "");
        if(url.contains("?")) {
            url = url + "&sort=" + sort;
        } else {
            url = url + "?sort=" + sort;
        }
        return url.replaceFirst("\\?\\&", "?");
    }

    public static String encode(String value, String enc){
        String encoded = value;
        try {
            encoded = UriUtils.encode(value, enc);
        }catch(UnsupportedEncodingException uee){
            return encoded;
        }
        return encoded;
    }
}
