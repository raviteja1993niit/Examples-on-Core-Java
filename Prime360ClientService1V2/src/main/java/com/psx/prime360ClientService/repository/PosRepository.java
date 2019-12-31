/**
 * 
 */
package com.psx.prime360ClientService.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psx.prime360ClientService.entity.BaseMetaInfo;
import com.psx.prime360ClientService.entity.BaseMetaInfoIdentity;

/**
 * @author Rahul
 *
 */
@Repository
public interface PosRepository extends JpaRepository<BaseMetaInfo, BaseMetaInfoIdentity> {
	@Query("SELECT b FROM BaseMetaInfo b WHERE b.srcSystemName =?1")
	public List<BaseMetaInfo> getAllBySrcSystemName(String srcSysName);
	
	
}
