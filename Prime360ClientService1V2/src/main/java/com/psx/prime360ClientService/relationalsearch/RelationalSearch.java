package com.psx.prime360ClientService.relationalsearch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.google.gson.Gson;
import com.psx.prime360ClientService.dto.ProfilesDTO;
import com.psx.prime360ClientService.serviceImpl.RequestPostingServiceImpl;

/**
 *
 * @author jayantronald
 *
 */

public class RelationalSearch {

	RelationalSearchBO relationalSearchBO;
	RelationalSearchDTO relationalSearchDTO;

	public RelationalSearchBO getRelationalSearchBO() {
		return relationalSearchBO;
	}

	public void setRelationalSearchBO(RelationalSearchBO relationalSearchBO) {
		this.relationalSearchBO = relationalSearchBO;
	}

	public RelationalSearchDTO getRelationalSearchDTO() {
		int tabCount=relationalSearchBO.getTables().size();
		List<String> resultsTableHeaderList = relationalSearchBO.getResultsTableHeader();
		List<String[]> resultsList = relationalSearchBO.getResults();
		
		RelationalSearchDTO relationalSearchDTO = new RelationalSearchDTO();
		List<RelationalSearchTableDTO> tables = new ArrayList<RelationalSearchTableDTO>();
		
		for (int i=0;i<tabCount;i++) {
			RelationalSearchTableDTO relationalSearchTableDTO = new RelationalSearchTableDTO();
			String tabName = relationalSearchBO.getTables().get(i).getTableName();
			List<RelationalSearchParameter> relationalSearchParameter  = relationalSearchBO.getTables().get(i).getRelationalSearchParameters();

			relationalSearchTableDTO.setTableName(tabName);
			relationalSearchTableDTO.setRelationalSearchParameter(relationalSearchParameter);
			
			tables.add(relationalSearchTableDTO);
		}
		
		relationalSearchDTO.setTables(tables);
		relationalSearchDTO.setResultsTableHeader(resultsTableHeaderList);
		relationalSearchDTO.setResults(resultsList);
		return relationalSearchDTO;
	}

	public void setRelationalSearchDTO(RelationalSearchDTO relationalSearchDTO) {
		this.relationalSearchDTO = relationalSearchDTO;
	}

	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static RelationalSearch fromJSON(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, RelationalSearch.class);
	}
	
	public static RelationalSearch fromProps(Map<String, String> properties) {
		String keyToFind = "relationalSearchTabs";
		String tagNames=properties.get(keyToFind);
		List<String> tableTagName = Arrays.asList(tagNames.split(","));
		RelationalSearchBO relationalSearchBO=new RelationalSearchBO();
		List<RelationalSearchTableBO> tables=new ArrayList<>();
		for (int index = 0; index < tableTagName.size(); index++) {
			RelationalSearchTableBO r=new RelationalSearchTableBO();
			if(index==0) {
				r.setStatusQuery(properties.get(keyToFind+"."+tableTagName.get(index)+".statusQuery"));
				r.setStatusBindValues(properties.get(keyToFind+"."+tableTagName.get(index)+".statusBindValues"));	
			}
			r.setTableName(properties.get(keyToFind+"."+tableTagName.get(index)+".tableName"));
			r.setTableTagName(tableTagName.get(index));
			int cntr=0;
			while(properties.get(keyToFind+"."+tableTagName.get(index)+".relationalSearchParameters."+cntr+".engineName")!=null) {
				RelationalSearchParameter rsp=new RelationalSearchParameter();
				rsp.setEngineName(properties.get(keyToFind+"."+tableTagName.get(index)+".relationalSearchParameters."+cntr+".engineName"));
				rsp.setParameterDisplayName(properties.get(keyToFind+"."+tableTagName.get(index)+".relationalSearchParameters."+cntr+".parameterDisplayValue"));
				rsp.setParameterValue(properties.get(keyToFind+"."+tableTagName.get(index)+".relationalSearchParameters."+cntr+".parameterValue"));
				r.getRelationalSearchParameters().add(rsp);
				cntr++;
			}
			r.setInsertQuery(properties.get(keyToFind+"."+tableTagName.get(index)+".insertQuery"));
			r.setBindValues(properties.get(keyToFind+"."+tableTagName.get(index)+".bindValues"));
			tables.add(r);
		}
		relationalSearchBO.setTables(tables);
		relationalSearchBO.setJmsMessage(properties.get(keyToFind+".jmsMessage"));
		relationalSearchBO.setQueueName(properties.get(keyToFind+".queueName"));
		relationalSearchBO.setResultsQuery(properties.get(keyToFind+".resultsQuery"));
		relationalSearchBO.setResultsTableHeader(Arrays.asList(properties.get(keyToFind+".resultsTableHeader").split(",")));
		RelationalSearch relationalSearch = new RelationalSearch();
		relationalSearch.setRelationalSearchBO(relationalSearchBO);
		return relationalSearch;
	}

	public RelationalSearchDTO persist(RequestPostingServiceImpl requestPostingServiceImpl, RelationalSearchDTO relationalSearchDTO, ProfilesDTO profilesDTO, DataSource dataSource) throws Exception {
		int dtoSize = relationalSearchDTO.getTables().size();
		HashMap<String,ArrayList<HashMap<String, Object>>> request=new HashMap<>();
		HashMap<String,Object> dataMap = new HashMap<String,Object>();
		String requestID=System.nanoTime()+"";
		for (int i = 0; i < dtoSize; i++) {
			String tableName = relationalSearchDTO.getTables().get(i).getTableName();
			tableName = tableName+".";
			ArrayList<HashMap<String,Object>> tableData=new ArrayList<>();
			HashMap<String,Object> row=new HashMap<>();
			
			List<RelationalSearchParameter> relationalSearchParameters  = relationalSearchDTO.getTables().get(i).getRelationalSearchParameter();
			for (RelationalSearchParameter relationalSearchParameter : relationalSearchParameters) {
				//String key = customerSearchParameter.getParameterDisplayName();
				String key = relationalSearchParameter.getEngineName();
				dataMap.put(tableName.concat(key), relationalSearchParameter.getParameterValue());
				row.put(relationalSearchParameter.getEngineName(),relationalSearchParameter.getParameterValue());
			}
			
			row.put("PSX_ID",  requestID);
			tableData.add(row);
			request.put(relationalSearchBO.getTables().get(i).getTableTagName(), tableData);
		}
		
		dataMap.put("PSX_ID",  requestID);
		dataMap.put("PSX_BATCH_ID", new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()));
		dataMap.put("CURRENT_TIME", new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime()));
		dataMap.put("CURRENT_USER", "PSX_USER");
		dataMap.put("REQUEST_STATUS","P");
		dataMap.put("INSERT_TIME", new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(Calendar.getInstance().getTime()));
		
		if(dataMap!=null && dataSource != null) {
			//System.out.println(dataMap + " : " + dataSource);
			//System.out.println("customerSearchBO : " + customerSearchBO);
			Object[] retValue = relationalSearchBO.persist(requestPostingServiceImpl,profilesDTO, dataMap, dataSource, request);
			if(retValue.length>0) {
				relationalSearchDTO.setResultsTableHeader((List<String>)retValue[0]);
				relationalSearchDTO.setResults((List<String[]>)retValue[1]);
			}
		}
		return relationalSearchDTO;
	}

}
