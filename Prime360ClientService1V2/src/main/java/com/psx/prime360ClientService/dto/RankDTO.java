package com.psx.prime360ClientService.dto;

import java.io.Serializable;

public class RankDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8390847484011276092L;
	private String parameterName;
	private String criteria;
	private String operator;

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "RankDTO [parameterName=" + parameterName + ", criteria=" + criteria + ", operator=" + operator + "]";
	}

}
