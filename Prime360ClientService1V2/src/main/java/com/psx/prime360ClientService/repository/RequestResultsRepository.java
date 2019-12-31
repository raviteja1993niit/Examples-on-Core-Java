package com.psx.prime360ClientService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.psx.prime360ClientService.entity.NSPRequestResultsEntity;
import com.psx.prime360ClientService.entity.ReqResIdentity;
import java.lang.String;

public interface RequestResultsRepository extends CrudRepository<NSPRequestResultsEntity, ReqResIdentity> {


	//@Query("SELECT u.name FROM NSPRequestResultsEntity u  WHERE u.request_id = ?1")
	//@Query("SELECT u.address,u.landmark,u.area,u.city,u.pin,u.cin,u.din,u.aadhaar,u.ca_number,u.registration_no,u.phone,u.phone_type,u.request_id,u.application_no,u.customer_no,u.segment,u.name,u.pan,u.dob,u.customer_status FROM NSPRequestResultsEntity u  WHERE u.request_id = ?1")
	//public List<NSPRequestResultsEntity> getResultsByRequestId(String requestId);
	
	
	
	/* @Query("SELECT a FROM NSPRequestResultsEntity a WHERE a.request_id=:requestId" )
	    List<NSPRequestResultsEntity> getResultsByRequestId(@Param("requestId") String requestId);*/
	
	
	@Query("SELECT a FROM NSPRequestResultsEntity a WHERE a.requestId=?1" )
		 List<NSPRequestResultsEntity> findByRequestId(String requestid);
		 
		 
		
		/* @Query("select u from NSPRequestResultsEntity u where u.request_id like %?1")
		  List<NSPRequestResultsEntity> findByRequest_Id(String request_id);*/
	
	
	
	}
