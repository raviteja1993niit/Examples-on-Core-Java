package com.psx.prime360ClientService.serviceImpl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.psx.prime360ClientService.entity.Customer;
import com.psx.prime360ClientService.entity.EodFileStats;
import com.psx.prime360ClientService.entity.EodProcessingStages;
import com.psx.prime360ClientService.entity.Error_Records_Info_T;
import com.psx.prime360ClientService.entity.ExceptionRecordsInfo;
import com.psx.prime360ClientService.exception.PosidexException;
import com.psx.prime360ClientService.serviceI.EodFileUploadService;
import com.psx.prime360ClientService.utils.ResponseJson;

@Service
@PropertySource({ "classpath:path.properties" })
public class EodFileUploadServiceImpl implements EodFileUploadService {

	// private final Log logger = LogFactory.getLog(this.getClass());
	private static Logger logger = Logger
			.getLogger(EodFileUploadServiceImpl.class.getName());
	@Autowired
	private DataSource dataSource;

	@Autowired
	Environment environment;

	String eodBatchId = "";
	long psx_batch_id1 = 0;
	int count1 = 0;
	int size = 0;
	Customer customer1 = null;
	List<Customer> listOfCustomers = new ArrayList<>();
	String errorMessage = "";
	List<ExceptionRecordsInfo> exceptionRecordsInfoList = new ArrayList<>();

	@Override
	public ResponseJson<HttpStatus, String> eodFileUpload(MultipartFile file,
			String dataSource1) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date date1 = new Date();
		String eodBatchId1 = dateFormat.format(date1);
		eodBatchId = eodBatchId1;
		logger.info("entered into getOther Sources services :: "
				+ file.getOriginalFilename() + " :: " + dataSource1);

		ResponseJson<HttpStatus, String> responseJson = new ResponseJson<>();
		String destinationPath = environment
				.getProperty("eodfileuploaddestinationPath");
		Path path = Paths.get(destinationPath + file.getOriginalFilename());

		String ext1 = FilenameUtils.getExtension(destinationPath.concat(file
				.getOriginalFilename()));
		if (file != null
				&& (ext1.equalsIgnoreCase("CSV") || (ext1
						.equalsIgnoreCase("TXT")))) {

			byte[] bytes;

			try {
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				logger.debug("successfully uploaded the file");
				bytes = file.getBytes();
				ByteArrayInputStream inputFilestream = new ByteArrayInputStream(
						bytes);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						inputFilestream));
				String line = "";
				int count = 0;
				int skip = 0;
				while ((line = br.readLine()) != null) {
					int s = line.length()
							- line.replaceAll("\\|", "").length();
					int headerCount = Integer.parseInt(environment
							.getProperty("HeaderFieldsCount"));
					if (!line.startsWith("CUSTOMER_NO") && !line.equals("")) {
						if (!(line.isEmpty())) {

							 if(s == 0){
								 errorMessage = " Delimiter Must Be Pipe Operator ";
									ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
									exceptionRecordsInfo.setEodBatchId(eodBatchId);
									exceptionRecordsInfo.setCustomerObj(line);
									exceptionRecordsInfo.setErrorInfo(errorMessage);
									exceptionRecordsInfoList
											.add(exceptionRecordsInfo);
									continue;
							 }

							if (s < headerCount - 1) {
								count = count + 1;

								errorMessage = " No of Input Fields Values are Lesser Than No of Header Column Fields ";
								ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
								exceptionRecordsInfo.setEodBatchId(eodBatchId);
								exceptionRecordsInfo.setCustomerObj(line);
								exceptionRecordsInfo.setErrorInfo(errorMessage);
								exceptionRecordsInfoList
										.add(exceptionRecordsInfo);

							}
							if (s > headerCount - 1) {
								count = count + 1;
								errorMessage = "";
								errorMessage += " No of Input Fields Values are Greater Than No of Header Column Fields ";
								ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
								exceptionRecordsInfo.setEodBatchId(eodBatchId);
								exceptionRecordsInfo.setCustomerObj(line);
								exceptionRecordsInfo.setErrorInfo(errorMessage);
								exceptionRecordsInfoList
										.add(exceptionRecordsInfo);
							}
							if (s == headerCount - 1) {
								count = count + parseLine(line);
							}

						}
					}
				}
				br.close();
				logger.info("Destination File Path::: " + destinationPath);
				File file1 = new File(path.toString());
				file.transferTo(file1);
				System.out.println("Total Count::" + count);
				count1 = count;
				// count=listOfCustomers.size();
				// insertBatch(listOfCustomers);
				listOfCustomers = new ArrayList<>();
				for (ExceptionRecordsInfo exceptionRecordsInfo : exceptionRecordsInfoList) {
					errorCode(exceptionRecordsInfo.getEodBatchId(),
							exceptionRecordsInfo.getCustomerObj(),
							exceptionRecordsInfo.getErrorInfo());
				}
				exceptionRecordsInfoList = new ArrayList<>();
				// count=0;

