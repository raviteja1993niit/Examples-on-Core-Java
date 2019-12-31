package com.psx.prime360ClientService.dto;

import java.util.List;

public class RankingDedupeParametersDTO {

	public String uiParameterValue;
	public String engineParameterValue;
	public List<String> displayValue;

	public String getUiParameterValue() {
		return uiParameterValue;
	}

	public void setUiParameterValue(String uiParameterValue) {
		this.uiParameterValue = uiParameterValue;
	}

	public String getEngineParameterValue() {
		return engineParameterValue;
	}

	public void setEngineParameterValue(String engineParameterValue) {
		this.engineParameterValue = engineParameterValue;
	}

	public List<String> getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(List<String> displayValue) {
		this.displayValue = displayValue;
	}

	@Override
	public String toString() {
		return "RankingDedupeParametersDTO [uiParameterValue=" + uiParameterValue + ", engineParameterValue="
				+ engineParameterValue + ", displayValue=" + displayValue + "]";
	}

}
