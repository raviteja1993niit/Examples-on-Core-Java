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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.psx.prime360ClientService.entity.ExceptionRecordsInfo;
import com.psx.prime360ClientService.entity.NegativeBaseDetails;
import com.psx.prime360ClientService.exception.PosidexException;
import com.psx.prime360ClientService.serviceI.NegativeEodFileUploadService;
import com.psx.prime360ClientService.utils.ResponseJson;

@Service
@PropertySource({ "classpath:path.properties" })
public class NegativeEodFileUploadServiceImpl implements NegativeEodFileUploadService {
//    private final Log         logger              = LogFactory.getLog(this.getClass());
	private static Logger logger = Logger
			.getLogger(NegativeEodFileUploadServiceImpl.class.getName());
    List<NegativeBaseDetails> negativeBaseDetails = new ArrayList<>();
    long                      psx_batch_id1       = 0;
    NegativeBaseDetails       negativeBaseDetail1 = null;
    String                    eodBatchId          = "";
    String                    dateformat          = "";
    String errorMessage = "";
    List<ExceptionRecordsInfo> exceptionRecordsInfoList = new ArrayList<>();
    @Autowired
    private DataSource        dataSource;
    @Autowired
    Environment               environment;
    @Autowired
    @Qualifier("jmsTemplate2")
    private JmsTemplate       jmsTemplate2;

