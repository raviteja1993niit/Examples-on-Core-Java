package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PSX_NSP_PROFILES")
public class Profile implements Serializable {

	private static final long serialVersionUID = 2466793342603533308L;
	@EmbeddedId
	private ProfileIdentity profileIdentity;
	@Column(name = "PROFILE_DESC")
	private String profileDescription;
	@Column(name = "PROFILE_NAME")
	private String profileName;
	@Column(name = "ACTIVE")
	private String active;
	@Column(name = "MATCHING_RULE_CSV", length = 4000)
	private String matchingRuleCSV;
	@Column(name = "WEIGHTAGES_CSV", length = 4000)
	private String weightagesCSV;
	@Column(name = "RESIDUAL_PARAMETERS", length = 4000)
	private String residualsCSV;
	@Column(name = "SCALE_STRINGENT_CSV", length = 4000)
	private String scaleStringentCSV;
	@Column(name = "MAKER")
	private String maker;
	@Column(name = "CHECKER")
	private String checker;
	@Column(name = "APPROVAL_TS")
	private Date approvalTs;
	@Column(name = "APPROVED")
	private String approved;

	@Column(name = "TENANT_ID")
	private String tenantId;
	@Column(name = "RANKING_CSV", length = 4000)
	private String rankingCSV;

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
		return "Profile [profileIdentity=" + profileIdentity + ", profileDescription=" + profileDescription
				+ ", profileName=" + profileName + ", active=" + active + ", matchingRuleCSV=" + matchingRuleCSV
				+ ", weightagesCSV=" + weightagesCSV + ", residualsCSV=" + residualsCSV + ", scaleStringentCSV="
				+ scaleStringentCSV + ", maker=" + maker + ", checker=" + checker + ", approvalTs=" + approvalTs
				+ ", approved=" + approved + ", tenantId=" + tenantId + ", rankingCSV=" + rankingCSV + "]";
	}

}
