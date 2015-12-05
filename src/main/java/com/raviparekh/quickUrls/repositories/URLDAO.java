package com.raviparekh.quickUrls.repositories;

import com.raviparekh.quickUrls.exceptions.URLNotFound;

public interface URLDAO {

    void saveURL(String URLKey, String fullURL);

    String getFullURL(String URLKey) throws URLNotFound;

}
