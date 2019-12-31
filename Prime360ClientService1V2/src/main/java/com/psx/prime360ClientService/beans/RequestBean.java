package com.psx.prime360ClientService.beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class RequestBean {
	String requestID;
	HashMap<String, ArrayList<HashMap<String, Object>>> requestInformation;
	String matchingRule;
	String scaleEquations;
	String processType;
	HashMap<String, String> paramMap;
	String requestStatus;
	String sourceSystemName;
	String weightages;
	String residualParams;
	String rankingOrders;
	String bulkQueryCount;
	String bulkQueryUniqueToken;
	List<String> results;

	public String getResidualParams() {
		return residualParams;
	}

	public void setResidualParams(String residualParams) {
		this.residualParams = residualParams;
	}

	public RequestBean(String weightages, HashMap<String, ArrayList<HashMap<String, Object>>> requestInformation,
			String matchingRule, String scaleEquations, String processType, HashMap<String, String> paramMap,
			String requestStatus, String requestID, String sourceSystemName, String rankingOrders) {
		this.weightages = weightages;
		this.requestInformation = requestInformation;
		this.matchingRule = matchingRule;
		this.scaleEquations = scaleEquations;
		this.processType = processType;
		this.paramMap = paramMap;
		this.requestStatus = requestStatus;
		this.requestID = requestID;
		this.sourceSystemName = sourceSystemName;
		this.rankingOrders = rankingOrders;
		setLCHGTime();
	}

	public RequestBean() {

	}

	public String toJson() {
		/*
		 * GsonBuilder gb=new GsonBuilder(); gb.disableHtmlEscaping(); Gson gson
		 * = gb.create(); return gson.toJson(this);
		 */
		String jsonString = "{\"sourceSystemName\":\"%s\",\"requestID\":\"%s\",\"processType\":\"%s\",\"requestStatus\":\"%s\",\"results\":[%s],\"paramMap\":%s}";
		StringBuilder resultsString = new StringBuilder();
		if(results!=null){
			for (int i = 0; i < results.size(); i++) {
				resultsString.append(results.get(i));
				if (i < (results.size() - 1)) {
					resultsString.append(",");
				}
			}
		}
		int x = 0;
		StringBuilder map = new StringBuilder("{");
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			map.append("\"").append(entry.getKey()).append("\":");
			map.append("\"").append(entry.getValue()).append("\"");
			if (x < (paramMap.size() - 1)) {
				map.append(",");
			}
			x++;
		}
		map.append("}");
		String retValue = String.format(jsonString, sourceSystemName, requestID, processType, requestStatus,
				resultsString, map);
		return retValue;

	}

	public static RequestBean fromJson(String json) {
		Gson gson = new Gson();
		RequestBean r1 = gson.fromJson(json, RequestBean.class);
		if (r1.getParamMap() == null) {
			r1.setParamMap(new HashMap<String, String>());
		}
		r1.setLCHGTime();// In case of JDBCAdapter, timestamp value should not
		// be null. If it is null, timestamp in database
		// will be null and in object, it will be current
		// timestamp.
		r1.setResults(new ArrayList<String>());
		return r1;
	}

	public HashMap<String, ArrayList<HashMap<String, Object>>> getRequestInformation() {
		return requestInformation;
	}

	public String getMatchingRule() {
		return matchingRule;
	}

	public String getScaleEquations() {
		return scaleEquations;
	}

	public String getProcessType() {
		return processType;
	}

	public String getSourceSystemName() {
		return sourceSystemName;
	}

	public static void main(String[] args) {
		HashMap<String, Object> row1 = new HashMap<String, Object>();
		/*
		 * input.put("NAME", new String[]{"mahi"}); input.put("PHONE",new
		 * String[]{"PHONE_8801726720;PHONETYPE_temp;STD_040"});
		 * input.put("DOB", new String[]{"28-02-1984"});
		 * input.put("ADDRESSCITYPIN", new
		 * String[]{"ADDRESS_ameerpet;CITY_hyderabad;PINCODE_500070;HNO_10-3"});
		 * input.put("EMAIL", new
		 * String[]{"EMAILID_ashitha.malla@posidex.com;EMAILTYPE_Office"});
		 */
		row1.put("NAME", "test1");
		row1.put("DOB", "28-02-1984");

		// row1.put("EMAIL",
		// "EMAILID_ashitha.malla@posidex.com;EMAILTYPE_Office");
		HashMap<String, Object> row2 = new HashMap<String, Object>();
		row2.put("EMAILID", "ashitha.malla@posidex.com");
		row2.put("EMAILTYPE", "Office");

		HashMap<String, Object> row3 = new HashMap<String, Object>();
		row3.put("ADDRESS", "ameerpet");
		row3.put("CITY", "hyderabad");
		row3.put("PINCODE", "500070");
		row3.put("HNO", "10-3");

		HashMap<String, Object> row4 = new HashMap<String, Object>();
		row4.put("PHONE", "8801726720");
		row4.put("PHONETYPE", "temp");
		row4.put("STD", "040");

		ArrayList<HashMap<String, Object>> dgAl = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> emAl = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> addrAl = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> cntAl = new ArrayList<HashMap<String, Object>>();
		dgAl.add(row1);
		emAl.add(row2);
		addrAl.add(row3);
		cntAl.add(row4);
		HashMap<String, ArrayList<HashMap<String, Object>>> entry = new HashMap<String, ArrayList<HashMap<String, Object>>>();
		entry.put("DG", dgAl);
		entry.put("ADDR", addrAl);
		entry.put("CONTACT", cntAl);
		entry.put("EMAIL", emAl);
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("key1", "value1");
		paramMap.put("key12", "value12");
		paramMap.put("key13", "value13");
		RequestBean r = new RequestBean(null, entry,
				"RuleNo,Condition,SrcParam,TrgParam,DedupeType,MatchCriteria,NameTMP,BaseLineTypeForAddr,NullConsiderList,ConsiderList,IgnoreList,isToConsiderLast5Digits,Cs1AddrWgt,Cs1HnoWgt,Cs1HnoNullWgt,BaseLineTypeForCs1,Cs1CorrectedValues,Cs2AddrWgt,Cs2HnoWgt,Cs2HnoNullWgt,BaseLineTypeForCs2,Cs2CorrectedValues,Cs3AddrWgt,Cs3HnoWgt,Cs3HnoNullWgt,BaseLineTypeForCs3,Cs3CorrectedValues,intersectAmongEachComponent;Rule0,AND,Name,Name,0,3,85,,,,,,,,,,,,,,,,,,,,,FALSE;QUERYON,,,OFFLINE,,,,,,,,,,,,,,,,,,,,,,,,;",
				null, "DUMMY", null, "P", "PROP113", "pan", "");
		RequestBean r1 = RequestBean.fromJson(r.toJson());
		System.out.println(r1.getParamMap());
		System.out.println(r.toJson());
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

	public void setRequestInformation(HashMap<String, ArrayList<HashMap<String, Object>>> requestInformation) {
		this.requestInformation = requestInformation;
		setLCHGTime();
	}

	public void setMatchingRule(String matchingRule) {
		this.matchingRule = matchingRule;
	}

	public void setScaleEquations(String scaleEquations) {
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

	public String getWeightages() {
		return weightages;
	}

	public String getRankingOrders() {
		return rankingOrders;
	}

	public void setRankingOrders(String rankingOrders) {
		this.rankingOrders = rankingOrders;
	}

	public void setWeightages(String weightages) {
		this.weightages = weightages;
	}

	@Override
	public String toString() {
		return "RequestBean [requestID=" + requestID + ", requestInformation=" + requestInformation + ", matchingRule="
				+ matchingRule + ", scaleEquations=" + scaleEquations + ", processType=" + processType + ", paramMap="
				+ paramMap + ", requestStatus=" + requestStatus + ", sourceSystemName=" + sourceSystemName
				+ ", weightages=" + weightages + ", residualParams=" + residualParams + ", rankingOrders="
				+ rankingOrders + "]";
	}

	public String getBulkQueryCount() {
		return bulkQueryCount;
	}

	public void setBulkQueryCount(String bulkQueryCount) {
		this.bulkQueryCount = bulkQueryCount;
	}

	public String getBulkQueryUniqueToken() {
		return bulkQueryUniqueToken;
	}

	public void setBulkQueryUniqueToken(String bulkQueryUniqueToken) {
		this.bulkQueryUniqueToken = bulkQueryUniqueToken;
	}

	public void setLCHGTime() {
		String LCHGTIME = "LCHGTIME";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if (requestInformation != null) {
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			for (Map.Entry<String, ArrayList<HashMap<String, Object>>> entry : requestInformation.entrySet()) {
				for (HashMap<String, Object> row : entry.getValue()) {
					if (row.get(LCHGTIME) == null || "".equals(row.get(LCHGTIME))) {
						row.put(LCHGTIME, ts);
					} else {
						row.put(LCHGTIME + "Given", row.get(LCHGTIME));
						row.put(LCHGTIME, getSQLTimeStampFromDateString((String) row.get(LCHGTIME)));
					}
				}
			}
		}
	}

	public synchronized void setResult(String result) {
		this.results.add(result);
	}

	public void setResults(List<String> results) {
		this.results = results;
	}

	public static Timestamp getSQLTimeStampFromDateString(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");// "yyyy-M-dd
			// HH:mm:ss"
			return new Timestamp(sdf.parse(date).getTime());
		} catch (Exception e) {
			return new Timestamp(System.currentTimeMillis());
		}
	}
}
