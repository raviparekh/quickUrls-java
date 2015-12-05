package com.raviparekh.quickUrls.config;

import com.raviparekh.quickUrls.services.SimpleURLService;
import com.raviparekh.quickUrls.services.URLService;
import com.raviparekh.quickUrls.utils.KeyGenerator;
import com.raviparekh.quickUrls.utils.URLKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.raviparekh.quickUrls.services")
public class ServiceConfig {

    @Bean
    public KeyGenerator getUrlGenerator() {
        return new URLKeyGenerator();
    }

    @Bean
    public URLService getURLService() {
        return new SimpleURLService();
    }
}
