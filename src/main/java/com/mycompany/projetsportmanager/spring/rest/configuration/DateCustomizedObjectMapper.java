/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.configuration;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DateCustomizedObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7181527889744818881L;

	public DateCustomizedObjectMapper() {
		super();
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

}
