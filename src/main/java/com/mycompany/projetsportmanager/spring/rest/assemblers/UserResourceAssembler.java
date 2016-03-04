/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mycompany.projetsportmanager.entity.User;
import com.mycompany.projetsportmanager.spring.rest.controllers.UserController;
import com.mycompany.projetsportmanager.spring.rest.resources.UserResource;

@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

	public UserResourceAssembler() {
		super(UserController.class, UserResource.class);
	}

	@Autowired
	private Mapper dozerBeanMapper;
	
	public User toEntity(UserResource ressource) {
		User user = dozerBeanMapper.map(ressource, User.class);
    	return user;
	}

	@Override
	public UserResource toResource(User entity) {
	
		UserResource resource = createResourceWithId(entity.getIdUser(), entity);
		//byte[] semble poser soucis pour le mécanisme methodOn
		resource.add(linkTo(UserController.class).slash(resource.getTech_id()).slash("picture").withRel("picture"));
		return resource;
		
	}

	@Override
    protected UserResource instantiateResource(User entity) {
    	UserResource user = dozerBeanMapper.map(entity, UserResource.class);
    	return user;
    }
	
}

