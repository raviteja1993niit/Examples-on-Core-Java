package com.posidex.eod.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.posidex.eod.dbutils.DBConnection;
import com.posidex.eod.model.Customer;

public class EodDataProcessor {
	private static Logger logger = Logger.getLogger(EodDataProcessor.class
			.getName());
	static String nodeOneQueue = "";
	static String nodeTwoQueue = "";
	static String intradayQueue1 = "";
	static String intradayQueue2 = "";
	static BasicDataSource dataSource;
	public static JmsTemplate jmsTemplate;
	public JmsTemplate amqjmsTemplate;
	public static int errorStatus = 0;
	static List<ExceptionRecordsInfo> exceptionRecordsInfoList = new ArrayList<ExceptionRecordsInfo>();

	public JmsTemplate getAmqjmsTemplate() {
		return amqjmsTemplate;
	}

	public void setAmqjmsTemplate(JmsTemplate amqjmsTemplate) {
		this.amqjmsTemplate = amqjmsTemplate;
	}

	static Properties props = new Properties();

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;

	}

	public static void main(String[] args) {
		logger.info("Entering into the Eod Processing Main...");
		InputStream inputStream = null;
		System.out.println("EodDataProcessor");
		try {
		//	 inputStream = new FileInputStream("./props/config.properties");
			inputStream = new FileInputStream(args[0]);
			props.load(inputStream);
		//	 ApplicationContext context = new ClassPathXmlApplicationContext(
		//	 "spring_core.xml");
			ApplicationContext context = new ClassPathXmlApplicationContext(
					args[1]);
			jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
			logger.info("jmsTemplate.........." + jmsTemplate);

		} catch (IOException ex) {
			ex.printStackTrace();
			ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
			exceptionRecordsInfo.setErrorInfo(ex.getMessage());
			exceptionRecordsInfoList.add(exceptionRecordsInfo);
			logger.error("Exception occured in main().........");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void notify(Message message) {
		logger.info("In Notify Method");
		try {
			if (message != null) {
				logger.info("in notify with Message " + message);
				startEodProcess();

			}
		} catch (Exception e) {
			// logger.error(e, e);
			ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
			exceptionRecordsInfo.setErrorInfo(e.getMessage());
			exceptionRecordsInfoList.add(exceptionRecordsInfo);
			e.printStackTrace();
			logger.error("Error while reading message in PMICQueueDequeue: "
					+ e.getMessage());
		}
	}

	/***
	 * This is the method where Segregation, Purging, Updating EOD stages will
	 * initiated.
	 * 
	 * @throws JSONException
	 */
	public void startEodProcess() throws JSONException {

		DBConnection dbConnection = new DBConnection();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int errorStatus = 0;
		try {
			conn = dbConnection.getConnection(props, dataSource);

			// truncateStagingTables(conn, props.getProperty("MB_Q_TBL_1"));
			// truncateStagingTables(conn, props.getProperty("MB_Q_TBL_2"));

			// truncatingMicroTables(conn);

			for (int i = 1; i <= 2; i++) {
				truncateStagingTables(conn, "PSX_COMMON_STAGING_T_" + i);
			}
			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			Date date = new Date();
			String eodBatchId = dateFormat.format(date);

			String sql = props.getProperty("FRAUD_DATA.UPDATE.QUERY");
			logger.info("Update Query ..." + sql);
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, eodBatchId);
				rs = pstmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();

				logger.error("Exception while updateEODBatchId "
						+ e.getMessage());
			}
			segregateCustomerData(conn, props, eodBatchId);

			List<Customer> customers = fetchCustomerData(conn, props,
					eodBatchId);
			preparePurgeData(conn, props, eodBatchId);
			intradayQueue1 = (String) props.get("intradayQueue1");
			intradayQueue2 = (String) props.get("intradayQueue2");
			nodeOneQueue = (String) props.get("nodeOneQueue");
			nodeTwoQueue = (String) props.get("nodeTwoQueue");

			// prepareIntraday1PurgeData(conn, props, intradayQueue1);
			// prepareIntraday2PurgeData(conn, props, intradayQueue2);

			
			String count = (String) props.get("nodesCount");
			// logger.info("NodeOneQueue............." +
			// props.get("nodeOneQueue"));
			logger.info("NodesCount.............." + Integer.parseInt(count));
			int nodesCount = Integer.parseInt(count);
			HashMap<String, List<Customer>> nodesMap = buildEachNodesCustomersMap(
					customers, nodesCount);

			if (!(nodesMap.isEmpty()) && nodesMap.size() > 0) {
				for (String key : nodesMap.keySet()) {
					logger.info("NODE HASHMAP key VALUE:::: " + key
							+ "  NO. OF RECORDS " + nodesMap.get(key).size());

					insertDataIntoIncrementalStaging(conn, nodesMap.get(key),
							"PSX_COMMON_STAGING_T_" + key, eodBatchId);
					insertDataIntoIncrementalStaging(conn, nodesMap.get(key),
							"PSX_COMMON_STAGING_T_ADT_" + key, eodBatchId);

				}

				if (customers.size() > 0 && errorStatus == 0) {
					updateNspEventDetails(conn, eodBatchId, "STAGE_2",
							"DATA SEGGREGATED TO RESPECTIVE NODES", "C", 0);
				}
				List<String> queueNames = new ArrayList<String>();
				String sql3 = "select NAME from user_queues where queue_type=?";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setString(1, "NORMAL_QUEUE");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					queueNames.add(rs.getString(1));
				}
				if (queueNames.contains(nodeOneQueue)
						&& queueNames.contains(nodeTwoQueue)) {
					sendIncrementalMessageToEngine1(conn, eodBatchId,
							nodeOneQueue);
					sendIncrementalMessageToEngine2(conn, eodBatchId,
							nodeTwoQueue);
					updateNspEventDetails(conn, eodBatchId, "STAGE_4",
							"REQUEST PROCESSING BY PRIME 360", "", 4);
				} else {
					logger.info("Queue Names in DB ::: " + queueNames);
					logger.info("Given Queue Names ::: 1." + nodeOneQueue
							+ " 2." + nodeTwoQueue);
					updateNspEventDetails(conn, eodBatchId, "STAGE_3",
							"REQUEST SENT FOR INCREMENTAL PROCESSING", "E", 1);
					updateNspEventDetails(conn, eodBatchId, "STAGE_3",
							"REQUEST SENT FOR INCREMENTAL PROCESSING", "E", 2);
				}

			} else {
				logger.info("No Data is found  to process in PSX_COMMON_STAGING_T ........ ");
				sendIncrementalMessageToEngine1(conn, eodBatchId, nodeOneQueue);
				sendIncrementalMessageToEngine2(conn, eodBatchId, nodeTwoQueue);
			}
			sendPurgingMessageToEngine(nodeOneQueue,nodeTwoQueue);
			prepareIntraday1_Online_Rebuild(conn, props, nodeOneQueue);
			prepareIntraday2_Online_Rebuild(conn, props, nodeTwoQueue);
			for (ExceptionRecordsInfo exceptionRecordsInfo : exceptionRecordsInfoList) {
				errorCode(exceptionRecordsInfo.getEodBatchId(),
						exceptionRecordsInfo.getRecord(),
						exceptionRecordsInfo.getErrorInfo(), conn);
			}
			exceptionRecordsInfoList = new ArrayList<ExceptionRecordsInfo>();
		} catch (ClassNotFoundException e) {
			logger.error("Exception Occurred in Main Method");
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("Exception Occurred in Main Method");
			e.printStackTrace();
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
				logger.error("Exception Occurred while closing connection");
			}
		}
	}

	private void prepareIntraday2_Online_Rebuild(Connection connection,
			Properties configProps, String intradayQ2) throws SQLException,
			JSONException {
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		long startTime = 0, stopTime = 0;
		long psxbatchid = 0L;
		try {

			startTime = System.currentTimeMillis();
			String query0 = configProps.getProperty(
					"INTRADAY2.ONLINE.REBUILD.BACKUP.QUERY");
			logger.info("Query to insert Online Rebuild data from PSX_NSP_INTRADAY_2_REBUILD::" + " is::"
					+ query0);
			pstmt = connection.prepareStatement(query0);
			int x0 = pstmt.executeUpdate();
			stopTime = System.currentTimeMillis();
			logger.info("Query executed Sucessfully no of rows inserted into PSX_NSP_INTRADAY_2_REBUILD ::"
					+ x0 + " time taken::" + (stopTime - startTime));
			if (pstmt != null)
				pstmt.close();
			startTime = System.currentTimeMillis();
			String query = configProps.getProperty(
					"INTRADAY2.ONLINE.REBUILD.DELETE.QUERY");
			logger.info("Query to Delete Online Rebuild data from PSX_NSP_INTRADAY_2::" + " is::"
					+ query);
			pstmt = connection.prepareStatement(query);
			int x = pstmt.executeUpdate();
			stopTime = System.currentTimeMillis();
			logger.info("Query executed Sucessfully no of rows deleted from PSX_NSP_INTRADAY_2_REBUILD ::"
					+ x + " time taken::" + (stopTime - startTime));
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error occured while exectuing queries for prepareIntraday2_Online_Rebuild::"
					+ e.getMessage());
			logger.error(e, e);
			throw e;
		} finally {
			try {
				closeDBHandles(null, pstmt, resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("Error while Closing DB Handles::" + e.getMessage());
			}
		}
		sendOnlineRebuildMessageToEngine(intradayQ2);

	}

	private void prepareIntraday1_Online_Rebuild(Connection connection,
			Properties configProps, String intradayQ1) throws SQLException,
			JSONException {
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		long startTime = 0, stopTime = 0;
		long psxbatchid = 0L;
		try {

			startTime = System.currentTimeMillis();

			String query0 = configProps.getProperty(
					"INTRADAY1.ONLINE.REBUILD.BACKUP.QUERY");
			logger.info("Query to insert Online Rebuild data from PSX_NSP_INTRADAY_1_REBUILD::" + " is::"
					+ query0);
			pstmt = connection.prepareStatement(query0);
			int x0 = pstmt.executeUpdate();
			stopTime = System.currentTimeMillis();
			logger.info("Query executed Sucessfully no of rows inserted into PSX_NSP_INTRADAY_1_REBUILD ::"
					+ x0 + " time taken::" + (stopTime - startTime));
			if (pstmt != null)
				pstmt.close();
			startTime = System.currentTimeMillis();

			String query = configProps.getProperty(
					"INTRADAY1.ONLINE.REBUILD.DELETE.QUERY");
			logger.info("Query to Delete Online Rebuild data from PSX_NSP_INTRADAY_1::" + " is::"
					+ query);
			pstmt = connection.prepareStatement(query);
			int x = pstmt.executeUpdate();
			stopTime = System.currentTimeMillis();
			logger.info("Query executed Sucessfully no of rows deleted from PSX_NSP_INTRADAY_1 ::"
					+ x + " time taken::" + (stopTime - startTime));
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error occured while exectuing queries for prepareIntraday1_Online_Rebuild::"
					+ e.getMessage());
			logger.error(e, e);
			throw e;
		} finally {
			try {
				closeDBHandles(null, pstmt, resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("Error while Closing DB Handles::" + e.getMessage());
			}
		}
		sendOnlineRebuildMessageToEngine(intradayQ1);
	}

	private void truncatingMicroTables(Connection conn) throws SQLException {
		CallableStatement proc_stmt = null;
		logger.info("TRUNCATING QUEUES ::: " + "BEGIN "
				+ props.getProperty("someStoredProc") + " END;");
		proc_stmt = conn.prepareCall("BEGIN "
				+ props.getProperty("someStoredProc") + " END;");

		proc_stmt.executeQuery();

		proc_stmt.close();
		logger.info("Truncating MicroBatching Queues");

	}

	/***
	 * In this method, we will report EOD stages status, during EOD process
	 * 
	 * @param conn
	 * @param eodBatchId
	 * @param stage
	 * @param stageDescription
	 * @param status
	 * @param stageNo
	 * @throws SQLException
	 */
	public static void updateNspEventDetails(Connection conn,
			String eodBatchId, String stage, String stageDescription,
			String status, int stageNo) throws SQLException {
		PreparedStatement pstmt;
		try {
			if (stageNo == 4) {
				String sql1 = "insert into PSX_NSP_EVENT_DETAILS"
						+ "(EOD_BATCH_ID,STAGE,STAGE_DESCRIPTION,NODE1_STATUS,NODE2_STATUS,PSX_BATCH_ID,INSERT_TIME) "
						+ "values(?,?,?,?,?,?,SYSTIMESTAMP)";
				logger.info("stage4 Query ..." + sql1);

				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, eodBatchId);
				pstmt.setString(2, stage);
				pstmt.setString(3, stageDescription);
				pstmt.setString(4, status);
				pstmt.setString(5, status);
				pstmt.setString(6, eodBatchId);
				pstmt.executeQuery();
			} else if (stageNo == 1) {
				String sql1 = "insert into PSX_NSP_EVENT_DETAILS"
						+ "(EOD_BATCH_ID,STAGE,STAGE_DESCRIPTION,NODE1_STATUS,INSERT_TIME) "
						+ "values(?,?,?,?,SYSTIMESTAMP)";
				logger.info("stage4 Query ..." + sql1);

				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, eodBatchId);
				pstmt.setString(2, stage);
				pstmt.setString(3, stageDescription);
				pstmt.setString(4, status);
				pstmt.executeQuery();
			} else if (stageNo == 2) {
				// String sql1 = "insert into PSX_NSP_EVENT_DETAILS"
				// +
				// "(EOD_BATCH_ID,STAGE,STAGE_DESCRIPTION,NODE2_STATUS,INSERT_TIME) "
				// + "values(?,?,?,?,SYSTIMESTAMP)";
				// logger.info("stage4 Query ..." + sql1);
				//
				// pstmt = conn.prepareStatement(sql1);
				// pstmt.setString(1, eodBatchId);
				// pstmt.setString(2, stage);
				// pstmt.setString(3, stageDescription);
				// pstmt.setString(4, status);
				// pstmt.executeQuery();

				String sql1 = "update PSX_NSP_EVENT_DETAILS set NODE2_STATUS=? where eod_batch_id=? and stage=? ";
				logger.info("stage4 Query ..." + sql1);
				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, status);
				pstmt.setString(2, eodBatchId);
				pstmt.setString(3, stage);
				pstmt.executeQuery();
			} else {
				String sql1 = "insert into PSX_NSP_EVENT_DETAILS"
						+ "(EOD_BATCH_ID,STAGE,STAGE_DESCRIPTION,NODE1_STATUS,NODE2_STATUS,INSERT_TIME) "
						+ "values(?,?,?,?,?,SYSTIMESTAMP)";
				logger.info("stage4 Query ..." + sql1);

				pstmt = conn.prepareStatement(sql1);
				pstmt.setString(1, eodBatchId);
				pstmt.setString(2, stage);
				pstmt.setString(3, stageDescription);
				pstmt.setString(4, status);
				pstmt.setString(5, status);
				pstmt.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Exception while stage1 Query " + e.getMessage());
			throw e;
		}
	}

	/***
	 * This method will collect and insert the Error records into Error Table.
	 * 
	 * @param eodBatchId2
	 * @param record
	 * @param error
	 * @param con
	 */
	public void errorCode(String eodBatchId2, String record, String error,
			Connection con) {
		logger.info("Enter into the Error Table" + eodBatchId2);
		PreparedStatement pstmt = null;

		String sql = "insert into ERROR_RECORDS_INFO_T(PSX_BATCH_ID,RECORD,ERROR_DESCRIPTION,INSERT_TS)"
				+ "values(?,?,?,systimestamp)";
		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setLong(1, Long.parseLong(eodBatchId2));
			pstmt.setString(2, record);
			pstmt.setString(3, error);
			int i = pstmt.executeUpdate();
			logger.info(i + " Error Records Inserted");

		} catch (SQLException e1) {

			e1.printStackTrace();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/***
	 * This method will
	 * 
	 * @param connection
	 * @param tablename
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private void truncateStagingTables(Connection connection, String tablename)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		if (pstmt != null)
			pstmt.close();
		String sql1 = "truncate table " + tablename;
		pstmt = connection.prepareStatement(sql1);
		pstmt.execute();
		logger.info("Table " + tablename + "Truncated");
		closeDBHandles(null, pstmt, resultSet);
	}

	@SuppressWarnings({ "unused", "resource" })
	private void prepareIntraday1PurgeData(Connection connection,
			Properties configProps, String intradayQueue1) throws SQLException,
			JSONException {
		logger.info("In prepareIntraday1PurgeData ...");
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		long startTime = 0, stopTime = 0;
		long psxbatchid = 0L;
		try {

			startTime = System.currentTimeMillis();
			if (pstmt != null)
				pstmt.close();

			String sql = "select appnextval('purge_req_psx_id') from dual";
			pstmt = connection.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				psxbatchid = resultSet.getLong(1);
			}

			startTime = System.currentTimeMillis();
			if (pstmt != null)
				pstmt.close();

			String query2 = configProps.getProperty(
					"INTRADAY1.PURGE.SELECT.QUERY").replace("<<psxbatchid>>",
					psxbatchid + "");
			pstmt = connection.prepareStatement(query2);
			resultSet = pstmt.executeQuery();
			Date lchgtime = new Date();
			while (resultSet.next()) {

				HashMap<String, Object> dg_row1 = new HashMap<String, Object>();
				dg_row1.put("INSERT_TIME", resultSet.getString("INSERT_TIME"));
				dg_row1.put("LCHGTIME", resultSet.getTimestamp("LCHGTIME"));
				// dg_row1.put("LCHGTIME", new SimpleDateFormat(
				// "dd-MM-yyyy HH:mm:ss").format(System.currentTimeMillis()));
				dg_row1.put("DUIFLAG", "D");
				dg_row1.put("EVENTTYPE", resultSet.getString("EVENTTYPE"));
				dg_row1.put("CUST_UNQ_ID", resultSet.getString("CUST_UNQ_ID"));
				dg_row1.put("PSX_ID", resultSet.getString("PSX_ID"));
				ArrayList<HashMap<String, Object>> dgAl = new ArrayList<HashMap<String, Object>>();
				dgAl.add(dg_row1);
				HashMap<String, ArrayList<HashMap<String, Object>>> entry = new HashMap<String, ArrayList<HashMap<String, Object>>>();
				entry.put("CDAP", dgAl);

				sendIntraday1MessageToEngine(
						resultSet.getString("CUST_UNQ_ID"), entry,
						intradayQueue1);
			}
			// buildAndSendMicrobatchingMessageToEngine(nodeOneQueue);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error occured while exectuing queries for prepareIntraday1PurgeData::"
					+ e.getMessage());
			logger.error(e, e);
			throw e;
		} finally {
			try {
				closeDBHandles(null, pstmt, resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("Error while Closing DB Handles::" + e.getMessage());
			}
		}
	}

	@SuppressWarnings({ "resource", "unused" })
	private void prepareIntraday2PurgeData(Connection connection,
			Properties configProps, String intradayQueue2) throws SQLException,
			JSONException {
		logger.info("In prepareIntraday2PurgeData ...");
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		long startTime = 0, stopTime = 0;
		long psxbatchid = 0L;
		try {
			startTime = System.currentTimeMillis();
			if (pstmt != null)
				pstmt.close();
			String sql = configProps.getProperty("SEQUENCE.QUERY");
			pstmt = connection.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				psxbatchid = resultSet.getLong(1);
			}

			// String query = configProps.getProperty("INTRADAY2.PURGE.QUERY")
			// .replace("<<psxbatchid>>", psxbatchid + "");
			// logger.info("Query to Insert purge Data for DataSource::" +
			// " is::"
			// + query);
			// pstmt = connection.prepareStatement(query);
			// int x = pstmt.executeUpdate();
			// stopTime = System.currentTimeMillis();
			// logger.info("Query executed Sucessfully no of rows inserted in PSX_NSP_INTRADAY_2 ::"
			// + x + " time taken::" + (stopTime - startTime));

			startTime = System.currentTimeMillis();
			if (pstmt != null)
				pstmt.close();

			String query2 = configProps.getProperty(
					"INTRADAY2.PURGE.SELECT.QUERY").replace("<<psxbatchid>>",
					psxbatchid + "");
			pstmt = connection.prepareStatement(query2);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				HashMap<String, Object> dg_row1 = new HashMap<String, Object>();
				dg_row1.put("INSERT_TIME", resultSet.getString("INSERT_TIME"));
				dg_row1.put("LCHGTIME", resultSet.getTimestamp("LCHGTIME"));
				// dg_row1.put("LCHGTIME", new SimpleDateFormat(
				// "dd-MM-yyyy HH:mm:ss").format(System.currentTimeMillis()));
				dg_row1.put("DUIFLAG", "D");
				dg_row1.put("EVENTTYPE", resultSet.getString("EVENTTYPE"));
				dg_row1.put("CUST_UNQ_ID", resultSet.getString("CUST_UNQ_ID"));
				// dg_row1.put("PSX_BATCH_ID",
				// resultSet.getString("PSX_BATCH_ID"));
				dg_row1.put("PSX_ID", resultSet.getString("PSX_ID"));
				ArrayList<HashMap<String, Object>> dgAl = new ArrayList<HashMap<String, Object>>();
				dgAl.add(dg_row1);
				HashMap<String, ArrayList<HashMap<String, Object>>> entry = new HashMap<String, ArrayList<HashMap<String, Object>>>();
				entry.put("CDAP", dgAl);
				sendIntraday2MessageToEngine(
						resultSet.getString("CUST_UNQ_ID"), entry,
						intradayQueue2);
			}
			// buildAndSendMicrobatchingMessageToEngine(nodeTwoQueue);
		} catch (SQLException e) {
			logger.error("Error occured while exectuing queries for prepareIntraday2PurgeData::"
					+ e.getMessage());
			logger.error(e, e);
			throw e;
		} finally {
			try {
				closeDBHandles(null, pstmt, resultSet);
			} catch (SQLException e) {
				logger.warn("Error while Closing DB Handles::" + e.getMessage());
			}
		}
	}

	private static List<Customer> fetchCustomerData(Connection conn,
			Properties configProps, String eodBatchId) throws SQLException,
			ParseException {
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<Customer> customersList = new ArrayList<Customer>();
		Customer customerData;

		String fetchCustomersQuery = "select NAME,FATHER_NAME,DOI,DOB1,PAN,PASSPORT,VOTERID,DRIVINGLIC,AADHAAR,GENDER,OFFICE_STATE,PERMANENT_STATE,RESIDENCE_STATE,TEMPORARY_STATE,PRESENT_STATE,CONTACT_STATE,PREFERRED_STATE,OFFICE_ADDRESS,OFFICE_CITY,OFFICE_PIN,PERMANENT_ADDRESS,PERMANENT_CITY,PERMANENT_PIN,PRESENT_ADDRESS,PRESENT_CITY,PRESENT_PIN,EMERGENCY_ADDRESS,EMERGENCY_CITY,EMERGENCY_PIN,CONTACT_ADDRESS,CONTACT_CITY,CONTACT_PIN,POSTAL_ADDRESS,POSTAL_CITY,POSTAL_PIN,RESIDENCE_ADDRESS,RESIDENCE_CITY,RESIDENCE_PIN,PREFERRED_ADDRESS,PREFERRED_CITY,PREFERRED_PIN,TEMPORARY_ADDRESS,TEMPORARY_CITY,TEMPORARY_PIN,PRESENT_EMAIL,EMERGENCY_EMAIL,CONTACT_EMAIL,OFFICE_EMAIL,PREFERRED_EMAIL,PERMANENT_EMAIL,RESIDENCE_EMAIL,POSTAL_EMAIL,TEMPORARY_EMAIL,OFFICE_PHONE,PERMANENT_PHONE,RESIDENCE_PHONE,PRESENT_PHONE,EMERGENCY_PHONE,POSTAL_PHONE,PREFERRED_PHONE,CONTACT_PHONE,TEMPORARY_PHONE,OFFICE_STREET_NUMBER,COUNTRY,PERMANENT_STREET_NUMBER,RESIDENCE_STREET_NUMBER,TEMP_STREET_NUMBER,EVENTTYPE,OLD_PSX_BATCH_ID,RATIONCARD_NO,PRESENT_COUNTRY,PREFERRED_COUNTRY,CONTACT_COUNTRY,POSTAL_COUNTRY,OFFICE_COUNTRY,PERMANENT_COUNTRY,TEMPORARY_COUNTRY,RESIDENCE_COUNTRY,TIMESTAMP1,TIMESTAMP2,AREA,POLICY_NUMBER,COUNTRY_OF_ORIGIN,LANDMARK,MOBILE,DATE_OF_INCORPORATION,TAN_NO,PROCESS_TYPE,APPLICANT_TYPE,EMPLOYER_NAME,ACCOUNT_NUMBER,CREDIT_CARD_NUMBER,PROCESS_FLAG,SOURCE_SYSTEM,UCIN_FLAG,CA_NUMBER,CIN,DIN,REGISTRATION_NO,CUSTOMER_NO,CUSTOMER_ID,SOURCE_SYS_ID,SEGMENT,FILLER_STRING_1,FILLER_STRING_2,FILLER_STRING_3,FILLER_STRING_4,FILLER_STRING_5,FILLER_DATE_1,FILLER_DATE_2,FILLER_NUMBER_1,FILLER_NUMBER_2,FILLER_NUMBER_3,FILLER_NUMBER_4,FILLER_NUMBER_5,FILLER_STRING_6,FILLER_STRING_7,FILLER_STRING_8,FILLER_STRING_9,BATCHID,ERROR_CODE,EOD_BATCH_ID,ERROR_DESC,PHONE,PHONE_TYPE,CUSTOMER_CONTACT_TYPE,ADDRESS,CUST_UNQ_ID,STD,LCHGTIME,DUIFLAG,INSERT_TIME,CONTACT_STD_CODE,EMERGENCY_STD_CODE,OFFICE_STD_CODE,PERMANENT_STD_CODE,POSTAL_STD_CODE,PREFERRED_STD_CODE,PRESENT_STD_CODE,RESIDENCE_STD_CODE,TEMPORARY_STD_CODE,CONTACT_ADDRESS_TYPE,EMERGENCY_ADDRESS_TYPE,OFFICE_ADDRESS_TYPE,PERMANENT_ADDRESS_TYPE,POSTAL_ADDRESS_TYPE,PREFERRED_ADDRESS_TYPE,PRESENT_ADDRESS_TYPE,RESIDENCE_ADDRESS_TYPE,TEMPORARY_ADDRESS_TYPE,PSX_ID,DATA_SOURCE_NAME,DAY,MONTH,YEAR,FIRSTNAME,MIDDLENAME,LASTNAME,REMARKS from PSX_COMMON_STAGING_T where EOD_BATCH_ID=?";

		pstmt = conn.prepareStatement(fetchCustomersQuery);
		pstmt.setString(1, eodBatchId);
		resultSet = pstmt.executeQuery();
		while (resultSet.next()) {
			try {
				customerData = new Customer();
				customerData.setLchgtime(resultSet.getTimestamp("LCHGTIME"));
				customerData.setInsertTime(resultSet
						.getTimestamp("INSERT_TIME"));
				customerData.setName(resultSet.getString("NAME"));
				customerData.setFatherName(resultSet.getString("FATHER_NAME"));
				customerData.setDateOfIncorporation(resultSet.getDate("DOI"));
				customerData.setDob(resultSet.getDate("DOB1"));
				customerData.setPan(resultSet.getString("PAN"));

				customerData.setPassportNo(resultSet.getString("PASSPORT"));
				customerData.setVoterId(resultSet.getString("VOTERID"));
				customerData.setDrivingLicense(resultSet
						.getString("DRIVINGLIC"));
				customerData.setAadharNo(resultSet.getString("AADHAAR"));
				customerData.setGender(resultSet.getString("GENDER"));

				// ,,,,,,,GSTIN
				customerData
						.setOfficeState(resultSet.getString("OFFICE_STATE"));
				customerData.setPermanentState(resultSet
						.getString("PERMANENT_STATE"));
				customerData.setResidenceState(resultSet
						.getString("RESIDENCE_STATE"));
				customerData.setTemporaryState(resultSet
						.getString("TEMPORARY_STATE"));
				customerData.setPresentState(resultSet
						.getString("PRESENT_STATE"));
				customerData.setContactState(resultSet
						.getString("CONTACT_STATE"));
				customerData.setPreferredState(resultSet
						.getString("PREFERRED_STATE"));

				// 1.Office
				customerData.setOfficeAddress(resultSet
						.getString("OFFICE_ADDRESS"));
				customerData.setOfficeCity(resultSet.getString("OFFICE_CITY"));
				customerData.setOfficePin(resultSet.getString("OFFICE_PIN"));
				customerData
						.setOfficePhone(resultSet.getString("OFFICE_PHONE"));
				customerData
						.setOfficeEmail(resultSet.getString("OFFICE_EMAIL"));
				customerData.setOfficeCountry(resultSet
						.getString("OFFICE_COUNTRY"));

				// 2.Permanent
				customerData.setPermanentAddress(resultSet
						.getString("PERMANENT_ADDRESS"));
				customerData.setPermanentCity(resultSet
						.getString("PERMANENT_CITY"));
				customerData.setPermanentPin(resultSet
						.getString("PERMANENT_PIN"));
				customerData.setPermanentPhone(resultSet
						.getString("PERMANENT_PHONE"));
				customerData.setPermanentEmail(resultSet
						.getString("PERMANENT_EMAIL"));
				customerData.setPermanentCountry(resultSet
						.getString("PERMANENT_COUNTRY"));

				// 3.Residence
				customerData.setResidenceAddress(resultSet
						.getString("RESIDENCE_ADDRESS"));
				customerData.setResidenceCity(resultSet
						.getString("RESIDENCE_CITY"));
				customerData.setResidencePin(resultSet
						.getString("RESIDENCE_PIN"));
				customerData.setResidencePhone(resultSet
						.getString("RESIDENCE_PHONE"));
				customerData.setResidenceEmail(resultSet
						.getString("RESIDENCE_EMAIL"));
				customerData.setResidenceCountry(resultSet
						.getString("RESIDENCE_COUNTRY"));

				// 4.Contact
				customerData.setContactAddress(resultSet
						.getString("CONTACT_ADDRESS"));
				customerData
						.setContactCity(resultSet.getString("CONTACT_CITY"));
				customerData.setContactPin(resultSet.getString("CONTACT_PIN"));
				customerData.setContactPhone(resultSet
						.getString("CONTACT_PHONE"));
				customerData.setContactEmail(resultSet
						.getString("CONTACT_EMAIL"));
				customerData.setContactCountry(resultSet
						.getString("CONTACT_COUNTRY"));

				// 5.Preferred
				customerData.setPreferredAddress(resultSet
						.getString("PREFERRED_ADDRESS"));
				customerData.setPreferredCity(resultSet
						.getString("PREFERRED_CITY"));
				customerData.setPreferredPin(resultSet
						.getString("PREFERRED_PIN"));
				customerData.setPreferredPhone(resultSet
						.getString("PREFERRED_PHONE"));
				customerData.setPreferredEmail(resultSet
						.getString("PREFERRED_EMAIL"));
				customerData.setPreferredCountry(resultSet
						.getString("PREFERRED_COUNTRY"));

				// 6.Present
				customerData.setPresentAddress(resultSet
						.getString("PRESENT_ADDRESS"));
				customerData
						.setPresentCity(resultSet.getString("PRESENT_CITY"));
				customerData.setPresentPin(resultSet.getString("PRESENT_PIN"));
				customerData.setPresentPhone(resultSet
						.getString("PRESENT_PHONE"));
				customerData.setPresentEmail(resultSet
						.getString("PRESENT_EMAIL"));
				customerData.setPresentCountry(resultSet
						.getString("PRESENT_COUNTRY"));

				// 7.Temporary
				customerData.setTemporaryAddress(resultSet
						.getString("TEMPORARY_ADDRESS"));
				customerData.setTemporaryCity(resultSet
						.getString("TEMPORARY_CITY"));
				customerData.setTemporaryPin(resultSet
						.getString("TEMPORARY_PIN"));
				customerData.setTemporaryPhone(resultSet
						.getString("TEMPORARY_PHONE"));
				customerData.setTemporaryEmail(resultSet
						.getString("TEMPORARY_EMAIL"));
				customerData.setTemporaryCountry(resultSet
						.getString("TEMPORARY_COUNTRY"));

				// 8.Postal
				customerData.setPostalAddress(resultSet
						.getString("POSTAL_ADDRESS"));
				customerData.setPostalCity(resultSet.getString("POSTAL_CITY"));
				customerData.setPostalPin(resultSet.getString("POSTAL_PIN"));
				customerData
						.setPostalPhone(resultSet.getString("POSTAL_PHONE"));
				customerData
						.setPostalEmail(resultSet.getString("POSTAL_EMAIL"));
				customerData.setPostalCountry(resultSet
						.getString("POSTAL_COUNTRY"));

				// 9.Emergency
				customerData.setEmergencyAddress(resultSet
						.getString("EMERGENCY_ADDRESS"));
				customerData.setEmergencyCity(resultSet
						.getString("EMERGENCY_CITY"));
				customerData.setEmergencyPin(resultSet
						.getString("EMERGENCY_PIN"));
				customerData.setEmergencyPhone(resultSet
						.getString("EMERGENCY_PHONE"));
				customerData.setEmergencyEmail(resultSet
						.getString("EMERGENCY_EMAIL"));

				customerData.setFillerString1(resultSet
						.getString("FILLER_STRING_1"));
				customerData.setFillerString2(resultSet
						.getString("FILLER_STRING_2"));
				customerData.setFillerString3(resultSet
						.getString("FILLER_STRING_3"));
				customerData.setFillerString4(resultSet
						.getString("FILLER_STRING_4"));
				customerData.setFillerString5(resultSet
						.getString("FILLER_STRING_5"));
				customerData.setFillerString6(resultSet
						.getString("FILLER_STRING_6"));
				customerData.setFillerString7(resultSet
						.getString("FILLER_STRING_7"));
				customerData.setFillerString8(resultSet
						.getString("FILLER_STRING_8"));
				customerData.setFillerString9(resultSet
						.getString("FILLER_STRING_9"));

				customerData.setFillerDate1(resultSet.getDate("FILLER_DATE_1"));
				customerData.setFillerDate1(resultSet.getDate("FILLER_DATE_2"));

				customerData.setFillerNumber1(resultSet
						.getLong("FILLER_NUMBER_1"));
				customerData.setFillerNumber2(resultSet
						.getLong("FILLER_NUMBER_2"));
				customerData.setFillerNumber3(resultSet
						.getLong("FILLER_NUMBER_3"));
				customerData.setFillerNumber4(resultSet
						.getLong("FILLER_NUMBER_4"));
				customerData.setFillerNumber5(resultSet
						.getLong("FILLER_NUMBER_5"));

				customerData.setTanNo(resultSet.getString("TAN_NO"));
				customerData
						.setProcessType(resultSet.getString("PROCESS_TYPE"));
				customerData.setApplicationType(resultSet
						.getString("APPLICANT_TYPE"));
				customerData.setEmployerName(resultSet
						.getString("EMPLOYER_NAME"));
				customerData.setAccountNumber(resultSet
						.getString("ACCOUNT_NUMBER"));
				customerData.setCreditCardNumber(resultSet
						.getString("CREDIT_CARD_NUMBER"));

				customerData
						.setProcessFlag(resultSet.getString("PROCESS_FLAG"));
				customerData.setSourceSystemId(resultSet
						.getString("SOURCE_SYS_ID"));
				customerData.setDataSourceName(resultSet
						.getString("SOURCE_SYSTEM"));
				customerData.setDirectorIdentitficationNo(resultSet
						.getString("DIN"));
				customerData.setCompanyIdentificationNo(resultSet
						.getString("CIN"));
				customerData.setRegistrationNumber(resultSet
						.getString("REGISTRATION_NO"));
				customerData.setCustomerNo(resultSet.getString("CUSTOMER_NO"));
				customerData.setCustomerId(resultSet.getString("CUSTOMER_ID"));
				customerData.setSegment(resultSet.getString("SEGMENT"));

				customerData.setCaNumber(resultSet.getString("CA_NUMBER"));

				customerData.setCustUnqId(resultSet.getString("CUST_UNQ_ID"));
				customerData.setDuiFlag(resultSet.getString("DUIFLAG"));

				customerData.setContact_std_code(resultSet
						.getString("CONTACT_STD_CODE"));
				customerData.setEmergency_std_code(resultSet
						.getString("EMERGENCY_STD_CODE"));
				customerData.setOffice_std_code(resultSet
						.getString("OFFICE_STD_CODE"));
				customerData.setPermanent_std_code(resultSet
						.getString("PERMANENT_STD_CODE"));
				customerData.setPostal_std_code(resultSet
						.getString("POSTAL_STD_CODE"));
				customerData.setPreferred_std_code(resultSet
						.getString("PREFERRED_STD_CODE"));
				customerData.setPresent_std_code(resultSet
						.getString("PRESENT_STD_CODE"));
				customerData.setResidence_std_code(resultSet
						.getString("RESIDENCE_STD_CODE"));
				customerData.setTemporary_std_code(resultSet
						.getString("TEMPORARY_STD_CODE"));

				customerData.setContact_address_type(resultSet
						.getString("CONTACT_ADDRESS_TYPE"));
				customerData.setEmergency_address_type(resultSet
						.getString("EMERGENCY_ADDRESS_TYPE"));
				customerData.setOffice_address_type(resultSet
						.getString("OFFICE_ADDRESS_TYPE"));
				customerData.setPermanent_address_type(resultSet
						.getString("PERMANENT_ADDRESS_TYPE"));
				customerData.setPostal_address_type(resultSet
						.getString("POSTAL_ADDRESS_TYPE"));
				customerData.setPreferred_address_type(resultSet
						.getString("PREFERRED_ADDRESS_TYPE"));
				customerData.setPresent_address_type(resultSet
						.getString("PRESENT_ADDRESS_TYPE"));
				customerData.setResidence_address_type(resultSet
						.getString("RESIDENCE_ADDRESS_TYPE"));
				customerData.setTemporary_address_type(resultSet
						.getString("TEMPORARY_ADDRESS_TYPE"));
				// added data
				customerData.setCin(resultSet.getString("CIN"));
				customerData.setDin(resultSet.getString("DIN"));
				customerData.setRegistrationNumber(resultSet
						.getString("REGISTRATION_NO"));
				customerData.setSourcesystem(resultSet
						.getString("SOURCE_SYSTEM"));
				customerData.setSourceSystemId(resultSet
						.getString("SOURCE_SYS_ID"));
				customerData.setPhone(resultSet.getString("PHONE"));
				customerData.setCustomerId(resultSet.getString("CUSTOMER_ID"));
				customerData.setDoi(resultSet.getDate("DOI"));
				customerData.setAadharNo(resultSet.getString("AADHAAR"));
				customerData.setGender(resultSet.getString("GENDER"));
				customerData.setPresentAddress(resultSet
						.getString("PRESENT_ADDRESS"));
				customerData
						.setPresentCity(resultSet.getString("PRESENT_CITY"));
				customerData.setPresentCountry(resultSet
						.getString("PRESENT_COUNTRY"));
				customerData.setPresentEmail(resultSet
						.getString("PRESENT_EMAIL"));
				customerData.setPresentPhone(resultSet
						.getString("PRESENT_PHONE"));
				customerData.setPresentPin(resultSet.getString("PRESENT_PIN"));
				customerData.setPresentState(resultSet
						.getString("PRESENT_STATE"));
				customerData.setPsxid(resultSet.getString("PSX_ID"));
				customerData.setFillerDate1(resultSet.getDate("FILLER_DATE_1"));
				customerData.setFillerDate2(resultSet.getDate("FILLER_DATE_2"));
				customerData.setBatchid(resultSet.getString("BATCHID"));
				customerData.setMobileno(resultSet.getString("MOBILE"));
				customerData.setFirstName(resultSet.getString("FIRSTNAME"));
				customerData.setLastName(resultSet.getString("LASTNAME"));
				customerData.setMiddleName(resultSet.getString("MIDDLENAME"));
				customerData.setDay(resultSet.getString("DAY"));
				customerData.setMonth(resultSet.getString("MONTH"));
				customerData.setYear(resultSet.getString("YEAR"));

				customerData.setRemarks(resultSet.getString("REMARKS"));
				customersList.add(customerData);

			} catch (SQLException e) {
				e.printStackTrace();
				updateNspEventDetails(conn, eodBatchId, "STAGE_1",
						"BATCH PICKED FOR PROCESSING", "E", 0);
				logger.error("Exception Occurred while fetching the data :::"
						+ e.getMessage());
				logger.info("inside into ERROR_RECORDS_INFO_T");
				java.sql.Timestamp date = new java.sql.Timestamp(
						new java.util.Date().getTime());
				String dateformat = (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"))
						.format(date);

				ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
				exceptionRecordsInfo.setEodBatchId(eodBatchId);
				exceptionRecordsInfo.setErrorInfo(e.getMessage());
				exceptionRecordsInfoList.add(exceptionRecordsInfo);
				throw e;
			}

		}
		logger.info("List Size is:::::::::::::: " + customersList.size());

		if (customersList.size() > 0) {
			updateNspEventDetails(conn, eodBatchId, "STAGE_1",
					"BATCH PICKED FOR PROCESSING", "C", 0);
		}
		return customersList;
	}

	private static HashMap<String, List<Customer>> buildEachNodesCustomersMap(
			List<Customer> customers, int nodesCount) {
		HashMap<String, List<Customer>> NodeWiseCustomersListMap = new HashMap<String, List<Customer>>();
		String custUnqId;
		int asciiValue;
		List<Customer> nodeOneCustomers = new ArrayList<Customer>();
		List<Customer> nodeTwoCustomers = new ArrayList<Customer>();
		if (customers.size() > 0) {
			for (Customer customer : customers) {
				asciiValue = 0;
				custUnqId = customer.getCustUnqId();

				if (custUnqId != null && !custUnqId.isEmpty()) {
					for (int i = 0; i < custUnqId.length(); i++) {
						asciiValue += custUnqId.charAt(i);
					}
				}
				if (asciiValue % nodesCount == 0) {
					nodeOneCustomers.add(customer);
				}
				if (asciiValue % nodesCount == 1) {
					nodeTwoCustomers.add(customer);
				}
			}
			logger.info("nodeOneCustomers:::" + nodeOneCustomers.size());
			logger.info("nodeTwoCustomers:::" + nodeTwoCustomers.size());
			NodeWiseCustomersListMap.put("1", nodeOneCustomers);
			NodeWiseCustomersListMap.put("2", nodeTwoCustomers);
		}
		return NodeWiseCustomersListMap;
	}

	private static void insertDataIntoIncrementalStaging(Connection conn,
			List<Customer> customers, String stagingTable, String eodBatchId)
			throws Exception {
		logger.info("Target table is " + stagingTable + " BatchId "
				+ eodBatchId + "List Size " + customers.size());

		PreparedStatement pstmt = null;

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
		java.sql.Timestamp date = new java.sql.Timestamp(
				new java.util.Date().getTime());
		String dateformat = (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"))
				.format(date);
		int inst1Cnt = 1;
		try {
			String insertQuery = "insert into "
					+ stagingTable
					+ "(PROCESS_FLAG,ERROR_CODE,ERROR_DESC,CUSTOMER_NO,SEGMENT,DOB1,PAN,DRIVINGLIC,VOTERID,DATE_OF_INCORPORATION,TAN_NO,"
					+ "PROCESS_TYPE,APPLICANT_TYPE,EMPLOYER_NAME,FATHER_NAME,PASSPORT,ACCOUNT_NUMBER,CREDIT_CARD_NUMBER,PERMANENT_ADDRESS,"
					+ "PERMANENT_CITY,PERMANENT_PIN,PERMANENT_EMAIL,PERMANENT_PHONE,TEMPORARY_ADDRESS,TEMPORARY_CITY,TEMPORARY_PIN,"
					+ "TEMPORARY_EMAIL,TEMPORARY_PHONE,OFFICE_ADDRESS,OFFICE_CITY,OFFICE_PIN,OFFICE_EMAIL,OFFICE_PHONE,EMERGENCY_ADDRESS,"
					+ "EMERGENCY_CITY,EMERGENCY_PIN,EMERGENCY_EMAIL,EMERGENCY_PHONE,CONTACT_ADDRESS,CONTACT_CITY,CONTACT_PIN,CONTACT_EMAIL,"
					+ "CONTACT_PHONE,POSTAL_ADDRESS,POSTAL_CITY,POSTAL_PIN,POSTAL_EMAIL,POSTAL_PHONE,RESIDENCE_ADDRESS,RESIDENCE_CITY,"
					+ "RESIDENCE_PIN,RESIDENCE_EMAIL,RESIDENCE_PHONE,PREFERRED_ADDRESS,PREFERRED_CITY,PREFERRED_PIN,PREFERRED_EMAIL,"
					+ "PREFERRED_PHONE,CUST_UNQ_ID,PSX_ID,PSX_BATCH_ID,EVENTTYPE,LCHGTIME,SOURCE_SYS_ID,CONTACT_STD_CODE,EMERGENCY_STD_CODE,OFFICE_STD_CODE,PERMANENT_STD_CODE,POSTAL_STD_CODE,PREFERRED_STD_CODE,PRESENT_STD_CODE,RESIDENCE_STD_CODE,TEMPORARY_STD_CODE,CONTACT_ADDRESS_TYPE,EMERGENCY_ADDRESS_TYPE,OFFICE_ADDRESS_TYPE,PERMANENT_ADDRESS_TYPE,POSTAL_ADDRESS_TYPE,PREFERRED_ADDRESS_TYPE,PRESENT_ADDRESS_TYPE,RESIDENCE_ADDRESS_TYPE,TEMPORARY_ADDRESS_TYPE,NAME,INSERT_TIME,CIN,DIN,REGISTRATION_NO,CUSTOMER_ID,FILLER_STRING_1,FILLER_STRING_2,FILLER_STRING_3,FILLER_STRING_4,FILLER_STRING_5,FILLER_STRING_6,FILLER_STRING_7,FILLER_STRING_8,FILLER_STRING_9,FILLER_DATE_1,FILLER_DATE_2,FILLER_NUMBER_1,FILLER_NUMBER_2,FILLER_NUMBER_3,FILLER_NUMBER_4,FILLER_NUMBER_5,DOI,AADHAAR,GENDER,PRESENT_ADDRESS,PRESENT_CITY,PRESENT_COUNTRY,PRESENT_EMAIL,PRESENT_PHONE,PRESENT_PIN,PRESENT_STATE,RATIONCARD_NO,SOURCE_SYSTEM,PHONE,BATCHID,MOBILE,DAY,MONTH,YEAR,FIRSTNAME,MIDDLENAME,LASTNAME,DUIFLAG)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'INCREMENTAL',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// +
			// "PREFERRED_PHONE,CUST_UNQ_ID,PSX_ID,PSX_BATCH_ID,EVENTTYPE,LCHGTIME,SOURCE_SYS_ID,CONTACT_STD_CODE,EMERGENCY_STD_CODE,OFFICE_STD_CODE,PERMANENT_STD_CODE,POSTAL_STD_CODE,PREFERRED_STD_CODE,PRESENT_STD_CODE,RESIDENCE_STD_CODE,TEMPORARY_STD_CODE,CONTACT_ADDRESS_TYPE,EMERGENCY_ADDRESS_TYPE,OFFICE_ADDRESS_TYPE,PERMANENT_ADDRESS_TYPE,POSTAL_ADDRESS_TYPE,PREFERRED_ADDRESS_TYPE,PRESENT_ADDRESS_TYPE,RESIDENCE_ADDRESS_TYPE,TEMPORARY_ADDRESS_TYPE,NAME,INSERT_TIME,CIN,DIN,REGISTRATION_NO,CUSTOMER_ID,FILLER_STRING_1,FILLER_STRING_2,FILLER_STRING_3,FILLER_STRING_4,FILLER_STRING_5,FILLER_STRING_6,FILLER_STRING_7,FILLER_STRING_8,FILLER_STRING_9,FILLER_DATE_1,FILLER_DATE_2,FILLER_NUMBER_1,FILLER_NUMBER_2,FILLER_NUMBER_3,FILLER_NUMBER_4,FILLER_NUMBER_5,DOI,AADHAAR,GENDER,PRESENT_ADDRESS,PRESENT_CITY,PRESENT_COUNTRY,PRESENT_EMAIL,PRESENT_PHONE,PRESENT_PIN,PRESENT_STATE,RATIONCARD_NO,SOURCE_SYSTEM,PHONE,BATCHID,MOBILE,DAY,MONTH,YEAR,FIRSTNAME,MIDDLENAME,LASTNAME,DUIFLAG)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'INCREMENTAL',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'dd-MM-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(insertQuery);
			for (Customer customer : customers) {
				try {

					pstmt.setString(1, customer.getProcessFlag());
					pstmt.setString(2, customer.getErrorCode());
					pstmt.setString(3, customer.getErrorDescription());
					pstmt.setString(4, customer.getCustomerNo());
					pstmt.setString(5, customer.getSegment());
					pstmt.setDate(6, customer.getDob());
					pstmt.setString(7, customer.getPan());
					pstmt.setString(8, customer.getDrivingLicense());
					pstmt.setString(9, customer.getVoterId());
					pstmt.setDate(10, customer.getDateOfIncorporation());
					pstmt.setString(11, customer.getTanNo());
					pstmt.setString(12, customer.getProcessType());
					pstmt.setString(13, customer.getApplicationType());
					pstmt.setString(14, customer.getEmployerName());
					pstmt.setString(15, customer.getFatherName());
					pstmt.setString(16, customer.getPassportNo());
					pstmt.setString(17, customer.getAccountNumber());
					pstmt.setString(18, customer.getCreditCardNumber());
					pstmt.setString(19, customer.getPermanentAddress());
					pstmt.setString(20, customer.getPermanentCity());
					pstmt.setString(21, customer.getPermanentPin());
					pstmt.setString(22, customer.getPermanentEmail());
					pstmt.setString(23, customer.getPermanentPhone());
					pstmt.setString(24, customer.getTemporaryAddress());
					pstmt.setString(25, customer.getTemporaryCity());
					pstmt.setString(26, customer.getTemporaryPin());
					pstmt.setString(27, customer.getTemporaryEmail());
					pstmt.setString(28, customer.getTemporaryPhone());
					pstmt.setString(29, customer.getOfficeAddress());
					pstmt.setString(30, customer.getOfficeCity());
					pstmt.setString(31, customer.getOfficePin());
					pstmt.setString(32, customer.getOfficeEmail());
					pstmt.setString(33, customer.getOfficePhone());
					pstmt.setString(34, customer.getEmergencyAddress());
					pstmt.setString(35, customer.getEmergencyCity());
					pstmt.setString(36, customer.getEmergencyPin());
					pstmt.setString(37, customer.getEmergencyEmail());
					pstmt.setString(38, customer.getEmergencyPhone());
					pstmt.setString(39, customer.getContactAddress());
					pstmt.setString(40, customer.getContactCity());
					pstmt.setString(41, customer.getContactPin());
					pstmt.setString(42, customer.getContactEmail());
					pstmt.setString(43, customer.getContactPhone());
					pstmt.setString(44, customer.getPostalAddress());
					pstmt.setString(45, customer.getPostalCity());
					pstmt.setString(46, customer.getPostalPin());
					pstmt.setString(47, customer.getPostalEmail());
					pstmt.setString(48, customer.getPostalPhone());
					pstmt.setString(49, customer.getResidenceAddress());
					pstmt.setString(50, customer.getResidenceCity());
					pstmt.setString(51, customer.getResidencePin());
					pstmt.setString(52, customer.getResidenceEmail());
					pstmt.setString(53, customer.getResidencePhone());
					pstmt.setString(54, customer.getPreferredAddress());
					pstmt.setString(55, customer.getPreferredCity());
					pstmt.setString(56, customer.getPreferredPin());
					pstmt.setString(57, customer.getPreferredEmail());
					pstmt.setString(58, customer.getPreferredPhone());
					pstmt.setString(59, customer.getCustUnqId());

					if (customer.getPsxid() == null
							|| customer.getPsxid().trim()
									.equalsIgnoreCase("null")
							|| customer.getPsxid().equals(" ")
							|| customer.getPsxid().isEmpty()) {
						customer.setPsxid("0");
						pstmt.setString(60, customer.getPsxid());

					} else {
						pstmt.setString(60, customer.getPsxid());
					}
					pstmt.setString(61, eodBatchId);
					/*
					 * pstmt.setObject(62, new Timestamp(new SimpleDateFormat(
					 * "dd-MM-yyyy HH:mm:ss").parse(dateformat).getTime()));
					 */
					if (customer.getLchgtime() != null) {
						pstmt.setObject(62, customer.getLchgtime());

					} else {
						// pstmt.setObject(62, new Timestamp(new
						// SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateformat).getTime()));
						// pstmt.setObject(62, new
						// SimpleDateFormat("dd-MM-yyyy HH:mm:ss SSS").format(System.currentTimeMillis()));

						pstmt.setObject(
								62,
								new java.sql.Timestamp((System
										.currentTimeMillis())));
						System.out.println("LCHGTIME ::: "
								+ new java.sql.Timestamp((System
										.currentTimeMillis())));

						// pstmt.setObject(62, new
						// java.sql.Timestamp((System.nanoTime())));
					}
					pstmt.setString(63, customer.getSourceSystemId());
					pstmt.setString(64, customer.getContact_std_code());
					pstmt.setString(65, customer.getEmergency_std_code());
					pstmt.setString(66, customer.getOffice_std_code());
					pstmt.setString(67, customer.getPermanent_std_code());
					pstmt.setString(68, customer.getPostal_std_code());
					pstmt.setString(69, customer.getPreferred_std_code());
					pstmt.setString(70, customer.getPresent_std_code());
					pstmt.setString(71, customer.getResidence_std_code());
					pstmt.setString(72, customer.getTemporary_std_code());
					pstmt.setString(73, customer.getContact_address_type());
					pstmt.setString(74, customer.getEmergency__address_type());
					pstmt.setString(75, customer.getOffice_address_type());
					pstmt.setString(76, customer.getPermanent_address_type());
					pstmt.setString(77, customer.getPostal_address_type());
					pstmt.setString(78, customer.getPreferred_address_type());
					pstmt.setString(79, customer.getPresent_address_type());
					pstmt.setString(80, customer.getResidence_address_type());
					pstmt.setString(81, customer.getTemporary_address_type());
					pstmt.setString(82, customer.getName());
					// pstmt.setObject(83, new Timestamp(new
					// SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateformat).getTime()));

					if (customer.getInsertTime() != null) {
						pstmt.setTimestamp(83, customer.getInsertTime());

					} else {
						// pstmt.setObject(83, new
						// SimpleDateFormat("dd-MM-yyyy HH:mm:ss S").format(System.currentTimeMillis()));
						pstmt.setTimestamp(
								83,
								new java.sql.Timestamp((System
										.currentTimeMillis())));

					}
					pstmt.setString(84, customer.getCin());
					pstmt.setString(85, customer.getDin());
					pstmt.setString(86, customer.getRegistrationNumber());
					pstmt.setString(87, customer.getCustomerId());
					pstmt.setString(88, customer.getFillerString1());
					pstmt.setString(89, customer.getFillerString2());
					pstmt.setString(90, customer.getFillerString3());
					pstmt.setString(91, customer.getFillerString4());
					pstmt.setString(92, customer.getFillerString5());
					pstmt.setString(93, customer.getFillerString6());

					/*
					 * Setting remarks value instead of FillerString7 as per the
					 * client request on 25-11-2019
					 */
					// pstmt.setString(94, customer.getFillerString7());
					pstmt.setString(94, customer.getRemarks());

					pstmt.setString(95, customer.getFillerString8());
					pstmt.setString(96, customer.getFillerString9());
					pstmt.setDate(97, customer.getFillerDate1());
					pstmt.setDate(98, customer.getFillerDate2());
					pstmt.setLong(99, customer.getFillerNumber1());
					pstmt.setLong(100, customer.getFillerNumber2());
					pstmt.setLong(101, customer.getFillerNumber3());
					pstmt.setLong(102, customer.getFillerNumber4());
					pstmt.setLong(103, customer.getFillerNumber5());
					pstmt.setDate(104, customer.getDoi());
					pstmt.setString(105, customer.getAadharNo());
					pstmt.setString(106, customer.getGender());
					pstmt.setString(107, customer.getPresentAddress());
					pstmt.setString(108, customer.getPresentCity());
					pstmt.setString(109, customer.getPresentCountry());
					pstmt.setString(110, customer.getPresentEmail());
					pstmt.setString(111, customer.getPresentPhone());
					pstmt.setString(112, customer.getPresentPin());
					pstmt.setString(113, customer.getPresentState());
					pstmt.setString(114, customer.getRationcardnumber());
					pstmt.setString(115, customer.getSourcesystem());
					pstmt.setString(116, customer.getPhone());
					pstmt.setString(117, customer.getBatchid());
					pstmt.setString(118, customer.getMobileno());
					pstmt.setString(119, customer.getDay());
					pstmt.setString(120, customer.getMonth());
					pstmt.setString(121, customer.getYear());
					pstmt.setString(122, customer.getFirstName());
					pstmt.setString(123, customer.getMiddleName());
					pstmt.setString(124, customer.getLastName());
					pstmt.setString(125, customer.getDuiFlag());
					pstmt.addBatch();
					if (inst1Cnt
							% Integer.parseInt(props
									.getProperty("MaxBatchLimit")) == 0) {
						pstmt.executeBatch();
						pstmt.clearBatch();
						conn.commit();
						logger.info("Batch executed Successfully for"
								+ stagingTable + " Records inserted");
					}
					inst1Cnt++;
				}

				catch (SQLException e) {
					e.printStackTrace();
					logger.error("Exception Occurred while inserting Data into Nodes :"
							+ e.getMessage());
					logger.info("inside into ERROR_RECORDS_INFO_T");
					String sql = "insert into ERROR_RECORDS_INFO_T"
							+ "(PSX_BATCH_ID,RECORD,ERROR_DESCRIPTION,INSERT_TS) "
							+ "values(?,?,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, customer.getBatchid());
					pstmt.setString(2, customer.toString());
					pstmt.setString(3, e.getMessage());
					pstmt.setObject(4, new Timestamp(new SimpleDateFormat(
							"dd-MM-yyyy HH:mm:ss").parse(dateformat).getTime()));
					logger.info("Record Inserted into ERROR_RECORDS_INFO_T");
					pstmt.close();
					throw e;
				} /*
				 * catch (ParseException e) { e.printStackTrace(); logger.error(
				 * "Exception Occurred while inserting Data into Nodes :" +
				 * e.getMessage());
				 * logger.info("inside into ERROR_RECORDS_INFO_T"); String sql =
				 * "insert into ERROR_RECORDS_INFO_T" +
				 * "(PSX_BATCH_ID,RECORD,ERROR_DESCRIPTION,INSERT_TS) " +
				 * "values(?,?,?,?)"; pstmt = conn.prepareStatement(sql);
				 * pstmt.setString(1, customer.getBatchid()); pstmt.setString(2,
				 * customer.toString()); pstmt.setString(3, e.getMessage());
				 * pstmt.setObject(4, new Timestamp(new SimpleDateFormat(
				 * "dd-MM-yyyy HH:mm:ss").parse(dateformat).getTime()));
				 * logger.info("Record Inserted into ERROR_RECORDS_INFO_T");
				 * pstmt.close(); throw e; }
				 */
			}
			pstmt.executeBatch();
			pstmt.clearBatch();
			conn.commit();
			pstmt.close();
			logger.info("Batch executed Successfully for" + stagingTable);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception Occurred while inserting Data into Nodes :"
					+ e.getMessage());
			ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
			exceptionRecordsInfo.setEodBatchId(eodBatchId);
			exceptionRecordsInfo.setErrorInfo(e.getMessage());
			exceptionRecordsInfoList.add(exceptionRecordsInfo);
			updateNspEventDetails(conn, eodBatchId, "STAGE_2",
					"DATA SEGGREGATED TO RESPECTIVE NODES", "E", 0);
			throw e;
		}
	}

	public static long appNextVal(Connection con) throws SQLException {
		logger.info("In appNextVal");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select appnextval('crms_req_psx_id') from dual";
		long reqPsxid = 0;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				reqPsxid = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Exception wfile getting the Sequence "
					+ e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error("Exception wfile getting the Sequence "
					+ e.getMessage());
		} finally {
			rs.close();
			pstmt.close();
		}
		return reqPsxid;
	}

	public static void sendIncrementalMessageToEngine1(Connection conn,
			String eodBatchId, String queueName) throws Exception {

		try {
			JSONObject json = new JSONObject();
			json.put("psxBatchID", eodBatchId);
			json.put("processType", "incrementalProcess");
			json.put("sourceSystemName", "PRIME360");
			String current_timeStamp = System.currentTimeMillis() + "";
			json.put("EODTimeStamp", current_timeStamp);
			logger.info("Incremental message::" + json.toString());
			final String message = json.toString();
			logger.info("MESSAGE :::" + message);

			jmsTemplate.setExplicitQosEnabled(true);
			jmsTemplate.setPriority(0);
			jmsTemplate.send(queueName, new MessageCreator() {

				public Message createMessage(Session session)
						throws JMSException {
					TextMessage textMessage = null;
					textMessage = session.createTextMessage(message);
					textMessage.setJMSPriority(0);
					return textMessage;
				}
			});
			updateNspEventDetails(conn, eodBatchId, "STAGE_3",
					"REQUEST SENT FOR INCREMENTAL PROCESSING", "C", 1);
		} catch (Exception e) {
			errorStatus = 1;
			// updateNspEventDetails(conn, eodBatchId,
			// "STAGE_3","REQUEST SENT FOR INCREMENTAL PROCESSING", "E", 1);
			throw e;
		}

	}

	public static void sendIncrementalMessageToEngine2(Connection conn,
			String eodBatchId, String queueName) throws Exception {

		try {
			JSONObject json = new JSONObject();
			json.put("psxBatchID", eodBatchId);
			json.put("processType", "incrementalProcess");
			json.put("sourceSystemName", "PRIME360");
			String current_timeStamp = System.currentTimeMillis() + "";
			json.put("EODTimeStamp", current_timeStamp);
			logger.info("Incremental message::" + json.toString());
			final String message = json.toString();
			logger.info("MESSAGE :::" + message);
			jmsTemplate.setExplicitQosEnabled(true);
			jmsTemplate.setPriority(0);
			jmsTemplate.send(queueName, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					TextMessage textMessage = null;
					textMessage = session.createTextMessage(message);
					textMessage.setJMSPriority(0);
					return textMessage;
				}
			});
			updateNspEventDetails(conn, eodBatchId, "STAGE_3",
					"REQUEST SENT FOR INCREMENTAL PROCESSING", "C", 2);
		} catch (Exception e) {
			errorStatus = 1;
			// updateNspEventDetails(conn, eodBatchId,
			// "STAGE_3","REQUEST SENT FOR INCREMENTAL PROCESSING", "E", 2);
			throw e;
		}

	}

