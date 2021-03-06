package be.kdg.trips.service.impl;

import be.kdg.trips.dao.UserDao;
import be.kdg.trips.model.UserRole;
import be.kdg.trips.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserService {
	private static final Logger logger = Logger.getLogger(MyUserDetailsService.class);
	@Autowired
	private UserDao userDao;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		be.kdg.trips.model.User user = null;
		try {
			user = userDao.findByUserName(username);
		} catch (Exception e) {
			logger.error("Error username",e);
			throw new UsernameNotFoundException(username);
		}
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);
		
	}

	// Converts User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(be.kdg.trips.model.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	public void createUser(be.kdg.trips.model.User user) throws Exception {
		user.setEnabled(true);
		userDao.saveUser(user);
	}

	public be.kdg.trips.model.User getUser(String username) throws Exception {
		return userDao.findByUserName(username);
	}

	@Override
	public List<be.kdg.trips.model.User> getAllInvitedUsers(int eventId) throws Exception {
		return userDao.getAllInvitedUsers(eventId);
	}
}