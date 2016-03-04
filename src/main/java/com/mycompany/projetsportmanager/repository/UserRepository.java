/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mycompany.projetsportmanager.entity.User;

/**
 *
 * @author Alex
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
	
	@Query(value="select u from User u",
			countQuery="select count(u) from User u")
	Page<User> findAllPaged(Pageable pageable);
	
	@Query(value="select u from User u where upper(u.lastName) like upper(?1) and upper(u.firstName) like upper(?2) and upper(u.mail) like upper(?3)",
			countQuery="select count(p) from User u where upper(u.lastName) like upper(?1) and upper(u.firstName) like upper(?2) and upper(u.mail) like upper(?3)")
	Page<User> findByLastNameLikeIgnoreCaseAndFirstNameLikeIgnoreCaseAndMailLikeIgnoreCase(String lastName,String firstName, String mail,Pageable pageable);
}
