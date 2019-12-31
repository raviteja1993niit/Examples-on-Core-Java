package com.psx.prime360ClientService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.psx.prime360ClientService.entity.Profile;
import com.psx.prime360ClientService.entity.ProfileParameters;




/**
 * @author yadagiri 
 * Created on 12-Jun-2018
 */
public interface ProfilesRepository extends CrudRepository<Profile, Integer> {
	
	

	@Query("SELECT MAX(P.profileIdentity.profileId) FROM Profile P")
	public Integer getMaxProfileId();
	
	@Query("SELECT p FROM Profile p  WHERE p.profileName = ?1 and ACTIVE='Y'")
	public Profile getProfileByName(String profileName);
	
	@Query("SELECT p FROM Profile p  WHERE p.profileIdentity.profileId = ?1 and ACTIVE='Y'")
	public Profile getProfileById(Integer profileId);
    
    @Query("SELECT p from Profile p WHERE p.active='Y' order by p.profileIdentity.profileId DESC")
	public List<Profile> getAllProfilesByStatus();
    
    @Query("SELECT p.profileName from Profile p WHERE p.active='Y' and p.checker!=null order by p.profileName ASC")
   	public List<String> getAllProfieNames();
    
    @Query("SELECT p from Profile p WHERE p.active='Y' and p.approved='Y' order by p.profileIdentity.profileId DESC")
	public List<Profile> getAllDistinctProfiles();

	@Query("SELECT p from ProfileParameters p WHERE p.active='Y'")
	public List<ProfileParameters> getAllProfileParameters();


	
	
	
	
}
