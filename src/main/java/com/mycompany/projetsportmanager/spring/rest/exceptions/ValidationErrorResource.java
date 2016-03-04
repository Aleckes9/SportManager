/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.exceptions;

import java.io.Serializable;

public class ValidationErrorResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 125342341069367292L;
	
	private String field;
	private String message;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
