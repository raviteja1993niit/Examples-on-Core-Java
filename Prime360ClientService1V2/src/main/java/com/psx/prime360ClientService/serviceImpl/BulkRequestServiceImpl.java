package com.psx.prime360ClientService.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.psx.prime360ClientService.dto.BulkFileDetailsDTO;
import com.psx.prime360ClientService.entity.BulkUploadColumnMapping;
import com.psx.prime360ClientService.entity.BulkUploadDefn;
import com.psx.prime360ClientService.entity.InstallablesEntity;
import com.psx.prime360ClientService.repository.BulkUploadRepository;
import com.psx.prime360ClientService.repository.InstallableRepository;
import com.psx.prime360ClientService.serviceI.BulkRequestServiceI;
import com.psx.prime360ClientService.utils.BulkQueryUtils;

/**
 * @author Rahul
 *
 */
@Service
public class BulkRequestServiceImpl implements BulkRequestServiceI {

	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	BulkUploadRepository bulkUploadRepository;

	@Autowired
	Environment environment;
	
	@Autowired
	DataSource dataSource;

	@Autowired
	InstallableRepository installableRepository;

	@Override
	public String processBulkRequest(String tableName, String csvFileName, String serverFileName,
			List<BulkUploadColumnMapping> bulkMappingList, int threshold, String psxBatchID, long submittedDate)
			throws Exception {
		BulkUploadDefn bulkDefn = new BulkUploadDefn();
		List<BulkUploadDefn> list = new ArrayList<BulkUploadDefn>();
		Map<String, String> headerDedupeMap = bulkMappingList.stream().filter(x -> x.getDisplayColName() != null)
				.collect(Collectors.toMap(BulkUploadColumnMapping::getHeaderColName,
						BulkUploadColumnMapping::getDisplayColName, (oldkey, newKey) -> oldkey));
		System.out.println("BulkRequestServiceImpl headerDedupeMap : " + headerDedupeMap);
		// converting header column and dedupe column map to json
		Gson gson = new Gson();
		String mappingInfo = gson.toJson(headerDedupeMap);
		System.out.println("BulkRequestServiceImpl mappingInfo : " + mappingInfo);
		bulkDefn.setMappingInfo(mappingInfo);
		bulkDefn.setFileName(csvFileName);
		bulkDefn.setSubmitDate(new Date(submittedDate));
		bulkDefn.setPsxbatchId(psxBatchID);
		bulkDefn.setServerFileName(serverFileName);
		bulkDefn.setStatus("P");
		list.add(bulkDefn);
		String s = processRequest(bulkDefn, tableName);
		return s;
	}

	@Override
	public List<BulkFileDetailsDTO> getBulkFileDetails() {
		List<BulkFileDetailsDTO> bulkFileDTOList = new ArrayList<BulkFileDetailsDTO>();
		return bulkFileDTOList;
	}

	public HashMap<String, Object> convertToDedupeMap(String csvFileName, HashMap<String, String> headerDedupeMap,
			HashMap<String, String> recordMap, String psxBatchID, String insertTimeStamp) {
		HashMap<String, Object> finalDedMap = new HashMap<String, Object>();
		Iterator<String> dedItr = headerDedupeMap.keySet().iterator();
		while (dedItr.hasNext()) {
			String key = dedItr.next();
			finalDedMap.put(csvFileName + "." + headerDedupeMap.get(key), recordMap.get(key));
		}
		finalDedMap.put(csvFileName + "." + "PSX_BATCH_ID", psxBatchID);
		finalDedMap.put(csvFileName + "." + "INSERT_TIME", insertTimeStamp);

		System.out.println("BulkRequestServiceImpl finalDedMap : " + finalDedMap);

		return finalDedMap;
	}

	@Override
	public String getUploadedDetails(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		return BulkQueryUtils.uploadFile(file);
	}

