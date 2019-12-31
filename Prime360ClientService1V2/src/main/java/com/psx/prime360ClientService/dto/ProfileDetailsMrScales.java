package com.psx.prime360ClientService.dto;

import java.util.List;

public class ProfileDetailsMrScales {
	private Integer ruleNumber;
	private String ruleName;
	private List<ProfileDetailsScaleParameters> ruleParameters;
	public Integer getRuleNumber() {
		return ruleNumber;
	}
	public void setRuleNumber(Integer ruleNumber) {
		this.ruleNumber = ruleNumber;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public List<ProfileDetailsScaleParameters> getRuleParameters() {
		return ruleParameters;
	}
	public void setRuleParameters(List<ProfileDetailsScaleParameters> ruleParameters) {
		this.ruleParameters = ruleParameters;
	}
	@Override
	public String toString() {
		return "ProfileDetailsMrScales [ruleNumber=" + ruleNumber + ", ruleName=" + ruleName + ", ruleParameters="
				+ ruleParameters + "]";
	}
	
}
