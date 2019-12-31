package com.psx.prime360ClientService.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import com.psx.prime360ClientService.entity.BaseMetaInfo;
import com.psx.prime360ClientService.entity.BaseMetaInfoIdentity;
import com.psx.prime360ClientService.entity.BulkPreviewWrapper;
import com.psx.prime360ClientService.entity.BulkUploadColumnMapping;
import com.psx.prime360ClientService.entity.HeaderColumnWrapper;
import com.psx.prime360ClientService.entity.InputMetaInfo;
import com.psx.prime360ClientService.entity.InputTableMetaInfo;
import com.psx.prime360ClientService.repository.PosRepository;
import com.psx.prime360ClientService.utils.BulkQueryUtils;
import com.psx.prime360ClientService.utils.Prime360ProductServiceUtils;

/**
 * @author Rahul
 *
 */
@Service("storageService")
public class StorageService {
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private Environment env;

	@Resource
	private PosRepository repository;

	private final Path rootLocation = Paths.get("upload-dir");

	// for uploading file and download to server and read the csv from server
	public BulkPreviewWrapper store(MultipartFile file) throws IOException {
		// for writing(upload) csv file to server
		System.out.println("StorageService rootLocation.toAbsolutePath() : " + rootLocation.toAbsolutePath());
		BulkPreviewWrapper retObj = new BulkPreviewWrapper();
		String fileName = file.getOriginalFilename();
		System.out.println("StorageService fileName : " + fileName);
		String arr[] = fileName.split("\\.");
		System.out.println(arr[0]);
		System.out.println(arr[1]);
		String serverFileName = arr[0] + "_" + BulkQueryUtils.getDateForBatchId(System.currentTimeMillis());
		serverFileName = serverFileName + "." + arr[1];
		System.out.println("StorageService serverFileName : " + serverFileName);
		Files.copy(file.getInputStream(), this.rootLocation.resolve(serverFileName));
		BufferedReader inputStream = Files.newBufferedReader(Paths.get("upload-dir/" + serverFileName));
		List<List<String>> fileData = new ArrayList<>();
		String line = null;
		int i = 0;
		while ((line = inputStream.readLine()) != null) {
			if (i < 50) {
				List<String> rows = Arrays.asList(line.split(","));
				fileData.add(rows);
			}
			i++;
		}
		retObj.setPreviewList(fileData);
		retObj.setFileName(fileName);
		retObj.setServerFile(serverFileName);
		System.out.println("StorageService FileUploadedData : " + fileData);
		inputStream.close();
		return retObj;
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}

	@SuppressWarnings("null")
	public HeaderColumnWrapper getData(String serverFileName) throws IOException {
		// getting header column name
		BufferedReader reader = Files.newBufferedReader(Paths.get("upload-dir/" + serverFileName));
		String[] headerColumns = reader.readLine().split(",");
		List<BulkUploadColumnMapping> bulkUploadColumnMappings = new ArrayList<BulkUploadColumnMapping>();
		HeaderColumnWrapper wrapper = new HeaderColumnWrapper();

		for (int i = 0; i < headerColumns.length; i++) {
			BulkUploadColumnMapping tempMapObj = new BulkUploadColumnMapping();
			tempMapObj.setHeaderColName(headerColumns[i]);
			tempMapObj.setCsvHeaderData(headerColumns[i]);
			bulkUploadColumnMappings.add(tempMapObj);
			wrapper.setBulkColmappingList(bulkUploadColumnMappings);
		}
		return wrapper;
	}

	public InputMetaInfo buildData(String input) {
		List<InputMetaInfo> mandatoryMissedList = new ArrayList<InputMetaInfo>();
		InputMetaInfo info = populateScreenData(input);
		mandatoryMissedList.add(info);
		System.out.println("StorageService mandatoryMissedList : " + mandatoryMissedList);
		return info;
	}

