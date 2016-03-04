/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SportManagerResource extends ResourceSupport {

	private Long tech_id;

	public Long getTech_id() {
		return tech_id;
	}

	public void setTech_id(Long tech_id) {
		this.tech_id = tech_id;
	}

}
