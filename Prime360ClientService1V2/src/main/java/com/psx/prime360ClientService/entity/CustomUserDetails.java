package com.psx.prime360ClientService.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
public class CustomUserDetails extends User implements UserDetails{
	
	public CustomUserDetails(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
		super(userId, password, authorities);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6452155215170711380L;

}
