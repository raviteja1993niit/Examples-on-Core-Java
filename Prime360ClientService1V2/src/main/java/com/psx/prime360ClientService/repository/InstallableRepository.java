package com.psx.prime360ClientService.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psx.prime360ClientService.entity.InstallablesEntity;

/**
 *
 * @author jayantronald
 *
 */
@Repository
public interface InstallableRepository extends JpaRepository<InstallablesEntity, Integer> {
	//@Query("SELECT i FROM InstallablesEntity i WHERE i.module='Installation' AND i.PROP_KEY='Config Details'")
	//public List<InstallablesEntity> getInstallablesEntity();

	@Query("SELECT u from InstallablesEntity u WHERE u.module='Client_Bulk_Upload'")
	public List<InstallablesEntity> getUploadDetails();

	@Query("SELECT coalesce(max(i.id), 0) FROM InstallablesEntity i")
	public Integer getMaxId();

	@Query("SELECT max(u.lchgTime) from InstallablesEntity u WHERE u.module='Client_Bulk_Upload'")
	List<Date> getAllTableBasedOnLchgtime();

	@Query("SELECT u from InstallablesEntity u WHERE lchgTime=?1 AND u.module='Client_Bulk_Upload'")
	List<InstallablesEntity> getUploadDetails(Date lchgTime);
}
