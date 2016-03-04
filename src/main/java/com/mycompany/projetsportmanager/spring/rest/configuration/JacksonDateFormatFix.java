/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.configuration;


import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Component
public class JacksonDateFormatFix {

	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	@Autowired
    private DateCustomizedObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
        for (HttpMessageConverter<?> messageConverter : messageConverters) {
            if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
            	MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) messageConverter;
                m.setObjectMapper(objectMapper);
            }
        }
    }
	
}
