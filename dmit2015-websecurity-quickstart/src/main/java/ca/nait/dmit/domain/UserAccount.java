package ca.nait.dmit.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="websecurity_quickstart_useraccount")
public class UserAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
		
	@Column(nullable=false, unique=true, length=100)
	@Email(message="Email Address is not in a recognizable format.")
	private String emailAddress;
	
	@Column(nullable=false)
	@Pattern(regexp = "^.*(?=.{6,})(?=.*[a-z])(?=.*[A-Z]).*$", 
	  message = "Password must be at least 6 characters and must contain at least one lower case letter and one upper case letter")
	private String password;

	private boolean enabled = true;
	
	@NotNull(message = "User must be assigned at least one role")
	@Size(min = 1, message = "User must be assigned at least one role")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "websecurity_quickstart_userrole", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "rolename", length = 35, nullable = false)
	private Set<String> roles = new HashSet<String>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public UserAccount() {
		super();
	}	
	
}
