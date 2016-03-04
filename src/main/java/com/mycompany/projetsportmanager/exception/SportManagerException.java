/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.exception;

/**
 *
 * @author Alex
 */
public class SportManagerException extends Throwable{
    
    /** Serial version UID */
	private static final long serialVersionUID = 7334600182659600274L;

	/**
	 * Default constructor
	 */
	public SportManagerException() {
		super();
	}

	/**
	 * Exception with message
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public SportManagerException(String message) {
		super(message);
	}

	/**
	 * Exception with message and original exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param throwable
	 *            original exception
	 */
	public SportManagerException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