				fileDatareport(file.getOriginalFilename(), count, dataSource1);
				responseJson.setData(HttpStatus.OK, eodBatchId);
//				if (skip == 1) {
//					responseJson.setData(HttpStatus.INTERNAL_SERVER_ERROR,
//							eodBatchId);
//					throw new PosidexException("File Not Supportted");
//				} else {
//					responseJson.setData(HttpStatus.OK, eodBatchId);
//				}
			} catch (Exception e1) {
				e1.printStackTrace();
				// errorCode(eodBatchId, customer1, errorMessage);
				responseJson.setData(HttpStatus.INTERNAL_SERVER_ERROR,
						eodBatchId);
			}

		} else {
			responseJson.setData(HttpStatus.INTERNAL_SERVER_ERROR, eodBatchId
					+ "::: Error: FILE FORMAT NOT SUPPORTED");
		}

		return responseJson;
	}

	public void fileDatareport(String fileName, int totalCount,
			String datasource) {
		try {
			int errorCount = 0;
			int importCount = 0;
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "select count(*) from PSX_COMMON_STAGING_T WHERE PSX_BATCH_ID=?";
			importCount = (Integer) jdbcTemplate.queryForObject(sql,
					new Object[] { eodBatchId }, Integer.class);
			logger.info("Import Count::" + importCount);
			String sql2 = "insert into EOD_FILE_STATS(BATCHID,PROCESS_TYPE,FILE_NAME,TOTAL_COUNT,IMPORT_COUNT,ERROR_COUNT,SOURCE_SYSTEM,INSERT_TIME)values(?,?,?,?,?,?,?,SYSTIMESTAMP)";
			jdbcTemplate.update(sql2, new Object[] { eodBatchId, "FILE_UPLOAD",
					fileName, totalCount, importCount, 0, datasource });

			String sql1 = "select count(*) from ERROR_RECORDS_INFO_T WHERE PSX_BATCH_ID=?";
			errorCount = (Integer) jdbcTemplate.queryForObject(sql1,
					new Object[] { eodBatchId }, Integer.class);

			String sql3 = "update EOD_FILE_STATS set ERROR_COUNT=? where BATCHID=?";

			logger.info("Import Error Count::" + errorCount);
			jdbcTemplate.update(sql3, new Object[] { errorCount, eodBatchId });
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
	}

	public int parseLine(String value) {
		Customer customer = null;
		// public void parseLine(String value) {
		try {
			value = value + "|";
			Scanner sc = new Scanner(value);

			sc.useDelimiter("[|]");

			while (sc.hasNext()) {

				customer = new Customer();

				// if(sc.next()!=null && sc.next() != ""){
				customer.setCustomerNo(sc.next());
				// customer1.setCustomerNo(customer.getCustomerNo());
				customer.setSourceSystemId(sc.next());
				customer.setDataSourceName(sc.next());

				String firstName = sc.next();
				String middleName = sc.next();
				String lastName = sc.next();

				customer.setFirstname(firstName);

				customer.setMiddlename(middleName);

				customer.setLastname(lastName);

				customer.setName(customer.getFirstname() + " "
						+ customer.getMiddlename() + " "
						+ customer.getLastname());
				customer.setGender(sc.next());

				customer.setDob(sc.next());
				String[] dob1Split = null;
				String day = "";
				String month = "";
				String year = "";
				if (customer.getDob() != null && (!customer.getDob().isEmpty())
						&& customer.getDob() != "null") {
					if (customer.getDob().contains("-")) {
						dob1Split = customer.getDob().split("-");
						day = dob1Split[0];
						month = dob1Split[1];
						year = dob1Split[2];
					}
					if (customer.getDob().contains("/")) {
						dob1Split = customer.getDob().split("/");
						day = dob1Split[0];
						month = dob1Split[1];
						year = dob1Split[2];
					}
				}

				if (!year.isEmpty()) {
					if (year.length() != 4) {
						throw new PosidexException(
								"Year should be in YYYY format");
					}
				}
				customer.setPan(sc.next());

				customer.setDrivingLicense(sc.next());

				customer.setAadharNo(sc.next());

				customer.setVoterId(sc.next());

				customer.setDateOfIncorporation(sc.next());

				customer.setTanNo(sc.next());

				customer.setProcessType(sc.next());

				customer.setApplicationType(sc.next());

				customer.setEmployerName(sc.next());

				customer.setFatherName(sc.next());

				customer.setPassportNo(sc.next());

				customer.setIfscCode(sc.next());

				customer.setAccountNumber(sc.next());

				customer.setCreditCardNumber(sc.next());

				customer.setCompanyIdentificationNo(sc.next());

				customer.setDirectorIdentitficationNo(sc.next());

				customer.setRegistrationNumber(sc.next());

				customer.setConstitution(sc.next());

				customer.setCaNumber(sc.next());

				customer.setAgreementNumber(sc.next());

				customer.setRemarks(sc.next());

//				String address1 = sc.next();
//
//				String address2 = sc.next();
//				String address3 = sc.next();
				
				customer.setAddress1(sc.next());
				customer.setAddress2(sc.next());
				customer.setAddress3(sc.next());
				if(customer.getAddress1()!=null && customer.getAddress1()!=" " && !customer.getAddress1().isEmpty()
						&&	customer.getAddress2()!=null && customer.getAddress2()!=" " && !customer.getAddress2().isEmpty()	
								&&	customer.getAddress3()!=null && customer.getAddress3()!=" " && !customer.getAddress3().isEmpty()
						){
				customer.setPermanentAddress(customer.getAddress1() + " " + customer.getAddress2() + "  "
						+ customer.getAddress3());
				}
				customer.setCity(sc.next());

				customer.setPin(sc.next());

				customer.setState(sc.next());

				customer.setAreaLocalityRes(sc.next());

				customer.setLandmarkRes(sc.next());

//				String office1 = sc.next();
//				String office2 = sc.next();
//				String office3 = sc.next();

				customer.setOffice1(sc.next());
				customer.setOffice2(sc.next());
				customer.setOffice3(sc.next());

				if(customer.getOffice1()!=null && customer.getOffice1()!=" " && !customer.getOffice1().isEmpty()
						&&	customer.getOffice2()!=null && customer.getOffice2()!=" " && !customer.getOffice2().isEmpty()	
								&&	customer.getOffice3()!=null && customer.getOffice3()!=" " && !customer.getOffice3().isEmpty()
						){
				customer.setOfficeAddress(customer.getOffice1() + "  " + customer.getOffice2() + " "
						+ customer.getOffice3());
				}

				customer.setOfficeCity(sc.next());

				customer.setOfficePin(sc.next());

				customer.setOffState(sc.next());

				customer.setOfficeAreaLocality(sc.next());

				customer.setLandlineRes1(sc.next());

				customer.setLandLineRes2(sc.next());

				customer.setMobile(sc.next());

				customer.setOfficeLandmark(sc.next());

				customer.setStd(sc.next());

				customer.setEmail(sc.next());

				if (sc.hasNext()) {
					customer.setSegment(sc.next());

				}
				// listOfCustomers.add(customer);
				break;
			}
			// size = listOfCustomers.size();
			// call function to insert the data in table
			// insertBatch(listOfCustomers);
			size = 1;
			insertRecord(customer);

			listOfCustomers = new ArrayList<>();
			sc.close();
		} catch (Exception e) {
			String error = e.getMessage();
			logger.info("Record Value ::: " + value);
			errorCode(eodBatchId, value, error);
			e.printStackTrace();
			logger.info("Try Catch :::: " + e.getMessage());

			logger.error(e, e);

		}
		return size;
	}

	/*
	 * public void insertBatch(List<Customer> list) throws ParseException {
	 * 
	 * // final int batchSize = 100;
	 * 
	 * final int batchSize = Integer.parseInt(environment
	 * .getProperty("batchSize"));
	 * 
	 * String SqlQuery = SQLQueries.BATCH_QUERY_OF_EOD[0]; DateFormat sdf = new
	 * SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US); java.sql.Timestamp
	 * date = new java.sql.Timestamp( new java.util.Date().getTime()); String
	 * dateformat = (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")) .format(date);
	 * 
	 * try {
	 * 
	 * JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	 * jdbcTemplate.batchUpdate(SqlQuery, new BatchPreparedStatementSetter() {
	 * 
	 * @Override public void setValues(PreparedStatement ps, int i) throws
	 * SQLException {
	 * 
	 * long psx_batch_id = 0; Customer customer = list.get(i); try {
	 * psx_batch_id = appNextValProc(); setValues(psx_batch_id, customer); //
	 * if(customer.getCustomerNo() !=null && customer.getSourceSystemId() !=null
	 * ){ if( (! Strings.isNullOrEmpty(customer.getCustomerNo())) &&(!
	 * Strings.isNullOrEmpty(customer.getSourceSystemId()))){ ps.setString(1,
	 * customer.getCustomerNo() + "||"+ customer.getSourceSystemId()); } else if
	 * (customer.getCustomerNo() !=null){ ps.setString(1,
	 * customer.getCustomerNo()); }else{ ps.setString(1,
	 * customer.getSourceSystemId()); } ps.setString(2,
	 * customer.getSourceSystemId()); ps.setString(3,
	 * customer.getDataSourceName()); ps.setString(4, customer.getName());
	 * ps.setString(5, customer.getGender());
	 * if(customer.getDob().equalsIgnoreCase("null") ||
	 * customer.getDob().equalsIgnoreCase(null) ) { ps.setString(6, ""); }else{
	 * ps.setString(6, customer.getDob()); } ps.setString(7, customer.getPan());
	 * ps.setString(8, customer.getDrivingLicense()); ps.setString(9,
	 * customer.getAadharNo()); ps.setString(10, customer.getVoterId());
	 * if(customer.getDob().equalsIgnoreCase("null") ||
	 * customer.getDob().equalsIgnoreCase(null)) { ps.setString(11, ""); }else{
	 * ps.setString(11,customer.getDateOfIncorporation()); } ps.setString(12,
	 * customer.getTanNo()); ps.setString(13, customer.getProcessType());
	 * ps.setString(14, customer.getApplicationType()); ps.setString(15,
	 * customer.getEmployerName()); ps.setString(16, customer.getFatherName());
	 * ps.setString(17, customer.getPassportNo()); ps.setString(18,
	 * customer.getIfscCode()); ps.setString(19, customer.getAccountNumber());
	 * ps.setString(20, customer.getCreditCardNumber());
	 * 
	 * ps.setString(21, customer.getCompanyIdentificationNo()); ps.setString(22,
	 * customer.getDirectorIdentitficationNo()); ps.setString(23,
	 * customer.getRegistrationNumber()); ps.setString(24,
	 * customer.getConstitution()); ps.setString(25, customer.getCaNumber());
	 * ps.setString(26, customer.getAgreementNumber()); ps.setString(27,
	 * customer.getRemarks()); ps.setString(28, customer.getPermanentAddress());
	 * ps.setString(29, customer.getCity()); ps.setString(30,
	 * customer.getPin()); ps.setString(31, customer.getState());
	 * ps.setString(32, customer.getAreaLocalityRes()); ps.setString(33,
	 * customer.getLandmarkRes()); ps.setString(34,
	 * customer.getOfficeAddress()); ps.setString(35, customer.getOfficeCity());
	 * ps.setString(36, customer.getOfficePin()); ps.setString(37,
	 * customer.getOffState()); ps.setString(38,
	 * customer.getOfficeAreaLocality()); ps.setString(39,
	 * customer.getLandlineRes1()); ps.setString(40,
	 * customer.getLandLineRes2()); ps.setString(41, customer.getMobile()); //
	 * ps.setString(42, // customer.getOfficeLandmark()); ps.setString(42,
	 * customer.getStd()); ps.setString(43, customer.getEmail());
	 * ps.setString(45, eodBatchId); ps.setLong(44, psx_batch_id);
	 * ps.setString(46, "I_OR_U");
	 * 
	 * ps.setObject( 47, new Timestamp(new SimpleDateFormat(
	 * "dd-MM-yyyy HH:mm:ss").parse( dateformat).getTime())); ps.setObject( 48,
	 * new Timestamp(new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss").parse(
	 * dateformat).getTime()));
	 * 
	 * ps.setString(49, customer.getCustomerNo()); ps.setString(50,
	 * customer.getSegment()); ps.setString(51, customer.getDataSourceName());
	 * ps.setLong(52, Long.parseLong(customer .getCustomerNo())); String[]
	 * dob1Split = null; String day = ""; String month = ""; String year = "";
	 * if (customer.getDob() != null) { if (customer.getDob().contains("-")) {
	 * dob1Split = customer.getDob() .split("-"); day = dob1Split[0]; month =
	 * dob1Split[1]; year = dob1Split[2]; } if (customer.getDob().contains("/"))
	 * { dob1Split = customer.getDob() .split("/"); day = dob1Split[0]; month =
	 * dob1Split[1]; year = dob1Split[2]; } }
	 * 
	 * ps.setString(53, day); ps.setString(54, month); ps.setString(55, year);
	 * ps.setString(56, "FILE_UPLOAD"); ps.setString(57,
	 * customer.getDateOfIncorporation()); ps.setString(58,
	 * customer.getFirstname()); ps.setString(59, customer.getMiddlename());
	 * ps.setString(60, customer.getLastname()); } catch (Exception e1) {
	 * e1.printStackTrace(); String error = e1.getMessage();
	 * customer.setError_desc(error); } }
	 * 
	 * private void setValues(long psx_batch_id, Customer customer) { customer1
	 * = customer;
	 * 
	 * }
	 * 
	 * @Override public int getBatchSize() { return list.size(); } }); } catch
	 * (Exception e) { e.printStackTrace(); logger.info("Try Catch :::: " +
	 * e.getMessage()); String error = e.getMessage();
	 * logger.info("Enter Into ErrorTable"); ExceptionRecordsInfo
	 * exceptionRecordsInfo = new ExceptionRecordsInfo();
	 * exceptionRecordsInfo.setEodBatchId(eodBatchId);
	 * exceptionRecordsInfo.setCustomerObj(customer1.toString());
	 * exceptionRecordsInfo.setErrorInfo("Outer Catch :::" + error);
	 * exceptionRecordsInfoList.add(exceptionRecordsInfo);
	 * System.out.println("Error Count outside::: " +
	 * exceptionRecordsInfoList.size()); }
	 * 
	 * }
	 */

	private void insertRecord(Customer customer) {

		String SqlQuery = SQLQueries.BATCH_QUERY_OF_EOD[0];
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
		java.sql.Timestamp date = new java.sql.Timestamp(
				new java.util.Date().getTime());
		String dateformat = (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"))
				.format(date);
		String cust_unq_id = "";
		long psx_id = 0;

		if ((!Strings.isNullOrEmpty(customer.getCustomerNo()))
				&& (!Strings.isNullOrEmpty(customer.getSourceSystemId()))) {
			cust_unq_id = customer.getCustomerNo() + "||"
					+ customer.getSourceSystemId();
		} else if (customer.getCustomerNo() != null) {
			cust_unq_id = customer.getCustomerNo();
		} else {
			cust_unq_id = customer.getSourceSystemId();
		}
		if (customer.getDob().equalsIgnoreCase("null")
				|| customer.getDob().equalsIgnoreCase(null)) {
			customer.setDob("");
		}
		if (customer.getDateOfIncorporation().equalsIgnoreCase("null")
				|| customer.getDateOfIncorporation().equalsIgnoreCase(null)) {
			customer.setDateOfIncorporation("");
		}
		String[] dob1Split = null;
		String day = "";
		String month = "";
		String year = "";
		if (customer.getDob() != null) {
			if (customer.getDob().contains("-")) {
				dob1Split = customer.getDob().split("-");
				day = dob1Split[0];
				month = dob1Split[1];
				year = dob1Split[2];
			}
			if (customer.getDob().contains("/")) {
				dob1Split = customer.getDob().split("/");
				day = dob1Split[0];
				month = dob1Split[1];
				year = dob1Split[2];
			}
		}
		Map<String,String> monthmap=new HashMap<>();
		monthmap.put("JAN","01");
		monthmap.put("FEB","02");
		monthmap.put("MAR","03");
		monthmap.put("APR","04");
		monthmap.put("MAY","05");
		monthmap.put("JUN","06");
		monthmap.put("JUL","07");
		monthmap.put("AUG","08");
		monthmap.put("SEP","09");
		monthmap.put("OCT","10");
		monthmap.put("NOV","11");
		monthmap.put("DEC","12");
		// CUST_UNQ_ID,SOURCE_SYS_ID,DATA_SOURCE_NAME,NAME,GENDER,DOB1,PAN,DRIVINGLIC,AADHAAR,VOTERID,DATE_OF_INCORPORATION,TAN_NO,PROCESS_TYPE,
		// APPLICANT_TYPE,EMPLOYER_NAME,FATHER_NAME,PASSPORT,IFSC_CODE,ACCOUNT_NUMBER,CREDIT_CARD_NUMBER,CIN,DIN,REGISTRATION_NO,CONSTITUTION,
		// CA_NUMBER,AGREEMENT_NUMBER,REMARKS,PERMANENT_ADDRESS,PERMANENT_CITY,PERMANENT_PIN,PERMANENT_STATE,PERMANENT_STREET_NUMBER,LANDMARK,
		// OFFICE_ADDRESS,OFFICE_CITY,OFFICE_PIN,OFFICE_STATE,OFFICE_STREET_NUMBER,RESIDENCE_PHONE,PERMANENT_PHONE,MOBILE,STD,CONTACT_EMAIL,PSX_ID,
		// PSX_BATCH_ID,DUIFLAG,LCHGTIME,INSERT_TIME,CUSTOMER_ID,SEGMENT,SOURCE_SYSTEM,CUSTOMER_NO,DAY,MONTH,YEAR,ENTITY_SOURCE,DOI,FIRSTNAME,MIDDLENAME,LASTNAME
		try {
			psx_id = appNextValProc();
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			int recordInserted = jdbcTemplate.update(
					SqlQuery,
					new Object[] {
							cust_unq_id,
							customer.getSourceSystemId(),
							customer.getDataSourceName(),
							customer.getName(),
							customer.getGender(),
							customer.getDob(),
							customer.getPan(),
							customer.getDrivingLicense(),
							customer.getAadharNo(),
							customer.getVoterId(),
							customer.getDateOfIncorporation(),
							customer.getTanNo(),
							customer.getProcessType(),
							customer.getApplicationType(),
							customer.getEmployerName(),
							customer.getFatherName(),
							customer.getPassportNo(),
							customer.getIfscCode(),
							customer.getAccountNumber(),
							customer.getCreditCardNumber(),
							customer.getCompanyIdentificationNo(),
							customer.getDirectorIdentitficationNo(),
							customer.getRegistrationNumber(),
							customer.getConstitution(),
							customer.getCaNumber(),
							customer.getAgreementNumber(),
							customer.getRemarks(),
							customer.getPermanentAddress(),
							customer.getCity(),
							customer.getPin(),
							customer.getState(),
							customer.getAreaLocalityRes(),
							customer.getLandmarkRes(),
							customer.getOfficeAddress(),
							customer.getOfficeCity(),
							customer.getOfficePin(),
							customer.getOffState(),
							customer.getOfficeAreaLocality(),
							customer.getLandlineRes1(),
							customer.getLandLineRes2(),
							customer.getMobile(),
							customer.getStd(),
							customer.getEmail(),
							psx_id,
							eodBatchId,
							"I_OR_U",
							new Timestamp(new SimpleDateFormat(
									"dd-MM-yyyy HH:mm:ss").parse(dateformat)
									.getTime()),
							new Timestamp(new SimpleDateFormat(
									"dd-MM-yyyy HH:mm:ss").parse(dateformat)
									.getTime()), customer.getCustomerNo(),
							customer.getSegment(),
							customer.getDataSourceName(),
							Long.parseLong(customer.getCustomerNo()), day,
							monthmap.get(month.toUpperCase()), year, "FILE_UPLOAD",
							customer.getDateOfIncorporation(),
							customer.getFirstname(), customer.getMiddlename(),
							customer.getLastname()

					});

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Try Catch :::: " + e.getMessage());
			String error = e.getMessage();
			logger.info("Enter Into ErrorTable");
			ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
			exceptionRecordsInfo.setEodBatchId(eodBatchId);
			exceptionRecordsInfo.setCustomerObj(customer.toString());
			exceptionRecordsInfo.setErrorInfo("Outer Catch :::" + error);
			exceptionRecordsInfoList.add(exceptionRecordsInfo);
			System.out.println("Error Count outside::: "
					+ exceptionRecordsInfoList.size());
		}

	}

	public void errorCode(String eodBatchId2, String string, String error) {
		logger.info("Enter into the Error Table" + eodBatchId2);
		// java.sql.Timestamp date = new java.sql.Timestamp(new
		// java.util.Date().getTime());
		// String dateformat = (new SimpleDateFormat("dd-MM-yyyy
		// HH:mm:ss")).format(date);
		String sql = "insert into ERROR_RECORDS_INFO_T(PSX_BATCH_ID,RECORD,ERROR_DESCRIPTION,INSERT_TS)"
				+ "values(?,?,?,systimestamp)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			/*
			 * jdbcTemplate.update(sql, new Object[] { psx_batch_id,
			 * customer.toString(), "ErrorRecord", new Timestamp(new
			 * SimpleDateFormat
			 * ("dd-MM-yyyy HH:mm:ss").parse(dateformat).getTime()) });
			 */

			jdbcTemplate.update(sql,
					new Object[] { eodBatchId2, string, error, });

			// } catch (DataAccessException | ParseException e) {
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public long appNextValProc() throws Exception {
		String sql = "select appnextval('eod_psx_id') from dual";
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			return jdbcTemplate.query(sql, new ResultSetExtractor<Long>() {

				public Long extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					Long reqPsxid = 0L;
					while (rs.next()) {
						reqPsxid = rs.getLong(1);
					}
					return reqPsxid;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while getting appNextValProc: "
					+ e.getMessage());
			return (Long) null;
		}

	}

	@Override
	public List<String> getAllDatasources() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			String sql = "SELECT SOURCE_NAME FROM PSX_DATASOURCE_MST_T";
			List<String> ls = new ArrayList<String>();

			return jdbcTemplate.query(sql, (ResultSet rs) -> {
				while (rs.next()) {
					ls.add(rs.getString("SOURCE_NAME"));
				}
				return ls;
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while getting getAllDatasources: "
					+ e.getMessage());
			return null;
		}
	}

	public List<EodFileStats> getEodFileStatus() {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String query = "select BATCHID,ERROR_COUNT,FILE_NAME,IMPORT_COUNT,INSERT_TIME,PROCESS_TYPE,SOURCE_SYSTEM,TOTAL_COUNT from EOD_FILE_STATS ";
			jdbcTemplate.setFetchSize(1000);
			return jdbcTemplate.query(query, (ResultSet rs, int rowNumber) -> {
				EodFileStats eodFileStats = new EodFileStats();
				eodFileStats.setBatchId(rs.getString("BATCHID"));
				eodFileStats.setErrorCount(rs.getInt("ERROR_COUNT"));
				eodFileStats.setFileName(rs.getString("FILE_NAME"));
				eodFileStats.setImportCount(rs.getInt("IMPORT_COUNT"));
				eodFileStats.setInsertTs(rs.getString("INSERT_TIME"));
				eodFileStats.setProcess_type(rs.getString("PROCESS_TYPE"));
				eodFileStats.setSourceSystem(rs.getString("SOURCE_SYSTEM"));
				eodFileStats.setTotalCount(rs.getInt("TOTAL_COUNT"));
				return eodFileStats;
			});
		} catch (Exception e) {
			logger.error("Exception while getting getEodFileStatus: "
					+ e.getMessage());
			return null;
		}
	}

	public List<Error_Records_Info_T> getErrorRecords(String batchId) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String query = "select CUSTOMER_NO,ERROR_DESCRIPTION,INSERT_TS,PSX_BATCH_ID,RECORD from ERROR_RECORDS_INFO_T WHERE PSX_BATCH_ID=?";
			jdbcTemplate.setFetchSize(1000);
			return jdbcTemplate
					.query(query,
							(ResultSet rs, int rowNumber) -> {
								Error_Records_Info_T errordetails1 = new Error_Records_Info_T();
								errordetails1.setCustomer_no(rs
										.getString("CUSTOMER_NO"));
								errordetails1.setError_description(rs
										.getString("ERROR_DESCRIPTION"));
								errordetails1.setInsert_ts(rs
										.getString("INSERT_TS"));
								errordetails1.setPsx_batch_id(rs
										.getString("PSX_BATCH_ID"));
								errordetails1.setRecord(rs.getString("RECORD"));
								return errordetails1;
							}, batchId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception while getting getErrorRecords: "
					+ e.getMessage());
			return null;
		}

	}

	public List<EodProcessingStages> getEodStagebatchIds1() {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// String Stagequery =
			// "select psx_batch_id,status from psx_nsp_event_details WHERE EOD_BATCH_ID IS NOT NULL";
			String Stagequery = "select distinct EOD_BATCH_ID,INSERT_TIME from psx_nsp_event_details where insert_time is not null order by insert_time desc";
			return jdbcTemplate
					.query(Stagequery,
							(ResultSet rs, int rowNumber) -> {

								EodProcessingStages eodstages1 = new EodProcessingStages();

								eodstages1.setEodbatchid(rs
										.getString("EOD_BATCH_ID"));
								eodstages1.setInserttime(rs
										.getTimestamp("INSERT_TIME") + "");
								return eodstages1;

							});
		} catch (Exception e) {
			logger.error("Exception while getting getEodStagebatchIds: "
					+ e.getMessage());
		}
		return null;
	}

	public List<EodProcessingStages> getEodStagebatchIds() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<EodProcessingStages> eodBatchIdsList = getEodStagebatchIds1();

		Map<String, List> batchIdsOfEodBatchIdMap = new HashMap<String, List>();
		Map<String, String> insertTimeEodBatchIdMap = new HashMap<String, String>();
		Map<String, Integer> snoEodBatchIdMap = new HashMap<String, Integer>();
		for (EodProcessingStages eodStage : eodBatchIdsList) {
			String sql = "select distinct batchid from PSX_COMMON_STAGING_T where EOD_BATCH_ID='"
					+ eodStage.getEodbatchid() + "'";

			List<String> batchIds = jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet resultSet, int i)
						throws SQLException {
					return resultSet.getString(1);
				}
			});

			batchIdsOfEodBatchIdMap.put(eodStage.getEodbatchid(), batchIds);
			insertTimeEodBatchIdMap.put(eodStage.getEodbatchid(),
					eodStage.getInserttime());
			// snoEodBatchIdMap.put(eodStage.getEodbatchid(),
			// eodStage.getSno());
			// obj.setPsxbatchid((String)row.get("PSX_BATCH_ID"));
		}
		List<EodProcessingStages> EodProcessingStagesList = new ArrayList<EodProcessingStages>();
		TreeMap<String, List> sorted = new TreeMap<>();

		// Copy all data from hashMap into TreeMap

		sorted.putAll(batchIdsOfEodBatchIdMap);

		int sno = batchIdsOfEodBatchIdMap.size();
		for (String eodBatchId : sorted.keySet()) {

			EodProcessingStages eodProcessingStage = new EodProcessingStages();
			// eodProcessingStage.setEodbatchid(eodBatchId + " " +
			// batchIdsOfEodBatchIdMap.get(eodBatchId) + " ");
			eodProcessingStage.setEodbatchid(eodBatchId);
			eodProcessingStage.setBatchIdsList(batchIdsOfEodBatchIdMap
					.get(eodBatchId));
			eodProcessingStage.setInserttime(insertTimeEodBatchIdMap
					.get(eodBatchId));

			eodProcessingStage.setSno(sno);
			sno = sno - 1;

			EodProcessingStagesList.add(eodProcessingStage);
		}
		return EodProcessingStagesList;
	}

	/***
	 * This method identifies the various stages of particular batch based on
	 * EODBatchId value Takes batchId as input returns the list of
	 * EodProcessingStages objects
	 */

	public List<EodProcessingStages> getEodStageStatus(String psxbatchid) {

		List<EodProcessingStages> ls = new ArrayList<EodProcessingStages>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String Descriptionquery = "select eod_batch_id,stage,stage_description,NODE1_STATUS,NODE2_STATUS from psx_nsp_event_details where EOD_BATCH_ID=?";

		try {

			jdbcTemplate.query(Descriptionquery,
					new ResultSetExtractor<List<EodProcessingStages>>() {
						@Override
						public List<EodProcessingStages> extractData(
								ResultSet rs) throws SQLException,
								DataAccessException {

							while (rs.next()) {
								EodProcessingStages eodstages = new EodProcessingStages();
								eodstages.setEodbatchid(rs
										.getString("EOD_BATCH_ID"));
								eodstages.setStage(rs.getString("STAGE"));
								eodstages.setStagedescription(rs
										.getString("STAGE_DESCRIPTION"));
								// eodstages.setStatus(rs.getString("STATUS"));
								eodstages.setNode1status(rs
										.getString("NODE1_STATUS"));
								eodstages.setNode2status(rs
										.getString("NODE2_STATUS"));
								ls.add(eodstages);
							}
							return ls;
						}
					}, psxbatchid);
			return ls;
		} catch (Exception e) {
			logger.error("Exception while getting getEodStagebatchIds: "
					+ e.getMessage());
			throw (e);

		}
	}

	@Override
	public InputStream getExportErrorData(String batchId) {

		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date date1 = new Date();
		String date = dateFormat.format(date1);
		List<Error_Records_Info_T> error_Records_Info_T = getErrorRecords(batchId);
		try {
			String destinationPath = environment
					.getProperty("errrorrecordsfiledownloadPath");
			File file = new File(destinationPath + "ERROR_RECORDS_" + batchId
					+ ".csv");

			String downloadFile = destinationPath + "ERROR_RECORDS_" + batchId
					+ ".csv";
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.append("CUSTOMER_NO (Unique Indentifier)");
			fileWriter.append('|');
			fileWriter.append("SOURCE_SYS_ID");
			fileWriter.append('|');
			fileWriter.append("DATA_SOURCE_NAME");
			fileWriter.append('|');
			fileWriter.append("FIRST_NAME");
			fileWriter.append('|');
			fileWriter.append("MIDDLE_NAME");
			fileWriter.append('|');
			fileWriter.append("LAST_NAME");
			fileWriter.append('|');
			fileWriter.append("GENDER");
			fileWriter.append('|');
			fileWriter.append("DOB");
			fileWriter.append('|');
			fileWriter.append("PAN");
			fileWriter.append('|');
			fileWriter.append("DRIVING_LICENSE_NUMBER");
			fileWriter.append('|');
			fileWriter.append("AADHAR_NO");
			fileWriter.append('|');
			fileWriter.append("VOTER_ID");
			fileWriter.append('|');
			fileWriter.append("DATE_OF_INCORPORATION");
			fileWriter.append('|');
			fileWriter.append("TAN_NO");
			fileWriter.append('|');
			fileWriter.append("PROCESS_TYPE");
			fileWriter.append('|');
			fileWriter.append("APPLICANT_TYPE");
			fileWriter.append('|');
			fileWriter.append("EMPOYER_NAME");
			fileWriter.append('|');
			fileWriter.append("FATHER_NAME");
			fileWriter.append('|');
			fileWriter.append("PASSPORT_NO");
			fileWriter.append('|');
			fileWriter.append("IFSC CODE");
			fileWriter.append('|');
			fileWriter.append("ACCOUNT_NUMBER");
			fileWriter.append('|');
			fileWriter.append("CREDIT_CARD_NUMBER");
			fileWriter.append('|');
			fileWriter.append("CIN (Company Identification Number)");
			fileWriter.append('|');
			fileWriter.append("DIN (Director Identification Number)");
			fileWriter.append('|');
			fileWriter.append("Registration Number");
			fileWriter.append('|');
			fileWriter.append("Constitution");
			fileWriter.append('|');
			fileWriter.append("CA NUMBER");
			fileWriter.append('|');
			fileWriter.append("Agreement Number");
			fileWriter.append('|');
			fileWriter.append("REMARKS");
			fileWriter.append('|');
			fileWriter.append("ADDRESS_1");
			fileWriter.append('|');
			fileWriter.append("ADDRESS_2");
			fileWriter.append('|');
			fileWriter.append("ADDRESS_3");
			fileWriter.append('|');
			fileWriter.append("CITY");
			fileWriter.append('|');
			fileWriter.append("PIN");
			fileWriter.append('|');
			fileWriter.append("State");
			fileWriter.append('|');
			fileWriter.append("AREA/LOCALITY_RES");
			fileWriter.append('|');
			fileWriter.append("LANDMARK_RES");
			fileWriter.append('|');
			fileWriter.append("OFFICE_1");
			fileWriter.append('|');
			fileWriter.append("OFFICE_2");
			fileWriter.append('|');
			fileWriter.append("OFFICE_3");
			fileWriter.append('|');
			fileWriter.append("OFF_CITY");
			fileWriter.append('|');
			fileWriter.append("OFF_PIN");
			fileWriter.append('|');
			fileWriter.append("OFF_STATE");
			fileWriter.append('|');
			fileWriter.append("OFF_AREA/LOCALITY");
			fileWriter.append('|');
			fileWriter.append("LANDLINE_1_RES");
			fileWriter.append('|');
			fileWriter.append("LANDLINE_2_RES");
			fileWriter.append('|');
			fileWriter.append("MOBILE");
			fileWriter.append('|');
			fileWriter.append("OFF_LANDMARK");
			fileWriter.append('|');
			fileWriter.append("STD");
			fileWriter.append('|');
			fileWriter.append("EMAIL");
			fileWriter.append('\n');
			for (Error_Records_Info_T record : error_Records_Info_T) {

				String recordinfo = record.getRecord();
				System.out.println("Objects :: "+recordinfo);
				String[] recordSplit = null;
				if (!recordinfo.startsWith("Customer [")) {
					//String result = StringUtils.substring(recordinfo, 0, recordinfo.length() - 1);
					 recordinfo=removeLastChar(recordinfo);
					System.out.println("Removing |  :: "+recordinfo);
					fileWriter.append(recordinfo);
				}
				if (recordinfo.startsWith("Customer [")) {
					recordSplit = recordinfo.split(",");
					
					StringTokenizer tokenizer = new StringTokenizer(recordinfo,
							",");
					boolean v1 = false;
					boolean v2= false;
					boolean v3= false;
					boolean v4= false;
					boolean v5= false;
					boolean v6= false;
					boolean v7= false;
					boolean v8= false;
					boolean v9= false;
					boolean v10= false;
					boolean v11= false;
					boolean v12= false;
					boolean v13= false;
					boolean v14= false;
					boolean v15= false;
					boolean v16= false;
					boolean v17= false;
					boolean v18= false;
					boolean v19= false;
					boolean v20= false;
					boolean v21= false;
					boolean v22= false;
					boolean v23= false;
					boolean v24= false;
					boolean v25= false;
					boolean v26= false;
					boolean v27= false;
					boolean v28= false;
					boolean v29= false;
					boolean v30= false;
					boolean v31= false;
					boolean v32= false;
					boolean v33= false;
					boolean v34= false;
					boolean v35= false;
					boolean v36= false;
					boolean v37= false;
					boolean v38= false;
					boolean v39= false;
					boolean v40= false;
					boolean v41= false;
					boolean v42= false;
					boolean v43= false;
					boolean v44= false;
					boolean v45= false;
					boolean v46= false;
					boolean v47= false;
					boolean v48= false;
					boolean v49= false;
					boolean v50= false;
					boolean v51= false;
					boolean v52= false;
					boolean v53= false;
					boolean v54= false;
					boolean v55= false;
					boolean v56= false;
					boolean v57= false;
					while (tokenizer.hasMoreTokens()) {
						// System.out.println(tokenizer.nextToken());
						String token = tokenizer.nextToken();
						if (token.contains("customerNo=") && v1!=true) {
							v1=true;
							System.out.println("Customer NO ::: "
									+ token.substring(12));
							fileWriter.append(token.substring(12));
							fileWriter.append('|');
							
						}
						if (token.contains("sourceSystemId=") && v2!=true) {
							v2 =true;
							System.out.println("sourceSystemId ::: "
									+ token.substring(16));
							fileWriter.append(token.substring(16));
							fileWriter.append('|');
						}
						if (token.contains("dataSourceName=") && v3!=true ) {
							v3=true;
							System.out.println("dataSourceName ::: "
									+ token.substring(16));
							fileWriter.append(token.substring(16));
							fileWriter.append('|');
						}
//						if (token.contains("name") && v4!=true ) {
//							v4 =true ;
//							System.out.println("name ::: "
//									+ token.substring(6));
//							fileWriter.append(token.substring(6));
//							fileWriter.append('|');
//						}
						
						if (token.contains("firstname=") && v51!=true) {
							v51=true; 
							System.out.println("firstname ::: "
									+ token.substring(11));
							fileWriter.append(token.substring(11));
							fileWriter.append('|');
						}
						if (token.contains("middlename=") && v52!=true) {
							v52=true; 
							System.out.println("middlename ::: "
									+ token.substring(12));
							fileWriter.append(token.substring(12));
							fileWriter.append('|');
						}
						if (token.contains("lastname=") && v53!=true) {
							v53=true; 
							System.out.println("lastname ::: "
									+ token.substring(10));
							fileWriter.append(token.substring(10).replaceAll("]", ""));
							fileWriter.append('|');
						}
						if (token.contains("gender=")&& v5!=true ) {
							v5=true; 
							System.out.println("gender ::: "
									+ token.substring(8));
							fileWriter.append(token.substring(8));
							fileWriter.append('|');
						}
						
						
						if (token.contains("dob=") && v8!=true) {
							v8=true; 
							System.out.println("dob ::: "
									+ token.substring(5));
							fileWriter.append(token.substring(5));
							fileWriter.append('|');
						}
						if (token.contains("pan=") && v10!=true) {
							v10=true; 
							System.out.println("pan ::: "
									+ token.substring(5));
							fileWriter.append(token.substring(5));
							fileWriter.append('|');
						}
						if (token.contains("drivingLicense=") && v9!=true) {
							v9=true; 
							System.out.println("drivingLicense ::: "
									+ token.substring(16));
							fileWriter.append(token.substring(16));
							fileWriter.append('|');
						}
						
						if (token.contains("aadharNo=") && v11!=true) {
							v11=true; 
							System.out.println("aadharNo ::: "
									+ token.substring(10));
							fileWriter.append(token.substring(10));
							fileWriter.append('|');
						}
						if (token.contains("voterId=") && v12!=true) {
							v12=true; 
							System.out.println("voterId ::: "
									+ token.substring(9));
							fileWriter.append(token.substring(9));
							fileWriter.append('|');
						}
						if (token.contains("dateOfIncorporation=") && v13!=true) {
							v13=true; 
							System.out.println("dateOfIncorporation ::: "
									+ token.substring(21));
							fileWriter.append(token.substring(21));
							fileWriter.append('|');
						}
						if (token.contains("tanNo=") && v14!=true) {
							v14=true; 
							System.out.println("tanNo ::: "
									+ token.substring(7));
							fileWriter.append(token.substring(7));
							fileWriter.append('|');
						}
						if (token.contains("processType=") && v15!=true) {
							v15=true; 
							System.out.println("processType ::: "
									+ token.substring(13));
							fileWriter.append(token.substring(13));
							fileWriter.append('|');
						}
						if (token.contains("applicationType=") && v16!=true) {
							v16=true; 
							System.out.println("applicationType ::: "
									+ token.substring(17));
							fileWriter.append(token.substring(17));
							fileWriter.append('|');
						}
						if (token.contains("employerName=") && v17!=true) {
							v17=true; 
							System.out.println("employerName ::: "
									+ token.substring(14));
							fileWriter.append(token.substring(14));
							fileWriter.append('|');
						}
						if (token.contains("fatherName=") && v18!=true) {
							v18=true; 
							System.out.println("fatherName ::: "
									+ token.substring(12));
							fileWriter.append(token.substring(12));
							fileWriter.append('|');
						}
						if (token.contains("passportNo=") && v19!=true) {
							v19=true; 
							System.out.println("passportNo ::: "
									+ token.substring(12));
							fileWriter.append(token.substring(12));
							fileWriter.append('|');
						}
						if (token.contains("ifscCode=") && v20!=true) {
							v20=true; 
							System.out.println("ifscCode ::: "
									+ token.substring(10));
							fileWriter.append(token.substring(10));
							fileWriter.append('|');
						}
						if (token.contains("accountNumber=") && v21!=true) {
							v21=true; 
							System.out.println("accountNumber ::: "
									+ token.substring(15));
							fileWriter.append(token.substring(15));
							fileWriter.append('|');
						}
						if (token.contains("creditCardNumber=") && v22!=true) {
							v22=true; 
							System.out.println("creditCardNumber ::: "
									+ token.substring(18));
							fileWriter.append(token.substring(18));
							fileWriter.append('|');
						}
						if (token.contains("companyIdentificationNo=") && v23!=true) {
							v23=true; 
							System.out.println("companyIdentificationNo ::: "
									+ token.substring(25));
							fileWriter.append(token.substring(25));
							fileWriter.append('|');
						}
						if (token.contains("directorIdentitficationNo=") && v24!=true) {
							v24=true; 
							System.out.println("directorIdentitficationNo ::: "
									+ token.substring(27));
							fileWriter.append(token.substring(27));
							fileWriter.append('|');
						}
						if (token.contains("registrationNumber=") && v25!=true) {
							v25=true; 
							System.out.println("registrationNumber ::: "
									+ token.substring(20));
							fileWriter.append(token.substring(20));
							fileWriter.append('|');
						}
						if (token.contains("constitution=") && v26!=true) {
							v26=true; 
							System.out.println("constitution ::: "
									+ token.substring(14));
							fileWriter.append(token.substring(14));
							fileWriter.append('|');
						}
						if (token.contains("caNumber=") && v27!=true) {
							v27=true; 
							System.out.println("caNumber ::: "
									+ token.substring(10));
							fileWriter.append(token.substring(10));
							fileWriter.append('|');
						}
						if (token.contains("agreementNumber=") && v28!=true) {
							v28=true; 
							System.out.println("agreementNumber ::: "
									+ token.substring(17));
							fileWriter.append(token.substring(17));
							fileWriter.append('|');
						}
						if (token.contains("remarks=") && v29!=true) {
							v29=true; 
							System.out.println("remarks ::: "
									+ token.substring(9));
							fileWriter.append(token.substring(9));
							fileWriter.append('|');
						}
//						if (token.contains("permanentAddress") && v30!=true) {
//							v30=true; 
//							System.out.println("permanentAddress ::: "
//									+ token.substring(18));
//							String addresses=token.substring(18);
////							fileWriter.append(token.substring(18));
////							fileWriter.append('|');
//							StringTokenizer tokenizer1 = new StringTokenizer(addresses, "\\s+");
//					        
//					        while (tokenizer1.hasMoreTokens()) {
//					            System.out.println("Has Address ::: "+tokenizer.nextToken());
//					        }        
//							
//							
//						}
						if (token.contains("address1=") && v30!=true) {
							v30=true; 
							System.out.println("address1 ::: "
									+ token.substring(10));
							fileWriter.append(token.substring(10));
							fileWriter.append('|');
						}
						if (token.contains("address2=") && v54!=true) {
							v51=true; 
							System.out.println("address ::: "
									+ token.substring(10));
							fileWriter.append(token.substring(10));
							fileWriter.append('|');
						}
						if (token.contains("address3=") && v55!=true) {
							v52=true; 
							System.out.println("address1 ::: "
									+ token.substring(10));
							fileWriter.append(token.substring(10));
							fileWriter.append('|');
						}
						if (token.contains("city=") && v31!=true) {
							v31=true; 
							System.out.println("city ::: "
									+ token.substring(6));
							fileWriter.append(token.substring(6));
							fileWriter.append('|');
						}
						if (token.contains("pin=") && v32!=true) {
							v32=true; 
							System.out.println("pin ::: "
									+ token.substring(5));
							fileWriter.append(token.substring(5));
							fileWriter.append('|');
						}
						if (token.contains("state=") && v33!=true) {
							v33=true; 
							System.out.println("state ::: "
									+ token.substring(7));
							fileWriter.append(token.substring(7));
							fileWriter.append('|');
						}
						if (token.contains("areaLocalityRes=") && v34!=true) {
							v34=true; 
							System.out.println("areaLocalityRes ::: "
									+ token.substring(17));
							fileWriter.append(token.substring(17));
							fileWriter.append('|');
						}
						if (token.contains("landmarkRes=") && v35!=true) {
							v35=true; 
							System.out.println("landmarkRes ::: "
									+ token.substring(13));
							fileWriter.append(token.substring(13));
							fileWriter.append('|');
						}
//						if (token.contains("officeAddress") && v36!=true) {
//							v36=true; 
//							System.out.println("officeAddress ::: "
//									+ token.substring(15));
//							fileWriter.append(token.substring(15));
//							fileWriter.append('|');
//						}
						
//						if (token.contains("residentAddress=") && v37!=true) {
//							v37=true; 
//							System.out.println("residentAddress ::: "
//									+ token.substring(17));
//							fileWriter.append(token.substring(17));
//							fileWriter.append('|');
//						}
						if (token.contains("office1=") && v38!=true) {
							v38=true; 
							System.out.println("office1 ::: "
									+ token.substring(9));
							fileWriter.append(token.substring(9));
							fileWriter.append('|');
						}
						if (token.contains("office2=") && v39!=true) {
							v39=true; 
							System.out.println("office2 ::: "
									+ token.substring(9));
							fileWriter.append(token.substring(9));
							fileWriter.append('|');
						}
						if (token.contains("office3=") && v40!=true) {
							v40=true; 
							System.out.println("office3 ::: "
									+ token.substring(9));
							fileWriter.append(token.substring(9));
							fileWriter.append('|');
						}
						if (token.contains("officeCity=") && v41!=true) {
							v41=true; 
							System.out.println("officeCity ::: "
									+ token.substring(12));
							fileWriter.append(token.substring(12));
							fileWriter.append('|');
						}
						if (token.contains("officePin=") && v42!=true) {
							v42=true; 
							System.out.println("officePin ::: "
									+ token.substring(11));
							fileWriter.append(token.substring(11));
							fileWriter.append('|');
						}
						if (token.contains("offState=") && v43!=true) {
							v43=true; 
							System.out.println("offState ::: "
									+ token.substring(10));
							fileWriter.append(token.substring(10));
							fileWriter.append('|');
						}
						if (token.contains("officeAreaLocality=") && v44!=true) {
							v44=true; 
							System.out.println("officeAreaLocality ::: "
									+ token.substring(20));
							fileWriter.append(token.substring(20));
							fileWriter.append('|');
						}
						if (token.contains("landlineRes1=") && v45!=true) {
							v45=true; 
							System.out.println("landlineRes1 ::: "
									+ token.substring(14));
							fileWriter.append(token.substring(14));
							fileWriter.append('|');
						}
						if (token.contains("landLineRes2=") && v46!=true) {
							v46=true; 
							System.out.println("landLineRes2 ::: "
									+ token.substring(14));
							fileWriter.append(token.substring(14));
							fileWriter.append('|');
						}
						if (token.contains("officeLandmark") && v47!=true) {
							v47=true; 
							System.out.println("officeLandmark ::: "
									+ token.substring(16));
							fileWriter.append(token.substring(16));
							fileWriter.append('|');
						}
						if (token.contains("mobile=") && v7!=true) {
							v7=true; 
							System.out.println("mobile ::: "
									+ token.substring(8));
							fileWriter.append(token.substring(8));
							fileWriter.append('|');
						}
						if (token.contains("std=") && v48!=true) {
							v48=true; 
							System.out.println("std ::: "
									+ token.substring(5));
							fileWriter.append(token.substring(5));
							fileWriter.append('|');
						}
//						if (token.contains("error_desc=") && v49!=true) {
//							v49=true; 
//							System.out.println("error_desc ::: "
//									+ token.substring(12));
//							fileWriter.append(token.substring(12));
//							fileWriter.append('|');
//						}
						
						if (token.contains("email=") && v6!=true) {
							v6=true; 
							System.out.println("email ::: "
									+ token.substring(7));
							fileWriter.append(token.substring(7));
						//	fileWriter.append('|');
						}
//						if (token.contains("segment") && v50!=true) {
//							v50=true; 
//							System.out.println("segment ::: "
//									+ token.substring(9));
//							fileWriter.append(token.substring(9));
//							fileWriter.append('|');
//						}
					}
					 v1 = false;
					 v2= false;
					 v3= false;
					 v4= false;
					 v5= false;
					 v6= false;
					 v7= false;
					 v8= false;
					 v9= false;
					 v10= false;
					 v11= false;
					 v12= false;
					 v13= false;
					 v14= false;
					 v15= false;
					 v16= false;
					 v17= false;
					 v18= false;
					 v19= false;
					 v20= false;
					 v21= false;
					 v22= false;
					 v23= false;
					 v24= false;
					 v25= false;
					 v26= false;
					 v27= false;
					 v28= false;
					 v29= false;
					 v30= false;
					 v31= false;
					 v32= false;
					 v33= false;
					 v34= false;
					 v35= false;
					 v36= false;
					 v37= false;
					 v38= false;
					 v39= false;
					 v40= false;
					 v41= false;
					 v42= false;
					 v43= false;
					 v44= false;
					 v45= false;
					 v46= false;
					 v47= false;
					 v48= false;
					 v49= false;
					 v50= false;
					 v51= false;
					 v52= false;
					 v53= false;
					 v54= false;
					 v55= false;
					 v56= false;
					 v57= false;
				}
				fileWriter.append('\n');
			}
			fileWriter.flush();
			fileWriter.close();
			logger.info("File Written Successfully........");
			InputStream input = null;
			logger.info("Download File Name in getExportErrorData::: "
					+ downloadFile);
			input = new FileInputStream(downloadFile);
			return input;
		} catch (IOException e) {
			logger.error(e);
		} finally {

		}
		return null;
	}
	
	public static String removeLastChar(String s) {
	    return (s == null || s.length() == 0)
	      ? null
	      : (s.substring(0, s.length() - 1));
	}
}