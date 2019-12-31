package com.psx.prime360ClientService.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@Component
@ConfigurationProperties("app")
public class AppProperties {
	
	private Long tokenExpiry;
	private String signingKey="devglan123r";
	private String tokenPrifix="Bearer";
	private String headerString="Authorization";
	private Long userExpiry;
	/**
	 * @return the tokenExpiry
	 */
	public Long getTokenExpiry() {
		return tokenExpiry;
	}
	/**
	 * @param tokenExpiry the tokenExpiry to set
	 */
	public void setTokenExpiry(Long tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}
	/**
	 * @return the signingKey
	 */
	public String getSigningKey() {
		return signingKey;
	}
	/**
	 * @param signingKey the signingKey to set
	 */
	public void setSigningKey(String signingKey) {
		this.signingKey = signingKey;
	}
	/**
	 * @return the tokenPrifix
	 */
	public String getTokenPrifix() {
		return tokenPrifix;
	}
	/**
	 * @param tokenPrifix the tokenPrifix to set
	 */
	public void setTokenPrifix(String tokenPrifix) {
		this.tokenPrifix = tokenPrifix;
	}
	/**
	 * @return the headerString
	 */
	public String getHeaderString() {
		return headerString;
	}
	/**
	 * @param headerString the headerString to set
	 */
	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}
	/**
	 * @return the userExpiry
	 */
	public Long getUserExpiry() {
		return userExpiry;
	}
	/**
	 * @param userExpiry the userExpiry to set
	 */
	public void setUserExpiry(Long userExpiry) {
		this.userExpiry = userExpiry;
	}
	
	

}
