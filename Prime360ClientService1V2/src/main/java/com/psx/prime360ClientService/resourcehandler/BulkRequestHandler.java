package com.psx.prime360ClientService.resourcehandler;

import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.psx.prime360ClientService.dto.BulkFileDetailsDTO;
import com.psx.prime360ClientService.dto.ListProcessDto;
import com.psx.prime360ClientService.dto.ProcessDto;
import com.psx.prime360ClientService.entity.BulkUploadColumnMapping;
import com.psx.prime360ClientService.entity.HeaderColumnWrapper;
import com.psx.prime360ClientService.serviceI.BulkRequestServiceI;
import com.psx.prime360ClientService.serviceI.RequestPostingServiceI;
import com.psx.prime360ClientService.utils.BulkQueryUtils;

/**
 * @author Rahul
 *
 */

@RequestMapping("/requestPosting")
@CrossOrigin(maxAge = 3600)
@RestController
public class BulkRequestHandler {

	@Autowired
	BulkRequestServiceI bulkrequestService;

	@Autowired
	RequestPostingServiceI requestPostingServiceI;

	@Autowired
	DataSource dataSource;

	@RequestMapping(value = "/processDto", method = RequestMethod.POST)
	@ResponseBody
	public List<String> processBulkrequest(@RequestBody ListProcessDto processDtoLst) throws Exception {
		System.out.println("BulkRequestHandler processBulkrequest(-) " + processDtoLst);
		List<String> returnData = null;
		List<BulkUploadColumnMapping> bcolMappingList = new ArrayList<BulkUploadColumnMapping>();
		HeaderColumnWrapper wrapper = new HeaderColumnWrapper();
		HashMap<String, String> appProps = bulkrequestService.getAllDataUpload("preBatchDmlBeforeAllRequests");
		try (Connection con = dataSource.getConnection();) {
			BulkQueryUtils.messageDmls(true, con, "preBatchDmlBeforeAllRequests.", appProps, new HashMap(), null);
		}
		String psxBatchID = BulkQueryUtils.getDateForBatchId(System.currentTimeMillis());
		long submittedDate = System.currentTimeMillis();
		String retValue = null;

		String[] fileNames = new String[processDtoLst.getProcessDtolst().size()];
		String[] tableNames = new String[processDtoLst.getProcessDtolst().size()];
		int index = 0;
		Map<String, String> headerColVsDBColName = new HashMap<String, String>();
		for (ProcessDto dto : processDtoLst.getProcessDtolst()) {
			System.out.println("BulkRequestHandler processBulkrequest(-) ProcessDtoList " + dto);
			wrapper = BulkQueryUtils.getHeaderWrapper(dto.getHeadercolumnWrapper());
			wrapper.getBulkColmappingList().forEach(x -> {headerColVsDBColName.put(x.getCsvHeaderData(), x.getDisplayColName());});
			System.out.println("BulkRequestHandler processBulkrequest(-) data " + wrapper);
			int thresholdValue = 0;
			returnData = new ArrayList<String>();
			if (wrapper != null) {
				bcolMappingList = wrapper.getBulkColmappingList();
				fileNames[index] = Paths.get("upload-dir/" + dto.getServerFile()).toString();
				tableNames[index] = dto.getTableName();
				retValue = bulkrequestService.processBulkRequest(dto.getTableName(), dto.getFile(), dto.getServerFile(),
						bcolMappingList, thresholdValue, psxBatchID, submittedDate);
				index++;
			}
//			else {
//				retValue = "Unable to parse the given json String into HeaderColumnWrapper class";
//			}
		}
		requestPostingServiceI.submitMultipleCustomerSearchRequest(fileNames, tableNames, "41", "blkUpload", headerColVsDBColName);
		System.out.println("BulkRequestHandler processBulkrequest(-) bulkUploadColMap : " + bcolMappingList);
		returnData.add(retValue);
		return returnData;
	}

	@GetMapping(value = "/bulkrequest/getallbulkdetails")
	public List<BulkFileDetailsDTO> getBulkUploadDetails() {
		List<BulkFileDetailsDTO> retValue = bulkrequestService.getBulkFileDetails();
		System.out.println("BulkRequestHandler getBulkUploadDetails() : " + retValue);
		return retValue;
	}

	@GetMapping(value = "/bulkrequest/reprocess/{psxBatchId}")
	public List<String> handleReprocessRequest(@PathVariable("psxBatchId") String psxBatchId) {
		List<String> returnData = new ArrayList<String>();
		String retValue = bulkrequestService.reprocessData(psxBatchId);
		System.out.println("BulkRequestHandler handleReprocessRequest(-) retValue  :: " + retValue);
		if (retValue != null) {
			returnData.add(retValue);
		} else {
			returnData.add("Reprocess Failed");
		}
		return returnData;
	}
}