package com.psx.prime360ClientService.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.psx.prime360ClientService.entity.HeaderColumnWrapper;
import com.psx.prime360ClientService.entity.ColMetaDataInfo;
import com.psx.prime360ClientService.entity.InputTableMetaInfo;



public class BulkQueryUtils {
	private static Logger logger=Logger.getLogger(BulkQueryUtils.class.getName());
	public static void messageDmls(boolean commitRights,DataSource dataSource,String key,HashMap<String,String> mappingProperties,HashMap<String,String> paramMap,String requestID) throws Exception{
		if(dataSource!=null){
			try(Connection con=dataSource.getConnection();){
				messageDmls(commitRights,con, key, mappingProperties, paramMap, requestID);
			}
		}
	}
	public static void messageDmlsForRecord(boolean commitRights,Connection con,String key,HashMap<String,String> mappingProperties,HashMap<String,Object> paramMap,String requestID) throws Exception{
		if(con==null){
			return;
		}
		paramMap.put("EngineRequestIDBatchID", requestID);
		paramMap.put("psxCurrentTime",getFormattedDate(System.currentTimeMillis()));
		int i=0;
		String statement;
		String[] bindValues;
		try{
			while((statement=(String)mappingProperties.get(key+i))!=null){
				long time=System.currentTimeMillis();
				bindValues=mappingProperties.get(key+i+".bindValues")!=null?mappingProperties.get(key+i+".bindValues").split(","):new String[0];
				logger.fine(String.format("Executing the statement:%s with arguments %s and argument values: %s ",statement,Arrays.deepToString(bindValues),paramMap.toString()));
				int effectiveCount=0;
				if(statement.startsWith("{ call ")){
					CallableStatement cstmt=con.prepareCall(statement);
					for(int b=0;b<bindValues.length;b++){
						if(!paramMap.containsKey(bindValues[b])){
							logger.severe("The key:"+bindValues[b]+": doesn't exists in the map.");
						}
						if(bindValues[b].equals("endTime")|| bindValues[b].equals("startTime")){
							cstmt.setTimestamp(b+1,new Timestamp(Long.parseLong((String)paramMap.get(bindValues[b]))));	
						}else if("lchgTime".equals(bindValues[b])) {
							cstmt.setObject(b+1,new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse((String)paramMap.get("lchgTime")).getTime()));
						}else if((paramMap.get(bindValues[b])!=null) && ((String)paramMap.get(bindValues[b])).matches("\\d{2}-\\d{2}-\\d{4}")){
							cstmt.setObject(b+1, new Date(new SimpleDateFormat("dd-mm-yyyy").parse((String)paramMap.get(bindValues[b])).getTime()));
						}else if((paramMap.get(bindValues[b])!=null) && ((String)paramMap.get(bindValues[b])).matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}")){
							cstmt.setTimestamp(b+1, new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse((String)paramMap.get(bindValues[b])).getTime()));
						}else{
							cstmt.setString(b+1, (String)paramMap.get(bindValues[b]));
						}
					}
					boolean effectiveStatus=cstmt.execute();
					effectiveCount=effectiveStatus?1:0;
					cstmt.close();
				}else{
					PreparedStatement pstmt=con.prepareStatement(statement);
					for(int b=0;b<bindValues.length;b++){
						if(!paramMap.containsKey(bindValues[b])){
							logger.severe("The key:"+bindValues[b]+": doesn't exists in the map.");
						}
						if(bindValues[b].equals("endTime")|| bindValues[b].equals("startTime")){
							pstmt.setTimestamp(b+1,new Timestamp(Long.parseLong((String)paramMap.get(bindValues[b]))));
						}else if("lchgTime".equals(bindValues[b])) {
							pstmt.setObject(b+1,new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse((String)paramMap.get("lchgTime")).getTime()));
						}else if((paramMap.get(bindValues[b])!=null) && ((String)paramMap.get(bindValues[b])).matches("\\d{2}-\\d{2}-\\d{4}")){
							pstmt.setObject(b+1, new Date(new SimpleDateFormat("dd-mm-yyyy").parse((String)paramMap.get(bindValues[b])).getTime()));
						}else if((paramMap.get(bindValues[b])!=null) && ((String)paramMap.get(bindValues[b])).matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}")){
							pstmt.setTimestamp(b+1, new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse((String)paramMap.get(bindValues[b])).getTime()));
						}else{
							pstmt.setString(b+1, (String)paramMap.get(bindValues[b]));
						}
					}

					effectiveCount=pstmt.executeUpdate();
					pstmt.close();
				}
				i++;
				time=System.currentTimeMillis()-time;
				logger.fine(String.format("In %d time Executed the statement with effective count %d:%s with arguments %s and argument values: %s",time,effectiveCount,statement,Arrays.deepToString(bindValues),paramMap.toString()));
			}

			if(commitRights){if(con.getAutoCommit()==false){con.commit();}}
		}
		catch(Exception e){
			logger.severe("Problem with the Query which is in the provided key:: "+(key+i)+": The query is:"+mappingProperties.get(key+i));
			logger.log(Level.SEVERE, e.getStackTrace().toString(), e);
			throw e;
		}
	}		

	public static void messageDmls(boolean commitRights,Connection con,String key,HashMap<String,String> mappingProperties,HashMap<String,String> paramMap,String requestID) throws Exception{
		if(con==null){
			return;
		}
		paramMap.put("EngineRequestIDBatchID", requestID);
		paramMap.put("psxCurrentTime",getFormattedDate(System.currentTimeMillis()));
		int i=0;
		String statement;
		String[] bindValues;
		try{
			while((statement=(String)mappingProperties.get(key+i))!=null){
				long time=System.currentTimeMillis();
				bindValues=mappingProperties.get(key+i+".bindValues")!=null?mappingProperties.get(key+i+".bindValues").split(","):new String[0];
				logger.fine(String.format("Executing the statement:%s with arguments %s and argument values: %s ",statement,Arrays.deepToString(bindValues),paramMap.toString()));
				int effectiveCount=0;
				if(statement.startsWith("{ call ")){
					CallableStatement cstmt=con.prepareCall(statement);
					for(int b=0;b<bindValues.length;b++){
						if(!paramMap.containsKey(bindValues[b])){
							logger.severe("The key:"+bindValues[b]+": doesn't exists in the map.");
						}
						if(bindValues[b].equals("endTime")|| bindValues[b].equals("startTime")){
							cstmt.setTimestamp(b+1,new Timestamp(Long.parseLong(paramMap.get(bindValues[b]))));	
						}else if("lchgTime".equals(bindValues[b])) {
							cstmt.setObject(b+1,new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(paramMap.get("lchgTime")).getTime()));
						}else if(paramMap.get(bindValues[b])!=null && paramMap.get(bindValues[b]).matches("\\d{2}-\\d{2}-\\d{4}")){
							cstmt.setObject(b+1, new Date(new SimpleDateFormat("dd-mm-yyyy").parse(paramMap.get(bindValues[b])).getTime()));
						}else if(paramMap.get(bindValues[b])!=null && paramMap.get(bindValues[b]).matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}")){
							cstmt.setTimestamp(b+1, new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(paramMap.get(bindValues[b])).getTime()));
						}else{
							cstmt.setString(b+1, paramMap.get(bindValues[b]));
						}
					}
					boolean effectiveStatus=cstmt.execute();
					effectiveCount=effectiveStatus?1:0;
					cstmt.close();
				}else{
					PreparedStatement pstmt=con.prepareStatement(statement);
					for(int b=0;b<bindValues.length;b++){
						if(!paramMap.containsKey(bindValues[b])){
							logger.severe("The key:"+bindValues[b]+": doesn't exists in the map.");
						}
						if(bindValues[b].equals("endTime")|| bindValues[b].equals("startTime")){
							pstmt.setTimestamp(b+1,new Timestamp(Long.parseLong(paramMap.get(bindValues[b]))));
						}else if("lchgTime".equals(bindValues[b])) {
							pstmt.setObject(b+1,new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(paramMap.get("lchgTime")).getTime()));
						}else if(paramMap.get(bindValues[b])!=null && paramMap.get(bindValues[b]).matches("\\d{2}-\\d{2}-\\d{4}")){
							pstmt.setObject(b+1, new Date(new SimpleDateFormat("dd-mm-yyyy").parse(paramMap.get(bindValues[b])).getTime()));
						}else if(paramMap.get(bindValues[b])!=null && paramMap.get(bindValues[b]).matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}")){
							pstmt.setTimestamp(b+1, new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(paramMap.get(bindValues[b])).getTime()));
						}else{
							pstmt.setString(b+1, paramMap.get(bindValues[b]));
						}
					}

					effectiveCount=pstmt.executeUpdate();
					pstmt.close();
				}
				i++;
				time=System.currentTimeMillis()-time;
				logger.fine(String.format("In %d time Executed the statement with effective count %d:%s with arguments %s and argument values: %s",time,effectiveCount,statement,Arrays.deepToString(bindValues),paramMap.toString()));
			}

			if(commitRights){if(con.getAutoCommit()==false){con.commit();}}
		}
		catch(Exception e){
			logger.severe("Problem with the Query which is in the provided key:: "+(key+i)+": The query is:"+mappingProperties.get(key+i));
			logger.log(Level.SEVERE, e.getStackTrace().toString(), e);
			throw e;
		}
	}		

	public static String getFormattedDate(Long timeInMillis){
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//"yyyy-M-dd HH:mm:ss"
		c.setTimeInMillis(timeInMillis);
		return sdf.format(c.getTime());
	}
	
	public static String getDateForBatchId(Long timeInMillis){
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyyHHmmss");//"yyyy-M-dd HH:mm:ss"
		c.setTimeInMillis(timeInMillis);
		return sdf.format(c.getTime());
	}


	public static Properties getPropertiesFromFile(Path fileName) throws Exception{
		Properties prop=new Properties();
		InputStream is=Files.newInputStream(fileName);
		prop.load(is);
		is.close();
		return prop;
	}
	public static String uploadFile(MultipartFile multipartFile) throws IOException {
		File file = convertMultiPartToFile(multipartFile);
		String fileContent=Files.lines(file.toPath()).collect(Collectors.joining(";"));
		return fileContent;
	}
	private static File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	public static HeaderColumnWrapper getHeaderWrapper(String jsonInfo) {
		Gson gson = new Gson();
		HeaderColumnWrapper hWrapper = gson.fromJson(jsonInfo, HeaderColumnWrapper.class);
		return hWrapper;
	}
	public static HashMap<String, ArrayList<HashMap<String, Object>>> buildReqInfo(String tabTag,HashMap<String,Object> dedupeMap) {
		HashMap<String, ArrayList<HashMap<String, Object>>> reqfinalMap = new HashMap<String,ArrayList<HashMap<String, Object>>>();
		ArrayList<HashMap<String, Object>> fieldList = new ArrayList<HashMap<String, Object>>();
		fieldList.add(dedupeMap);
		reqfinalMap.put(tabTag, fieldList);
		return reqfinalMap;
	}
	
	public static Object[] buildData(String csvContent) {
		HashMap<String, List<ColMetaDataInfo>> data = new HashMap<String, List<ColMetaDataInfo>>();
		HashMap<String, ColMetaDataInfo> slColMetadataMap = new HashMap<String, ColMetaDataInfo>();

		String tableName = null;
		List<String> tableAliasName=new ArrayList<>();
		String[] lines = csvContent.split(";");
		String[] header = lines[0].split(",");
		for (int lc = 1; lc < lines.length; lc++) {
			// splitting by considering empty trailing characters in the end
			String[] recordArray = lines[lc].split(",", -1);
			HashMap<String, String> record = new HashMap<String, String>();
			for (int i = 0; i < recordArray.length; i++) {
				record.put(header[i].toUpperCase(), recordArray[i]!=null?recordArray[i].trim():recordArray[i]);
			}
			tableName = record.get("TABLE_NAME");
			if(!tableAliasName.isEmpty()) {
				if(!(tableAliasName.indexOf(record.get("TABLE_ALIAS_NAME"))>-1)) {
					tableAliasName.add(record.get("TABLE_ALIAS_NAME"));
				}
			}else {
				tableAliasName.add(record.get("TABLE_ALIAS_NAME"));
			}
			
			ColMetaDataInfo columnInfo = new ColMetaDataInfo();
			columnInfo.setSlNo(record.get("SL"));
			columnInfo.setColumnName(record.get("COLUMN_NAME"));
			columnInfo.setDataType(record.get("DATA_TYPE"));
			columnInfo.setNullable(record.get("NULLABLE"));
			
			columnInfo.setIsPrimarykey(record.get("ISPRIMARYKEY"));
			columnInfo.setIsDedupe(record.get("ISDEDUPE"));
			columnInfo.setDedupeType(record.get("DEDUPETYPE"));
			columnInfo.setCleanProfile(record.get("CLEANPROFILE"));
			columnInfo.setDisplayName(record.get("DISPLAYNAME"));
			columnInfo.setAssociationParameters(
					record.get("ASSOCIATIONPARAMETERS") != null && !(record.get("ASSOCIATIONPARAMETERS").isEmpty())
							? record.get("ASSOCIATIONPARAMETERS").split("~")
							: null);

			
			if (data.get(tableName) != null) {
				List<ColMetaDataInfo> value = data.get(tableName);
				value.add(columnInfo);
				data.put(tableName, value);
			} else {
				List<ColMetaDataInfo> newTableInfo = new ArrayList<ColMetaDataInfo>();
				newTableInfo.add(columnInfo);
				data.put(tableName, newTableInfo);
			}
			slColMetadataMap.put(record.get("SL"), columnInfo);
		}
		Set<String> colmetaDataSet = data.keySet();
		Iterator<String> tempMetadataItr = colmetaDataSet.iterator();
		InputTableMetaInfo[] inputTablemetainfoArray = new InputTableMetaInfo[colmetaDataSet.size()];
		int index = 0;
		while (tempMetadataItr.hasNext()) {
			String tabName = tempMetadataItr.next();
			List<ColMetaDataInfo> value = data.get(tabName);
			List<String> colNames = new ArrayList<String>();
			for (ColMetaDataInfo colmetadataInfo : value) {
				colNames.add(colmetadataInfo.getColumnName());
			}

			InputTableMetaInfo ipInfo = new InputTableMetaInfo();
			ipInfo.setTableName(tabName);
			ipInfo.setTableAliasName(tableAliasName.get(index));
			Collections.sort(value, new Comparator<ColMetaDataInfo>() {
				@Override
				public int compare(ColMetaDataInfo o1, ColMetaDataInfo o2) {
					return Integer.parseInt(o1.getSlNo()) - Integer.parseInt(o2.getSlNo());
				}
			});					
			ipInfo.setColNames(colNames);
			inputTablemetainfoArray[index] = ipInfo;
			index++;

		}
		return new Object[] { inputTablemetainfoArray, slColMetadataMap };

	}

	
}