	@Override
	public String reprocessData(String psxBatchId) {
		// TODO Auto-generated method stub
		BulkUploadDefn blkDefn = bulkUploadRepository.findBypsxbatchId(psxBatchId);
		BulkUploadDefn bulkUploadDefn = new BulkUploadDefn();
		BeanUtils.copyProperties(blkDefn, bulkUploadDefn);
		bulkUploadDefn.setPsxbatchId(BulkQueryUtils.getDateForBatchId(System.currentTimeMillis()));
		String s = null;
		try {
			// s = processRequest(bulkUploadDefn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public String processRequest(BulkUploadDefn defn, String tableName) throws Exception {
		bulkUploadRepository.save(defn);//PSX_NSP_BULKUPLOAD_INITIAL
		HashMap<String, String> appProps = new HashMap<String, String>();
		HashMap<String, String> appProps1 = getAllDataUpload(tableName);
		Gson gson = new Gson();
		//display column name and mapping logic
		HashMap<String, String> headerDedupeMap = gson.fromJson(defn.getMappingInfo(), HashMap.class);
		CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(',').withQuote('"').withRecordSeparator("\r\n");
		BufferedReader reader = Files.newBufferedReader(Paths.get("upload-dir/" + defn.getServerFileName()));
		CSVParser csvFileParser = new CSVParser(reader, format);
		int count = 0;
		int psxId = 0;
		int responseCount = 0;
		Iterator<CSVRecord> itr = csvFileParser.iterator();
		Long bpStarttime = System.currentTimeMillis();
		while (true) {
			try {
				boolean next = itr.hasNext();
				if (next) {
					CSVRecord record = itr.next();
					HashMap<String, String> data = (HashMap<String, String>) record.toMap();
					HashMap<String, Object> convertedData = convertToDedupeMap(tableName, headerDedupeMap, data,
							defn.getPsxbatchId(), BulkQueryUtils.getFormattedDate(defn.getSubmitDate().getTime()));
					// convertedData.put("PSX_ID", psxId + "");
					// convertedData.put("INSERT_TIME",
					// BulkQueryUtils.getFormattedDate(System.currentTimeMillis()));
					// convertedData.put("PSX_BATCH_ID", defn.getPsxbatchId() + "");
					// Setting request information
					HashMap<String, ArrayList<HashMap<String, Object>>> requestInfo = BulkQueryUtils
							.buildReqInfo("CDAP", convertedData);
					// Setting param map

					HashMap<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("paramMapQueueName", "myqueue");
					paramMap.put("psxBatchID", defn.getPsxbatchId() + "");
					// consider from property file
					// This dml is to add data in psx_nsp_request table
					System.out.println("BulkRequestServiceImpl Bind Values Map : " + convertedData);
					try (Connection con = dataSource.getConnection();) {
						BulkQueryUtils.messageDmlsForRecord(true, con, "dml.stg." + tableName + ".", appProps1,
								convertedData, null);
					}

				} else {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				psxId++;
				count++;
			}
		}
		try (Connection con = dataSource.getConnection();) {
			BulkQueryUtils.messageDmls(true, con, "postBatchDmlAfterAllRequests.", appProps, new HashMap(), null);
		}
		Long bpEndTime = (System.currentTimeMillis()) - bpStarttime;
		defn.setProcessedTime(bpEndTime);
		defn.setProcessedDate(Calendar.getInstance().getTime());

		defn.setStatus("C");
		bulkUploadRepository.save(defn);
		try (Connection con = dataSource.getConnection();) {
			BulkQueryUtils.messageDmls(true, con, "postBatchDmlAfterAllRespnoses.", appProps, new HashMap(), null);
		}
		return defn.getPsxbatchId();
	}

	public HashMap<String, String> getAllDataUpload(String tableName) {
		List<Date> s = installableRepository.getAllTableBasedOnLchgtime();
		HashMap<String, String> hmap = new HashMap<>();
		List<InstallablesEntity> entity = installableRepository.getUploadDetails(new Date(s.get(0).getTime()));
		InstallablesEntity entity1 = new InstallablesEntity();
		entity.stream().filter(x -> {
			return x.getPropKey().indexOf(tableName) >= 0;
		}).forEach(o -> {
			hmap.put(o.getPropKey(), o.getPropValue());
		});
		System.out.println("APP_CONFIG" + entity);
		System.out.println();
		return hmap;
	}

	public List<Date> getTableBasedLchgtime() {
		List<Date> s = installableRepository.getAllTableBasedOnLchgtime();
		return s;
	}
}