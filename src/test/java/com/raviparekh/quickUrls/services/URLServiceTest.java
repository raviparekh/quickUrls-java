package com.raviparekh.quickUrls.services;

import com.raviparekh.quickUrls.exceptions.EncodingError;
import com.raviparekh.quickUrls.exceptions.URLNotFound;
import com.raviparekh.quickUrls.repositories.URLDAO;
import com.raviparekh.quickUrls.utils.KeyGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class URLServiceTest {

    @Mock
    private URLDAO urldao;

    @Mock
    private KeyGenerator urlKeyGenerator;

    @InjectMocks
    private SimpleURLService urlService;

    @Test
    public void getURLReturnsExpectedFullURL() throws URLNotFound {
        String expectedURL = "http://google.co.uk";
        String urlKey = "123a";
        when(urldao.getFullURL(urlKey)).thenReturn(expectedURL);

        String returnedUrl = urlService.getFullUrl(urlKey);

        assertEquals(expectedURL, returnedUrl);

    }

    @Test
    public void saveUrl() throws Exception {
        String fullUrl = "http://google.co.uk";

        when(urlKeyGenerator.generate(fullUrl)).thenReturn("123a");

        urlService.createShortenUrl(fullUrl);

        verify(urldao, times(1)).saveURL("123a", fullUrl);
    }

    @Test(expected = EncodingError.class)
    public void saveUrlErrorsForInvalidURLs() throws Exception {
        String invalidURL = "google";
        when(urlKeyGenerator.generate(invalidURL)).thenThrow(MalformedURLException.class);
        urlService.createShortenUrl(invalidURL);
    }
}
