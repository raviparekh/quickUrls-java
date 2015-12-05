package com.raviparekh.quickUrls.controller;


import com.raviparekh.quickUrls.exceptions.URLNotFound;
import com.raviparekh.quickUrls.services.URLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class URLControllerTest {

    @Mock
    private URLService urlService;

    @InjectMocks
    private URLController urlController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
    }

    @Test
    public void urlControllerShouldRedirectToFullURL() throws Exception {
        String expectedRedirectUrl = "http://google.co.uk";
        String urlKey = "123a";

        when(urlService.getFullUrl(urlKey)).thenReturn(expectedRedirectUrl);

        mockMvc.perform(get("/url/123a"))
                .andExpect(status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl(expectedRedirectUrl));
    }

    @Test
    public void urlControllerShouldRedirectToHomePageIfURLNotFound() throws Exception{
        when(urlService.getFullUrl(any(String.class))).thenThrow(URLNotFound.class);

        mockMvc.perform(get("/url/123a"))
                .andExpect(status().is(200))
                .andExpect(view().name("error"));

    }

    @Test
    public void urlControllerShouldReturnNewlyCreateShortenURL() throws Exception {
        String expectedRedirectUrl = "http://google.co.uk/12351212";
        when(urlService.createShortenUrl(expectedRedirectUrl)).thenReturn("123a");

        mockMvc.perform(post("/url/").param("fullURL", expectedRedirectUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("url"))
                .andExpect(model().attribute("shortenURL", "http://localhost/url/123a"));

    }

    @Test
    public void urlControllerShouldReturnCreateForm() throws Exception {
        mockMvc.perform(get("/url/"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"));

    }

}
