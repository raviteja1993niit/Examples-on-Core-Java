package com.psx.prime360ClientService.dto;

import java.io.Serializable;

public class ProfileDetailsWeightageParameters implements Serializable{
	
	private static final long serialVersionUID = 2917044798320764142L;
	private String displayParameter;
	private String engineParameter;
	private String percentage;
	public String getDisplayParameter() {
		return displayParameter;
	}
	public void setDisplayParameter(String displayParameter) {
		this.displayParameter = displayParameter;
	}
	public String getEngineParameter() {
		return engineParameter;
	}
	public void setEngineParameter(String engineParameter) {
		this.engineParameter = engineParameter;
	}
	public String getEngineParameterNameSource(){
		return engineParameter.substring(0,engineParameter.indexOf("_IN_"));
	}
	public String getEngineParameterNameTarget(){
		return engineParameter.substring(engineParameter.indexOf("_IN_")+4);
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	@Override
	public String toString() {
		return "ProfileDetailsWeightageParameters [displayParameter=" + displayParameter + ", engineParameter="
				+ engineParameter + ", percentage=" + percentage + "]";
	}
	
}
