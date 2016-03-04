/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.accessKeyTransformer;

import java.io.Serializable;

/**
 *
 * @author Alex
 */
public class AccessKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String accessKey = "";
	private String idProfil = "";
	private String type = "";
	private String value = "";
	

	public AccessKey() {
	}
	
	
	public AccessKey(String pAccessKey, String pIdProfil, String pType, String pValue) {
		this.accessKey = pAccessKey;
		this.idProfil = pIdProfil;
		this.type = pType;
		this.value = pValue;
	}
	
	
	public String getIdProfil() {
		return idProfil;
	}


	public void setIdProfil(String idProfil) {
		this.idProfil = idProfil;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
}

