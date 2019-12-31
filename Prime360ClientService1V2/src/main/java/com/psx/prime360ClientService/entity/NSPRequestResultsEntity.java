package com.psx.prime360ClientService.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PSX_NSP_REQUEST_RESULTS")
public class NSPRequestResultsEntity {
	@Id
	@Column(name="CUST_UNQ_ID")
	private String cust_unq_id;
	public String getCust_unq_id() {
		return cust_unq_id;
	}

	public void setCust_unq_id(String cust_unq_id) {
		this.cust_unq_id = cust_unq_id;
	}

	@Column(name="REQUEST_ID")
	private String requestId;
	@Column(name="APPLICATION_NO")
	private String application_no;
	
	@Column(name="CUSTOMER_NO")
	private String customer_no;
	@Column(name="SEGMENT")
	private String segment;
	@Column(name="NAME")
	private String name;
	@Column(name="PAN")
	private String pan;
	@Column(name="DOB1")
	private Date dob;
	@Column(name="CUSTOMER_STATUS")
	private String customer_status;
	@Column(name="ADDRESS")
	private String address;
	@Column(name="LANDMARK")
	private String landmark;
	@Column(name="AREA")
	private String area;
	@Column(name="CITY")
	private String city;
	@Column(name="PIN")
	private String pin;
	@Column(name="CIN")
	private String cin;
	@Column(name="DIN")
	private String din;
	
	@Column(name="AADHAAR")
	private String aadhaar;

	@Column(name="CA_NUMBER")
	private String ca_number;

	@Column(name="REGISTRATION_NO")
	private String registration_no;

	@Column(name="PHONE")
	private String phone;

	@Column(name="PHONE_TYPE")
	private String phone_type;
	
	@Column(name="CONTACT_ADDRESS")
	private String contact_address;
	
	@Column(name="EMERGENCY_ADDRESS")
	private String emergency_address;
	public String getContact_address() {
		return contact_address;
	}

	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}

	public String getEmergency_address() {
		return emergency_address;
	}

	public void setEmergency_address(String emergency_address) {
		this.emergency_address = emergency_address;
	}

	public String getOffice_address() {
		return office_address;
	}

	public void setOffice_address(String office_address) {
		this.office_address = office_address;
	}

	public String getPermanent_address() {
		return permanent_address;
	}

	public void setPermanent_address(String permanent_address) {
		this.permanent_address = permanent_address;
	}

	public String getPostal_address() {
		return postal_address;
	}

	public void setPostal_address(String postal_address) {
		this.postal_address = postal_address;
	}

	public String getPreferred_address() {
		return preferred_address;
	}

	public void setPreferred_address(String preferred_address) {
		this.preferred_address = preferred_address;
	}

	public String getPresent_address() {
		return present_address;
	}

	public void setPresent_address(String present_address) {
		this.present_address = present_address;
	}

	public String getResidence_address() {
		return residence_address;
	}

	public void setResidence_address(String residence_address) {
		this.residence_address = residence_address;
	}

	public String getTemporary_address() {
		return temporary_address;
	}

	public void setTemporary_address(String temporary_address) {
		this.temporary_address = temporary_address;
	}

	@Column(name="OFFICE_ADDRESS")
	private String office_address;
	@Column(name="PERMANENT_ADDRESS")
	private String permanent_address;
	@Column(name="POSTAL_ADDRESS")
	private String postal_address;
	@Column(name="PREFERRED_ADDRESS")
	private String preferred_address;
	@Column(name="PRESENT_ADDRESS")
	private String present_address;
	@Column(name="RESIDENCE_ADDRESS")
	private String residence_address;
	@Column(name="TEMPORARY_ADDRESS")
	private String temporary_address;

	public String getRequestId() {
		return requestId;
	}

	public String getApplication_no() {
		return application_no;
	}

	public String getCustomer_no() {
		return customer_no;
	}

	public String getSegment() {
		return segment;
	}

	public String getName() {
		return name;
	}

	public String getPan() {
		return pan;
	}

	public Date getDob() {
		return dob;
	}

	public String getCustomer_status() {
		return customer_status;
	}

	public String getAddress() {
		return address;
	}

	public String getLandmark() {
		return landmark;
	}

	public String getArea() {
		return area;
	}

	public String getCity() {
		return city;
	}

	public String getPin() {
		return pin;
	}

	public String getCin() {
		return cin;
	}

	public String getDin() {
		return din;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public String getCa_number() {
		return ca_number;
	}

	public String getRegistration_no() {
		return registration_no;
	}

	public String getPhone() {
		return phone;
	}

	public String getPhone_type() {
		return phone_type;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setApplication_no(String application_no) {
		this.application_no = application_no;
	}

	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setCustomer_status(String customer_status) {
		this.customer_status = customer_status;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public void setDin(String din) {
		this.din = din;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public void setCa_number(String ca_number) {
		this.ca_number = ca_number;
	}

	public void setRegistration_no(String registration_no) {
		this.registration_no = registration_no;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPhone_type(String phone_type) {
		this.phone_type = phone_type;
	}

	@Override
	public String toString() {
		return "NSPRequestResultsEntity [cust_unq_id=" + cust_unq_id + ", requestId=" + requestId + ", application_no="
				+ application_no + ", customer_no=" + customer_no + ", segment=" + segment + ", name=" + name + ", pan="
				+ pan + ", dob=" + dob + ", customer_status=" + customer_status + ", address=" + address + ", landmark="
				+ landmark + ", area=" + area + ", city=" + city + ", pin=" + pin + ", cin=" + cin + ", din=" + din
				+ ", aadhaar=" + aadhaar + ", ca_number=" + ca_number + ", registration_no=" + registration_no
				+ ", phone=" + phone + ", phone_type=" + phone_type + ", contact_address=" + contact_address
				+ ", emergency_address=" + emergency_address + ", office_address=" + office_address
				+ ", permanent_address=" + permanent_address + ", postal_address=" + postal_address
				+ ", preferred_address=" + preferred_address + ", present_address=" + present_address
				+ ", residence_address=" + residence_address + ", temporary_address=" + temporary_address + "]";
	}
	

	
	
	
	
	
	
	
	
	}
