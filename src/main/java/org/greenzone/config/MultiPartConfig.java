/*
    ================================================================================================
    This code is part of the Greenzone software.

    GreenZone is a closed source software of EN (Eliah Ngugi) which is owned by EN.
    
        Elijah Ngugi Gachuki
    
    This software is private closed source software.
    
    For further details look at or request greenzone-license.txt for further details.

    Copyright (C) 2024 

    Email:  elijah.ngugi.gachuki@gmail.com
    Domain: N/A

    ================================================================================================
    Author : EN
    ================================================================================================
 */
package org.greenzone.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;
import lombok.RequiredArgsConstructor;

/**
 * @author EN - Dec 16, 2024
 */
@Configuration
@RequiredArgsConstructor
public class MultiPartConfig {

    @Bean
    public MultipartResolver multipartResolver() {

        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize( DataSize.ofMegabytes( 200L ) );
        factory.setMaxRequestSize( DataSize.ofMegabytes( 200L ) );
        return factory.createMultipartConfig();
    }
}
