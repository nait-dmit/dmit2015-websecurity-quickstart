package ca.nait.dmit.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ca.nait.dmit.domain.UserAccount;

@Stateless
public class UserAccountService implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(UserAccountService.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	public UserAccountService() {
		super();
	}

	public void createUserAccount(UserAccount userAccount) {
		if (findByEmailAddress(userAccount.getEmailAddress()) == null) {
			Set<String> roles = new HashSet<String>();
			roles.add("ROLE_CUSTOMER");
			userAccount.setRoles(roles);
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode( userAccount.getPassword() );
			userAccount.setPassword(hashedPassword);
						
			entityManager.persist(userAccount);
		} else {
			throw new RuntimeException("Email Address is already in use.");
		}
	}


	public UserAccount findByEmailAddress(String emailAddress) {
		UserAccount userAccount = null;

		Query query = entityManager
				.createQuery("SELECT ua FROM UserAccount ua WHERE ua.emailAddress = :emailAddressValue");
		query.setParameter("emailAddressValue", emailAddress);

		try {
			userAccount = (UserAccount) query.getSingleResult();
		} catch (NoResultException ex) {
			logger.log(Level.INFO, "No results returned.");
		}

		return userAccount;
	}

	@SuppressWarnings("unchecked")
	public List<UserAccount> findAll() {
		List<UserAccount> accounts = null;

		Query query = entityManager
				.createQuery("SELECT ua FROM UserAccount ua");
		accounts = query.getResultList();

		return accounts;
	}
}
