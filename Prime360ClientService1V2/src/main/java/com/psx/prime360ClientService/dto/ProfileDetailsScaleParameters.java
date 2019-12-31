package com.psx.prime360ClientService.dto;

import java.io.Serializable;

public class ProfileDetailsScaleParameters implements Comparable<ProfileDetailsScaleParameters>,Serializable {
	
	private static final long serialVersionUID = -6573213500556252183L;
	private String condition;
	private String displayParameter;
	private String engineParameter;
	private String criteria;
	private String operator;
	private String strength;
	private String ruleNo;
	private String scaleType;
	private String classification;
	private String dedupeType;
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDisplayParameter() {
		return displayParameter;
	}

	public void setDisplayParameter(String displayParameter) {
		this.displayParameter = displayParameter;
	}

	public String getEngineParameter() {
		return engineParameter;
	}

	public String getEngineParameterNameSource() {
		return engineParameter.substring(0, engineParameter.indexOf("_IN_"));
	}

	public String getEngineParameterNameTarget() {
		return engineParameter.substring(engineParameter.indexOf("_IN_") + 4);
	}

	public void setEngineParameter(String engineParameter) {
		this.engineParameter = engineParameter;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getScaleType() {
		return scaleType;
	}

	public void setScaleType(String scaleType) {
		this.scaleType = scaleType;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public int compareTo(ProfileDetailsScaleParameters o) {
		return this.getDisplayParameter().compareTo(o.getDisplayParameter());
	}

	public String getDedupeType() {
		return dedupeType;
	}

	public void setDedupeType(String dedupeType) {
		this.dedupeType = dedupeType;
	}

	@Override
	public String toString() {
		return "ProfileDetailsScaleParameters [condition=" + condition + ", displayParameter=" + displayParameter
				+ ", engineParameter=" + engineParameter + ", criteria=" + criteria + ", operator=" + operator
				+ ", strength=" + strength + ", ruleNo=" + ruleNo + ", scaleType=" + scaleType + ", classification="
				+ classification + ", dedupeType=" + dedupeType + "]";
	}

}