//	public static void sendPurgingMessageToEngine(String eodBatchId,
//			String queueName, String queue2) throws JSONException {

		public static void sendPurgingMessageToEngine(String queueName, String queue2) throws JSONException {
		JSONObject json = new JSONObject();

		json.put("psxBatchID", "100");
		json.put("processType", "PurgeData");
		json.put("sourceSystemName", "PRIME360");
		json.put("probingComponent", "BOTH");
		logger.info("Purging message::" + json.toString());
		final String message = json.toString();
		logger.info("MESSAGE :::" + message);
		jmsTemplate.setExplicitQosEnabled(true);
		jmsTemplate.setPriority(0);
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = null;
				textMessage = session.createTextMessage(message);
				textMessage.setJMSPriority(0);
				return textMessage;
			}
		});

		jmsTemplate.send(queue2, new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = null;
				textMessage = session.createTextMessage(message);
				textMessage.setJMSPriority(0);
				return textMessage;
			}
		});

	}

	public void sendIntraday1MessageToEngine(String custUnqID,
			HashMap<String, ArrayList<HashMap<String, Object>>> entry,
			String queueName) throws JSONException {

		JSONObject json = new JSONObject();

		json.put("custUnqID", custUnqID);
		json.put("requestInformation", entry);
		json.put("sourceSystemName", "PRIME360");
		json.put("processType", "intraDayProcess");

		final String message = json.toString();
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = null;
				textMessage = session.createTextMessage(message);
				return textMessage;
			}
		});

	}

	public void sendOnlineRebuildMessageToEngine(String queueName)
			throws JSONException {

		JSONObject json = new JSONObject();

		json.put("processType", "onlineRebuild");
		json.put("sourceSystemName", "PRIME360");

		final String message = json.toString();
		jmsTemplate.setExplicitQosEnabled(true);
		jmsTemplate.setPriority(0);
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = null;
				textMessage = session.createTextMessage(message);
				textMessage.setJMSPriority(0);
				return textMessage;
			}
		});

	}

	public void sendIntraday2MessageToEngine(String custUnqID,
			HashMap<String, ArrayList<HashMap<String, Object>>> entry,
			String queueName) throws JSONException {

		JSONObject json = new JSONObject();

		json.put("custUnqID", custUnqID);
		json.put("requestInformation", entry);
		json.put("sourceSystemName", "PRIME360");
		json.put("processType", "intraDayProcess");

		final String message = json.toString();
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = null;
				textMessage = session.createTextMessage(message);
				return textMessage;
			}
		});

	}

	public void buildAndSendMicrobatchingMessageToEngine(String queueName)
			throws JSONException, JMSException {
		JSONObject json = new JSONObject();
		String processType = "microBatchProcessing";
		json.put("psxBatchID", 525);
		json.put("processType", processType);
		json.put("sourceSystemName", "PRIME360");

		logger.info("Microbatching message::" + json.toString());
		final String message = json.toString();
		// jmsTemplate.setExplicitQosEnabled(true);
		// jmsTemplate.setPriority(0);
		// System.out.println("::: Setting TimeToLive ::: ");
		// jmsTemplate.setTimeToLive(60000);
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = null;
				textMessage = session.createTextMessage(message);

				return textMessage;
			}
		});
		logger.info("Message has been sent succesfully to EventQueue:");
	}

	public void segregateCustomerData(Connection connection, Properties props,
			String eodBatchId) throws SQLException {
		String dataCount = null;
		logger.debug("In segregateCustomerData..");
		List<PsxDataSourceMSTDTO> dataSourceList = new ArrayList<PsxDataSourceMSTDTO>();
		HashMap<String, Long> dataSourceMap = new HashMap<String, Long>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmtBatch = null;
		long capValue = 0;
		long fraudDataCount = 0;
		int[] updatedRows = null;

		try {
			pstmt = connection.prepareStatement(props
					.getProperty("Cap_fetch_query"));
			rs = pstmt.executeQuery();
			if (rs.next())
				capValue = rs.getLong(1);
			logger.info("Cap value for Eod Fetched is::" + capValue);
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();

			pstmt = connection.prepareStatement(props.getProperty(
					"fraud_data_count").replace("<<psxbatchid>>", eodBatchId));
			rs = pstmt.executeQuery();
			if (rs.next())
				fraudDataCount = rs.getLong(1);

			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
			pstmt = connection.prepareStatement(props
					.getProperty("DataSource_MST_query"));
			rs = pstmt.executeQuery();
			dataCount = "fraudDataCount::" + fraudDataCount;
			logger.info("fraudDataCount::" + fraudDataCount);
			if (fraudDataCount < capValue) {
				capValue = capValue - fraudDataCount;
				while (rs.next()) {
					PsxDataSourceMSTDTO psxDataSourceTemp = new PsxDataSourceMSTDTO();
					psxDataSourceTemp.setSOURCE_SYS_ID(rs
							.getString("SOURCE_SYS_ID"));
					psxDataSourceTemp.setSOURCE_NAME(rs
							.getString("SOURCE_NAME"));
					psxDataSourceTemp.setALLOCATE_UCIC(rs
							.getString("ALLOCATE_UCIC"));
					psxDataSourceTemp.setDAYS_TO_RETAIN(rs
							.getInt("DAYS_TO_RETAIN"));
					psxDataSourceTemp.setPRIORITY(rs.getInt("PRIORITY"));
					dataSourceList.add(psxDataSourceTemp);
				}
				logger.info("Number of dataSources Fetched from MST::"
						+ dataSourceList.size());
				logger.info("DataSources Fetched from MST::" + dataSourceList);
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				pstmt = connection.prepareStatement(props
						.getProperty("DataSource_data_count_query"));
				rs = pstmt.executeQuery();
				while (rs.next()) {
					dataSourceMap.put(rs.getString("SOURCE_SYSTEM"),
							rs.getLong("CNT"));
				}
				logger.info("DataSource count Map::" + dataSourceMap);
				for (int i = 0; i < dataSourceList.size() && capValue > 0; i++) {
					int updateCount = 0;
					logger.info("dataSourceList.get(i).getSOURCE_NAME()::"
							+ dataSourceList.get(i).getSOURCE_NAME()
							+ "...capValue...." + capValue);
					if (dataSourceMap.containsKey(dataSourceList.get(i)
							.getSOURCE_NAME())) {
						if (dataSourceMap.get(dataSourceList.get(i)
								.getSOURCE_NAME()) <= capValue) {
							logger.debug("In if dataSourceMap.get(dataSourceList.get(i).getSOURCE_NAME()::"
									+ dataSourceMap.get(dataSourceList.get(i)
											.getSOURCE_NAME())
									+ " capValue::"
									+ capValue);
							if (pstmt != null)
								pstmt.close();
							pstmt = connection
									.prepareStatement("UPDATE PSX_COMMON_STAGING_T SET EOD_BATCH_ID=?  WHERE UPPER(SOURCE_SYSTEM)=? AND EOD_BATCH_ID IS NULL");
							pstmt.setString(1, eodBatchId);
							pstmt.setString(2, dataSourceList.get(i)
									.getSOURCE_NAME());
							updateCount = pstmt.executeUpdate();
						} else {
							logger.debug("In Else dataSourceMap.get(dataSourceList.get(i).getSOURCE_NAME()::"
									+ dataSourceMap.get(dataSourceList.get(i)
											.getSOURCE_NAME())
									+ " capValue::"
									+ capValue);
							String queryTemp = null;
							String batchId = eodBatchId;
							pstmtBatch = connection
									.prepareStatement("UPDATE PSX_COMMON_STAGING_T SET EOD_BATCH_ID=? WHERE UPPER(SOURCE_SYSTEM)=? AND EOD_BATCH_ID IS NULL and CUSTOMER_NO=? and SOURCE_SYS_ID=?");
							if (pstmt != null)
								pstmt.close();
							queryTemp = props.getProperty(
									"Select_DataSource_Data_Query").replace(
									"<<datasource>>",
									dataSourceList.get(i).getSOURCE_NAME());
							logger.debug("Query to Select Data for Marking::"
									+ queryTemp);
							pstmt = connection.prepareStatement(queryTemp);
							if (rs != null)
								rs.close();
							rs = pstmt.executeQuery();
							int tempCounter = 0;
							while (rs.next()) {
								if (tempCounter < capValue) {
									pstmtBatch.setString(1, batchId);
									pstmtBatch.setString(2,
											dataSourceList.get(i)
													.getSOURCE_NAME());
									// pstmtBatch.setInt(3,rs.getInt("CUSTOMER_NO"));
									pstmtBatch.setLong(3,
											rs.getLong("CUSTOMER_NO"));
									pstmtBatch.setString(4,
											rs.getString("SOURCE_SYS_ID"));
									pstmtBatch.addBatch();
									tempCounter++;
								} else {
									break;
								}
							}
							if (tempCounter > 0) {
								updatedRows = pstmtBatch.executeBatch();
								updateCount = tempCounter;

							}
						}
					} else {
						logger.info("No Data Present for DataSource::"
								+ dataSourceList.get(i).getSOURCE_NAME());
					}
					dataCount = dataCount + ", "
							+ dataSourceList.get(i).getSOURCE_NAME() + "::"
							+ updateCount;
					capValue = capValue - updateCount;
				}
			} else {
				logger.info("Fraud Data Count::" + fraudDataCount
						+ " More than or equal to Cap value::" + capValue);
			}
			int t = 0;
			if (pstmt != null)
				pstmt.close();
			pstmt = connection.prepareStatement(props
					.getProperty("ControlTable_Insert_Query"));
			logger.debug("Query for Control table Insert is::"
					+ props.getProperty("ControlTable_Insert_Query"));
			pstmt.setString(1, eodBatchId);
			pstmt.setString(2, eodBatchId);
			pstmt.setString(3, dataCount);
			t = pstmt.executeUpdate();
			logger.debug("Insert into Control Table done Sucessfully::" + t);

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error occured while segregating CustomerData::"
					+ e.getMessage());
			ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
			exceptionRecordsInfo.setEodBatchId(eodBatchId);
			exceptionRecordsInfo.setErrorInfo(e.getMessage());
			exceptionRecordsInfoList.add(exceptionRecordsInfo);
			logger.error(e, e);
			throw e;
		} finally {
			try {
				closeDBHandles(null, pstmt, rs);
				closeDBHandles(null, pstmtBatch, null);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("Error while Closing DB Handles::" + e.getMessage());
			}
		}

	}

	public void closeDBHandles(Connection conn, PreparedStatement pstmt,
			ResultSet rs) throws SQLException {
		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("Error in closeDBHandles::" + e.getMessage());
			throw e;
		}
	}

	public void preparePurgeData(Connection connection, Properties configProps,
			String eodBatchId) throws SQLException, ParseException,
			JSONException {
		logger.info("In prepareCommonStagingPurgeData ...");

		PreparedStatement datasourcePstmt = null;
		ResultSet datasourceRs = null;

		PreparedStatement purgeDataPstmt = null;
		ResultSet purgeDataRs = null;

		PreparedStatement insertDataPstmt = null;

		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		nodeOneQueue = (String) props.get("nodeOneQueue");
		nodeTwoQueue = (String) props.get("nodeTwoQueue");
		ResultSet rs = null;
		HashMap<String, Long> purgeMap = new HashMap<String, Long>();
		long startTime = 0, stopTime = 0;
		try {
			datasourcePstmt = connection.prepareStatement(configProps
					.getProperty("DataSource_MST_Purge_query"));
			datasourceRs = datasourcePstmt.executeQuery();
			while (datasourceRs.next()) {
				purgeMap.put(datasourceRs.getString("SOURCE_NAME"),
						datasourceRs.getLong("DAYS_TO_RETAIN"));
			}
			Set<String> purgeKeySet = purgeMap.keySet();
			Iterator<String> mapKeyIterator = purgeKeySet.iterator();
			while (mapKeyIterator.hasNext()) {
				startTime = System.currentTimeMillis();
				String dataSourceName = mapKeyIterator.next();

				String query = configProps.getProperty(
						"Purge_data_insert_query").replace("<<psxbatchid>>",
						eodBatchId);
				query = query.replace("<<DataSource>>", dataSourceName);
				query = query.replace("<<NoOfDays>>",
						purgeMap.get(dataSourceName).toString());
				logger.info("Query to Inert purge Data for DataSource::"
						+ dataSourceName + " is::" + query);
				purgeDataPstmt = connection.prepareStatement(query);
				int x = purgeDataPstmt.executeUpdate();
				stopTime = System.currentTimeMillis();
				logger.info("Query executed Sucessfully no of rows inserted in PSX_COMMON_STAGING_T ::"
						+ x + " time taken::" + (stopTime - startTime));

				startTime = System.currentTimeMillis();
				if (purgeDataPstmt != null)
					purgeDataPstmt.close();
				String query1 = configProps.getProperty(
						"Purge_data_insert_BKP_query").replace(
						"<<psxbatchid>>", eodBatchId);
				query1 = query1.replace("<<DataSource>>", dataSourceName);
				query1 = query1.replace("<<NoOfDays>>",
						purgeMap.get(dataSourceName).toString());
				purgeDataPstmt = connection.prepareStatement(query1);
				int x1 = purgeDataPstmt.executeUpdate();
				stopTime = System.currentTimeMillis();
				logger.info("Query executed Sucessfully no of rows inserted in PSX_COMMON_STAGING_BKP_T ::"
						+ x1 + " time taken::" + (stopTime - startTime));

				if (purgeDataPstmt != null)
					purgeDataPstmt.close();

			}
			logger.info("Count of DataSources select for purging are::"
					+ purgeMap.size());
			List<Customer> customersList = new ArrayList<Customer>();
			Customer customerData;

			String query2 = configProps.getProperty("Purge_data_select_query")
					.replace("<<psxbatchid>>", eodBatchId);
			purgeDataPstmt = connection.prepareStatement(query2);
			purgeDataRs = purgeDataPstmt.executeQuery(); // ,,,LCHGTIME,,,,,EOD_BATCH_ID
			while (purgeDataRs.next()) {
				customerData = new Customer();
				customerData.setCustUnqId(purgeDataRs.getString("CUST_UNQ_ID"));
				customerData
						.setCustomerId(purgeDataRs.getString("CUSTOMER_ID"));
				customerData.setDuiFlag(purgeDataRs.getString("DUIFLAG"));
				customerData.setPsxid(purgeDataRs.getString("PSX_ID"));
				customerData.setSegment(purgeDataRs.getString("SEGMENT"));
				customerData.setSourcesystem(purgeDataRs
						.getString("SOURCE_SYSTEM"));
				customerData.setPsxbatchid(purgeDataRs
						.getString("PSX_BATCH_ID"));
				customerData.setInsertTime(purgeDataRs
						.getTimestamp("INSERT_TIME"));
				customersList.add(customerData);
			}
			if (purgeDataPstmt != null)
				purgeDataPstmt.close();
			int asciiValue = 0;
			String custUnqId;
			String count = (String) props.get("nodesCount");
			int nodesCount = Integer.parseInt(count);
			DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
					Locale.US);
			java.sql.Timestamp date = new java.sql.Timestamp(
					new java.util.Date().getTime());
			String dateformat = (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"))
					.format(date);
			int inst1Cnt1 = 0;
			int inst1Cnt2 = 0;
			if (customersList.size() > 0) {
				logger.info("No of records to be deleted... "
						+ customersList.size());
				String sql = "insert into PSX_COMMON_STAGING_T_1"
						+ "(CUST_UNQ_ID,CUSTOMER_ID,DUIFLAG,LCHGTIME,PSX_BATCH_ID,PSX_ID,SEGMENT,SOURCE_SYSTEM,EOD_BATCH_ID,INSERT_TIME,EVENTTYPE ) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?)";
				insertDataPstmt = connection.prepareStatement(sql);
				String sql2 = "insert into PSX_COMMON_STAGING_T_2"
						+ "(CUST_UNQ_ID,CUSTOMER_ID,DUIFLAG,LCHGTIME,PSX_BATCH_ID,PSX_ID,SEGMENT,SOURCE_SYSTEM,EOD_BATCH_ID,INSERT_TIME,EVENTTYPE ) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?)";
				pstmt1 = connection.prepareStatement(sql2);
				String sql3 = "insert into PSX_COMMON_STAGING_T_ADT_1"
						+ "(CUST_UNQ_ID,CUSTOMER_ID,DUIFLAG,LCHGTIME,PSX_BATCH_ID,PSX_ID,SEGMENT,SOURCE_SYSTEM,EOD_BATCH_ID,INSERT_TIME,EVENTTYPE ) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?)";
				pstmt2 = connection.prepareStatement(sql3);
				String sql4 = "insert into PSX_COMMON_STAGING_T_ADT_2"
						+ "(CUST_UNQ_ID,CUSTOMER_ID,DUIFLAG,LCHGTIME,PSX_BATCH_ID,PSX_ID,SEGMENT,SOURCE_SYSTEM,EOD_BATCH_ID,INSERT_TIME,EVENTTYPE ) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?)";
				pstmt3 = connection.prepareStatement(sql4);
				for (Customer customer : customersList) {
					asciiValue = 0;
					custUnqId = customer.getCustUnqId();
					if (custUnqId != null && !custUnqId.isEmpty()) {
						for (int i = 0; i < custUnqId.length(); i++) {
							asciiValue += custUnqId.charAt(i);
						}
					}
					if (asciiValue % nodesCount == 0) {

						insertDataPstmt.setString(1, customer.getCustUnqId());
						insertDataPstmt.setString(2, customer.getCustomerId());
						insertDataPstmt.setString(3, customer.getDuiFlag());
						insertDataPstmt.setObject(4, new Timestamp(
								new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
										.parse(dateformat).getTime()));
						insertDataPstmt.setString(5, eodBatchId);
						insertDataPstmt.setString(6, customer.getPsxid());
						insertDataPstmt.setString(7, customer.getSegment());
						insertDataPstmt
								.setString(8, customer.getSourcesystem());
						insertDataPstmt.setString(9, eodBatchId);

						if (customer.getInsertTime() != null) {
							// insertDataPstmt.setObject(10, new Timestamp(new
							// SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(customer.getInsertTime()).getTime()));
							insertDataPstmt.setObject(10,
									customer.getInsertTime());
						} else {
							insertDataPstmt.setTimestamp(10,
									customer.getInsertTime());
						}
						insertDataPstmt.setString(11, "INCREMENTAL");

						insertDataPstmt.addBatch();

						pstmt2.setString(1, customer.getCustUnqId());
						pstmt2.setString(2, customer.getCustomerId());
						pstmt2.setString(3, customer.getDuiFlag());
						pstmt2.setObject(4, new Timestamp(new SimpleDateFormat(
								"dd-MM-yyyy HH:mm:ss").parse(dateformat)
								.getTime()));
						pstmt2.setString(5, eodBatchId);
						pstmt2.setString(6, customer.getPsxid());
						pstmt2.setString(7, customer.getSegment());
						pstmt2.setString(8, customer.getSourcesystem());
						pstmt2.setString(9, eodBatchId);

						if (customer.getInsertTime() != null) {
							// pstmt2.setObject(10, new Timestamp(new
							// SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(customer.getInsertTime()).getTime()));
							pstmt2.setObject(10, customer.getInsertTime());
						} else {
							pstmt2.setTimestamp(10, customer.getInsertTime());
						}
						pstmt2.setString(11, "INCREMENTAL");

						pstmt2.addBatch();

						inst1Cnt1++;

						if (inst1Cnt1
								% Integer.parseInt(props
										.getProperty("MaxBatchLimit")) == 0) {
							insertDataPstmt.executeBatch();
							insertDataPstmt.clearBatch();
							pstmt2.executeBatch();
							pstmt2.clearBatch();
							connection.commit();
							logger.info("Batch executed Successfully for PSX_COMMON_STAGING_T_1 for D flag "
									+ inst1Cnt1 + " Records Inserted");
							logger.info("Batch executed Successfully for PSX_COMMON_STAGING_T_ADT_1 for D flag "
									+ inst1Cnt1 + " Records Inserted");
							inst1Cnt1 = 0;
						}

						insertDataPstmt.executeBatch();
						insertDataPstmt.clearBatch();
						pstmt2.executeBatch();
						pstmt2.clearBatch();
						connection.commit();

					}

					if (asciiValue % nodesCount == 1) {

						pstmt1.setString(1, customer.getCustUnqId());
						pstmt1.setString(2, customer.getCustomerId());
						pstmt1.setString(3, customer.getDuiFlag());
						pstmt1.setObject(4, new Timestamp(new SimpleDateFormat(
								"dd-MM-yyyy HH:mm:ss").parse(dateformat)
								.getTime()));
						pstmt1.setString(5, eodBatchId);
						pstmt1.setString(6, customer.getPsxid());
						pstmt1.setString(7, customer.getSegment());
						pstmt1.setString(8, customer.getSourcesystem());
						pstmt1.setString(9, eodBatchId);

						if (customer.getInsertTime() != null) {
							// pstmt1.setObject(10, new Timestamp(new
							// SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(customer.getInsertTime()).getTime()));
							pstmt1.setObject(10, customer.getInsertTime());
						} else {
							pstmt1.setTimestamp(10, customer.getInsertTime());
						}
						pstmt1.setString(11, "INCREMENTAL");

						pstmt1.addBatch();

						pstmt3.setString(1, customer.getCustUnqId());
						pstmt3.setString(2, customer.getCustomerId());
						pstmt3.setString(3, customer.getDuiFlag());
						pstmt3.setObject(4, new Timestamp(new SimpleDateFormat(
								"dd-MM-yyyy HH:mm:ss").parse(dateformat)
								.getTime()));
						pstmt3.setString(5, eodBatchId);
						pstmt3.setString(6, customer.getPsxid());
						pstmt3.setString(7, customer.getSegment());
						pstmt3.setString(8, customer.getSourcesystem());
						pstmt3.setString(9, eodBatchId);

						if (customer.getInsertTime() != null) {
							// pstmt3.setObject(10, new Timestamp(new
							// SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(customer.getInsertTime()).getTime()));
							pstmt3.setObject(10, customer.getInsertTime());
						} else {
							pstmt3.setTimestamp(10, customer.getInsertTime());
						}
						pstmt3.setString(11, "INCREMENTAL");

						pstmt3.addBatch();

						inst1Cnt2++;
						if (inst1Cnt2
								% Integer.parseInt(props
										.getProperty("MaxBatchLimit")) == 0) {
							pstmt1.executeBatch();
							pstmt1.clearBatch();
							pstmt3.executeBatch();
							pstmt3.clearBatch();
							connection.commit();
							logger.info("Batch executed Successfully for PSX_COMMON_STAGING_T_2 for D flag "
									+ inst1Cnt2 + " Records Inserted");
							logger.info("Batch executed Successfully for PSX_COMMON_STAGING_T_ADT_2 for D flag "
									+ inst1Cnt2 + " Records Inserted");
							inst1Cnt2 = 0;

						}

						pstmt1.executeBatch();
						pstmt1.clearBatch();
						pstmt3.executeBatch();
						pstmt3.clearBatch();
						connection.commit();

					}
				}
				if (inst1Cnt1 > 0) {
					insertDataPstmt.executeBatch();
					insertDataPstmt.clearBatch();
					pstmt2.executeBatch();
					pstmt2.clearBatch();
					connection.commit();
				}
				if (inst1Cnt2 > 0) {

					pstmt1.executeBatch();
					pstmt1.clearBatch();
					pstmt3.executeBatch();
					pstmt3.clearBatch();
					connection.commit();
				}

				logger.info("Batch executed Successfully for PSX_COMMON_STAGING_T_1 for D flag "
						+ inst1Cnt1 + " Records Inserted");
				logger.info("Batch executed Successfully for PSX_COMMON_STAGING_T_2 for D flag "
						+ inst1Cnt2 + " Records Inserted");

			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error occured while exectuing queries for preparePurgedata::"
					+ e.getMessage());
			ExceptionRecordsInfo exceptionRecordsInfo = new ExceptionRecordsInfo();
			exceptionRecordsInfo.setEodBatchId(eodBatchId);
			exceptionRecordsInfo.setErrorInfo(e.getMessage());
			exceptionRecordsInfoList.add(exceptionRecordsInfo);
			logger.error(e, e);
			throw e;
		} finally {
			try {
				closeDBHandles(null, pstmt1, null);
				closeDBHandles(null, pstmt2, null);
				closeDBHandles(null, pstmt3, null);
				closeDBHandles(null, insertDataPstmt, null);
				closeDBHandles(null, purgeDataPstmt, purgeDataRs);
				closeDBHandles(null, datasourcePstmt, datasourceRs);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.warn("Error while Closing DB Handles::" + e.getMessage());
			}
			logger.debug("Leaving preparePurgedata...");
		}
	}

}
