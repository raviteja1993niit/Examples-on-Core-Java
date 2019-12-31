package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfileIdentity implements Serializable {

	private static final long serialVersionUID = -1819714867446685899L;
	@Column(name = "PROFILE_ID")
	private Integer profileId;
	@Column(name = "LCHGTIME")
	private Date lchgTime;

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public Date getLchgTime() {
		return lchgTime;
	}

	public void setLchgTime(Date lchgTime) {
		this.lchgTime = lchgTime;
	}

	@Override
	public String toString() {
		return "ProfileIdentity [profileId=" + profileId + ", lchgTime=" + lchgTime + "]";
	}

}
