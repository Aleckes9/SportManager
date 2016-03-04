/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.springsecurity.userdetailsservice;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mycompany.projetsportmanager.accessKeyTransformer.UserProfilesAccessKey;

public class AuthenticatedUserDTO extends User {

	private static final long serialVersionUID = 1L;
	private UserProfilesAccessKey userProfilesAccessKey;

	public AuthenticatedUserDTO(String username, String password,
			Collection<? extends GrantedAuthority> authorities, UserProfilesAccessKey userProfilesAccessKey) {
		super(username, password, authorities);
		this.userProfilesAccessKey = userProfilesAccessKey;
	}
	
	public UserProfilesAccessKey getUserProfilesAccessKey() {
		return userProfilesAccessKey;
	}
}
