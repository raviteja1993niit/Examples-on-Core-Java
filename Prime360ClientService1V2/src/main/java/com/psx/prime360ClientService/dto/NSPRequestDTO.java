package com.psx.prime360ClientService.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class NSPRequestDTO implements Serializable {

	private Long psx_Id;
	private String psx_Batch_ID;
	private String request_Status;
	private String engine_Pickup_Time;
	private String engine_Processed_Time;
	private String insert_Time;
	private Integer process_Duration_Millis;
	private Integer match_Count;
	private Integer offline_Match_Count;
	private Integer online_Match_Count;
	private String name;
	private String mother_Name;
	private String father_Name;
	private String spouse_Name;
	private String dob1;
	private String dob2;
	private String dob3;
	private String dob4;
	private String equality1;
	private String equality2;
	private String equality3;
	private String equality4;
	private String equalityType;
	private String flag1;
	private String flag2;
	private String flag3;
	private String flag4;
	private String office_Address;
	private String office_City;
	private String office_Pin;
	private String permanent_Address;
	private String permanent_City;
	private String permanent_Pin;
	private String residence_Address;
	private String residence_City;
	private String residence_Pin;
	private String temporary_Address;
	private String temporary_City;
	private String temporary_Pin;
	private String office_Email;
	private String permanent_Email;
	private String residence_Email;
	private String temporary_Email;
	private String office_Phone;
	private String permanent_Phone;
	private String residence_Phone;
	private String temporary_Phone;
	private String office_Street_Number;
	private String country;
	private String permanent_Street_Number;
	private String residence_Street_Number;
	private String temp_Street_Number;

	public Long getPsx_Id() {
		return psx_Id;
	}

	public void setPsx_Id(Long psx_Id) {
		this.psx_Id = psx_Id;
	}

	public String getPsx_Batch_ID() {
		return psx_Batch_ID;
	}

	public void setPsx_Batch_ID(String psx_Batch_ID) {
		this.psx_Batch_ID = psx_Batch_ID;
	}

	public String getRequest_Status() {
		return request_Status;
	}

	public void setRequest_Status(String request_Status) {
		this.request_Status = request_Status;
	}

	public String getEngine_Pickup_Time() {
		return engine_Pickup_Time;
	}

	public void setEngine_Pickup_Time(String engine_Pickup_Time) {
		this.engine_Pickup_Time = engine_Pickup_Time;
	}

	public String getEngine_Processed_Time() {
		return engine_Processed_Time;
	}

	public void setEngine_Processed_Time(String engine_Processed_Time) {
		this.engine_Processed_Time = engine_Processed_Time;
	}

	public String getInsert_Time() {
		return insert_Time;
	}

	public void setInsert_Time(String insert_Time) {
		this.insert_Time = insert_Time;
	}

	public Integer getProcess_Duration_Millis() {
		return process_Duration_Millis;
	}

	public void setProcess_Duration_Millis(Integer process_Duration_Millis) {
		this.process_Duration_Millis = process_Duration_Millis;
	}

	public Integer getMatch_Count() {
		return match_Count;
	}

	public void setMatch_Count(Integer match_Count) {
		this.match_Count = match_Count;
	}

	public Integer getOffline_Match_Count() {
		return offline_Match_Count;
	}

	public void setOffline_Match_Count(Integer offline_Match_Count) {
		this.offline_Match_Count = offline_Match_Count;
	}

	public Integer getOnline_Match_Count() {
		return online_Match_Count;
	}

	public void setOnline_Match_Count(Integer online_Match_Count) {
		this.online_Match_Count = online_Match_Count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMother_Name() {
		return mother_Name;
	}

	public void setMother_Name(String mother_Name) {
		this.mother_Name = mother_Name;
	}

	public String getFather_Name() {
		return father_Name;
	}

	public void setFather_Name(String father_Name) {
		this.father_Name = father_Name;
	}

	public String getSpouse_Name() {
		return spouse_Name;
	}

	public void setSpouse_Name(String spouse_Name) {
		this.spouse_Name = spouse_Name;
	}

	public String getDob1() {
		return dob1;
	}

	public void setDob1(String dob1) {
		this.dob1 = dob1;
	}

	public String getDob2() {
		return dob2;
	}

	public void setDob2(String dob2) {
		this.dob2 = dob2;
	}

	public String getDob3() {
		return dob3;
	}

	public void setDob3(String dob3) {
		this.dob3 = dob3;
	}

	public String getDob4() {
		return dob4;
	}

	public void setDob4(String dob4) {
		this.dob4 = dob4;
	}

	public String getEquality1() {
		return equality1;
	}

	public void setEquality1(String equality1) {
		this.equality1 = equality1;
	}

	public String getEquality2() {
		return equality2;
	}

	public void setEquality2(String equality2) {
		this.equality2 = equality2;
	}

	public String getEquality3() {
		return equality3;
	}

	public void setEquality3(String equality3) {
		this.equality3 = equality3;
	}

	public String getEquality4() {
		return equality4;
	}

	public void setEquality4(String equality4) {
		this.equality4 = equality4;
	}

	public String getEqualityType() {
		return equalityType;
	}

	public void setEqualityType(String equalityType) {
		this.equalityType = equalityType;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public String getFlag3() {
		return flag3;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	public String getFlag4() {
		return flag4;
	}

	public void setFlag4(String flag4) {
		this.flag4 = flag4;
	}

	public String getOffice_Address() {
		return office_Address;
	}

	public void setOffice_Address(String office_Address) {
		this.office_Address = office_Address;
	}

	public String getOffice_City() {
		return office_City;
	}

	public void setOffice_City(String office_City) {
		this.office_City = office_City;
	}

	public String getOffice_Pin() {
		return office_Pin;
	}

	public void setOffice_Pin(String office_Pin) {
		this.office_Pin = office_Pin;
	}

	public String getPermanent_Address() {
		return permanent_Address;
	}

	public void setPermanent_Address(String permanent_Address) {
		this.permanent_Address = permanent_Address;
	}

	public String getPermanent_City() {
		return permanent_City;
	}

	public void setPermanent_City(String permanent_City) {
		this.permanent_City = permanent_City;
	}

	public String getPermanent_Pin() {
		return permanent_Pin;
	}

	public void setPermanent_Pin(String permanent_Pin) {
		this.permanent_Pin = permanent_Pin;
	}

	public String getResidence_Address() {
		return residence_Address;
	}

	public void setResidence_Address(String residence_Address) {
		this.residence_Address = residence_Address;
	}

	public String getResidence_City() {
		return residence_City;
	}

	public void setResidence_City(String residence_City) {
		this.residence_City = residence_City;
	}

	public String getResidence_Pin() {
		return residence_Pin;
	}

	public void setResidence_Pin(String residence_Pin) {
		this.residence_Pin = residence_Pin;
	}

	public String getTemporary_Address() {
		return temporary_Address;
	}

	public void setTemporary_Address(String temporary_Address) {
		this.temporary_Address = temporary_Address;
	}

	public String getTemporary_City() {
		return temporary_City;
	}

	public void setTemporary_City(String temporary_City) {
		this.temporary_City = temporary_City;
	}

	public String getTemporary_Pin() {
		return temporary_Pin;
	}

	public void setTemporary_Pin(String temporary_Pin) {
		this.temporary_Pin = temporary_Pin;
	}

	public String getOffice_Email() {
		return office_Email;
	}

	public void setOffice_Email(String office_Email) {
		this.office_Email = office_Email;
	}

	public String getPermanent_Email() {
		return permanent_Email;
	}

	public void setPermanent_Email(String permanent_Email) {
		this.permanent_Email = permanent_Email;
	}

	public String getResidence_Email() {
		return residence_Email;
	}

	public void setResidence_Email(String residence_Email) {
		this.residence_Email = residence_Email;
	}

	public String getTemporary_Email() {
		return temporary_Email;
	}

	public void setTemporary_Email(String temporary_Email) {
		this.temporary_Email = temporary_Email;
	}

	public String getOffice_Phone() {
		return office_Phone;
	}

	public void setOffice_Phone(String office_Phone) {
		this.office_Phone = office_Phone;
	}

	public String getPermanent_Phone() {
		return permanent_Phone;
	}

	public void setPermanent_Phone(String permanent_Phone) {
		this.permanent_Phone = permanent_Phone;
	}

	public String getResidence_Phone() {
		return residence_Phone;
	}

	public void setResidence_Phone(String residence_Phone) {
		this.residence_Phone = residence_Phone;
	}

	public String getTemporary_Phone() {
		return temporary_Phone;
	}

	public void setTemporary_Phone(String temporary_Phone) {
		this.temporary_Phone = temporary_Phone;
	}

	public String getOffice_Street_Number() {
		return office_Street_Number;
	}

	public void setOffice_Street_Number(String office_Street_Number) {
		this.office_Street_Number = office_Street_Number;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPermanent_Street_Number() {
		return permanent_Street_Number;
	}

	public void setPermanent_Street_Number(String permanent_Street_Number) {
		this.permanent_Street_Number = permanent_Street_Number;
	}

	public String getResidence_Street_Number() {
		return residence_Street_Number;
	}

	public void setResidence_Street_Number(String residence_Street_Number) {
		this.residence_Street_Number = residence_Street_Number;
	}

	public String getTemp_Street_Number() {
		return temp_Street_Number;
	}

	public void setTemp_Street_Number(String temp_Street_Number) {
		this.temp_Street_Number = temp_Street_Number;
	}

	@Override
	public String toString() {
		return "NSPRequestDTO [psx_Id=" + psx_Id + ", psx_Batch_ID=" + psx_Batch_ID + ", request_Status="
				+ request_Status + ", engine_Pickup_Time=" + engine_Pickup_Time + ", engine_Processed_Time="
				+ engine_Processed_Time + ", insert_Time=" + insert_Time + ", process_Duration_Millis="
				+ process_Duration_Millis + ", match_Count=" + match_Count + ", offline_Match_Count="
				+ offline_Match_Count + ", online_Match_Count=" + online_Match_Count + ", name=" + name
				+ ", mother_Name=" + mother_Name + ", father_Name=" + father_Name + ", spouse_Name=" + spouse_Name
				+ ", dob1=" + dob1 + ", dob2=" + dob2 + ", dob3=" + dob3 + ", dob4=" + dob4 + ", equality1=" + equality1
				+ ", equality2=" + equality2 + ", equality3=" + equality3 + ", equality4=" + equality4
				+ ", equalityType=" + equalityType + ", flag1=" + flag1 + ", flag2=" + flag2 + ", flag3=" + flag3
				+ ", flag4=" + flag4 + ", office_Address=" + office_Address + ", office_City=" + office_City
				+ ", office_Pin=" + office_Pin + ", permanent_Address=" + permanent_Address + ", permanent_City="
				+ permanent_City + ", permanent_Pin=" + permanent_Pin + ", residence_Address=" + residence_Address
				+ ", residence_City=" + residence_City + ", residence_Pin=" + residence_Pin + ", temporary_Address="
				+ temporary_Address + ", temporary_City=" + temporary_City + ", temporary_Pin=" + temporary_Pin
				+ ", office_Email=" + office_Email + ", permanent_Email=" + permanent_Email + ", residence_Email="
				+ residence_Email + ", temporary_Email=" + temporary_Email + ", office_Phone=" + office_Phone
				+ ", permanent_Phone=" + permanent_Phone + ", residence_Phone=" + residence_Phone + ", temporary_Phone="
				+ temporary_Phone + ", office_Street_Number=" + office_Street_Number + ", country=" + country
				+ ", permanent_Street_Number=" + permanent_Street_Number + ", residence_Street_Number="
				+ residence_Street_Number + ", temp_Street_Number=" + temp_Street_Number + "]";
	}

}
