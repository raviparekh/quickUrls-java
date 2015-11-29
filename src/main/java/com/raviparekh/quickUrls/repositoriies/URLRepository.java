package com.raviparekh.quickUrls.repositoriies;

public interface URLRepository {

    void saveURL(String URLKey, String fullURL);

    String getFullURL(String URLKey);

}
