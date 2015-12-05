package com.raviparekh.quickUrls.utils;

import java.net.MalformedURLException;

public interface KeyGenerator {
    String generate(String fullURL) throws MalformedURLException;
}
