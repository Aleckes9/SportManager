/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.entity;
// add not generated imports here
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 *
 * @author Alex
 */
@Entity
@Table(name="POC_USER")
@SequenceGenerator(
    name="USER_SEQ",
sequenceName="USER_SEQ"
)
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
  /**
     * idUser
     */
    @Id
    @GeneratedValue(strategy=AUTO, generator="USER_SEQ")    
    private Long idUser;
        
            
    /**
     * lastName
     */
    private String lastName;
        
            
    /**
     * firstName
     */
    private String firstName;
    
    
	/**
	 * mail
	 */
	private String mail;
        

    /**
     * picture
     */
    @Lob
    private byte[] picture;
        
            
    /** 
     * default constructor 
     */
    public User() {
    }
    
    
    /**
    *
    * @return idUser
    */
    public Long getIdUser() {
		return idUser;
	}


    /**
    *
    * @param idUser idUser value
    */
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}



	/**
     *
     * @return firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     *
     * @param pFirstName firstName value
     */
    public void setFirstName(String pFirstName) {
        this.firstName = pFirstName;
    }

    /**
     *
     * @return lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     *
     * @param pLastName lastName value
     */
    public void setLastName(String pLastName) {
        this.lastName = pLastName;
    }
    
    /**
     *
     * @return mail
     */
    public String getMail() {
		return mail;
	}


    /**
    *
    * @param mail mail value
    */
	public void setMail(String mail) {
		this.mail = mail;
	}


	/**
     *
     * @return picture
     */
    public byte[] getPicture() {
        return this.picture;
    }

    /**
     *
     * @param pPicture picture value
     */
    public void setPicture(byte[] pPicture) {
        this.picture = pPicture;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        result = toStringImpl();
        return result;
    }
    
    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("idUser=").append(getIdUser());
        buffer.append(",");
        buffer.append("lastName=").append(getLastName());
        buffer.append(",");
        buffer.append("firstName=").append(getFirstName());
        buffer.append(",");
        buffer.append("mail=").append(getMail());
        buffer.append("]");
        return buffer.toString();
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (mail == null) {
			if (other.mail != null) {
				return false;
			}
		} else if (!mail.equals(other.mail)) {
			return false;
		}
		return true;
	}
}

