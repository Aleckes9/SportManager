/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.repository;

import java.util.List;

import com.mycompany.projetsportmanager.entity.User;
import com.mycompany.projetsportmanager.exception.SportManagerException;

/**
 *
 * @author Alex
 */
public interface UserRepositoryCustom {
   
    	// ajout de methodes supplementaires si necessaire
	/**
	 * Construit findByExample
	 * @param user
	 * @param nbLignes
	 * @param indexDepart
	 * @param ignoreCase
	 * @param likeMode
	 * @param matchMode
	 * @return
	 */
	List <User> findByExample(User user, int nbLignes, int indexDepart, boolean ignoreCase, boolean likeMode, int matchMode) throws SportManagerException;
	
	void clearCache();
     
}
