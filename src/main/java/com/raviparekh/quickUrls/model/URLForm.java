package com.raviparekh.quickUrls.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

public class URLForm {

    @NotEmpty
    @NotNull
    @URL
    private String URL;


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
