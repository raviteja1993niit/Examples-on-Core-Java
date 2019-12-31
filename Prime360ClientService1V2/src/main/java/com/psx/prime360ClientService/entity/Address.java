package com.psx.prime360ClientService.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_address")
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="PIN")
	private String pin;
	
	@Column(name="STATE")
	private String state;

	
	
	@Column(name="AREA_LOCALITY_RES")
	private String areaLocalityRes;
	
	@Column(name="LANDMARK_RES")
	private String landmarkRes;
	
	@Column(name="OFFICE_ADDRESS")
	private String officeAddress;
	
	@Column(name="RESIDENT_ADDRESS")
	private String residentAddress;
	
	@Column(name="OFFICE_1")
	private String office1;
	
	@Column(name="OFFICE_2")
	private String office2;
	
	@Column(name="OFFICE_3")
	private String office3;
	
	@Column(name="OFF_CITY")
	private String officeCity;
	
	@Column(name="OFF_PIN")
	private String officePin;
	
	@Column(name="OFF_STATE")
	private String offState;
	
	
	@Column(name="OFF_AREA_LOCALITY")
	private String officeAreaLocality;
	
	@Column(name="LANDLINE_1_RES")
	private String landlineRes1;
	
	@Column(name="LANDLINE_2_RES")
	private String landLineRes2;
	
	@Column(name="OFF_LANDMARK")
	private String officeLandmark;
	
	
	@Column(name="STD")
	private String std;
	
	@OneToOne
	@JoinColumn(name="CUSTOMER_ID")
	private Customer customer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", city=" + city + ", pin=" + pin + ", state=" + state + ", areaLocalityRes="
				+ areaLocalityRes + ", landmarkRes=" + landmarkRes + ", officeAddress=" + officeAddress
				+ ", residentAddress=" + residentAddress + ", office1=" + office1 + ", office2=" + office2
				+ ", office3=" + office3 + ", officeCity=" + officeCity + ", officePin=" + officePin + ", offState="
				+ offState + ", officeAreaLocality=" + officeAreaLocality + ", landlineRes1=" + landlineRes1
				+ ", landLineRes2=" + landLineRes2 + ", officeLandmark=" + officeLandmark + ", std=" + std
				+ ", customer=" + customer + "]";
	}
	
	
	
	
}
