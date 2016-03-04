/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.exceptions;

/**
 *
 * @author Alex
 */
public class DefaultSportManagerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4084469028531589930L;
	
	public DefaultSportManagerException(ErrorResource errorResource) {
		super();
		this.errorResource = errorResource;
	}

	private ErrorResource errorResource;

	public ErrorResource getErrorResource() {
		return errorResource;
	}

	public void setErrorResource(ErrorResource errorResource) {
		this.errorResource = errorResource;
	} 
}
