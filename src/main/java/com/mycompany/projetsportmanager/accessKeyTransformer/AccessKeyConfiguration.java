/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.accessKeyTransformer;


import java.io.Serializable;

public class AccessKeyConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProfileAccessKey[] accessKeyConfiguration = null;
	
	public AccessKeyConfiguration() {
	}
	
	public AccessKeyConfiguration(ProfileAccessKey[] accessKeyConfiguration) {
		this.accessKeyConfiguration = accessKeyConfiguration;
	}

	
	
	public ProfileAccessKey[] getAccessKeyConfiguration() {
		return accessKeyConfiguration;
	}

	public void setAccessKeyConfiguration(ProfileAccessKey[] accessKeyConfiguration) {
		this.accessKeyConfiguration = accessKeyConfiguration;
	}
}

