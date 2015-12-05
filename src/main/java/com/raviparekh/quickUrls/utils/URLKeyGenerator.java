package com.raviparekh.quickUrls.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class URLKeyGenerator implements KeyGenerator{

    private static Base64.Encoder encoder = Base64.getEncoder();

    public String generate(String fullURL) throws MalformedURLException {
        URL url = new URL(fullURL);
        StringBuilder builder = new StringBuilder();
        builder.append(url.getPath())
                .append(url.getHost());

        return encoder.encodeToString(builder.toString().getBytes()).substring(0, 6);
    }
}
