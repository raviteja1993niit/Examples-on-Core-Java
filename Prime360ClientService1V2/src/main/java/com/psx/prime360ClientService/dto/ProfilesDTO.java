package com.psx.prime360ClientService.dto;

import java.util.Date;

import com.psx.prime360ClientService.entity.ProfileIdentity;

public class ProfilesDTO {

	private ProfileIdentity profileIdentity;
	private String profileDescription;
	private String profileName;
	private String active;
	private String matchingRuleCSV;
	private String weightagesCSV;
	private String residualsCSV;
	private String scaleStringentCSV;
	private String maker;
	private String checker;
	private Date approvalTs;
	private String tenantId;
	private String rankingCSV;
	private String approved;

	public ProfileIdentity getProfileIdentity() {
		return profileIdentity;
	}

	public void setProfileIdentity(ProfileIdentity profileIdentity) {
		this.profileIdentity = profileIdentity;
	}

	public String getProfileDescription() {
		return profileDescription;
	}

	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getMatchingRuleCSV() {
		return matchingRuleCSV;
	}

	public void setMatchingRuleCSV(String matchingRuleCSV) {
		this.matchingRuleCSV = matchingRuleCSV;
	}

	public String getWeightagesCSV() {
		return weightagesCSV;
	}

	public void setWeightagesCSV(String weightagesCSV) {
		this.weightagesCSV = weightagesCSV;
	}

	public String getResidualsCSV() {
		return residualsCSV;
	}

	public void setResidualsCSV(String residualsCSV) {
		this.residualsCSV = residualsCSV;
	}

	public String getScaleStringentCSV() {
		return scaleStringentCSV;
	}

	public void setScaleStringentCSV(String scaleStringentCSV) {
		this.scaleStringentCSV = scaleStringentCSV;
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

	public Date getApprovalTs() {
		return approvalTs;
	}

	public void setApprovalTs(Date approvalTs) {
		this.approvalTs = approvalTs;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getRankingCSV() {
		return rankingCSV;
	}

	public void setRankingCSV(String rankingCSV) {
		this.rankingCSV = rankingCSV;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "ProfilesDTO [profileIdentity=" + profileIdentity + ", profileDescription=" + profileDescription
				+ ", profileName=" + profileName + ", active=" + active + ", matchingRuleCSV=" + matchingRuleCSV
				+ ", weightagesCSV=" + weightagesCSV + ", residualsCSV=" + residualsCSV + ", scaleStringentCSV="
				+ scaleStringentCSV + ", maker=" + maker + ", checker=" + checker + ", approvalTs=" + approvalTs
				+ ", tenantId=" + tenantId + ", rankingCSV=" + rankingCSV + ", approved=" + approved + "]";
	}

}
