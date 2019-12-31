package com.psx.prime360ClientService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.psx.prime360ClientService.entity.NSPRequestEntity;
import com.psx.prime360ClientService.entity.NSPRequestResultsEntity;

public interface RequestRepository extends JpaRepository<NSPRequestEntity, Long> {

	@Query("select distinct(request_id) from NSPRequestEntity where  customer_search='Y' AND (node1_request_status='C' AND node2_request_status='C')")
	public List<String> getAllDistinctRequstId();
	
	@Query("SELECT u FROM NSPRequestEntity u  WHERE u.request_id = ?1")
	public NSPRequestEntity getResByreqid(String requestId);
	
	@Query("select max(psx_Id) from NSPRequestEntity")
	public Integer getMaxOfPsxId();

}
