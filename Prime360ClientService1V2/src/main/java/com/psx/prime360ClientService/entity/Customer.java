package com.psx.prime360ClientService.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tbl_customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "CUSTOMER_NO", unique = true)
	private String customerNo;

	@Column(name = "SOURCE_SYS_ID")
	private String sourceSystemId;

	@Column(name = "DATA_SOURCE_NAME")
	private String dataSourceName;

	@Column(name = "FIRSTNAME")
	private String firstname;
	@Column(name = "MIDDLENAME")
	private String middlename;
	@Column(name = "LASTNAME")
	private String lastname;
	@Column(name = "DOB1")
	private String dob;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PAN")
	private String pan;
	
	
	@Column(name = "DRIVINGLIC")
	private String drivingLicense;

	@Column(name = "AADHAR_NO")
	private String aadharNo;

	@Column(name = "VOTER_ID")
	private String voterId;

	@Column(name = "DATE_OF_INCORPORATION")
	private String dateOfIncorporation;

	@Column(name = "TAN_NO")
	private String tanNo;

	@Column(name = "PROCESS_TYPE")
	private String processType;
	
	
	@Column(name = "APPLICATION_TYPE")
	private String applicationType;

	@Column(name = "EMPLOYER_NAME")
	private String employerName;

	@Column(name = "FATHER_NAME")
	private String fatherName;
	

	@Column(name = "PASSPORT")
	private String passportNo;

	@Column(name = "IFSC_CODE")
	private String ifscCode;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;


	@Column(name = "CREDIT_CARD_NUMBER")
	private String creditCardNumber;

	@Column(name = "CIN")
	private String companyIdentificationNo;

	@Column(name = "DIN")
	private String directorIdentitficationNo;


	@Column(name = "REGISTRATION_NO")
	private String registrationNumber;

	@Column(name = "CONSTITUTION")
	private String constitution;

	@Column(name = "CA_NUMBER")
	private String caNumber;
	
	@Column(name = "AGREEMENT_NUMBER")
	private String agreementNumber;

	@Column(name = "REMARKS")
	private String remarks;

	

	

	private String address1;
	private String address2;
	private String address3;
	
	@Column(name = "PERMANENT_CITY")
	private String city;

	@Column(name = "PERMANENT_PIN")
	private String pin;

	@Column(name = "PERMANENT_STATE")
	private String state;
	
	@Column(name = "PERMANENT_STREET_NUMBER")
	private String areaLocalityRes;

	@Column(name = "LANDMARK")
	private String landmarkRes;

	@Column(name = "OFFICE_1")
	private String office1;

	@Column(name = "OFFICE_2")
	private String office2;

	@Column(name = "OFFICE_3")
	private String office3;

	@Column(name = "PERMANENT_ADDRESS")
	private String permanentAddress;

	@Column(name = "OFF_CITY")
	private String officeCity;

	@Column(name = "OFF_PIN")
	private String officePin;

	@Column(name = "OFF_STATE")
	private String offState;

	@Column(name = "OFFICE_STREET_NUMBER")
	private String officeAreaLocality;
	
	@Column(name = "RESIDENCE_PHONE")
	private String landlineRes1;

	@Column(name = "PERMANENT_PHONE")
	private String landLineRes2;

	@Column(name = "MOBILE")
	private String mobile;
	
	@Column(name = "STD")
	private String std;
	@Column(name = "CONTACT_EMAIL")
	private String email;
	
	@Column(name = "OFFICE_LANDMARK")
	private String officeLandmark;
	@Column(name = "RESIDENT_ADDRESS")
	private String residentAddress;

	@Column(name = "OFFICE_ADDRESS")
	private String officeAddress;
	
	@Column(name = "ERROR_DESC")
	private String error_desc;

	@Column(name = "SEGMENT")
	private String segment;

	@Column(name = "NAME")
	private String name;


	
	
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getError_desc() {
		return error_desc;
	}

	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerId) {
		this.customerNo = customerId;
	}

	public String getSourceSystemId() {
		return sourceSystemId;
	}

	public void setSourceSystemId(String sourceSystemId) {
		this.sourceSystemId = sourceSystemId;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public String getTanNo() {
		return tanNo;
	}

	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCompanyIdentificationNo() {
		return companyIdentificationNo;
	}

	public void setCompanyIdentificationNo(String companyIdentificationNo) {
		this.companyIdentificationNo = companyIdentificationNo;
	}

	public String getDirectorIdentitficationNo() {
		return directorIdentitficationNo;
	}

	public void setDirectorIdentitficationNo(String directorIdentitficationNo) {
		this.directorIdentitficationNo = directorIdentitficationNo;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getConstitution() {
		return constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

	public String getCaNumber() {
		return caNumber;
	}

	public void setCaNumber(String caNumber) {
		this.caNumber = caNumber;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAreaLocalityRes() {
		return areaLocalityRes;
	}

	public void setAreaLocalityRes(String areaLocalityRes) {
		this.areaLocalityRes = areaLocalityRes;
	}

	public String getLandmarkRes() {
		return landmarkRes;
	}

	public void setLandmarkRes(String landmarkRes) {
		this.landmarkRes = landmarkRes;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}

	public String getOffice1() {
		return office1;
	}

	public void setOffice1(String office1) {
		this.office1 = office1;
	}

	public String getOffice2() {
		return office2;
	}

	public void setOffice2(String office2) {
		this.office2 = office2;
	}

	public String getOffice3() {
		return office3;
	}

	public void setOffice3(String office3) {
		this.office3 = office3;
	}

	public String getOfficeCity() {
		return officeCity;
	}

	public void setOfficeCity(String officeCity) {
		this.officeCity = officeCity;
	}

	public String getOfficePin() {
		return officePin;
	}

	public void setOfficePin(String officePin) {
		this.officePin = officePin;
	}

	public String getOffState() {
		return offState;
	}

	public void setOffState(String offState) {
		this.offState = offState;
	}

	public String getOfficeAreaLocality() {
		return officeAreaLocality;
	}

	public void setOfficeAreaLocality(String officeAreaLocality) {
		this.officeAreaLocality = officeAreaLocality;
	}

	public String getLandlineRes1() {
		return landlineRes1;
	}

	public void setLandlineRes1(String landlineRes1) {
		this.landlineRes1 = landlineRes1;
	}

	public String getLandLineRes2() {
		return landLineRes2;
	}

	public void setLandLineRes2(String landLineRes2) {
		this.landLineRes2 = landLineRes2;
	}

	public String getOfficeLandmark() {
		return officeLandmark;
	}

	public void setOfficeLandmark(String officeLandmark) {
		this.officeLandmark = officeLandmark;
	}

	public String getStd() {
		return std;
	}

	public void setStd(String std) {
		this.std = std;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerNo=" + customerNo
				+ ", sourceSystemId=" + sourceSystemId + ", dataSourceName="
				+ dataSourceName + ", firstname=" + firstname + ", middlename="
				+ middlename + ", lastname=" + lastname+ ", gender=" + gender + ", dob=" + dob
				 + ", pan=" + pan + ", drivingLicense="
				+ drivingLicense + ", aadharNo=" + aadharNo + ", voterId="
				+ voterId + ", dateOfIncorporation=" + dateOfIncorporation
				+ ", tanNo=" + tanNo + ", processType=" + processType
				+ ", applicationType=" + applicationType + ", employerName="
				+ employerName + ", fatherName=" + fatherName + ", passportNo="
				+ passportNo + ", ifscCode=" + ifscCode + ", accountNumber="
				+ accountNumber + ", creditCardNumber=" + creditCardNumber
				+ ", companyIdentificationNo=" + companyIdentificationNo
				+ ", directorIdentitficationNo=" + directorIdentitficationNo
				+ ", registrationNumber=" + registrationNumber
				+ ", constitution=" + constitution + ", caNumber=" + caNumber
				+ ", agreementNumber=" + agreementNumber + ", remarks="
				+ remarks + ", address1=" + address1 + ", address2=" + address2
				+ ", address3=" + address3 + ", city=" + city + ", pin=" + pin
				+ ", state=" + state + ", areaLocalityRes=" + areaLocalityRes
				+ ", landmarkRes=" + landmarkRes + ", office1=" + office1
				+ ", office2=" + office2 + ", office3=" + office3
				+ ", permanentAddress=" + permanentAddress + ", officeCity="
				+ officeCity + ", officePin=" + officePin + ", offState="
				+ offState + ", officeAreaLocality=" + officeAreaLocality
				+ ", landlineRes1=" + landlineRes1 + ", landLineRes2="
				+ landLineRes2 + ", mobile=" + mobile +  ", officeLandmark=" + officeLandmark
				+", std=" + std
				+ ", email=" + email + ", residentAddress=" + residentAddress + ", officeAddress="
				+ officeAddress + ", error_desc=" + error_desc + ", segment="
				+ segment + ", name=" + name + "]";
	}

	


}
