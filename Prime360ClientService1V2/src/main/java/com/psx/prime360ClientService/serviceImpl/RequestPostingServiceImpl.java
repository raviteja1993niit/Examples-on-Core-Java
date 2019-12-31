package com.psx.prime360ClientService.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.psx.core.join.util.VDNDataProvider;
import com.psx.core.join.util.VirtualDenormalizer;
import com.psx.prime360ClientService.beans.RequestBean;
import com.psx.prime360ClientService.config.Producer;
import com.psx.prime360ClientService.customersearch.CustomerSearch;
import com.psx.prime360ClientService.customersearch.CustomerSearchBO;
import com.psx.prime360ClientService.customersearch.CustomerSearchDTO;
import com.psx.prime360ClientService.customersearch.CustomerSearchParameter;
import com.psx.prime360ClientService.customersearch.CustomerSearchTableDTO;
import com.psx.prime360ClientService.dto.IdentitySearchDTO;
import com.psx.prime360ClientService.dto.ProfilesDTO;
import com.psx.prime360ClientService.entity.InstallablesEntity;
import com.psx.prime360ClientService.entity.NSPRequestEntity;
import com.psx.prime360ClientService.entity.NSPRequestResultsEntity;
import com.psx.prime360ClientService.entity.Profile;
import com.psx.prime360ClientService.relationalsearch.RelationalSearch;
import com.psx.prime360ClientService.relationalsearch.RelationalSearchBO;
import com.psx.prime360ClientService.relationalsearch.RelationalSearchDTO;
import com.psx.prime360ClientService.repository.InstallableRepository;
import com.psx.prime360ClientService.repository.ProfilesRepository;
import com.psx.prime360ClientService.repository.RequestRepository;
import com.psx.prime360ClientService.repository.RequestResultsRepository;
import com.psx.prime360ClientService.serviceI.RequestPostingServiceI;

import posidex.core.prime360.dmlutils.PlainJDBCDML;
import posidex.core.prime360.dmlutils.PlainJDBCUtils;

/**
 *
 * @author jayantronald
 *
 */

@Service
@PropertySource({ "classpath:csv.properties", "classpath:application.properties" })
public class RequestPostingServiceImpl implements RequestPostingServiceI {
	// private static final Logger logger =
	// LoggerFactory.getLogger(RequestPostingServiceImpl.class);
	private static Logger logger = Logger.getLogger(RequestPostingServiceImpl.class.getName());

	static Properties prop = new Properties();
	Connection con = null;
	// Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	boolean status = false;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	ProfilesRepository profilesRepository;

	@Autowired
	Environment environment;

	@Autowired
	EntityManagerFactory factory;

	@Autowired
	InstallableRepository installableRepository;

	@Autowired
	RequestRepository requestRepository;

	@Autowired
	RequestResultsRepository requestResultsRepository;

	@Autowired
	DataSource dataSource;

	@Autowired
	Producer producer;

	@Override
	public List<String> getAllRequestId() {
		EntityManager em = factory.createEntityManager();

		List<String> requestReposit = requestRepository.getAllDistinctRequstId();

		logger.info("RequestPostingServiceImpl getAllRequestId() : " + requestReposit);

		em.close();
		return requestReposit;
	}

	@Override
	public NSPRequestEntity getRecordsBasedOnId(String requestId) {
		EntityManager em = factory.createEntityManager();
		NSPRequestEntity nspRequestEntity = requestRepository.getResByreqid(requestId);
		logger.info("RequestPostingServiceImpl Object getRecordsBasedOnId(-) requestId : " + requestId);
		em.close();
		return nspRequestEntity;
	}

	@Override
	public List<NSPRequestResultsEntity> getResultsBasedOnId(String requestId) {
		List<NSPRequestResultsEntity> nspRequestResultsEntity = requestResultsRepository.findByRequestId(requestId);
		logger.info("RequestPostingServiceImpl List getResultsBasedOnId(-) requestId : " + requestId);
		logger.info("RequestPostingServiceImpl List getResultsBasedOnId(-) nspRequestResultsEntity : " + nspRequestResultsEntity);

		return nspRequestResultsEntity;
	}