    public long appNextValProc() throws Exception {
        String sql = "select appnextval('eod_psx_id') from dual";

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            return jdbcTemplate.query(sql,
                                      new ResultSetExtractor<Long>() {
                                          public Long extractData(ResultSet rs)
                                                  throws SQLException, DataAccessException {
                                              Long reqPsxid = 0L;

                                              while (rs.next()) {
                                                  reqPsxid = rs.getLong(1);
                                              }

                                              return reqPsxid;
                                          }
                                      });
        } catch (Exception e) {
            logger.error("Exception while getting appNextValProc: " + e.getMessage());

            return (Long) null;
        }
    }

    private void dataMovementToHistoryTable(String eodBatchId) {
        String sqlQuery = SQLNegativeQueries.BATCH_NEG_QUERY_OF_EOD[3];

        logger.info("SQLQUERY ::" + sqlQuery);

        JdbcTemplate              jdbcTemplate        = new JdbcTemplate(dataSource);
        List<NegativeBaseDetails> negativeBaseDetails = jdbcTemplate.query(sqlQuery,
                                                                           new RowMapper<NegativeBaseDetails>() {
                                                                               @Override
                                                                               public NegativeBaseDetails mapRow(
                                                                                       ResultSet rs, int rownumber)
                                                                                       throws SQLException {
                                                                                   NegativeBaseDetails details1 =
                                                                                       new NegativeBaseDetails();

                                                                                   details1.setArea(
                                                                                       rs.getString("AREA"));
                                                                                   details1.setArea_id(
                                                                                       rs.getString("AREA_ID"));
                                                                                   details1.setArea_unq_id(
                                                                                       rs.getString("AREA_UNQ_ID"));
                                                                                   details1.setBatchid(
                                                                                       rs.getString("BATCHID"));
                                                                                   details1.setCity(
                                                                                       rs.getString("CITY"));
                                                                                   details1.setDuiFlag(
                                                                                       rs.getString("DUIFLAG"));
                                                                                   details1.setEventType(
                                                                                       rs.getString("EVENTTYPE"));
                                                                                   details1.setInsertTime(
                                                                                       rs.getTimestamp("INSERT_TIME"));
                                                                                   details1.setLchgTime(
                                                                                       rs.getTimestamp("LCHGTIME"));

                                                                                   // details1.setLchgTime(to_char(lchgtime,'dd-MM-yyyy HH24:mi:ss'));
                                                                                   details1.setOrg(rs.getString("ORG"));
                                                                                   details1.setPin(rs.getString("PIN"));
                                                                                   details1.setPsx_area(
                                                                                       rs.getString("PSX_AREA"));
                                                                                   details1.setPsx_batchid(
                                                                                       rs.getString("PSX_BATCH_ID"));
                                                                                   details1.setSegment(
                                                                                       rs.getString("SEGMENT"));
                                                                                   details1.setState(
                                                                                       rs.getString("STATE"));

                                                                                   return details1;
                                                                               }
                                                                           },
                                                                           eodBatchId,
                                                                           eodBatchId,
                                                                           eodBatchId);

        logger.info("NegativeBaseDetails ::: " + negativeBaseDetails);
        insertDataIntoNegativeAreaHistory(negativeBaseDetails);
        truncateBaseData(eodBatchId);
    }

    public InputStream downLoadingFile() throws SQLException {
        Statement stmt  = null;
        String    query =
            "select AREA,AREA_ID,AREA_UNQ_ID,BATCHID,CITY,DUIFLAG,EVENTTYPE,INSERT_TIME,LCHGTIME,ORG,PIN,PSX_AREA,PSX_BATCH_ID,SEGMENT,STATE from PSX_NEGATIVEAREA_BASE";
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date       date1      = new Date();
        String     date       = dateFormat.format(date1);

        try {
            JdbcTemplate              jdbcTemplate        = new JdbcTemplate(dataSource);
            List<NegativeBaseDetails> negativeBaseDetails = jdbcTemplate.query(query,
                                                                               new RowMapper<NegativeBaseDetails>() {
                                                                                   @Override
                                                                                   public NegativeBaseDetails mapRow(
                                                                                           ResultSet rs, int rownumber)
                                                                                           throws SQLException {
                                                                                       NegativeBaseDetails details1 =
                                                                                           new NegativeBaseDetails();

                                                                                       details1.setArea(
                                                                                           rs.getString("AREA"));
                                                                                       details1.setArea_id(
                                                                                           rs.getString("AREA_ID"));
                                                                                       details1.setArea_unq_id(
                                                                                           rs.getString("AREA_UNQ_ID"));
                                                                                       details1.setBatchid(
                                                                                           rs.getString("BATCHID"));
                                                                                       details1.setCity(
                                                                                           rs.getString("CITY"));
                                                                                       details1.setDuiFlag(
                                                                                           rs.getString("DUIFLAG"));
                                                                                       details1.setEventType(
                                                                                           rs.getString("EVENTTYPE"));
                                                                                       details1.setInsertTime(
                                                                                           rs.getTimestamp(
                                                                                               "INSERT_TIME"));
                                                                                       details1.setLchgTime(
                                                                                           rs.getTimestamp("LCHGTIME"));
                                                                                       details1.setOrg(
                                                                                           rs.getString("ORG"));
                                                                                       details1.setPin(
                                                                                           rs.getString("PIN"));
                                                                                       details1.setPsx_area(
                                                                                           rs.getString("PSX_AREA"));
                                                                                       details1.setPsx_batchid(
                                                                                           rs.getString(
                                                                                               "PSX_BATCH_ID"));
                                                                                       details1.setSegment(
                                                                                           rs.getString("SEGMENT"));
                                                                                       details1.setState(
                                                                                           rs.getString("STATE"));

                                                                                       return details1;
                                                                                   }
                                                                               });
            String     destinationPath = environment.getProperty("negativefiledownloaddestinationPath");
            File       file            = new File(destinationPath + date + ".csv");
            String     downloadFile    = destinationPath + date + ".csv";
            FileWriter fileWriter      = new FileWriter(file);

            fileWriter.append("AREA_UNQ_ID");
            fileWriter.append('|');
            fileWriter.append("AREA_ID");
            fileWriter.append('|');
            fileWriter.append("AREA");
            fileWriter.append('|');
            fileWriter.append("CITY");
            fileWriter.append('|');
            fileWriter.append("PIN");
            fileWriter.append('|');
            fileWriter.append("STATE");
            fileWriter.append('|');
            fileWriter.append("SEGMENT");
            fileWriter.append('|');
            fileWriter.append("ORG");
            fileWriter.append('\n');

            for (NegativeBaseDetails details1 : negativeBaseDetails) {
                String area        = details1.getArea();
                String area_id     = details1.getArea_id();
                String area_unq_id = details1.getArea_unq_id();
                String city        = details1.getCity();
                String eventType   = details1.getEventType();
                String org         = details1.getOrg();
                String pin         = details1.getPin();
                String segment     = details1.getSegment();
                String state       = details1.getState();
                String batchid     = details1.getBatchid();
                String duiFlag     = details1.getDuiFlag();
                String insertTime  = details1.getInsertTime() + "";
                String lchgTime    = details1.getLchgTime() + "";
                String psx_area    = details1.getPsx_area();
                String psx_batchid = details1.getPsx_batchid();

                fileWriter.append(area_unq_id);
                fileWriter.append('|');
                fileWriter.append(area_id);
                fileWriter.append('|');
                fileWriter.append(area);
                fileWriter.append('|');
                fileWriter.append(city);
                fileWriter.append('|');
                fileWriter.append(pin);
                fileWriter.append('|');
                fileWriter.append(state);
                fileWriter.append('|');
                fileWriter.append(segment);
                fileWriter.append('|');
                fileWriter.append(org);
                fileWriter.append('\n');
            }

            fileWriter.flush();
            fileWriter.close();
            logger.info("File Written Successfully........");

            InputStream input = null;

            logger.info("Download File Name ::: " + downloadFile);
            input = new FileInputStream(downloadFile);

            return input;
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return null;
    }

    public void errorCode(long psx_batch_id, NegativeBaseDetails negativeBaseDetail, String error) {
        logger.info("Enter into the Error Table" + psx_batch_id);

        String sql = "insert into ERROR_RECORDS_INFO_T(PSX_BATCH_ID,RECORD,ERROR_DESCRIPTION,INSERT_TS)"
                     + "values(?,?,?,systimestamp)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            jdbcTemplate.update(sql,
                                new Object[] { psx_batch_id, negativeBaseDetail.toString(),
            		error });
        } catch (DataAccessException e) {
            logger.error(e);
        }
    }

   /* public void insertBatch(List<NegativeBaseDetails> list) throws JSONException {
        String    nodeOneQueue = environment.getProperty("nodeOneQueue");
        final int batchSize    = Integer.parseInt(environment.getProperty("batchSize"));
        String    SqlQuery     = SQLNegativeQueries.BATCH_NEG_QUERY_OF_EOD[0];

        logger.info("SQLQUERY ::" + SqlQuery);

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            jdbcTemplate.batchUpdate(SqlQuery,
                                     new BatchPreparedStatementSetter() {
                                         @Override
                                         public void setValues(PreparedStatement ps, int i) throws SQLException {
                                             long                psx_batch_id       = 0;
                                             NegativeBaseDetails negativeBaseDetail = list.get(i);

                                             try {
                                                 psx_batch_id = appNextValProc();
                                                 ps.setString(1, negativeBaseDetail.getArea());
                                                 ps.setString(2, negativeBaseDetail.getArea_id());
                                                 ps.setString(3, negativeBaseDetail.getArea_unq_id());
                                                 ps.setLong(4, psx_batch_id);
                                                 ps.setString(5, negativeBaseDetail.getCity());
                                                 ps.setString(6, "I_OR_U");
                                                 ps.setString(7, "INITIAL");

                                                 try {
                                                     ps.setObject(
                                                         8, new Timestamp(
                                                             new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(
                                                                 dateformat).getTime()));
                                                     ps.setObject(
                                                         9, new Timestamp(
                                                             new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(
                                                                 dateformat).getTime()));
                                                 } catch (ParseException e) {
                                                     logger.error(e.getMessage());
                                                 }

                                                 ps.setString(10, negativeBaseDetail.getOrg());
                                                 ps.setString(11, negativeBaseDetail.getPin());
                                                 ps.setString(12, negativeBaseDetail.getArea());
                                                 ps.setString(13, eodBatchId);
                                                 ps.setString(14, negativeBaseDetail.getSegment());
                                                 ps.setString(15, negativeBaseDetail.getState());
                                             } catch (Exception e1) {
                                                 String error = e1.getMessage();

                                                 logger.info("Enter Into ErrorTable");
                                                 errorCode(psx_batch_id, negativeBaseDetail, error);
                                             }
                                         }
                                         private void setValues(long psx_batch_id,
                                                                NegativeBaseDetails negativeBaseDetail) {
                                             System.out.println("Setting Values :::");
                                             psx_batch_id1       = psx_batch_id;
                                             negativeBaseDetail1 = negativeBaseDetail;
                                         }
                                         @Override
                                         public int getBatchSize() {
                                             return list.size();
                                         }
                                     });
        } catch (Exception e1) {
            logger.error(e1);
        }

        logger.info("Moving Duplicated data to PSX_NEGATIVEAREA_BASE_HIST....");
        dataMovementToHistoryTable(eodBatchId);

        try {
            logger.info("Sending Negative Message To Engine");
            sendNegativeMessageToEngine(eodBatchId, nodeOneQueue);
        } catch (JSONException e) {
            throw e;
        }
        }

   */

    private void insertDataIntoNegativeAreaHistory(List<NegativeBaseDetails> negativeBaseDetails) {
        String       sqlQuery     = SQLNegativeQueries.BATCH_NEG_QUERY_OF_EOD[4];
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.batchUpdate(sqlQuery,
                                 new BatchPreparedStatementSetter() {
                                     @Override
                                     public void setValues(PreparedStatement ps, int i) throws SQLException {
                                         NegativeBaseDetails negativeBaseDetail = negativeBaseDetails.get(i);

                                         ps.setString(1, negativeBaseDetail.getArea());
                                         ps.setString(2, negativeBaseDetail.getArea_id());
                                         ps.setString(3, negativeBaseDetail.getArea_unq_id());
                                         ps.setString(13, negativeBaseDetail.getBatchid());
                                         ps.setString(5, negativeBaseDetail.getCity());
                                         ps.setString(6, negativeBaseDetail.getDuiFlag());
                                         ps.setString(7, negativeBaseDetail.getEventType());
                                         ps.setObject(8, negativeBaseDetail.getInsertTime());

                                         // ps.setDate(9,to_char(negativeBaseDetail.getLchgTime())); TO_CHAR(SYSDATE,
                                         // 'dd/mm/yyyy')
                                         ps.setObject(9, negativeBaseDetail.getLchgTime());
                                         ps.setString(10, negativeBaseDetail.getOrg());
                                         ps.setString(11, negativeBaseDetail.getPin());
                                         ps.setString(12, negativeBaseDetail.getArea());
                                         ps.setString(4, negativeBaseDetail.getPsx_batchid());
                                         ps.setString(14, negativeBaseDetail.getSegment());
                                         ps.setString(15, negativeBaseDetail.getState());
                                     }
                                     @Override
                                     public int getBatchSize() {
                                         return negativeBaseDetails.size();
                                     }
                                 });
        logger.info("Data moved to PSX_NEGATIVEAREnegativeBaseDetailA_BASE_HIST");
    }

    public ResponseJson<HttpStatus, String> negativeEodFileUpload(MultipartFile file) throws Exception {
        logger.info("entered into getOther Sources services  inside negativeEodFileUpload:: "
                    + file.getOriginalFilename());

        DateFormat         sdf  = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());

        dateformat = (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(date);

        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date       date1      = new Date();

        eodBatchId = dateFormat.format(date1);

        ResponseJson<HttpStatus, String> responseJson = new ResponseJson<>();

        if (file != null) {
            byte[] bytes;

            try {
                bytes = file.getBytes();

                ByteArrayInputStream inputFilestream = new ByteArrayInputStream(bytes);
                BufferedReader       br              = new BufferedReader(new InputStreamReader(inputFilestream));
                String               line            = "";
             
				int count = 0;
				int skip = 0;
                while ((line = br.readLine()) != null) {
                	
                	int s = line.length()
							- line.replaceAll("\\|", "").length();
					int headerCount = Integer.parseInt(environment
							.getProperty("NegativeHeaderFieldsCount"));
					 if (!line.startsWith("AREA_UNQ_ID") && !line.equals("")) {

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
					    parseLine(line);
					}
                	
                	
                	
                	
//                    if (!line.startsWith("AREA_UNQ_ID")) {
//                        parseLine(line);
//                    }
					 }
                }

                br.close();
            	for (ExceptionRecordsInfo exceptionRecordsInfo : exceptionRecordsInfoList) {
					errorCode1(exceptionRecordsInfo.getEodBatchId(),
							exceptionRecordsInfo.getCustomerObj(),
							exceptionRecordsInfo.getErrorInfo());
				}
				exceptionRecordsInfoList = new ArrayList<>();
                String destinationPath = environment.getProperty("negativefileuploaddestinationPath");
                Path   path            = Paths.get(destinationPath + file.getOriginalFilename());

                logger.info("Destination File Path::: " + destinationPath);

                File file1 = new File(path.toString());

                file.transferTo(file1);
                logger.debug("successfully uploaded the file");
                logger.info("No of records to be inserted ::: "+negativeBaseDetails.size());
          //      insertBatch(negativeBaseDetails);
                negativeBaseDetails.forEach(new Consumer<NegativeBaseDetails>() {

					@Override
					public void accept(NegativeBaseDetails t) {
						
						try {
							insertRecord(t);
						} catch (JSONException e) {
							logger.info("Error While Sending Message Json Exception");
						}
					}
				});
                logger.info("Moving Duplicated data to PSX_NEGATIVEAREA_BASE_HIST....");
    	        dataMovementToHistoryTable(eodBatchId);
    			String nodeOneQueue = environment.getProperty("nodeOneQueue");

    	        try {
    	            logger.info("Sending Negative Message To Engine");
    	            sendNegativeMessageToEngine(eodBatchId, nodeOneQueue);
    	        } catch (JSONException e) {
    	            throw e;
    	        }
                
                negativeBaseDetails = new ArrayList<>();
                responseJson.setData(HttpStatus.OK, eodBatchId);
            } catch (IOException e1) {
                logger.error(e1);
                responseJson.setData(HttpStatus.INTERNAL_SERVER_ERROR, eodBatchId);

                throw e1;
            }
        }

        return responseJson;
    }

    private void errorCode1(String eodBatchId2, String customerObj,
			String errorInfo) {

		logger.info("Enter into the Error Table" + eodBatchId2);
	
		String sql = "insert into ERROR_RECORDS_INFO_T(PSX_BATCH_ID,RECORD,ERROR_DESCRIPTION,INSERT_TS)"
				+ "values(?,?,?,systimestamp)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			
			jdbcTemplate.update(sql,
					new Object[] { eodBatchId2, customerObj, errorInfo,});

			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	
		
	}

	private void insertRecord(NegativeBaseDetails negativeBaseDetail) throws JSONException {
		//AREA,AREA_ID,AREA_UNQ_ID,BATCHID,CITY,DUIFLAG,EVENTTYPE,INSERT_TIME,LCHGTIME,ORG,PIN,PSX_AREA,PSX_BATCH_ID,SEGMENT,STATE
		String SqlQuery = SQLNegativeQueries.BATCH_NEG_QUERY_OF_EOD[0];
		long psx_batch_id = 0;
		try {

			psx_batch_id = appNextValProc();
			logger.info("SQLQUERY ::" + SqlQuery);
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(
					SqlQuery,
					new Object[] {
							
							negativeBaseDetail.getArea(),
							negativeBaseDetail.getArea_id(),
							negativeBaseDetail.getArea_unq_id(),
							psx_batch_id,
							negativeBaseDetail.getCity(),
							"I_OR_U",
							"INITIAL",

							new Timestamp(new SimpleDateFormat(
									"dd-MM-yyyy HH:mm:ss").parse(dateformat)
									.getTime()),
							new Timestamp(new SimpleDateFormat(
									"dd-MM-yyyy HH:mm:ss").parse(dateformat)
									.getTime()),

							negativeBaseDetail.getOrg(),
							negativeBaseDetail.getPin(),
							negativeBaseDetail.getArea(), eodBatchId,
							negativeBaseDetail.getSegment(),
							negativeBaseDetail.getState() });
		} catch (Exception e1) {
			String error = e1.getMessage();

			logger.info("Enter Into While Inserting ErrorTable ::: "+error);
			logger.error(e1,e1);
			errorCode(Long.parseLong(eodBatchId), negativeBaseDetail, error);
		}
		
	}

    @SuppressWarnings("resource")
	public void parseLine(String value) throws PosidexException {
    	value = value + "|";
    	 NegativeBaseDetails negativeBaseDetail=null;
        Scanner sc = new Scanner(value);

        sc.useDelimiter("[|]");
        
  
            while (sc.hasNext()) {
                try {
                negativeBaseDetail = new NegativeBaseDetails();

                negativeBaseDetail.setArea_unq_id(sc.next());
                negativeBaseDetail.setArea_id(sc.next());
                negativeBaseDetail.setArea(sc.next());
                negativeBaseDetail.setCity(sc.next());
                negativeBaseDetail.setPin(sc.next());
                negativeBaseDetail.setState(sc.next());
                negativeBaseDetail.setSegment(sc.next());
                negativeBaseDetail.setOrg(sc.next());
                negativeBaseDetails.add(negativeBaseDetail);
                logger.info(" NegativeBaseDetails :: " + negativeBaseDetail);
                } catch (Exception e) {
                    logger.info(e, e);
                    errorCode(Long.parseLong(eodBatchId),negativeBaseDetail, "While building NegativeBaseDetails Object");
                    //throw new PosidexException(e.getMessage());
                }

            }
        
        // call function to insert the data in table

        sc.close();
    }

    public void sendNegativeMessageToEngine(String eodBatchId, String queueName) throws JSONException {
        logger.info("QueueName................" + queueName);

        JSONObject json = new JSONObject();

        json.put("psxBatchID", eodBatchId);
        json.put("processType", "initialProcess");
        json.put("sourceSystemName", "NEGATIVE");

        String current_timeStamp = System.currentTimeMillis() + "";

        json.put("EODTimeStamp", current_timeStamp);
        logger.info("Initial message::" + json.toString());

        final String message = json.toString();

        logger.info("MESSAGE :::" + message);
        jmsTemplate2.setExplicitQosEnabled(true);
        jmsTemplate2.setPriority(0);
        jmsTemplate2.send(queueName,
                          new MessageCreator() {
                              public Message createMessage(Session session) throws JMSException {
                                  TextMessage textMessage = null;

                                  textMessage = session.createTextMessage(message);
              					textMessage.setJMSPriority(0);

                                  return textMessage;
                              }
                          });
        logger.info("Negative Message has been sent succesfully to EventQueue:");
    }

    private void truncateBaseData(String eodBatchId) {
        String       deleteQuery  = SQLNegativeQueries.BATCH_NEG_QUERY_OF_EOD[5];
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(deleteQuery, eodBatchId, eodBatchId, eodBatchId);
        logger.info("Clean Up PSX_NEGATIVEAREA_BASE_HIST Table");
    }
}


