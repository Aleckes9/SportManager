/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.service;

import com.mycompany.projetsportmanager.exception.SportManagerException;
import java.io.InputStream;

/**
 *
 * @author Alex
 */
public interface UserService {
    /**
	 * Retrieve user picture
	 * 
	 * @param userId
	 *            the user identifier
	 * @return the user picture
	 * @throws SportManagerException
	 */
	byte[] getUserPicture(Long userId) throws SportManagerException;

	/**
	 * Change user picture
	 * 
	 * @param userId
	 *            the user identifier
	 * @param uploadedInputStream
	 *            image uploaded input stream
	 * @throws SportManagerException
	 */
	void addUserPicture(Long userId, InputStream uploadedInputStream)
			throws SportManagerException;

	/**
	 * Delete user picture
	 * 
	 * @param userId
	 *            the user identifier
	 * @throws SportManagerException
	 */
	void deleteUserPicture(Long userId) throws SportManagerException;
}
