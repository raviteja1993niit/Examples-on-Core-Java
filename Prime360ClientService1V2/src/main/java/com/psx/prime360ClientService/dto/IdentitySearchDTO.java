package com.psx.prime360ClientService.dto;

import java.util.List;

/**
 *
 * @author jayantronald
 *
 */

public class IdentitySearchDTO {
	List<String> identitySearchTableHeader;
	List<String[]> identitySearchTableData;
	String hyperLinkIndex;

	public List<String> getIdentitySearchTableHeader() {
		return identitySearchTableHeader;
	}

	public void setIdentitySearchTableHeader(List<String> identitySearchTableHeader) {
		this.identitySearchTableHeader = identitySearchTableHeader;
	}

	public List<String[]> getIdentitySearchTableData() {
		return identitySearchTableData;
	}

	public void setIdentitySearchTableData(List<String[]> identitySearchTableData) {
		this.identitySearchTableData = identitySearchTableData;
	}

	public String getHyperLinkIndex() {
		return hyperLinkIndex;
	}

	public void setHyperLinkIndex(String hyperLinkIndex) {
		this.hyperLinkIndex = hyperLinkIndex;
	}

	@Override
	public String toString() {
		return "IdentitySearchDTO [identitySearchTableHeader=" + identitySearchTableHeader
				+ ", identitySearchTableData=" + identitySearchTableData + ", hyperLinkIndex=" + hyperLinkIndex + "]";
	}

}
