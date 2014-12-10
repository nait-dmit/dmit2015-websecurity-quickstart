package ca.nait.dmit.controller;

import javax.inject.Named;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Named
@javax.enterprise.context.SessionScoped
public class AuthenticationBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public boolean isLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal() instanceof UserDetails;
	}

	public boolean hasRole(String rolename) {
		GrantedAuthority ga = new SimpleGrantedAuthority(rolename);
		return SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().contains(ga);
	}

	public boolean hasAnyRole(String... rolenames) {
		boolean exists = false;
		for (String rolename : rolenames) {
			if (hasRole(rolename)) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	public String getLoggedInUsername() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}

}