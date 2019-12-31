package com.psx.prime360ClientService.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BulkRequestBean {
	String requestID;
	HashMap<String,ArrayList<HashMap<String,String>>> requestInformation;
	String matchingRule;
	String[] scaleEquations;
	String processType;
	HashMap<String,String> paramMap;
	String requestStatus;
	String sourceSystemName;
	String weightages;
	String residualParams;
	public String getResidualParams() {
		return residualParams;
	}
	public void setResidualParams(String residualParams) {
		this.residualParams = residualParams;
	}
	public BulkRequestBean(String weightages,HashMap<String,ArrayList<HashMap<String,String>>> requestInformation, String matchingRule,String[] scaleEquations,String processType,HashMap<String,String> paramMap,String requestStatus,String requestID,String sourceSystemName) {
		this.weightages=weightages;
		this.requestInformation = requestInformation;
		this.matchingRule = matchingRule;
		this.scaleEquations = scaleEquations;
		this.processType=processType;
		this.paramMap=paramMap;
		this.requestStatus=requestStatus;
		this.requestID=requestID;
		this.sourceSystemName=sourceSystemName;
	}
	public BulkRequestBean(){
		
	}
	public String toJson() {
		GsonBuilder gb=new GsonBuilder();
		gb.disableHtmlEscaping();
		Gson gson = gb.create();
		return gson.toJson(this);
	}

	public static BulkRequestBean fromJson(String json) {
		Gson gson = new Gson();
		BulkRequestBean r1= gson.fromJson(json, BulkRequestBean.class);
		if(r1.getParamMap()==null){
			r1.setParamMap(new HashMap<String,String>());
		}
		return r1;
	}

	public HashMap<String,ArrayList<HashMap<String,String>>> getRequestInformation() {
		return requestInformation;
	}

	public String getMatchingRule() {
		return matchingRule;
	}

	public String[] getScaleEquations() {
		return scaleEquations;
	}
	public String getProcessType(){
		return processType;
	}
	public String getSourceSystemName() {
		return sourceSystemName;
	}
	public static void main(String[] args) throws Exception {
		HashMap<String,String> row1=new HashMap<String,String>();
		/*input.put("NAME", new String[]{"mahi"});
		input.put("PHONE",new String[]{"PHONE_8801726720;PHONETYPE_temp;STD_040"});
		input.put("DOB", new String[]{"28-02-1984"});
		 input.put("ADDRESSCITYPIN", new String[]{"ADDRESS_ameerpet;CITY_hyderabad;PINCODE_500070;HNO_10-3"});
		input.put("EMAIL", new String[]{"EMAILID_ashitha.malla@posidex.com;EMAILTYPE_Office"});
		*/
		row1.put("NAME", "test1");
		row1.put("DOB", "28-02-1984");
		
		//row1.put("EMAIL", "EMAILID_ashitha.malla@posidex.com;EMAILTYPE_Office");
		HashMap<String,String> row2=new HashMap<String,String>();
		row2.put("EMAILID", "ashitha.malla@posidex.com");
		row2.put("EMAILTYPE", "Office");
		
		HashMap<String,String> row3=new HashMap<String,String>();
		row3.put("ADDRESS", "ameerpet");
		row3.put("CITY", "hyderabad");
		row3.put("PINCODE", "500070");
		row3.put("HNO", "10-3");
		
		
		HashMap<String,String> row4=new HashMap<String,String>();
		row4.put("PHONE", "8801726720");
		row4.put("PHONETYPE", "temp");
		row4.put("STD", "040");
		
		
		ArrayList<HashMap<String,String>> dgAl=new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String,String>> emAl=new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String,String>> addrAl=new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String,String>> cntAl=new ArrayList<HashMap<String,String>>();
		dgAl.add(row1);
		emAl.add(row2);
		addrAl.add(row3);
		cntAl.add(row4);
		HashMap<String,ArrayList<HashMap<String,String>>> entry=new HashMap<String,ArrayList<HashMap<String,String>>>();
		entry.put("DG", dgAl);
		entry.put("ADDR", addrAl);
		entry.put("CONTACT", cntAl);
		entry.put("EMAIL", emAl);
		HashMap<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("key1", "value1");
		paramMap.put("key12", "value12");
		paramMap.put("key13", "value13");
		BulkRequestBean r=new BulkRequestBean(null,entry, "(NAME_IN_NAME_WITH_MatchCriteria:3_TMP:60)", new String[]{},"DUMMY",null,"P","PROP113","pan");
		
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
		System.out.println(r.toJson());
		String str = reader.readLine(); 
		BulkRequestBean r1=BulkRequestBean.fromJson(str);
		Gson json=new Gson();
		results res=json.fromJson(r1.getParamMap().get("Results"), results.class);
		//json.
		//System.out.println("+++"+r1.getParamMap().get("Results"));
		//System.out.println(r1.getParamMap());
		//System.out.println(r.toJson());
		System.out.println(res.results.get("offline").get(0).get("id"));
	}
	public class results{
		Map<String,List<Map<String,String>>> results;
	}
	public HashMap<String, String> getParamMap() {
		return paramMap;
	}
	public void setParamMap(HashMap<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public void setRequestInformation(HashMap<String, ArrayList<HashMap<String, String>>> requestInformation) {
		this.requestInformation = requestInformation;
	}
	public void setMatchingRule(String matchingRule) {
		this.matchingRule = matchingRule;
	}
	public void setScaleEquations(String[] scaleEquations) {
		this.scaleEquations = scaleEquations;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}
	@Override
	public String toString() {
		return "RequestBean [requestInformation=" + requestInformation + ", matchingRule=" + matchingRule
				+ ", scaleEquations=" + Arrays.toString(scaleEquations) + ", processType=" + processType
				+ ", sourceSystemName=" + sourceSystemName + "]";
	}
	public String getWeightages() {
		return weightages;
	}
}
