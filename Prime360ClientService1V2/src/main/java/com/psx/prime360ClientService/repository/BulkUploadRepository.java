package com.psx.prime360ClientService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.psx.prime360ClientService.entity.BulkUploadDefn;


public interface BulkUploadRepository extends JpaRepository<BulkUploadDefn,Integer> {
	
	BulkUploadDefn findBypsxbatchId(String batchId);
	@Query("SELECT u.mappingInfo from BulkUploadDefn u ORDER BY fileName")
	List<String> findAllMappingInfo();
}
