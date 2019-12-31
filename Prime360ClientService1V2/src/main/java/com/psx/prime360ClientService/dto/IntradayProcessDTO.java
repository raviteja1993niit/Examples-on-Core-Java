package com.psx.prime360ClientService.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author sunny
 *
 * 23-Jun-2018
 */
public class IntradayProcessDTO {

	private String requestInformation;

	public String getRequestInformation() {
		return requestInformation;
	}

	public void setRequestInformation(String requestInformation) {
		this.requestInformation = requestInformation;
	}

	@Override
	public String toString() {
		return "IntradayProcessBO [requestInformation=" + requestInformation + "]";
	}

	public String toJson() {
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		Gson gson = gb.create();
		return gson.toJson(this);
	}
}