	public InputMetaInfo populateScreenData(String csvFormat) {
		String dataTypes = env.getProperty("dataTypes");
		Object[] inputDataObj = BulkQueryUtils.buildData(csvFormat);
		InputTableMetaInfo[] tabColMetadataMap = (InputTableMetaInfo[]) inputDataObj[0];
		InputMetaInfo ipMetaInfo = new InputMetaInfo();
		ipMetaInfo.setInputTableMetaInfos(tabColMetadataMap);
		return ipMetaInfo;
	}

	public BaseMetaInfo getLastestRecord() {
		String srcSysName = "PRIME360";
		List<BaseMetaInfo> baseMetaInfos = repository.getAllBySrcSystemName(srcSysName).parallelStream()
				.sorted(Comparator.comparing(BaseMetaInfo::getLchgTime).reversed()).collect(Collectors.toList());

		BaseMetaInfo baseMetaInfo = baseMetaInfos.isEmpty() ? addFirstRecord() : baseMetaInfos.get(0);

		System.out.println("StorageService getLastestRecord : " + baseMetaInfo);
		return baseMetaInfo;
	}

	private BaseMetaInfo addFirstRecord() {
		BaseMetaInfo metaInfo = new BaseMetaInfo();
		BaseMetaInfoIdentity infoIdentity = new BaseMetaInfoIdentity();
		String input2 = "SL,Table_Name,Table_Alias_Name,COLUMN_NAME,DATA_TYPE,Nullable,isPrimarykey,isDedupe,DedupeType,CleanProfile,DisplayName,AssociationParameters,FlagParameter;18,PSX_NSP_ACE_BLK_TRG,ACE,PSX_ID,PSXVARCHAR(200),TRUE,TRUE,TRUE,,,PSX_ID,,;20,PSX_NSP_ACE_BLK_TRG,ACE,OFFICE_ADDRESS,PSXVARCHAR(200),,,TRUE,ADDRESSTYPE,POS_ADDRESS_CLEAN,OFFICE_ADDRESS,21~22~40,;21,PSX_NSP_ACE_BLK_TRG,ACE,OFFICE_CITY,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-DC,POS_CITY_CLEAN,OFFICE_CITY,,;22,PSX_NSP_ACE_BLK_TRG,ACE,OFFICE_PIN,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-PIN,POS_PINCODE_CLEAN,OFFICE_PIN,,;23,PSX_NSP_ACE_BLK_TRG,ACE,PERMANENT_ADDRESS,PSXVARCHAR(200),,,TRUE,ADDRESSTYPE,POS_ADDRESS_CLEAN,PERMANENT_ADDRESS,24~25~42,;24,PSX_NSP_ACE_BLK_TRG,ACE,PERMANENT_CITY,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-DC,POS_CITY_CLEAN,PERMANENT_CITY,,;25,PSX_NSP_ACE_BLK_TRG,ACE,PERMANENT_PIN,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-PIN,POS_PINCODE_CLEAN,PERMANENT_PIN,,;26,PSX_NSP_ACE_BLK_TRG,ACE,RESIDENCE_ADDRESS,PSXVARCHAR(200),,,TRUE,ADDRESSTYPE,POS_ADDRESS_CLEAN,RESIDENCE_ADDRESS,27~28~43,;27,PSX_NSP_ACE_BLK_TRG,ACE,RESIDENCE_CITY,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-DC,POS_CITY_CLEAN,RESIDENCE_CITY,,;28,PSX_NSP_ACE_BLK_TRG,ACE,RESIDENCE_PIN,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-PIN,POS_PINCODE_CLEAN,RESIDENCE_PIN,,;29,PSX_NSP_ACE_BLK_TRG,ACE,TEMPORARY_ADDRESS,PSXVARCHAR(200),,,TRUE,ADDRESSTYPE,POS_ADDRESS_CLEAN,TEMPORARY_ADDRESS,30~31~44,;30,PSX_NSP_ACE_BLK_TRG,ACE,TEMPORARY_CITY,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-DC,POS_CITY_CLEAN,TEMPORARY_CITY,,;31,PSX_NSP_ACE_BLK_TRG,ACE,TEMPORARY_PIN,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-PIN,POS_PINCODE_CLEAN,TEMPORARY_PIN,,;32,PSX_NSP_ACE_BLK_TRG,ACE,OFFICE_EMAIL,PSXVARCHAR(200),,,TRUE,EMAILTYPE,POS_EMAIL_TYPE_CLEAN,OFFICE_EMAIL,,;33,PSX_NSP_ACE_BLK_TRG,ACE,PERMANENT_EMAIL,PSXVARCHAR(200),,,TRUE,EMAILTYPE,POS_EMAIL_TYPE_CLEAN,PERMANENT_EMAIL,,;34,PSX_NSP_ACE_BLK_TRG,ACE,RESIDENCE_EMAIL,PSXVARCHAR(200),,,TRUE,EMAILTYPE,POS_EMAIL_TYPE_CLEAN,RESIDENCE_EMAIL,,;35,PSX_NSP_ACE_BLK_TRG,ACE,TEMPORARY_EMAIL,PSXVARCHAR(200),,,TRUE,EMAILTYPE,POS_EMAIL_TYPE_CLEAN,TEMPORARY_EMAIL,,;36,PSX_NSP_ACE_BLK_TRG,ACE,OFFICE_PHONE,PSXVARCHAR(200),,,TRUE,PHONETYPE,POS_PHONE_CLEAN,OFFICE_PHONE,,;37,PSX_NSP_ACE_BLK_TRG,ACE,PERMANENT_PHONE,PSXVARCHAR(200),,,TRUE,PHONETYPE,POS_PINCODE_CLEAN,PERMANENT_PHONE,,;38,PSX_NSP_ACE_BLK_TRG,ACE,RESIDENCE_PHONE,PSXVARCHAR(200),,,TRUE,PHONETYPE,POS_PHONE_CLEAN,RESIDENCE_PHONE,,;39,PSX_NSP_ACE_BLK_TRG,ACE,TEMPORARY_PHONE,PSXVARCHAR(200),,,TRUE,PHONETYPE,POS_PHONE_CLEAN,TEMPORARY_PHONE,,;40,PSX_NSP_ACE_BLK_TRG,ACE,OFFICE_STREET_NUMBER,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-ST,POS_EXTRA_EQ_COLUMN1_CLEAN,OFFICE_STREET_NUMBER,,;41,PSX_NSP_ACE_BLK_TRG,ACE,COUNTRY,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-CNTRY,POS_COUNTRY_CLEAN,COUNTRY,,;42,PSX_NSP_ACE_BLK_TRG,ACE,PERMANENT_STREET_NUMBER,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-ST,POS_EXTRA_EQ_COLUMN2_CLEAN,PERMANENT_STREET_NUMBER,,;43,PSX_NSP_ACE_BLK_TRG,ACE,RESIDENCE_STREET_NUMBER,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-ST,POS_EXTRA_EQ_COLUMN3_CLEAN,RESIDENCE_STREET_NUMBER,,;44,PSX_NSP_ACE_BLK_TRG,ACE,TEMP_STREET_NUMBER,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE-ST,POS_EXTRA_EQ_COLUMN4_CLEAN,TEMP_STREET_NUMBER,,;45,PSX_NSP_RPT_BLK_TRG,REPORT,PSX_ID,PSXVARCHAR(200),TRUE,TRUE,TRUE,,,PSX_ID,,;48,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_DATE1,PSXDATE,,,,TRUE,,RPT_DATE1,,;49,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_DATE2,PSXDATE,,,,TRUE,,RPT_DATE2,,;50,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_DATE3,PSXDATE,,,,TRUE,,RPT_DATE3,,;51,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_DATE4,PSXDATE,,,,TRUE,,RPT_DATE4,,;52,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_NUMBER1,PSXNUMBER(10),,,,TRUE,,RPT_NUMBER1,,;53,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_NUMBER2,PSXNUMBER(10),,,,TRUE,,RPT_NUMBER2,,;54,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_NUMBER3,PSXNUMBER(10),,,,TRUE,,RPT_NUMBER3,,;55,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_NUMBER4,PSXNUMBER(10),,,,TRUE,,RPT_NUMBER4,,;56,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_VARCHAR1,PSXVARCHAR(200),,,,TRUE,,RPT_VARCHAR1,,;57,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_VARCHAR2,PSXVARCHAR(200),,,,TRUE,,RPT_VARCHAR2,,;58,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_VARCHAR3,PSXVARCHAR(200),,,,TRUE,,RPT_VARCHAR3,,;59,PSX_NSP_RPT_BLK_TRG,REPORT,RPT_VARCHAR4,PSXVARCHAR(200),,,,TRUE,,RPT_VARCHAR4,,;1,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,PSX_ID,PSXVARCHAR(200),TRUE,TRUE,TRUE,,,PSX_ID,,;6,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,DOB1,PSXDATE,,,TRUE,DOBTYPE,POS_DOB_CLEAN,DOB1,,;7,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,DOB2,PSXDATE,,,TRUE,DOBTYPE,POS_DOB_CLEAN,DOB2,,;8,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,DOB3,PSXDATE,,,TRUE,DOBTYPE,POS_DOB_CLEAN,DOB3,,;9,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,DOB4,PSXDATE,,,TRUE,DOBTYPE,POS_DOB_CLEAN,DOB4,,;10,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,EQUALITY1,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE,POS_EXTRA_EQ_COLUMN1_CLEAN,EQUALITY1,,;11,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,EQUALITY2,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE,POS_EXTRA_EQ_COLUMN2_CLEAN,EQUALITY2,,;12,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,EQUALITY3,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE,POS_EXTRA_EQ_COLUMN3_CLEAN,EQUALITY3,,;13,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,EQUALITY4,PSXVARCHAR(200),,,TRUE,EQUALITYTYPE,POS_EXTRA_EQ_COLUMN4_CLEAN,EQUALITY4,,;14,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,FLAG1,PSXVARCHAR(200),,,TRUE,FILTERTYPE,,FLAG1,,FLAG1$FLAG1;15,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,FLAG2,PSXVARCHAR(200),,,TRUE,FILTERTYPE,,FLAG2,,FLAG2$FLAG2;16,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,FLAG3,PSXVARCHAR(200),,,TRUE,FILTERTYPE,,FLAG3,,FLAG3$FLAG3;17,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,FLAG4,PSXVARCHAR(200),,,TRUE,FILTERTYPE,,FLAG4,,FLAG4$FLAG4;61,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,MOTHER_NAME,PSXVARCHAR(200),null,null,true,NAMETYPE,POS_MOTHER_NAME_CLEAN,MOTHER NAME,,;62,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,FATHER_NAME,PSXVARCHAR(200),null,null,true,NAMETYPE,POS_FATHER_NAME_CLEAN,FATHER NAME,,;63,PSX_NSP_DG_BLK_TRG,DEMOGRAPHICS,NAME,PSXVARCHAR(200),null,null,true,NAMETYPE,POS_NAME_CLEAN,NAME,,";
		metaInfo.setCsvString(input2);
		// metaInfo.setIsEnabled("N");
		metaInfo.setSrcSystemName("PRIME360");
		infoIdentity.setId((long) 1);
		infoIdentity.setLchgtime(Prime360ProductServiceUtils.getFormattedDate(System.currentTimeMillis()));
		metaInfo.setMetaInfoIdentity(infoIdentity);
		List<BaseMetaInfo> list = new ArrayList<>();
		// metaInfo = posRepository.save(metaInfo);
		return metaInfo;
	}
}