	@Override
	public List<String> getEquality() throws IOException {
		EntityManager em = factory.createEntityManager();
		List<InstallablesEntity> installablesEntities = installableRepository.findAll();
		logger.info("RequestPostingServiceImpl getEquality() : " + installablesEntities);
		List<String> retValue = new ArrayList<String>();
		String keyColumnName = "insertIdentityRequest.equality";

		for (InstallablesEntity installablesEntity : installablesEntities) {
			if (installablesEntity.getPropKey().equalsIgnoreCase(keyColumnName)
					&& installablesEntity.getModule().equalsIgnoreCase("IdentitySearch")) {
				String[] equality = installablesEntity.getPropValue().split(",");
				for (String equalityCol : equality) {
					retValue.add(equalityCol);
				}
			}
		}
		em.close();
		return retValue;
	}

	@Override
	public String submitIdentitySearchRequest(List<String> equalityData, String profileId, String operation)
			throws Exception {
		String propertyCommonKey = "insertIdentityRequest." + equalityData.get(0) + ".";
		Connection con = dataSource.getConnection();
		List<InstallablesEntity> installablesEntities = installableRepository.findAll();
		String requestID = System.nanoTime() + "";
		HashMap<String, Object> data = new HashMap<>();
		data.put("PSX_ID", requestID);
		data.put("PSX_BATCH_ID", new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()));
		data.put(equalityData.get(0), equalityData.get(1));
		data.put("CURRENT_TIME", new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()));
		data.put("CURRENT_USER", "PSX_USER");

		HashMap<String, String> map = new HashMap<>();
		for (InstallablesEntity installablesEntity : installablesEntities) {
			if (installablesEntity.getModule().equalsIgnoreCase("IdentitySearch")) {
				map.put(installablesEntity.getPropKey(), installablesEntity.getPropValue());
			}
		}

		HashMap<String, String> additionalProps = new HashMap<String, String>();
		HashMap<String, PlainJDBCDML> myDMLs = PlainJDBCUtils.getMyPreparedStatements(con, map, propertyCommonKey);
		PlainJDBCUtils.setBindValuesExecuteAndCloseDMLs(myDMLs, data, additionalProps);
		ArrayList<HashMap<String, Object>> data1 = new ArrayList<>();
		data1.add(data);
		HashMap<String, ArrayList<HashMap<String, Object>>> mapData = new HashMap<>();
		mapData.put("CDAP", data1);
		try {
			con.close();
		} catch (Exception e) {
		}

		HashMap<String, String> paramMap = new HashMap<>();
		RequestBean bean = new RequestBean();
		bean.setRequestID(requestID);
		bean.setRequestInformation(mapData);
		bean.setProcessType("matchingProcess");
		bean.setParamMap(paramMap);
		bean.setRequestStatus("P");
		bean.setSourceSystemName("PRIME360");
		bean.setMatchingRule(map.get(propertyCommonKey + "matchingRuleString"));
		bean.setResidualParams(map.get(propertyCommonKey + "residual"));
		bean.setWeightages(map.get(propertyCommonKey + "weightages"));
		bean.setScaleEquations(map.get(propertyCommonKey + "scaleEquations"));
		bean.setRankingOrders(map.get(propertyCommonKey + "rankingOrders"));
		logger.info("Bean : " + bean);

		String response = sendSynchronousRequest(bean);
		logger.info("RequestPostingServiceImpl response : " + response);
		if (response != "false") {
			String retValue = "";
			RequestBean requestBean = RequestBean.fromJson(response);
			if (!(requestBean.getRequestStatus().equals("E"))) {
				retValue = requestID + "";
			} else {

			}
			return retValue;
		} else {
			return "false" + "";
		}
	}

	public String sendSynchronousRequest(RequestBean requestBean) {
		String enginePort = environment.getProperty("engine.port");
		String engineUrl = environment.getProperty("engine.Url");
		// String url = "http://localhost:" + enginePort + "/request";
		// String url = "http://192.168.1.114:" + enginePort + "/request";
		String url = engineUrl + enginePort + "/request";
		logger.info("RequestPostingServiceImpl engine url : " + url);

		String LCHGTIME = "LCHGTIME";
		if (requestBean.getRequestInformation() != null) {
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			for (Map.Entry<String, ArrayList<HashMap<String, Object>>> entry : requestBean.getRequestInformation()
					.entrySet()) {
				for (HashMap<String, Object> row : entry.getValue()) {
					row.put(LCHGTIME, null);
				}
			}
		}

		String pid = getStatus("tbtDkbcKp");
		logger.info("RequestPostingServiceImpl pid : " + pid);
		String requestJson = new Gson().toJson(requestBean, RequestBean.class);
		producer.sendMessage(environment.getProperty("Node1_Queue"), requestJson);
		producer.sendMessage(environment.getProperty("Node2_Queue"), requestJson);
		if (pid != null && pid.length() > 0) {
			/*
			 * ResponseEntity<String> r1 = restTemplate.postForEntity(url, requestBean,
			 * String.class); logger.info("RequestPostingServiceImpl r1.getBody() : " +
			 * r1.getBody()); return r1.getBody();
			 */
			logger.info("RequestPostingServiceImpl responce : " + requestBean);

			return "true";
		} else {
			return "false";
		}
	}

	@Override
	public String getStatus(String serviceName) {
		String cmd = "jps -v";
		String pid = null;
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.redirectErrorStream(true);
			pb.command("jps", "-v");
			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;

			while ((line = reader.readLine()) != null) {
				// if(line.contains("-DApplicationName="+serviceName))
				if (line.contains(serviceName)) {
					pid = line.split(" ")[0];
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}

	@Override
	public IdentitySearchDTO getIdentitySearchResultsByRequestId(String requestId) {
		EntityManager em = factory.createEntityManager();
		List<InstallablesEntity> installablesEntities = installableRepository.findAll();
		logger.info("RequestPostingServiceImpl getResultsByRequestId(-) requestId : " + requestId);
		String keyColumnName = "identitySearchResult.";

		IdentitySearchDTO identitySearchDTO = new IdentitySearchDTO();
		boolean headerAdded = false;

		for (InstallablesEntity installablesEntity : installablesEntities) {
			if (installablesEntity.getPropKey().equalsIgnoreCase(keyColumnName + "tableHeader")
					&& installablesEntity.getModule().equalsIgnoreCase("IdentitySearch")) {
				List<String> identitySearchTableHeader = new ArrayList<>();
				try {
					String[] headerRow = installablesEntity.getPropValue().split(",");
					for (String headerCol : headerRow) {
						identitySearchTableHeader.add(headerCol);
					}
					identitySearchDTO.setIdentitySearchTableHeader(identitySearchTableHeader);
					headerAdded = true;
				} catch (Exception e) {
					logger.info("Exception : " + e.getMessage());
				}
			}
			if (installablesEntity.getPropKey().equalsIgnoreCase(keyColumnName + "hyperLinkIndex")
					&& installablesEntity.getModule().equalsIgnoreCase("IdentitySearch")) {
				identitySearchDTO.setHyperLinkIndex(installablesEntity.getPropValue());
			}
		}
		if (headerAdded) {
			for (InstallablesEntity installablesEntity : installablesEntities) {
				if (installablesEntity.getPropKey().equalsIgnoreCase(keyColumnName + "tableData")
						&& installablesEntity.getModule().equalsIgnoreCase("IdentitySearch")) {
					try {
						StringBuilder queryBuilder = new StringBuilder(installablesEntity.getPropValue());
						Query query = em.createNativeQuery(queryBuilder.toString());
						query.setParameter(1, requestId);
						List<?> res = query.getResultList();

						List<String[]> identitySearchTableData = new ArrayList<String[]>();
						for (int i = 0; i < res.size(); i++) {
							String[] str = new String[identitySearchDTO.getIdentitySearchTableHeader().size()];
							if (res.get(i) instanceof Object[]) {
								for (int j = 0; j < ((Object[]) res.get(i)).length; j++) {
									str[j] = ((Object[]) res.get(i))[j] != null ? ((Object[]) res.get(i))[j].toString()
											: null;
								}
							} else {
								str[0] = res.get(i) != null ? res.get(i).toString() : null;
							}
							identitySearchTableData.add(str);
						}
						identitySearchDTO.setIdentitySearchTableData(identitySearchTableData);
					} catch (Exception e) {
						logger.info("Exception : " + e.getMessage());
					}
				}
			}
		}
		em.close();
		return identitySearchDTO;
	}

	@Override
	public List<Profile> getAllActiveProfiles() {
		List<Profile> profiles = profilesRepository.getAllDistinctProfiles();
		logger.info("RequestPostingServiceImpl getAllActiveProfiles() : " + profiles);
		return profiles;
	}

	@Override
	public CustomerSearchDTO getCustomerSearchTable() {
		CustomerSearchDTO customerSearchDTO = getCustomerSearch().getCustomerSearchDTO();
		return customerSearchDTO;
	}

	@Override
	public String submitCustomerSearchRequest(CustomerSearchDTO customerSearchDTO, String profileId,
			String operation) throws Exception {
		ProfilesDTO profilesDTO = new ProfilesDTO();
		Profile profile = profilesRepository.getProfileById(Integer.parseInt(profileId));
		BeanUtils.copyProperties(profile, profilesDTO);

		CustomerSearch customerSearch = getCustomerSearch();
		String rqID="";
		try {
			rqID = customerSearch.persist(this, customerSearchDTO, profilesDTO, dataSource, operation);

			logger.info("RequestPostingServiceImpl customerSearchDTO : " + customerSearchDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String rqId=customerSearchDTO.getRequest_id();
		return rqId;
	}

	@Override
	public void submitMultipleCustomerSearchRequest(String[] fileNames, String[] tableTags, String profileId,
			String operation, Map<String, String> headerColVsDBCol) throws Exception {
		// String[] fileNames=new String[]
		// {"sampleData/FileA.csv","sampleData/FileB.csv"};
		// String[] tableTags=new String[] {"DG","ADDRESS"};
		String[] rowDist = new String[tableTags.length];
		Arrays.fill(rowDist, "DEFAULT");
		String primaryKey = "PSX_ID";

		VDNDataProvider[] provider = VDNDataProvider.getFileVDNDataProviders(0, 1, fileNames, primaryKey, 1, false,
				tableTags, rowDist, System.currentTimeMillis());
		VirtualDenormalizer virtualDenormalizer = new VirtualDenormalizer(provider);
		HashMap<String, ArrayList<HashMap<String, Object>>> buffer = null;
		while ((buffer = virtualDenormalizer.getNextRecordSets()) != null) {
			CustomerSearchDTO customerSearchDTO = new CustomerSearchDTO();
			List<CustomerSearchTableDTO> listOfCustomerSearchTableDTO = new ArrayList<CustomerSearchTableDTO>();

			for (Entry<String, ArrayList<HashMap<String, Object>>> entry : buffer.entrySet()) {
				CustomerSearchTableDTO customerSearchTableDTO = new CustomerSearchTableDTO();
				customerSearchTableDTO.setTableName(entry.getKey());

				List<CustomerSearchParameter> customerSearchParameters = new ArrayList<CustomerSearchParameter>();
				for (HashMap<String, Object> row : entry.getValue()) {
					for (Entry<String, Object> entry1 : row.entrySet()) {
						if (headerColVsDBCol.get(entry1.getKey()) == null) {
							// ignore this entry. Because, we are not considering it for matching process.
							// for example, LCHGTime,DUIFLAG,PSXBATCHID etc.
						} else {
							CustomerSearchParameter customerSearchParameter = new CustomerSearchParameter();
							customerSearchParameter.setEngineName(headerColVsDBCol.get(entry1.getKey()));
							customerSearchParameter.setParameterValue(entry1.getValue().toString());
							customerSearchParameters.add(customerSearchParameter);
						}
					}
				}
				customerSearchTableDTO.setCustomerSearchParameters(customerSearchParameters);
				listOfCustomerSearchTableDTO.add(customerSearchTableDTO);
			}
			customerSearchDTO.setTables(listOfCustomerSearchTableDTO);
			submitCustomerSearchRequest(customerSearchDTO, profileId, operation);
		}
	}

	private CustomerSearch getCustomerSearch() {
		List<InstallablesEntity> installablesEntities = installableRepository.findAll();
		CustomerSearch customerSearch = null;

		// String keyColumnName = "customerSearchScreen";

		Map<String, String> props = new HashMap<>();

		for (InstallablesEntity installablesEntity : installablesEntities) {
			// if (installablesEntity.getPropKey().equalsIgnoreCase(keyColumnName) &&
			// installablesEntity.getModule().equalsIgnoreCase("CustomerSearch")) {
			// String jsonStr = installablesEntity.getPropValue().toString();
			// customerSearch = CustomerSearch.fromJSON(jsonStr);
			// }
			if (installablesEntity.getModule().equalsIgnoreCase("CustomerSearch")) {
				props.put(installablesEntity.getPropKey(), installablesEntity.getPropValue());
			}
		}
		customerSearch = CustomerSearch.fromProps(props);
		return customerSearch;
	}

	@Override
	public void getCustomerSearchResultsByRequestId(CustomerSearchBO customerSearchBO, String requestId) {
		logger.info("requestId : " + requestId + " = customerSearchBO : " + customerSearchBO);
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder queryBuilder = new StringBuilder(customerSearchBO.getResultsQuery());
			Query query = em.createNativeQuery(queryBuilder.toString());
			query.setParameter(1, requestId);
			List<?> res = query.getResultList();

			List<String[]> resultsTableHeader = new ArrayList<String[]>();
			for (int i = 0; i < res.size(); i++) {
				String[] str = new String[customerSearchBO.getResultsTableHeader().size()];
				if (res.get(i) instanceof Object[]) {
					for (int j = 0; j < ((Object[]) res.get(i)).length; j++) {
						str[j] = ((Object[]) res.get(i))[j] != null ? ((Object[]) res.get(i))[j].toString() : null;
					}
				} else {
					str[0] = res.get(i) != null ? res.get(i).toString() : null;
				}
				resultsTableHeader.add(str);
			}
			customerSearchBO.setResults(resultsTableHeader);
		} catch (Exception e) {
			logger.info("Exception : " + e.getMessage());
		}
		em.close();
	}

	@Override
	public List<Profile> getAllRelationalProfiles() {
		List<Profile> profiles = profilesRepository.getAllDistinctProfiles();
		logger.info("RequestPostingServiceImpl getAllRelationalProfiles() :: " + profiles);

		List<Profile> retValue = new ArrayList<>();
		if (profiles.size() > 0) {
			for (Profile profile : profiles) {
				String matchingRuleCSVContent = profile.getMatchingRuleCSV();
				if (matchingRuleCSVContent != null) {
					String[] lines = matchingRuleCSVContent.split(";");
					String[] header = lines[0].split(",");
					boolean familyRuleExists = false;
					for (int lc = 1; lc < lines.length; lc++) {
						// LOGGER.info("parsing the line:"+lines[lc]);
						String[] recordArray = lines[lc].split(",");
						String srcParam = null;
						String trgParam = null;

						for (int i = 0; i < recordArray.length; i++) {
							if (!familyRuleExists) {
								if (header[i].toUpperCase().equalsIgnoreCase("SrcParam")) {
									srcParam = recordArray[i];
								}
								if (header[i].toUpperCase().equalsIgnoreCase("TrgParam")) {
									trgParam = recordArray[i];
								}
								if (srcParam != null && trgParam != null) {
									if (srcParam.equalsIgnoreCase(trgParam)) {

									} else {
										retValue.add(profile);
										srcParam = "";
										trgParam = "";
										familyRuleExists = true;
									}
								}
							}
						}
					}
				}
			}
		}
		return retValue;
	}

	@Override
	public RelationalSearchDTO getRelationalSearchTable() {
		RelationalSearchDTO relationalSearchDTO = getRelationalSearch().getRelationalSearchDTO();
		return relationalSearchDTO;
	}

	@Override
	public RelationalSearchDTO submitRelationalSearchRequest(RelationalSearchDTO relationalSearchDTO, String profileId,
			String operation) throws SQLException, Exception {
		ProfilesDTO profilesDTO = new ProfilesDTO();
		Profile profile = profilesRepository.getProfileById(Integer.parseInt(profileId));
		BeanUtils.copyProperties(profile, profilesDTO);

		RelationalSearch relationalSearch = getRelationalSearch();
		try {
			relationalSearchDTO = relationalSearch.persist(this, relationalSearchDTO, profilesDTO, dataSource);
			logger.info("RequestPostingServiceImpl relationalSearchDTO : " + relationalSearchDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return relationalSearchDTO;
	}

	private RelationalSearch getRelationalSearch() {
		List<InstallablesEntity> installablesEntities = installableRepository.findAll();
		RelationalSearch relationalSearch = null;

		// String keyColumnName = "relationalSearchScreen";
		Map<String, String> props = new HashMap<>();

		for (InstallablesEntity installablesEntity : installablesEntities) {
			// if (installablesEntity.getPropKey().equalsIgnoreCase(keyColumnName) &&
			// installablesEntity.getModule().equalsIgnoreCase("RelationalSearch")) {
			// String jsonStr = installablesEntity.getPropValue().toString();
			// relationalSearch = RelationalSearch.fromJSON(jsonStr);
			// }
			if (installablesEntity.getModule().equalsIgnoreCase("RelationalSearch")) {
				props.put(installablesEntity.getPropKey(), installablesEntity.getPropValue());
			}
		}
		relationalSearch = RelationalSearch.fromProps(props);
		return relationalSearch;
	}

	@Override
	public void getRelationalSearchResultsByRequestId(RelationalSearchBO relationalSearchBO, String requestId) {
		logger.info("requestId : " + requestId + " = relationalSearchBO : " + relationalSearchBO);
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder queryBuilder = new StringBuilder(relationalSearchBO.getResultsQuery());
			Query query = em.createNativeQuery(queryBuilder.toString());
			query.setParameter(1, requestId);
			List<?> res = query.getResultList();

			List<String[]> resultsTableHeader = new ArrayList<String[]>();
			for (int i = 0; i < res.size(); i++) {
				String[] str = new String[relationalSearchBO.getResultsTableHeader().size()];
				if (res.get(i) instanceof Object[]) {
					for (int j = 0; j < ((Object[]) res.get(i)).length; j++) {
						str[j] = ((Object[]) res.get(i))[j] != null ? ((Object[]) res.get(i))[j].toString() : null;
					}
				} else {
					str[0] = res.get(i) != null ? res.get(i).toString() : null;
				}
				resultsTableHeader.add(str);
			}
			relationalSearchBO.setResults(resultsTableHeader);
		} catch (Exception e) {
			logger.info("Exception : " + e.getMessage());
		}
		em.close();
	}

	@Override
	public boolean saveCustomerSearch(NSPRequestEntity nspRequestEntity)
			throws ClassNotFoundException, SQLException, IOException {

		try {
			// System.out.println("JdbcTemplate=====>>>>" + jdbc);
			//logger.info("loadind connection");
			prop.load(RequestPostingServiceImpl.class.getClassLoader().getResourceAsStream("database.properties"));

			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(prop.getProperty("spring.datasource.url"),
					prop.getProperty("spring.datasource.username"), prop.getProperty("spring.datasource.password"));
			//logger.info("UserName  "+prop.getProperty("spring.datasource.username"));
	//	logger.info("Password  "+prop.getProperty("spring.datasource.password"));
	//	logger.info("before insert query");
			String query = "insert into psx_nsp_request(AADHAAR,ACCOUNT_NUMBER,APPLICATION_NO,CA_NUMBER,CIN,CREDIT_CARD_NUMBER,CUST_UNQ_ID,CUSTOMER_NO,DIN,DRIVINGLIC,EMPLOYER_NAME,FATHER_NAME,NAME,OFFICE_AREA,OFFICE_CITY,OFFICE_EMAIL,OFFICE_PIN,PAN,PASSPORT,PERMANENT_ADDRESS,PERMANENT_PIN,REGISTRATION_NO,SEGMENT,TAN_NO,VOTERID,REQUEST_ID,REQUEST_STATUS,PSX_ID,CUSTOMER_SEARCH,INSERT_TIME,DOB1,NODE1_REQUEST_STATUS,NODE2_REQUEST_STATUS,DOI,OFFICE_ADDRESS,PERMANENT_CITY,PERMANENT_EMAIL,PERMANENT_PHONE,OFFICE_PHONE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP,?,?,?,?,?,?,?,?,?)";

			pstmt = con.prepareStatement(query);
		//	logger.info(" insert query  "+query);

			pstmt.setString(1, nspRequestEntity.getAadhar());
			pstmt.setString(2, nspRequestEntity.getAccount_no());
			pstmt.setString(3, nspRequestEntity.getApplication_no());
			pstmt.setString(4, nspRequestEntity.getCa_no());
			pstmt.setString(5, nspRequestEntity.getCin());
			pstmt.setString(6, nspRequestEntity.getCredit_card());
			pstmt.setString(7, nspRequestEntity.getCust_unq_id());
			pstmt.setString(8, nspRequestEntity.getCustomer_no());
			pstmt.setString(9, nspRequestEntity.getDin());
			pstmt.setString(10, nspRequestEntity.getDriving_lic());
			pstmt.setString(11, nspRequestEntity.getEmployer_name());
			pstmt.setString(12, nspRequestEntity.getFather_Name());
			pstmt.setString(13, nspRequestEntity.getName());
			pstmt.setString(14, nspRequestEntity.getOff_area());
			pstmt.setString(15, nspRequestEntity.getOffice_City());
			pstmt.setString(16, nspRequestEntity.getOffice_Email());
			pstmt.setString(17, nspRequestEntity.getOffice_Pin());
			pstmt.setString(18, nspRequestEntity.getPan());
			pstmt.setString(19, nspRequestEntity.getPassport());
			pstmt.setString(20, nspRequestEntity.getPermanent_Address());
			pstmt.setString(21, nspRequestEntity.getPermanent_Pin());
			pstmt.setString(22, nspRequestEntity.getRegistration_no());
			pstmt.setString(23, nspRequestEntity.getSegment());
			pstmt.setString(24, nspRequestEntity.getTan_no());
			pstmt.setString(25, nspRequestEntity.getVoterid());
			pstmt.setString(26, nspRequestEntity.getRequest_id());
			logger.info("inserting request id "+nspRequestEntity.getRequest_id());
			// pstmt.setLong(26, nspRequestEntity.getPsx_Id());
			pstmt.setString(27, "P");
			pstmt.setString(28, nspRequestEntity.getRequest_id());
			pstmt.setString(29, "Y");
			if (nspRequestEntity.getDob1() != null) {
				pstmt.setDate(30, nspRequestEntity.getDob1());
			} else {
				pstmt.setDate(30, null);
			}
			
			pstmt.setString(31, "P");
			pstmt.setString(32, "P");
			if (nspRequestEntity.getDoi() != null) {
				pstmt.setDate(33, nspRequestEntity.getDoi());
			} else {
				pstmt.setDate(33, null);
			}
			
			//pstmt.setString(33, nspRequestEntity.getDoi());
			pstmt.setString(34, nspRequestEntity.getOffice_Address());
			pstmt.setString(35, nspRequestEntity.getPermanent_City());
			pstmt.setString(36, nspRequestEntity.getPermanent_Email());
			pstmt.setString(37, nspRequestEntity.getPermanent_Phone());
			pstmt.setString(38, nspRequestEntity.getOffice_Phone());
			
			status = pstmt.execute();
			status = true;
			logger.info("The Record Inserted Succefully into psx_nsp_request Table:::");
			pstmt.close();

		} catch (SQLException e) {
			logger.error("Error Occured While Inserting into psx_nsp_request table " + e.getMessage());
			pstmt.close();
		}

		return status;
	}

}