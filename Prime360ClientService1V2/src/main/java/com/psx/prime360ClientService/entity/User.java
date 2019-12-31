package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psx.prime360ClientService.entity.UserIdentity;

/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@Entity
@Table(name="psx_nsp_user")
public class User implements Serializable { 
	private static final long serialVersionUID = -3024444545542109016L;
	@EmbeddedId
    private UserIdentity userIdentity;  
	@Column(name="user_Name")
    private String userName;
	@Column(name="department_name")	
	private String departmentName;
	@Column(name="joining_date")	
	private Date dateOfJoining;
	@Column(name="email_id")	
	private String emailID;
	@Column(name="mobile")	
	private String mobile;
	@Column(name="alternative_mobile")	
	private String alternativeMobile;
	@Column(name="branch_code")	
	private String branchCode;
	@Column(name="classification")	
	private String userClassification;
	@Column(name="reportingManager")	
	private String reportingManager;
	@Column(name="role_ID")	
	private String roleID;
	@Column(name="password")	
	private String password;
	@Column(name="creational_password")	
	private String creationalPassword;
	@Column(name="maker")	
	private String maker;
	@Column(name="checker")	
	private String checker;
	@Column(name="active")
	private String active="Y";
	@Column(name="approval_rejected")	
	private String approvalRejected;
	@Column(name="suspended")//Intentionally suspend a user
	private Integer suspended;	
	@Column(name="expiry_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm", timezone="IST")
	private Date expiryDate;	
	@Column(name="account_expired")
	private Integer accountExpired;	
	@Column(name="account_locked")//Lock an account due to invalid password attempts
	private Integer accountLocked;
	@Column(name="updater")
	private String updater;
	
	public UserIdentity getUserIdentity() {
		return userIdentity;
	}
	public void setUserIdentity(UserIdentity userIdentity) {
		this.userIdentity = userIdentity;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAlternativeMobile() {
		return alternativeMobile;
	}
	public void setAlternativeMobile(String alternativeMobile) {
		this.alternativeMobile = alternativeMobile;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getUserClassification() {
		return userClassification;
	}
	public void setUserClassification(String userClassification) {
		this.userClassification = userClassification;
	}
	public String getReportingManager() {
		return reportingManager;
	}
	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreationalPassword() {
		return creationalPassword;
	}
	public void setCreationalPassword(String creationalPassword) {
		this.creationalPassword = creationalPassword;
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
	public String getApprovalRejected() {
		return approvalRejected;
	}
	public void setApprovalRejected(String approvalRejected) {
		this.approvalRejected = approvalRejected;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Integer getAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(Integer accountExpired) {
		this.accountExpired = accountExpired;
	}
	public Integer getAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(Integer accountLocked) {
		this.accountLocked = accountLocked;
	}
	public String getUserID() {
		return userIdentity.getUserID();
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	public Integer getSuspended() {
		return suspended;
	}
	public void setSuspended(Integer suspended) {
		this.suspended = suspended;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	@Override
	public String toString() {
		return "User [userIdentity=" + userIdentity + ", userName=" + userName + ", departmentName=" + departmentName
				+ ", dateOfJoining=" + dateOfJoining + ", emailID=" + emailID + ", mobile=" + mobile
				+ ", alternativeMobile=" + alternativeMobile + ", branchCode=" + branchCode + ", userClassification="
				+ userClassification + ", reportingManager=" + reportingManager + ", roleID=" + roleID + ", password="
				+ password + ", creationalPassword=" + creationalPassword + ", maker=" + maker + ", checker=" + checker
				+ ", active=" + active + ", approvalRejected=" + approvalRejected + ", suspended=" + suspended
				+ ", expiryDate=" + expiryDate + ", accountExpired=" + accountExpired + ", accountLocked="
				+ accountLocked + "]";
	}
	
	
	
} 
