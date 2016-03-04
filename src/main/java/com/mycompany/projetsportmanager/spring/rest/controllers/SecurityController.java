/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.controllers;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.projetsportmanager.accessKeyTransformer.UserProfilesAccessKey;
import com.mycompany.projetsportmanager.springsecurity.userdetailsservice.AuthenticatedUserDTO;

@RestController
@RequestMapping("/securities")
public class SecurityController {

	final static Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public UserProfilesAccessKey securitiesGet(HttpServletRequest request){
		final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null
                && securityContext.getAuthentication().getPrincipal() instanceof AuthenticatedUserDTO) {
            return ((AuthenticatedUserDTO) securityContext.getAuthentication().getPrincipal()).getUserProfilesAccessKey();
        }
        return new UserProfilesAccessKey();
	}
	
}
