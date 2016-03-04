/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.accessKeyTransformer;

import java.io.Serializable;



public class ProfileAccessKey implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5503139443133039549L;
	
	private String profile = "";
	private AccessKey[] accessKeyLst = null;

	
	
	
	public ProfileAccessKey() {
	}

	
	public ProfileAccessKey(String profile, AccessKey[] accessKeyLst) {
		this.profile = profile;
		this.accessKeyLst = accessKeyLst;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public AccessKey[] getAccessKeyLst() {
		return accessKeyLst;
	}
	public void setAccessKeyLst(AccessKey[] accessKeyLst) {
		this.accessKeyLst = accessKeyLst;
	}	
}

