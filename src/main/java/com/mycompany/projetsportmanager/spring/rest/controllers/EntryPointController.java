/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.projetsportmanager.spring.rest.resources.EntryPointResource;

@RestController
@RequestMapping("/")
public class EntryPointController {

	final static Logger logger = LoggerFactory.getLogger(EntryPointController.class);
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public EntryPointResource entryPointGet(){
	
		EntryPointResource entryPoint = new EntryPointResource();
		entryPoint.setApplicationName("Sport Manager");
		
		entryPoint.add(linkTo(UserController.class).withRel("users"));
		
		return entryPoint;
	}
	
}
