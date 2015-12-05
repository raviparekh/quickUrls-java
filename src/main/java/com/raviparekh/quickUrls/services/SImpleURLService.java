package com.raviparekh.quickUrls.services;

import com.raviparekh.quickUrls.exceptions.EncodingError;
import com.raviparekh.quickUrls.exceptions.URLNotFound;
import com.raviparekh.quickUrls.repositories.URLDAO;

import java.net.MalformedURLException;

import com.raviparekh.quickUrls.utils.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;


public class SimpleURLService implements URLService {

    @Autowired
    private URLDAO urldao;

    @Autowired
    private KeyGenerator urlGenerator;

    public String getFullUrl(String urlKey) throws URLNotFound {
        return urldao.getFullURL(urlKey);
    }

    public String createShortenUrl(String fullURL) throws EncodingError {
        try {
            String generatedURLKey = urlGenerator.generate(fullURL);
            urldao.saveURL(generatedURLKey, fullURL);
            return generatedURLKey;
        } catch (MalformedURLException e) {
            throw new EncodingError("Invalid URL cannot generate hash");
        }
    }
}
