/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.resources;

import java.io.Serializable;

public class EntryPointResource extends SportManagerResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1517483939445778428L;
	
	private String applicationName;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
}
