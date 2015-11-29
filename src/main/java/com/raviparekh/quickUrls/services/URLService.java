package com.raviparekh.quickUrls.services;


import com.raviparekh.quickUrls.exceptions.URLNotFound;

public interface URLService {

    String getFullUrl(String urlKey) throws URLNotFound;

    String createShortenUrl(String fullURL);
}
