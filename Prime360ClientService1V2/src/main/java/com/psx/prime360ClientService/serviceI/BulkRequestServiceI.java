package com.psx.prime360ClientService.serviceI;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.psx.prime360ClientService.dto.BulkFileDetailsDTO;
import com.psx.prime360ClientService.entity.BulkUploadColumnMapping;

public interface BulkRequestServiceI {
	//String processBulkRequest(MultipartFile csvFileName,String profileId) throws Exception;
	
	List<BulkFileDetailsDTO> getBulkFileDetails();

	/*String processBulkRequest(String csvFileName,String serverfileName, List<BulkUploadColumnMapping> bulkMappingList,
			int thValue) throws Exception;*/
	
	String processBulkRequest(String tableName,String csvFileName, String serverFileName,
			List<BulkUploadColumnMapping> bulkMappingList,
			int thValue,String psxBatchID,long submittedDate) throws Exception;
	
	//RequestResultsWrapper getResByReqId(String reqId);
	
	String getUploadedDetails(MultipartFile file) throws IOException;
	
	String reprocessData(String psxBatchId);
	public HashMap<String,String> getAllDataUpload(String tableName);

}
