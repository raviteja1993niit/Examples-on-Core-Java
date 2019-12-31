package com.psx.prime360ClientService.serviceI;

import java.io.InputStream;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.psx.prime360ClientService.entity.EodFileStats;
import com.psx.prime360ClientService.entity.EodProcessingStages;
import com.psx.prime360ClientService.entity.Error_Records_Info_T;
import com.psx.prime360ClientService.utils.ResponseJson;

public interface EodFileUploadService {

	
	ResponseJson<HttpStatus, ?> eodFileUpload(MultipartFile file,String dataSource)throws Exception;

	List<String> getAllDatasources();
	List<EodFileStats> getEodFileStatus();

	List<Error_Records_Info_T> getErrorRecords(String batchId);

	List<EodProcessingStages> getEodStagebatchIds();

	List<EodProcessingStages> getEodStageStatus(String batchId);

	InputStream getExportErrorData(String batchId);

	
}
