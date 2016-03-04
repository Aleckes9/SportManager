/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.projetsportmanager.entity.User;
import com.mycompany.projetsportmanager.exception.SportManagerException;
import com.mycompany.projetsportmanager.repository.UserRepository;
import com.mycompany.projetsportmanager.service.UserService;
import com.mycompany.projetsportmanager.spring.rest.assemblers.UserResourceAssembler;
import com.mycompany.projetsportmanager.spring.rest.exceptions.DefaultSportManagerException;
import com.mycompany.projetsportmanager.spring.rest.exceptions.ErrorResource;
import com.mycompany.projetsportmanager.spring.rest.resources.UserResource;

@RestController
@RequestMapping("/users")
public class UserController {
	
	final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private Mapper dozerBeanMapper;
	
	@Autowired
	private UserResourceAssembler userResourceAssembler;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public  PagedResources<UserResource> collectionList(Pageable pageable, 
		    PagedResourcesAssembler<User> assembler,
		    @RequestParam(value="firstName", required=false) String firstName,
		    @RequestParam(value="lastName", required=false) String lastName,
		    @RequestParam(value="mail", required=false) String mail,
		    HttpServletRequest httpServletRequest){
		
		Page<User> users = null;
		
		try {
			if(firstName != null || lastName != null || mail != null){
				
				if(firstName == null)
					firstName = "";
				if(lastName == null)
					lastName = "";
				if(mail == null)
					mail = "";
				
				users = userRepo.findByLastNameLikeIgnoreCaseAndFirstNameLikeIgnoreCaseAndMailLikeIgnoreCase("%"+lastName+"%", "%"+firstName+"%", "%"+mail+"%",pageable);
			} else {
				users = userRepo.findAllPaged(pageable);
			}
		
		} catch (DataAccessException e) {
			String msg = "Can't retrieve asked users from DB";
			logger.error(msg, e);
			throw new DefaultSportManagerException(new ErrorResource("db error", msg, HttpStatus.INTERNAL_SERVER_ERROR));
		}
		
		PagedResources<UserResource> resources = assembler.toResource(users, userResourceAssembler);
		resources.add(linkTo(UserController.class).withSelfRel());
		if(httpServletRequest.isUserInRole("AK_ADMIN")){
			resources.add(linkTo(UserController.class).withRel(ActionsConstants.CREATE_VIA_POST));
			//On rajoute les liens sur les users
			for(UserResource resource : resources){
				resource.add(linkTo(methodOn(UserController.class).userGet(resource.getTech_id(), null)).withRel(ActionsConstants.UPDATE_VIA_PUT));
				resource.add(linkTo(methodOn(UserController.class).userGet(resource.getTech_id(), null)).withRel(ActionsConstants.DELETE_VIA_DELETE));
			}
		}
		return resources;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasRole('AK_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public void collectionAdd(@Valid @RequestBody UserResource p, HttpServletRequest request, HttpServletResponse response) {

		User bo = dozerBeanMapper.map(p, User.class);
		bo.setIdUser(null);

		try {
			userRepo.save(bo);
		} catch (DataAccessException e) {
			logger.error("Can't create user into DB", e);
			throw new DefaultSportManagerException(
					new ErrorResource("db error", "Can't create user into DB",HttpStatus.INTERNAL_SERVER_ERROR));
		}

		response.setHeader("Location", request.getRequestURL()
				.append((request.getRequestURL().toString().endsWith("/") ? "": "/"))
		        .append(bo.getIdUser()).toString());

	}
	
	
	/**
	 * Retrieve user by id
	 * 
	 * @param userId
	 *            the user identifier
	 * @return the user corresponding to the specified user identifier
	 */
	@RequestMapping(method = RequestMethod.GET, value="/{userId}")
	public UserResource userGet(@PathVariable("userId") Long userId, HttpServletRequest httpServletRequest) {

		User requestBo = null;
		try {
			requestBo = userRepo.findOne(userId);
		} catch (DataAccessException e) {
			
			String msg = "Can't retrieve asked users from DB";
			logger.error(msg, e);
			throw new DefaultSportManagerException(new ErrorResource("db error", msg, HttpStatus.INTERNAL_SERVER_ERROR));
		}
		
		if(requestBo == null){
			String msg = "User with id " + userId
					+ " not found";
			throw new DefaultSportManagerException(new ErrorResource("not found", msg, HttpStatus.NOT_FOUND));
		}
		
		UserResource resource = userResourceAssembler.toResource(requestBo);
		if(httpServletRequest.isUserInRole("AK_ADMIN")){
			resource.add(linkTo(methodOn(UserController.class).userGet(userId, null)).withRel(ActionsConstants.UPDATE_VIA_PUT));
			resource.add(linkTo(methodOn(UserController.class).userGet(userId, null)).withRel(ActionsConstants.DELETE_VIA_DELETE));
		}
		return resource;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{userId}/picture", produces = "image/jpeg")
	public byte[] pictureGet(@PathVariable("userId") Long userId) {
		try {
			return userService.getUserPicture(userId);
		} catch (SportManagerException e1) { 
			String msg = "Can't retrieve user picture with id " + userId + " from DB";
			logger.error(msg, e1);
			throw new DefaultSportManagerException(new ErrorResource("db error", msg, HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	/**
	 * Affect a image to a specified user
	 * 
	 * @param userId
	 *            the user identifier
	 * @param uploadedInputStream
	 *            the image uploaded input stream
	 * @param fileDetail
	 *            the image detail format
	 * @return response
	 */
	@RequestMapping(method = RequestMethod.POST, value="/{userId}/picture")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(value = "hasRole('AK_ADMIN')")
	public void pictureUpdate(
			@PathVariable("userId") Long userId,
			@RequestParam MultipartFile user_picture) {

		//Le nom du paramètre correspond au nom du champ de formulaire qu'il faut utiliser !!!!
		
		if (user_picture == null || user_picture.isEmpty()) {
			logger.debug("Incorrect input stream or file name for image of user"
					+ userId);
			throw new DefaultSportManagerException(
					new ErrorResource(
							"parameter missing",
							"Picture should be uploaded as a multipart/form-data file param named user_picture",
							HttpStatus.BAD_REQUEST));
		}
		
		if(!(user_picture.getOriginalFilename().endsWith(".jpg") || user_picture.getOriginalFilename().endsWith(".jpeg"))){
			logger.debug("File for picture of user"+userId+" must be a JPEG file.");
			throw new DefaultSportManagerException(new ErrorResource("incorrect file format", "Picture should be a JPG file", HttpStatus.BAD_REQUEST));
		}

		try {
			userService.addUserPicture(userId, user_picture.getInputStream());
		} catch (SportManagerException e) {
			String msg = "Can't update user picture with id " + userId + " into DB";
			logger.error(msg, e);
			throw new DefaultSportManagerException(new ErrorResource("db error", msg,HttpStatus.INTERNAL_SERVER_ERROR));
		} catch (IOException e) {
			String msg = "Can't update user picture with id " + userId + " because of IO Error";
			logger.error(msg, e);
			throw new DefaultSportManagerException(new ErrorResource("io error", msg,HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	/**
	 * Delete the picture of a specified user
	 * 
	 * @param userId
	 *            the user identifier
	 * @return reponse
	 */
	@RequestMapping(method = RequestMethod.DELETE, value="/{userId}/picture")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize(value = "hasRole('AK_ADMIN')")
	public void pictureDelete(@PathVariable("userId") Long userId) {

		try {
			userService.deleteUserPicture(userId);
		} catch (SportManagerException e) {
			String msg = "Can't delete user picture with id " + userId + " from DB";
			logger.error(msg, e);
			throw new DefaultSportManagerException(
					new ErrorResource("db error", msg,HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	/**
	 * Put a user
	 * 
	 * @param p
	 *            the {@link UserResourceBean}
	 * @param userId
	 *            the user identifier
	 * @return {@link UserResourceBean}
	 */
	@RequestMapping(method = RequestMethod.PUT, value="/{userId}", consumes = "application/json; charset=utf-8")
	@PreAuthorize(value = "hasRole('AK_ADMIN')")
	public UserResource userUpdate(@Valid @RequestBody UserResource p,
			@PathVariable("userId") Long userId) {

		try {
			User userToUpdate = new User();
			dozerBeanMapper.map(p, userToUpdate);
			User userBo = userRepo.saveAndFlush(userToUpdate);
			return userResourceAssembler.toResource(userBo);
		} catch (Exception e) {
			String msg = "Can't update user with id " + userId + " from DB";
			logger.error(msg, e);
			throw new DefaultSportManagerException(
					new ErrorResource("db error",msg,HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	/**
	 * Delete a specific user
	 * 
	 * @param userId
	 *            the user identifier
	 * @return response
	 */
	@RequestMapping(method = RequestMethod.DELETE, value="/{userId}")
	@PreAuthorize(value = "hasRole('AK_ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public void userDelete(@PathVariable("userId") Long userId) {

		try {
			userRepo.delete(userId);
		} catch (DataAccessException e) {
			String msg = "Can't delete user with id " + userId	+ " into DB";
			logger.error(msg, e);
			throw new DefaultSportManagerException(
					new ErrorResource("db error", msg,HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

}
