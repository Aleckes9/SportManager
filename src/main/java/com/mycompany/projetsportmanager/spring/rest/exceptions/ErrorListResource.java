/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorListResource {

	private List<ValidationErrorResource> validationErrorResources;
	private HttpStatus status;
	
	public ErrorListResource( List<ValidationErrorResource> validationErrorResources, HttpStatus status) {
		super();
		this.validationErrorResources = validationErrorResources;
		this.status = status;
	}
	
	public List<ValidationErrorResource> getValidationErrorResources() {
		return validationErrorResources;
	}

	public void setValidationErrorResources(
			List<ValidationErrorResource> validationErrorResources) {
		this.validationErrorResources = validationErrorResources;
	}

	@JsonIgnore
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	//Getter supplémentaire + JsonIgnore sur le champ original pour récupérer le code en int et pas son alias en String
	public int getStatusCode() {
		return status.value();
	}
	
}

