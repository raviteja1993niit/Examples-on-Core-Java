package com.psx.prime360ClientService.dto;

import java.util.Date;

public class ProfileDTO {
	private Integer profileId;
	private String profileName;
	private String profileDescription;
	private Date LCHGTime;
	private String maker;
	private String checker;
	private Date approvalTime;
	private String tenantId;
	private String isApproved;
	private String remarks;
	
	
	public String getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public Date getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getProfileDescription() {
		return profileDescription;
	}
	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	public Date getLCHGTime() {
		return LCHGTime;
	}
	public void setLCHGTime(Date lCHGTime) {
		LCHGTime = lCHGTime;
	}
	@Override
	public String toString() {
		return "ProfileDTO [profileId=" + profileId + ", profileName=" + profileName + ", profileDescription="
				+ profileDescription + ", LCHGTime=" + LCHGTime + ", maker=" + maker + ", checker=" + checker
				+ ", approvalTime=" + approvalTime + ", tenantId=" + tenantId + "]";
	}
	
	
}
