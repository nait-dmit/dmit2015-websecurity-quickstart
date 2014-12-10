package ca.nait.dmit.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import util.JSFUtil;
import ca.nait.dmit.domain.UserAccount;
import ca.nait.dmit.service.UserAccountService;

@Named("uaBean")
@RequestScoped
public class UserAccountBean {
	
	@Inject
	private UserAccountService userAccountService;
	
	private UserAccount userAccount = new UserAccount();

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public UserAccountBean() {
		super();
	}
	
	public String createUserAccount() {
		String nextPage = null;
	
		try {
			userAccountService.createUserAccount(userAccount);
										
			userAccount = new UserAccount();
			nextPage = "/pages/login?faces-redirect=true";
			JSFUtil.addInfoMessage("Successfully created user account");
		} catch( Exception e ) {
			JSFUtil.addErrorMessage("Create user account was not successful with exception: " + e.getMessage());
		}
		
		return nextPage;
	}

}
