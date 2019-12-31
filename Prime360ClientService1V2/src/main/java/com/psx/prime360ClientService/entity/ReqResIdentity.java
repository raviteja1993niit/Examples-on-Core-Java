package com.psx.prime360ClientService.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReqResIdentity implements Serializable {

	@Column(name = "PSX_ID")
	private Long psxId;
	@Column(name = "REQUEST_ID")
	private Integer requestId;

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public Long getPsxId() {
		return psxId;
	}

	public void setPsxId(Long psxId) {
		this.psxId = psxId;
	}

	@Override
	public String toString() {
		return "ReqResIdentity [psxId=" + psxId + ", requestId=" + requestId + "]";
	}

}
