package com.psx.prime360ClientService.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "psx_nsp_request")
public class NSPRequestEntity {

	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "REQUEST_ID")
	private String request_id;
	@Column(name = "CUSTOMER_SEARCH")
	private String customer_search;

	public String getCUSTOMER_SEARCH() {
		return customer_search;
	}

	public void setCUSTOMER_SEARCH(String cUSTOMER_SEARCH) {
		customer_search = cUSTOMER_SEARCH;
	}

	@Column(name = "APPLICATION_NO")
	private String application_no;
	@Column(name = "PAN")
	private String pan;
	@Column(name = "psx_Id")
	private Long psx_Id;
	@Column(name = "PSX_BATCH_ID", length = 30)
	private String psx_Batch_ID;
	@Column(name = "REQUEST_STATUS")
	private String request_Status;
	@Column(name = "ENGINE_PICKUP_TIME")
	private Date engine_Pickup_Time;
	@Column(name = "ENGINE_PROCESSED_TIME")
	private Date engine_Processed_Time;
	@Column(name = "INSERT_TIME")
	private Date insert_Time;
	@Column(name = "PROCESS_DURATION_MILLIS")
	private Integer process_Duration_Millis;
	@Column(name = "MATCH_COUNT")
	private Integer match_Count;
	@Column(name = "OFFLINE_MATCH_COUNT")
	private Integer offline_Match_Count;
	@Column(name = "ONLINE_MATCH_COUNT")
	private Integer online_Match_Count;
	@Column(name = "NAME")
	private String name;
	@Column(name = "MOTHER_NAME")
	private String mother_Name;
	@Column(name = "FATHER_NAME")
	private String father_Name;
	@Column(name = "SPOUSE_NAME")
	private String spouse_Name;
	@Column(name = "DOB1")
	private Date dob1;
	@Column(name = "DOB2")
	private Date dob2;
	@Column(name = "DOB3")
	private Date dob3;
	@Column(name = "DOB4")
	private Date dob4;
	@Column(name = "EQUALITY1")
	private String equality1;
	@Column(name = "EQUALITY2")
	private String equality2;
	@Column(name = "EQUALITY3")
	private String equality3;
	@Column(name = "EQUALITY4")
	private String equality4;
	@Column(name = "FLAG1")
	private String flag1;
	@Column(name = "FLAG2")
	private String flag2;
	@Column(name = "FLAG3")
	private String flag3;
	@Column(name = "FLAG4")
	private String flag4;
	@Column(name = "OFFICE_ADDRESS")
	private String office_Address;
	@Column(name = "OFFICE_CITY")
	private String office_City;
	@Column(name = "OFFICE_PIN")
	private String office_Pin;
	@Column(name = "PERMANENT_ADDRESS")
	private String permanent_Address;
	@Column(name = "PERMANENT_CITY")
	private String permanent_City;
	@Column(name = "PERMANENT_PIN")
	private String permanent_Pin;
	@Column(name = "RESIDENCE_ADDRESS")
	private String residence_Address;
	@Column(name = "RESIDENCE_CITY")
	private String residence_City;
	@Column(name = "RESIDENCE_PIN")
	private String residence_Pin;
	@Column(name = "TEMPORARY_ADDRESS")
	private String temporary_Address;
	@Column(name = "TEMPORARY_CITY")
	private String temporary_City;
	@Column(name = "TEMPORARY_PIN")
	private String temporary_Pin;
	@Column(name = "OFFICE_EMAIL")
	private String office_Email;
	@Column(name = "PERMANENT_EMAIL")
	private String permanent_Email;
	@Column(name = "RESIDENCE_EMAIL")
	private String residence_Email;
	@Column(name = "TEMPORARY_EMAIL")
	private String temporary_Email;
	@Column(name = "OFFICE_PHONE")
	private String office_Phone;
	@Column(name = "PERMANENT_PHONE")
	private String permanent_Phone;
	@Column(name = "RESIDENCE_PHONE")
	private String residence_Phone;
	@Column(name = "TEMPORARY_PHONE")
	private String temporary_Phone;
	@Column(name = "OFFICE_STREET_NUMBER")
	private String office_Street_Number;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "PERMANENT_STREET_NUMBER")
	private String Permanent_Street_Number;
	@Column(name = "RESIDENCE_STREET_NUMBER")
	private String residence_Street_Number;
	@Column(name = "TEMP_STREET_NUMBER")
	private String temp_Street_Number;
	@Column(name = "CUSTOMER_STATUS")
	private String customer_status;
	@Column(name = "CUSTOMER_NO")
	private String customer_no;

	@Column(name = "NODE1_REQUEST_STATUS")
	private String node1_request_status;

	@Column(name = "NODE2_REQUEST_STATUS")
	private String node2_request_status;

	public String getNode1_request_status() {
		return node1_request_status;
	}

	public void setNode1_request_status(String node1_request_status) {
		this.node1_request_status = node1_request_status;
	}

	public String getNode2_request_status() {
		return node2_request_status;
	}

	public void setNode2_request_status(String node2_request_status) {
		this.node2_request_status = node2_request_status;
	}

	@Column(name = "AADHAAR")
	private String aadhar;

	@Column(name = "OFFICE_AREA")
	private String off_area;

	@Column(name = "REGISTRATION_NO")
	private String registration_no;

	@Column(name = "EMPLOYER_NAME")
	private String employer_name;

	@Column(name = "SEGMENT")
	private String segment;

	@Column(name = "DRIVINGLIC")
	private String driving_lic;

	@Column(name = "DOI")
	private Date doi;

	@Column(name = "DIN")
	private String din;

	@Column(name = "VOTERID")
	private String voterid;

	@Column(name = "CIN")
	private String cin;

	@Column(name = "CREDIT_CARD_NUMBER")
	private String credit_card;

	@Column(name = "TAN_NO")
	private String tan_no;

	@Column(name = "CUST_UNQ_ID")
	private String cust_unq_id;

	@Column(name = "PASSPORT")
	private String passport;

	@Column(name = "ACCOUNT_NUMBER")
	private String account_no;

	@Column(name = "CA_NUMBER")
	private String ca_no;

	public String getRequest_id() {
		return request_id;
	}

	public String getCustomer_search() {
		return customer_search;
	}

	public String getApplication_no() {
		return application_no;
	}

	public String getPan() {
		return pan;
	}

	public Long getPsx_Id() {
		return psx_Id;
	}

	public String getPsx_Batch_ID() {
		return psx_Batch_ID;
	}

	public String getRequest_Status() {
		return request_Status;
	}

	public Date getEngine_Pickup_Time() {
		return engine_Pickup_Time;
	}

	public Date getEngine_Processed_Time() {
		return engine_Processed_Time;
	}

	public Date getInsert_Time() {
		return insert_Time;
	}

	public Integer getProcess_Duration_Millis() {
		return process_Duration_Millis;
	}

	public Integer getMatch_Count() {
		return match_Count;
	}

	public Integer getOffline_Match_Count() {
		return offline_Match_Count;
	}

	public Integer getOnline_Match_Count() {
		return online_Match_Count;
	}

	public String getName() {
		return name;
	}

	public String getMother_Name() {
		return mother_Name;
	}

	public String getFather_Name() {
		return father_Name;
	}

	public String getSpouse_Name() {
		return spouse_Name;
	}

	public Date getDob1() {
		return dob1;
	}

	public Date getDob2() {
		return dob2;
	}

	public Date getDob3() {
		return dob3;
	}

	public Date getDob4() {
		return dob4;
	}

	public String getEquality1() {
		return equality1;
	}

	public String getEquality2() {
		return equality2;
	}

	public String getEquality3() {
		return equality3;
	}

	public String getEquality4() {
		return equality4;
	}

	public String getFlag1() {
		return flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public String getFlag3() {
		return flag3;
	}

	public String getFlag4() {
		return flag4;
	}

	public String getOffice_Address() {
		return office_Address;
	}

	public String getOffice_City() {
		return office_City;
	}

	public String getOffice_Pin() {
		return office_Pin;
	}

	public String getPermanent_Address() {
		return permanent_Address;
	}

	public String getPermanent_City() {
		return permanent_City;
	}

	public String getPermanent_Pin() {
		return permanent_Pin;
	}

	public String getResidence_Address() {
		return residence_Address;
	}

	public String getResidence_City() {
		return residence_City;
	}

	public String getResidence_Pin() {
		return residence_Pin;
	}

	public String getTemporary_Address() {
		return temporary_Address;
	}

	public String getTemporary_City() {
		return temporary_City;
	}

	public String getTemporary_Pin() {
		return temporary_Pin;
	}

	public String getOffice_Email() {
		return office_Email;
	}

	public String getPermanent_Email() {
		return permanent_Email;
	}

	public String getResidence_Email() {
		return residence_Email;
	}

	public String getTemporary_Email() {
		return temporary_Email;
	}

	public String getOffice_Phone() {
		return office_Phone;
	}

	public String getPermanent_Phone() {
		return permanent_Phone;
	}

	public String getResidence_Phone() {
		return residence_Phone;
	}

	public String getTemporary_Phone() {
		return temporary_Phone;
	}

	public String getOffice_Street_Number() {
		return office_Street_Number;
	}

	public String getCountry() {
		return country;
	}

	public String getPermanent_Street_Number() {
		return Permanent_Street_Number;
	}

	public String getResidence_Street_Number() {
		return residence_Street_Number;
	}

	public String getTemp_Street_Number() {
		return temp_Street_Number;
	}

	public String getCustomer_status() {
		return customer_status;
	}

	public String getCustomer_no() {
		return customer_no;
	}

	public String getAadhar() {
		return aadhar;
	}

	public String getOff_area() {
		return off_area;
	}

	public String getRegistration_no() {
		return registration_no;
	}

	public String getEmployer_name() {
		return employer_name;
	}

	public String getSegment() {
		return segment;
	}

	public String getDriving_lic() {
		return driving_lic;
	}

	public String getDin() {
		return din;
	}

	public String getVoterid() {
		return voterid;
	}

	public String getCin() {
		return cin;
	}

	public String getCredit_card() {
		return credit_card;
	}

	public String getTan_no() {
		return tan_no;
	}

	public String getCust_unq_id() {
		return cust_unq_id;
	}

	public String getPassport() {
		return passport;
	}

	public String getAccount_no() {
		return account_no;
	}

	public String getCa_no() {
		return ca_no;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public void setCustomer_search(String customer_search) {
		this.customer_search = customer_search;
	}

	public void setApplication_no(String application_no) {
		this.application_no = application_no;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public void setPsx_Id(Long psx_Id) {
		this.psx_Id = psx_Id;
	}

	public void setPsx_Batch_ID(String psx_Batch_ID) {
		this.psx_Batch_ID = psx_Batch_ID;
	}

	public void setRequest_Status(String request_Status) {
		this.request_Status = request_Status;
	}

	public void setEngine_Pickup_Time(Date engine_Pickup_Time) {
		this.engine_Pickup_Time = engine_Pickup_Time;
	}

	public void setEngine_Processed_Time(Date engine_Processed_Time) {
		this.engine_Processed_Time = engine_Processed_Time;
	}

	public void setInsert_Time(Date insert_Time) {
		this.insert_Time = insert_Time;
	}

	public void setProcess_Duration_Millis(Integer process_Duration_Millis) {
		this.process_Duration_Millis = process_Duration_Millis;
	}

	public void setMatch_Count(Integer match_Count) {
		this.match_Count = match_Count;
	}

	public void setOffline_Match_Count(Integer offline_Match_Count) {
		this.offline_Match_Count = offline_Match_Count;
	}

	public void setOnline_Match_Count(Integer online_Match_Count) {
		this.online_Match_Count = online_Match_Count;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMother_Name(String mother_Name) {
		this.mother_Name = mother_Name;
	}

	public void setFather_Name(String father_Name) {
		this.father_Name = father_Name;
	}

	public void setSpouse_Name(String spouse_Name) {
		this.spouse_Name = spouse_Name;
	}

	public void setDob1(Date dob1) {
		this.dob1 =  dob1;
	}

	public void setDob2(Date dob2) {
		this.dob2 = dob2;
	}

	public void setDob3(Date dob3) {
		this.dob3 = dob3;
	}

	public void setDob4(Date dob4) {
		this.dob4 = dob4;
	}

	public void setEquality1(String equality1) {
		this.equality1 = equality1;
	}

	public void setEquality2(String equality2) {
		this.equality2 = equality2;
	}

	public void setEquality3(String equality3) {
		this.equality3 = equality3;
	}

	public void setEquality4(String equality4) {
		this.equality4 = equality4;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	public void setFlag4(String flag4) {
		this.flag4 = flag4;
	}

	public void setOffice_Address(String office_Address) {
		this.office_Address = office_Address;
	}

	public void setOffice_City(String office_City) {
		this.office_City = office_City;
	}

	public void setOffice_Pin(String office_Pin) {
		this.office_Pin = office_Pin;
	}

	public void setPermanent_Address(String permanent_Address) {
		this.permanent_Address = permanent_Address;
	}

	public void setPermanent_City(String permanent_City) {
		this.permanent_City = permanent_City;
	}

	public void setPermanent_Pin(String permanent_Pin) {
		this.permanent_Pin = permanent_Pin;
	}

	public void setResidence_Address(String residence_Address) {
		this.residence_Address = residence_Address;
	}

	public void setResidence_City(String residence_City) {
		this.residence_City = residence_City;
	}

	public void setResidence_Pin(String residence_Pin) {
		this.residence_Pin = residence_Pin;
	}

	public void setTemporary_Address(String temporary_Address) {
		this.temporary_Address = temporary_Address;
	}

	public void setTemporary_City(String temporary_City) {
		this.temporary_City = temporary_City;
	}

	public void setTemporary_Pin(String temporary_Pin) {
		this.temporary_Pin = temporary_Pin;
	}

	public void setOffice_Email(String office_Email) {
		this.office_Email = office_Email;
	}

	public void setPermanent_Email(String permanent_Email) {
		this.permanent_Email = permanent_Email;
	}

	public void setResidence_Email(String residence_Email) {
		this.residence_Email = residence_Email;
	}

	public void setTemporary_Email(String temporary_Email) {
		this.temporary_Email = temporary_Email;
	}

	public void setOffice_Phone(String office_Phone) {
		this.office_Phone = office_Phone;
	}

	public void setPermanent_Phone(String permanent_Phone) {
		this.permanent_Phone = permanent_Phone;
	}

	public void setResidence_Phone(String residence_Phone) {
		this.residence_Phone = residence_Phone;
	}

	public void setTemporary_Phone(String temporary_Phone) {
		this.temporary_Phone = temporary_Phone;
	}

	public void setOffice_Street_Number(String office_Street_Number) {
		this.office_Street_Number = office_Street_Number;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setPermanent_Street_Number(String permanent_Street_Number) {
		Permanent_Street_Number = permanent_Street_Number;
	}

	public void setResidence_Street_Number(String residence_Street_Number) {
		this.residence_Street_Number = residence_Street_Number;
	}

	public void setTemp_Street_Number(String temp_Street_Number) {
		this.temp_Street_Number = temp_Street_Number;
	}

	public void setCustomer_status(String customer_status) {
		this.customer_status = customer_status;
	}

	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public void setOff_area(String off_area) {
		this.off_area = off_area;
	}

	public void setRegistration_no(String registration_no) {
		this.registration_no = registration_no;
	}

	public void setEmployer_name(String employer_name) {
		this.employer_name = employer_name;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setDriving_lic(String driving_lic) {
		this.driving_lic = driving_lic;
	}

	public void setDin(String din) {
		this.din = din;
	}

	public void setVoterid(String voterid) {
		this.voterid = voterid;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}

	public void setTan_no(String tan_no) {
		this.tan_no = tan_no;
	}

	public void setCust_unq_id(String cust_unq_id) {
		this.cust_unq_id = cust_unq_id;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public void setCa_no(String ca_no) {
		this.ca_no = ca_no;
	}

	public Date getDoi() {
		return doi;
	}

	public void setDoi(Date doi) {
		this.doi = doi;
	}

	@Override
	public String toString() {
		return "NSPRequestEntity [request_id=" + request_id + ", customer_search=" + customer_search
				+ ", application_no=" + application_no + ", pan=" + pan + ", psx_Id=" + psx_Id + ", psx_Batch_ID="
				+ psx_Batch_ID + ", request_Status=" + request_Status + ", engine_Pickup_Time=" + engine_Pickup_Time
				+ ", engine_Processed_Time=" + engine_Processed_Time + ", insert_Time=" + insert_Time
				+ ", process_Duration_Millis=" + process_Duration_Millis + ", match_Count=" + match_Count
				+ ", offline_Match_Count=" + offline_Match_Count + ", online_Match_Count=" + online_Match_Count
				+ ", name=" + name + ", mother_Name=" + mother_Name + ", father_Name=" + father_Name + ", spouse_Name="
				+ spouse_Name + ", dob1=" + dob1 + ", dob2=" + dob2 + ", dob3=" + dob3 + ", dob4=" + dob4
				+ ", equality1=" + equality1 + ", equality2=" + equality2 + ", equality3=" + equality3 + ", equality4="
				+ equality4 + ", flag1=" + flag1 + ", flag2=" + flag2 + ", flag3=" + flag3 + ", flag4=" + flag4
				+ ", office_Address=" + office_Address + ", office_City=" + office_City + ", office_Pin=" + office_Pin
				+ ", permanent_Address=" + permanent_Address + ", permanent_City=" + permanent_City + ", permanent_Pin="
				+ permanent_Pin + ", residence_Address=" + residence_Address + ", residence_City=" + residence_City
				+ ", residence_Pin=" + residence_Pin + ", temporary_Address=" + temporary_Address + ", temporary_City="
				+ temporary_City + ", temporary_Pin=" + temporary_Pin + ", office_Email=" + office_Email
				+ ", permanent_Email=" + permanent_Email + ", residence_Email=" + residence_Email + ", temporary_Email="
				+ temporary_Email + ", office_Phone=" + office_Phone + ", permanent_Phone=" + permanent_Phone
				+ ", residence_Phone=" + residence_Phone + ", temporary_Phone=" + temporary_Phone
				+ ", office_Street_Number=" + office_Street_Number + ", country=" + country
				+ ", Permanent_Street_Number=" + Permanent_Street_Number + ", residence_Street_Number="
				+ residence_Street_Number + ", temp_Street_Number=" + temp_Street_Number + ", customer_status="
				+ customer_status + ", customer_no=" + customer_no + ", node1_request_status=" + node1_request_status
				+ ", node2_request_status=" + node2_request_status + ", aadhar=" + aadhar + ", off_area=" + off_area
				+ ", registration_no=" + registration_no + ", employer_name=" + employer_name + ", segment=" + segment
				+ ", driving_lic=" + driving_lic + ", doi=" + doi + ", din=" + din + ", voterid=" + voterid + ", cin="
				+ cin + ", credit_card=" + credit_card + ", tan_no=" + tan_no + ", cust_unq_id=" + cust_unq_id
				+ ", passport=" + passport + ", account_no=" + account_no + ", ca_no=" + ca_no + "]";
	}

	
}
