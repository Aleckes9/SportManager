/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.accessKeyTransformer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserProfilesAccessKey implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -395939703331220050L;
	
	private List<ProfileAccessKey> accessKeyConfiguration = new ArrayList<ProfileAccessKey>();
	
	public UserProfilesAccessKey() {
	}
	
	
	public void addProfileAccessKey(ProfileAccessKey profileAccessKey) {
		accessKeyConfiguration.add(profileAccessKey);
	}	

	
	
	public List<ProfileAccessKey> getAccessKeyConfiguration() {
		return accessKeyConfiguration;
	}


	public void setAccessKeyConfiguration(
			List<ProfileAccessKey> accessKeyConfiguration) {
		this.accessKeyConfiguration = accessKeyConfiguration;
	}
}

