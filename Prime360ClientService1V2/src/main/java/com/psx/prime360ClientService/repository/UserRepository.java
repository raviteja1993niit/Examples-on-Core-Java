package com.psx.prime360ClientService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.psx.prime360ClientService.entity.User;



/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
public interface UserRepository extends CrudRepository<User, String> {
	
	@Query("select u from User u where u.userIdentity.userID= ?1 and u.active='Y'")
	List<User> getActiveUsers(String id);
}
