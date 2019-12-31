package com.posidex.eod.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {

	private int id;

	private String customerNo;

	private String sourceSystemId;

	private String dataSourceName;

	private String firstName;

	private String middleName;

	private String lastName;

	private String day;

	private String month;

	private String year;

	private String gender;

	private String email;

	private Date dob;

	private String drivingLicense;

	private String pan;

	private String aadharNo;

	private String voterId;

	private Date dateOfIncorporation;

	private String tanNo;

	private String processType;

	private String applicationType;

	private String employerName;

	private String fatherName;

	private String passportNo;

	private String ifscCode;

	private String accountNumber;

	private String creditCardNumber;

	private String companyIdentificationNo;

	private String directorIdentitficationNo;

	private String registrationNumber;

	private String constitution;

	private String caNumber;

	private String agreementNumber;

	private String remarks;

	private String city;

	private String pin;

	private String state;

	private String areaLocalityRes;

	private String landmarkRes;

	private String residentAddress;

	private String office1;

	private String office2;

	private String office3;

	private String officeAreaLocality;

	private String landlineRes1;

	private String landLineRes2;

	private String officeLandmark;

	private String std;

	private String eodBatchId;

	private String name;
	

	// Permanent Details
	private String permanentAddress;
	private String permanentCity;
	private String permanentPin;
	private String permanentState;
	private String permanentCountry;
	private String permanentPhone;
	private String permanentEmail;

	private String contactAddress;
	private String contactCity;
	private String contactPin;
	private String contactState;
	private String contactCountry;
	private String contactPhone;
	private String contactEmail;

	private String emergencyAddress;
	private String emergencyCity;
	private String emergencyPin;
	private String emergencyState;
	private String emergencyCountry;
	private String emergencyPhone;
	private String emergencyEmail;

	private String officeAddress;
	private String officeCity;
	private String officePin;
	private String officeState;
	private String officeCountry;
	private String officePhone;
	private String officeEmail;

	private String postalAddress;
	private String postalCity;
	private String postalPin;
	private String postalState;
	private String postalCountry;
	private String postalPhone;
	private String postalEmail;

	private String preferredAddress;
	private String preferredCity;
	private String preferredPin;
	private String preferredState;
	private String preferredCountry;
	private String preferredPhone;
	private String preferredEmail;

	private String presentAddress;
	private String presentCity;
	private String presentPin;
	private String presentState;
	private String presentCountry;
	private String presentPhone;
	private String presentEmail;

	private String residenceAddress;
	private String residenceCity;
	private String residencePin;
	private String residenceState;
	private String residenceCountry;
	private String residencePhone;
	private String residenceEmail;

	private String temporaryAddress;
	private String temporaryCity;
	private String temporaryPin;
	private String temporaryState;
	private String temporaryCountry;
	private String temporaryPhone;
	private String temporaryEmail;

	private String fillerString1;
	private String fillerString2;
	private String fillerString3;
	private String fillerString4;
	private String fillerString5;
	private String fillerString6;
	private String fillerString7;
	private String fillerString8;
	private String fillerString9;

	private long fillerNumber1;
	private long fillerNumber2;
	private long fillerNumber3;
	private long fillerNumber4;
	private long fillerNumber5;

	private Date fillerDate1;
	private Date fillerDate2;

	private String processFlag;
	private String customerId;
	private String segment;

	private String custUnqId;
	private String duiFlag;

	private String errorCode;
	private String errorDescription;

	private String office_std_code;
	private String permanent_std_code;
	private String present_std_code;
	private String emergency_std_code;
	private String contact_std_code;
	private String residence_std_code;
	private String preferred_std_code;
	private String temporary_std_code;
	private String postal_std_code;

	private String office_address_type;
	private String permanent_address_type;
	private String present_address_type;
	private String emergency_address_type;
	private String contact_address_type;
	private String residence_address_type;
	private String preferred_address_type;
	private String temporary_address_type;
	private String postal_address_type;
	private String cin;
	private String din;
	private Date doi;
	private String rationcardnumber;
	private String sourcesystem;
	private String phone;
	private String psxid;
	private String batchid;
	private String mobileno;
	private String psxbatchid;
	private Timestamp insertTime;
	private Timestamp lchgtime;

	public Timestamp getLchgtime() {
		return lchgtime;
	}

	public void setLchgtime(Timestamp lchgtime) {
		this.lchgtime = lchgtime;
	}
	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public String getPsxbatchid() {
		return psxbatchid;
	}

	public void setPsxbatchid(String psxbatchid) {
		this.psxbatchid = psxbatchid;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getEmergency_address_type() {
		return emergency_address_type;
	}

	public String getPsxid() {
		return psxid;
	}

	public void setPsxid(String psxid) {
		this.psxid = psxid;
	}

	public String getRationcardnumber() {
		return rationcardnumber;
	}

	public void setRationcardnumber(String rationcardnumber) {
		this.rationcardnumber = rationcardnumber;
	}

	public String getSourcesystem() {
		return sourcesystem;
	}

	public void setSourcesystem(String sourcesystem) {
		this.sourcesystem = sourcesystem;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDoi() {
		return doi;
	}

	public void setDoi(Date doi) {
		this.doi = doi;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getDin() {
		return din;
	}

	public void setDin(String din) {
		this.din = din;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setDateOfIncorporation(Date dateOfIncorporation) {
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

	public Date getDateOfIncorporation() {
		return dateOfIncorporation;
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

	public String getEodBatchId() {
		return eodBatchId;
	}

	public void setEodBatchId(String eodBatchId) {
		this.eodBatchId = eodBatchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getPermanentCity() {
		return permanentCity;
	}

	public void setPermanentCity(String permanentCity) {
		this.permanentCity = permanentCity;
	}

	public String getPermanentPin() {
		return permanentPin;
	}

	public void setPermanentPin(String permanentPin) {
		this.permanentPin = permanentPin;
	}

	public String getPermanentState() {
		return permanentState;
	}

	public void setPermanentState(String permanentState) {
		this.permanentState = permanentState;
	}

	public String getPermanentCountry() {
		return permanentCountry;
	}

	public void setPermanentCountry(String permanentCountry) {
		this.permanentCountry = permanentCountry;
	}

	public String getPermanentPhone() {
		return permanentPhone;
	}

	public void setPermanentPhone(String permanentPhone) {
		this.permanentPhone = permanentPhone;
	}

	public String getPermanentEmail() {
		return permanentEmail;
	}

	public void setPermanentEmail(String permanentEmail) {
		this.permanentEmail = permanentEmail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactCity() {
		return contactCity;
	}

	public void setContactCity(String contactCity) {
		this.contactCity = contactCity;
	}

	public String getContactPin() {
		return contactPin;
	}

	public void setContactPin(String contactPin) {
		this.contactPin = contactPin;
	}

	public String getContactState() {
		return contactState;
	}

	public void setContactState(String contactState) {
		this.contactState = contactState;
	}

	public String getContactCountry() {
		return contactCountry;
	}

	public void setContactCountry(String contactCountry) {
		this.contactCountry = contactCountry;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getEmergencyAddress() {
		return emergencyAddress;
	}

	public void setEmergencyAddress(String emergencyAddress) {
		this.emergencyAddress = emergencyAddress;
	}

	public String getEmergencyCity() {
		return emergencyCity;
	}

	public void setEmergencyCity(String emergencyCity) {
		this.emergencyCity = emergencyCity;
	}

	public String getEmergencyPin() {
		return emergencyPin;
	}

	public void setEmergencyPin(String emergencyPin) {
		this.emergencyPin = emergencyPin;
	}

	public String getEmergencyState() {
		return emergencyState;
	}

	public void setEmergencyState(String emergencyState) {
		this.emergencyState = emergencyState;
	}

	public String getEmergencyCountry() {
		return emergencyCountry;
	}

	public void setEmergencyCountry(String emergencyCountry) {
		this.emergencyCountry = emergencyCountry;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	public String getEmergencyEmail() {
		return emergencyEmail;
	}

	public void setEmergencyEmail(String emergencyEmail) {
		this.emergencyEmail = emergencyEmail;
	}

	public String getOfficeState() {
		return officeState;
	}

	public void setOfficeState(String officeState) {
		this.officeState = officeState;
	}

	public String getOfficeCountry() {
		return officeCountry;
	}

	public void setOfficeCountry(String officeCountry) {
		this.officeCountry = officeCountry;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getOfficeEmail() {
		return officeEmail;
	}

	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPostalCity() {
		return postalCity;
	}

	public void setPostalCity(String postalCity) {
		this.postalCity = postalCity;
	}

	public String getPostalPin() {
		return postalPin;
	}

	public void setPostalPin(String postalPin) {
		this.postalPin = postalPin;
	}

	public String getPostalState() {
		return postalState;
	}

	public void setPostalState(String postalState) {
		this.postalState = postalState;
	}

	public String getPostalCountry() {
		return postalCountry;
	}

	public void setPostalCountry(String postalCountry) {
		this.postalCountry = postalCountry;
	}

	public String getPostalPhone() {
		return postalPhone;
	}

	public void setPostalPhone(String postalPhone) {
		this.postalPhone = postalPhone;
	}

	public String getPostalEmail() {
		return postalEmail;
	}

	public void setPostalEmail(String postalEmail) {
		this.postalEmail = postalEmail;
	}

	public String getPreferredAddress() {
		return preferredAddress;
	}

	public void setPreferredAddress(String preferredAddress) {
		this.preferredAddress = preferredAddress;
	}

	public String getPreferredCity() {
		return preferredCity;
	}

	public void setPreferredCity(String preferredCity) {
		this.preferredCity = preferredCity;
	}

	public String getPreferredPin() {
		return preferredPin;
	}

	public void setPreferredPin(String preferredPin) {
		this.preferredPin = preferredPin;
	}

	public String getPreferredState() {
		return preferredState;
	}

	public void setPreferredState(String preferredState) {
		this.preferredState = preferredState;
	}

	public String getPreferredCountry() {
		return preferredCountry;
	}

	public void setPreferredCountry(String preferredCountry) {
		this.preferredCountry = preferredCountry;
	}

	public String getPreferredPhone() {
		return preferredPhone;
	}

	public void setPreferredPhone(String preferredPhone) {
		this.preferredPhone = preferredPhone;
	}

	public String getPreferredEmail() {
		return preferredEmail;
	}

	public void setPreferredEmail(String preferredEmail) {
		this.preferredEmail = preferredEmail;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getPresentCity() {
		return presentCity;
	}

	public void setPresentCity(String presentCity) {
		this.presentCity = presentCity;
	}

	public String getPresentPin() {
		return presentPin;
	}

	public void setPresentPin(String presentPin) {
		this.presentPin = presentPin;
	}

	public String getPresentState() {
		return presentState;
	}

	public void setPresentState(String presentState) {
		this.presentState = presentState;
	}

	public String getPresentCountry() {
		return presentCountry;
	}

	public void setPresentCountry(String presentCountry) {
		this.presentCountry = presentCountry;
	}

	public String getPresentPhone() {
		return presentPhone;
	}

	public void setPresentPhone(String presentPhone) {
		this.presentPhone = presentPhone;
	}

	public String getPresentEmail() {
		return presentEmail;
	}

	public void setPresentEmail(String presentEmail) {
		this.presentEmail = presentEmail;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getResidenceCity() {
		return residenceCity;
	}

	public void setResidenceCity(String residenceCity) {
		this.residenceCity = residenceCity;
	}

	public String getResidencePin() {
		return residencePin;
	}

	public void setResidencePin(String residencePin) {
		this.residencePin = residencePin;
	}

	public String getResidenceState() {
		return residenceState;
	}

	public void setResidenceState(String residenceState) {
		this.residenceState = residenceState;
	}

	public String getResidenceCountry() {
		return residenceCountry;
	}

	public void setResidenceCountry(String residenceCountry) {
		this.residenceCountry = residenceCountry;
	}

	public String getResidencePhone() {
		return residencePhone;
	}

	public void setResidencePhone(String residencePhone) {
		this.residencePhone = residencePhone;
	}

	public String getResidenceEmail() {
		return residenceEmail;
	}

	public void setResidenceEmail(String residenceEmail) {
		this.residenceEmail = residenceEmail;
	}

	public String getTemporaryAddress() {
		return temporaryAddress;
	}

	public void setTemporaryAddress(String temporaryAddress) {
		this.temporaryAddress = temporaryAddress;
	}

	public String getTemporaryCity() {
		return temporaryCity;
	}

	public void setTemporaryCity(String temporaryCity) {
		this.temporaryCity = temporaryCity;
	}

	public String getTemporaryPin() {
		return temporaryPin;
	}

	public void setTemporaryPin(String temporaryPin) {
		this.temporaryPin = temporaryPin;
	}

	public String getTemporaryState() {
		return temporaryState;
	}

	public void setTemporaryState(String temporaryState) {
		this.temporaryState = temporaryState;
	}

	public String getTemporaryCountry() {
		return temporaryCountry;
	}

	public void setTemporaryCountry(String temporaryCountry) {
		this.temporaryCountry = temporaryCountry;
	}

	public String getTemporaryPhone() {
		return temporaryPhone;
	}

	public void setTemporaryPhone(String temporaryPhone) {
		this.temporaryPhone = temporaryPhone;
	}

	public String getTemporaryEmail() {
		return temporaryEmail;
	}

	public void setTemporaryEmail(String temporaryEmail) {
		this.temporaryEmail = temporaryEmail;
	}

	public String getFillerString1() {
		return fillerString1;
	}

	public void setFillerString1(String fillerString1) {
		this.fillerString1 = fillerString1;
	}

	public String getFillerString2() {
		return fillerString2;
	}

	public void setFillerString2(String fillerString2) {
		this.fillerString2 = fillerString2;
	}

	public String getFillerString3() {
		return fillerString3;
	}

	public void setFillerString3(String fillerString3) {
		this.fillerString3 = fillerString3;
	}

	public String getFillerString4() {
		return fillerString4;
	}

	public void setFillerString4(String fillerString4) {
		this.fillerString4 = fillerString4;
	}

	public String getFillerString5() {
		return fillerString5;
	}

	public void setFillerString5(String fillerString5) {
		this.fillerString5 = fillerString5;
	}

	public String getFillerString6() {
		return fillerString6;
	}

	public void setFillerString6(String fillerString6) {
		this.fillerString6 = fillerString6;
	}

	public String getFillerString7() {
		return fillerString7;
	}

	public void setFillerString7(String fillerString7) {
		this.fillerString7 = fillerString7;
	}

	public String getFillerString8() {
		return fillerString8;
	}

	public void setFillerString8(String fillerString8) {
		this.fillerString8 = fillerString8;
	}

	public String getFillerString9() {
		return fillerString9;
	}

	public void setFillerString9(String fillerString9) {
		this.fillerString9 = fillerString9;
	}

	public long getFillerNumber1() {
		return fillerNumber1;
	}

	public void setFillerNumber1(long fillerNumber1) {
		this.fillerNumber1 = fillerNumber1;
	}

	public long getFillerNumber2() {
		return fillerNumber2;
	}

	public void setFillerNumber2(long fillerNumber2) {
		this.fillerNumber2 = fillerNumber2;
	}

	public long getFillerNumber3() {
		return fillerNumber3;
	}

	public void setFillerNumber3(long fillerNumber3) {
		this.fillerNumber3 = fillerNumber3;
	}

	public long getFillerNumber4() {
		return fillerNumber4;
	}

	public void setFillerNumber4(long fillerNumber4) {
		this.fillerNumber4 = fillerNumber4;
	}

	public long getFillerNumber5() {
		return fillerNumber5;
	}

	public void setFillerNumber5(long fillerNumber5) {
		this.fillerNumber5 = fillerNumber5;
	}

	public Date getFillerDate1() {
		return fillerDate1;
	}

	public void setFillerDate1(Date fillerDate1) {
		this.fillerDate1 = fillerDate1;
	}

	public Date getFillerDate2() {
		return fillerDate2;
	}

	public void setFillerDate2(Date fillerDate2) {
		this.fillerDate2 = fillerDate2;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDuiFlag() {
		return duiFlag;
	}

	public void setDuiFlag(String duiFlag) {
		this.duiFlag = duiFlag;
	}

	public String getCustUnqId() {
		return custUnqId;
	}

	public void setCustUnqId(String custUnqId) {
		this.custUnqId = custUnqId;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getOffice_std_code() {
		return office_std_code;
	}

	public void setOffice_std_code(String office_std_code) {
		this.office_std_code = office_std_code;
	}

	public String getPermanent_std_code() {
		return permanent_std_code;
	}

	public void setPermanent_std_code(String permanent_std_code) {
		this.permanent_std_code = permanent_std_code;
	}

	public String getPresent_std_code() {
		return present_std_code;
	}

	public void setPresent_std_code(String present_std_code) {
		this.present_std_code = present_std_code;
	}

	public String getEmergency_std_code() {
		return emergency_std_code;
	}

	public void setEmergency_std_code(String emergency_std_code) {
		this.emergency_std_code = emergency_std_code;
	}

	public String getContact_std_code() {
		return contact_std_code;
	}

	public void setContact_std_code(String contact_std_code) {
		this.contact_std_code = contact_std_code;
	}

	public String getResidence_std_code() {
		return residence_std_code;
	}

	public void setResidence_std_code(String residence_std_code) {
		this.residence_std_code = residence_std_code;
	}

	public String getPreferred_std_code() {
		return preferred_std_code;
	}

	public void setPreferred_std_code(String preferred_std_code) {
		this.preferred_std_code = preferred_std_code;
	}

	public String getTemporary_std_code() {
		return temporary_std_code;
	}

	public void setTemporary_std_code(String temporary_std_code) {
		this.temporary_std_code = temporary_std_code;
	}

	public String getPostal_std_code() {
		return postal_std_code;
	}

	public void setPostal_std_code(String postal_std_code) {
		this.postal_std_code = postal_std_code;
	}

	public String getOffice_address_type() {
		return office_address_type;
	}

	public void setOffice_address_type(String office_address_type) {
		this.office_address_type = office_address_type;
	}

	public String getPermanent_address_type() {
		return permanent_address_type;
	}

	public void setPermanent_address_type(String permanent_address_type) {
		this.permanent_address_type = permanent_address_type;
	}

	public String getPresent_address_type() {
		return present_address_type;
	}

	public void setPresent_address_type(String present_address_type) {
		this.present_address_type = present_address_type;
	}

	public String getEmergency__address_type() {
		return emergency_address_type;
	}

	public void setEmergency_address_type(String emergency__address_type) {
		this.emergency_address_type = emergency__address_type;
	}

	public String getContact_address_type() {
		return contact_address_type;
	}

	public void setContact_address_type(String contact_address_type) {
		this.contact_address_type = contact_address_type;
	}

	public String getResidence_address_type() {
		return residence_address_type;
	}

	public void setResidence_address_type(String residence_address_type) {
		this.residence_address_type = residence_address_type;
	}

	public String getPreferred_address_type() {
		return preferred_address_type;
	}

	public void setPreferred_address_type(String preferred_address_type) {
		this.preferred_address_type = preferred_address_type;
	}

	public String getTemporary_address_type() {
		return temporary_address_type;
	}

	public void setTemporary_address_type(String temporary_address_type) {
		this.temporary_address_type = temporary_address_type;
	}

	public String getPostal_address_type() {
		return postal_address_type;
	}

	public void setPostal_address_type(String postal_address_type) {
		this.postal_address_type = postal_address_type;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerNo=" + customerNo
				+ ", sourceSystemId=" + sourceSystemId + ", dataSourceName="
				+ dataSourceName + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", day=" + day
				+ ", month=" + month + ", year=" + year + ", gender=" + gender
				+ ", email=" + email + ", dob=" + dob + ", drivingLicense="
				+ drivingLicense + ", pan=" + pan + ", aadharNo=" + aadharNo
				+ ", voterId=" + voterId + ", dateOfIncorporation="
				+ dateOfIncorporation + ", tanNo=" + tanNo + ", processType="
				+ processType + ", applicationType=" + applicationType
				+ ", employerName=" + employerName + ", fatherName="
				+ fatherName + ", passportNo=" + passportNo + ", ifscCode="
				+ ifscCode + ", accountNumber=" + accountNumber
				+ ", creditCardNumber=" + creditCardNumber
				+ ", companyIdentificationNo=" + companyIdentificationNo
				+ ", directorIdentitficationNo=" + directorIdentitficationNo
				+ ", registrationNumber=" + registrationNumber
				+ ", constitution=" + constitution + ", caNumber=" + caNumber
				+ ", agreementNumber=" + agreementNumber + ", remarks="
				+ remarks + ", city=" + city + ", pin=" + pin + ", state="
				+ state + ", areaLocalityRes=" + areaLocalityRes
				+ ", landmarkRes=" + landmarkRes + ", residentAddress="
				+ residentAddress + ", office1=" + office1 + ", office2="
				+ office2 + ", office3=" + office3 + ", officeAreaLocality="
				+ officeAreaLocality + ", landlineRes1=" + landlineRes1
				+ ", landLineRes2=" + landLineRes2 + ", officeLandmark="
				+ officeLandmark + ", std=" + std + ", eodBatchId="
				+ eodBatchId + ", name=" + name + ", permanentAddress="
				+ permanentAddress + ", permanentCity=" + permanentCity
				+ ", permanentPin=" + permanentPin + ", permanentState="
				+ permanentState + ", permanentCountry=" + permanentCountry
				+ ", permanentPhone=" + permanentPhone + ", permanentEmail="
				+ permanentEmail + ", contactAddress=" + contactAddress
				+ ", contactCity=" + contactCity + ", contactPin=" + contactPin
				+ ", contactState=" + contactState + ", contactCountry="
				+ contactCountry + ", contactPhone=" + contactPhone
				+ ", contactEmail=" + contactEmail + ", emergencyAddress="
				+ emergencyAddress + ", emergencyCity=" + emergencyCity
				+ ", emergencyPin=" + emergencyPin + ", emergencyState="
				+ emergencyState + ", emergencyCountry=" + emergencyCountry
				+ ", emergencyPhone=" + emergencyPhone + ", emergencyEmail="
				+ emergencyEmail + ", officeAddress=" + officeAddress
				+ ", officeCity=" + officeCity + ", officePin=" + officePin
				+ ", officeState=" + officeState + ", officeCountry="
				+ officeCountry + ", officePhone=" + officePhone
				+ ", officeEmail=" + officeEmail + ", postalAddress="
				+ postalAddress + ", postalCity=" + postalCity + ", postalPin="
				+ postalPin + ", postalState=" + postalState
				+ ", postalCountry=" + postalCountry + ", postalPhone="
				+ postalPhone + ", postalEmail=" + postalEmail
				+ ", preferredAddress=" + preferredAddress + ", preferredCity="
				+ preferredCity + ", preferredPin=" + preferredPin
				+ ", preferredState=" + preferredState + ", preferredCountry="
				+ preferredCountry + ", preferredPhone=" + preferredPhone
				+ ", preferredEmail=" + preferredEmail + ", presentAddress="
				+ presentAddress + ", presentCity=" + presentCity
				+ ", presentPin=" + presentPin + ", presentState="
				+ presentState + ", presentCountry=" + presentCountry
				+ ", presentPhone=" + presentPhone + ", presentEmail="
				+ presentEmail + ", residenceAddress=" + residenceAddress
				+ ", residenceCity=" + residenceCity + ", residencePin="
				+ residencePin + ", residenceState=" + residenceState
				+ ", residenceCountry=" + residenceCountry
				+ ", residencePhone=" + residencePhone + ", residenceEmail="
				+ residenceEmail + ", temporaryAddress=" + temporaryAddress
				+ ", temporaryCity=" + temporaryCity + ", temporaryPin="
				+ temporaryPin + ", temporaryState=" + temporaryState
				+ ", temporaryCountry=" + temporaryCountry
				+ ", temporaryPhone=" + temporaryPhone + ", temporaryEmail="
				+ temporaryEmail + ", fillerString1=" + fillerString1
				+ ", fillerString2=" + fillerString2 + ", fillerString3="
				+ fillerString3 + ", fillerString4=" + fillerString4
				+ ", fillerString5=" + fillerString5 + ", fillerString6="
				+ fillerString6 + ", fillerString7=" + fillerString7
				+ ", fillerString8=" + fillerString8 + ", fillerString9="
				+ fillerString9 + ", fillerNumber1=" + fillerNumber1
				+ ", fillerNumber2=" + fillerNumber2 + ", fillerNumber3="
				+ fillerNumber3 + ", fillerNumber4=" + fillerNumber4
				+ ", fillerNumber5=" + fillerNumber5 + ", fillerDate1="
				+ fillerDate1 + ", fillerDate2=" + fillerDate2
				+ ", processFlag=" + processFlag + ", customerId=" + customerId
				+ ", segment=" + segment + ", custUnqId=" + custUnqId
				+ ", duiFlag=" + duiFlag + ", errorCode=" + errorCode
				+ ", errorDescription=" + errorDescription
				+ ", office_std_code=" + office_std_code
				+ ", permanent_std_code=" + permanent_std_code
				+ ", present_std_code=" + present_std_code
				+ ", emergency_std_code=" + emergency_std_code
				+ ", contact_std_code=" + contact_std_code
				+ ", residence_std_code=" + residence_std_code
				+ ", preferred_std_code=" + preferred_std_code
				+ ", temporary_std_code=" + temporary_std_code
				+ ", postal_std_code=" + postal_std_code
				+ ", office_address_type=" + office_address_type
				+ ", permanent_address_type=" + permanent_address_type
				+ ", present_address_type=" + present_address_type
				+ ", emergency_address_type=" + emergency_address_type
				+ ", contact_address_type=" + contact_address_type
				+ ", residence_address_type=" + residence_address_type
				+ ", preferred_address_type=" + preferred_address_type
				+ ", temporary_address_type=" + temporary_address_type
				+ ", postal_address_type=" + postal_address_type + ", cin="
				+ cin + ", din=" + din + ", doi=" + doi + ", rationcardnumber="
				+ rationcardnumber + ", sourcesystem=" + sourcesystem
				+ ", phone=" + phone + ", psxid=" + psxid + ", batchid="
				+ batchid + ", mobileno=" + mobileno + ", psxbatchid="
				+ psxbatchid + "]";
	}

}