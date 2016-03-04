/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.springsecurity.userdetailsservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.mycompany.projetsportmanager.accessKeyTransformer.AccessKey;
import com.mycompany.projetsportmanager.accessKeyTransformer.AccessKeyConfiguration;
import com.mycompany.projetsportmanager.accessKeyTransformer.ProfileAccessKey;
import com.mycompany.projetsportmanager.accessKeyTransformer.UserProfilesAccessKey;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * A custom authentication user details service.
 *
 */
public class MyAuthenticationUserDetailsService  implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Value("classpath:/accessKeyConfig/accessKey.json")
	private Resource accessKeyConfigurationFile;
	
    // Ensemble des AccessKey resultant de l'import du fichier de conf 
    private AccessKeyConfiguration accessKeyConfiguration = null;
    
    /**
     * Load the access key configuration.
     */
    @PostConstruct
    public void loadAccessKeyConfiguration() throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        accessKeyConfiguration = mapper.readValue(accessKeyConfigurationFile.getInputStream(), AccessKeyConfiguration.class);
    }
    	
    /**
     * Loads the user details from the token.
     */
	@SuppressWarnings("unchecked")
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) throws UsernameNotFoundException {
		List<String> ldapProfiles = (List<String>) preAuthenticatedAuthenticationToken.getCredentials();
		
		// Injection des AK des profils du fichier JSON en fonction des profils "Habile" trouves
    	// Boucle sur les profils Habile
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		UserProfilesAccessKey userProfilesAccessKey = new UserProfilesAccessKey();
		for (String profil : ldapProfiles) {
			// Boucle sur tout les AccessKey du fichier JSON
			for (ProfileAccessKey pak : accessKeyConfiguration.getAccessKeyConfiguration()) {
				// Si profil Habile trouvé dans AccessKeyConfiguration alors on injecte ses AK correspondant 
    			if (profil.equalsIgnoreCase(pak.getProfile())) {
    				userProfilesAccessKey.addProfileAccessKey(pak);
    				for (AccessKey ak : pak.getAccessKeyLst()) {
    					authorities.add(new SimpleGrantedAuthority(ak.getAccessKey()));
					}
    			}
			}
		}
		
		// BUild the user details.
		return new AuthenticatedUserDTO((String) preAuthenticatedAuthenticationToken.getPrincipal(), "none", authorities, userProfilesAccessKey);
	}
}
