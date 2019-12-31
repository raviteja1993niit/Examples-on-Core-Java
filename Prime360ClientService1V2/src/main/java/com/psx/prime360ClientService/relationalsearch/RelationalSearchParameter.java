package com.psx.prime360ClientService.relationalsearch;

/**
 *
 * @author jayantronald
 *
 */

public class RelationalSearchParameter {
	String engineName;
	String parameterDisplayName;
	String parameterValue;// Value from text box comes here.

	public String getEngineName() {
		return engineName;
	}

	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}

	public String getParameterDisplayName() {
		return parameterDisplayName;
	}

	public void setParameterDisplayName(String parameterDisplayName) {
		this.parameterDisplayName = parameterDisplayName;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	@Override
	public String toString() {
		return "RelationalSearchParameter [engineName=" + engineName + ", parameterDisplayName=" + parameterDisplayName
				+ ", parameterValue=" + parameterValue + "]";
	}

}
