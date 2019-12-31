package com.psx.prime360ClientService.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.psx.prime360ClientService.entity.CustomUserDetails;
import com.psx.prime360ClientService.entity.User;
import com.psx.prime360ClientService.exception.ResourceNotFoundException;
import com.psx.prime360ClientService.repository.UserRepository;
import com.psx.prime360ClientService.resourcehandler.NegativeEodFileUploadHander;
import com.psx.prime360ClientService.serviceI.UserService;



/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	
	//private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
	private static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	@Autowired
	private UserRepository userRepository;
	
	private User getLatestUser(List<User> users) {
		Collections.sort(users, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o2.getUserIdentity().getLchgTime().compareTo(o1.getUserIdentity().getLchgTime());
			}
		});
		return users.get(0);

	}
	
	@Override
	public User getUserById(String userId) {
		List<User> existingUser = userRepository.getActiveUsers(userId);
		if (existingUser.size() > 0) {
			return getLatestUser(existingUser);
		} else {
			throw new ResourceNotFoundException(userId, "User not exist.");
		}
	}
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		logger.info("loadUserByUsername service started :: ");
		User user = getUserById(userId);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new CustomUserDetails(user.getUserID(), user.getPassword(), getAuthority(user));
	}
	
	private List<GrantedAuthority> getAuthority(User user) {
		
		logger.info("getAuthority service started :: ");
		String userRole = user.getRoleID();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if (userRole != null) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + userRole));
		}
		return grantedAuthorities;

	}

}
