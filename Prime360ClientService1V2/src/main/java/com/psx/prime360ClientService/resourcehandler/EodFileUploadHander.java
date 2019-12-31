package com.psx.prime360ClientService.resourcehandler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psx.prime360ClientService.entity.EodFileStats;
import com.psx.prime360ClientService.entity.EodProcessingStages;
import com.psx.prime360ClientService.entity.Error_Records_Info_T;
import com.psx.prime360ClientService.serviceI.EodFileUploadService;
import com.psx.prime360ClientService.utils.ResponseJson;

@RequestMapping("/requestPosting")
@CrossOrigin()
@RestController
public class EodFileUploadHander {

	private static Logger logger = Logger.getLogger(EodFileUploadHander.class
			.getName());

	@Autowired
	private EodFileUploadService eodService;

	/***
	 * 
	 * @param dataSource
	 * @param file
	 * @param request
	 * @return ResponseEntity object
	 */
	@PostMapping(value = "/uploadeodfile")
	public ResponseEntity<?> primeMatch360RequestProcess(
			@RequestParam("dataSource") String dataSource,
			@RequestPart("file") MultipartFile file, HttpServletRequest request) {

		logger.info("Entered into primeMatch360Request" + dataSource);

		ResponseEntity<?> responseEntity = null;

		try {

			ResponseJson<HttpStatus, ?> responseJson = eodService
					.eodFileUpload(file, dataSource);
			responseEntity = new ResponseEntity<>(responseJson, HttpStatus.OK);

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception ce) {

			ResponseJson<HttpStatus, Map<String, String>> responseJson1 = new ResponseJson<>();

			responseJson1.setMessage("unsuccessfull");

			responseEntity = new ResponseEntity<>(responseJson1,
					HttpStatus.PRECONDITION_FAILED);

		}

		return responseEntity;
	}

	/***
	 * 
	 * @returns List of String Values
	 */
	@GetMapping("/getAllDatasources")
	public List<String> getAllDatasources() throws IOException {
		List<String> retValue = eodService.getAllDatasources();
		logger.info("EodFileHandler getAllDatasources() :: " + retValue);
		return retValue;
	}

	/***
	 * 
	 * @returns List of EodFileStats object
	 */
	@GetMapping("/getEodFileStatus")
	public List<EodFileStats> getEodFileStatus() {
		logger.info("RequestPostingResourceHandler getAllRequestId()");
		List<EodFileStats> retValue = new ArrayList<>();
		retValue = eodService.getEodFileStatus();

		logger.info("RequestPostingResourceHandler getAllRequestId() : "
				+ retValue);
		return retValue;
	}

	/***
	 * 
	 * @param batchId
	 *            (batchId)
	 * @returns List of Error_Records_Info_T object
	 */
	@GetMapping("/getErrorData/{batchId}")
	public List<Error_Records_Info_T> getErrorRecords(
			@PathVariable String batchId) {
		logger.info("RequestPostingResourceHandler getErrorRecords()");
		List<Error_Records_Info_T> retValue = new ArrayList<>();
		retValue = eodService.getErrorRecords(batchId);
		logger.info("RequestPostingResourceHandler getErrorRecords() : "
				+ retValue);
		return retValue;
	}
	
//	@GetMapping("/getExportErrorData/{batchId}")
//	public List<Error_Records_Info_T> getExportErrorData(
//			@PathVariable String batchId) {
	 @GetMapping("/getExportErrorData/{batchId}")
	    public ResponseEntity<InputStreamResource> getPdf2(@PathVariable String batchId) {
	        logger.info("Downloading...");
	 
	        InputStream input = null;
		logger.info("RequestPostingResourceHandler getExportErrorData()");
		 logger.info("Downloading File from Service....");
		 try{
		input = eodService.getExportErrorData(batchId);
		 }
		 catch(Exception e)
		 {
			 logger.error(e,e);
		 }
	        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/csv"))
	                .body(new InputStreamResource(input));
	 
	     
	 
	    }
	

	/***
	 * 
	 * @returns List of EodProcessingStages object
	 */

	@GetMapping("/getEodStageStatus")
	public List<EodProcessingStages> getEodStagebatchIds() {
		logger.info("RequestPostingResourceHandler getEodStagebatchIds()");
		List<EodProcessingStages> retValue = new ArrayList<>();
		retValue = eodService.getEodStagebatchIds();
		logger.info("RequestPostingResourceHandler getEodStagebatchIds() : "
				+ retValue);
		return retValue;
	}

	/***
	 * 
	 * @param bid
	 *            (batchId)
	 * @returns List of EodProcessingStages object
	 */
	@GetMapping("/getEodStageStatus1/{bid}")
	public List<EodProcessingStages> getEodStageStatus(@PathVariable String bid) {
		logger.info("RequestPostingResourceHandler getEodStageStatus111()");
		List<EodProcessingStages> retValue = new ArrayList<>();
		logger.info("request obtained value ::" + bid);
		retValue = eodService.getEodStageStatus(bid.trim());
		logger.info("RequestPostingResourceHandler getEodStageStatus() : "
				+ retValue);
		return retValue;
	}
}
