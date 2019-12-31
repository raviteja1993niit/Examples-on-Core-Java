package com.psx.prime360ClientService.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class NegativeBaseDetails {
	private String area;
	private String area_id;
	private String area_unq_id;
	private String city;
	private String eventType;
	private String org;
	private String pin;
	private String segment;
	private String state;
	private String batchid;
	private String duiFlag;
	private Timestamp insertTime;
	private Timestamp lchgTime;
	private String psx_area;
	private String psx_batchid;
	private String error_desc;

	public String getError_desc() {
		return error_desc;
	}

	public void set_Errordesc(String error_desc) {
		this.error_desc = error_desc;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getArea_unq_id() {
		return area_unq_id;
	}

	public void setArea_unq_id(String area_unq_id) {
		this.area_unq_id = area_unq_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getDuiFlag() {
		return duiFlag;
	}

	public void setDuiFlag(String duiFlag) {
		this.duiFlag = duiFlag;
	}

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Timestamp getLchgTime() {
		return lchgTime;
	}

	public void setLchgTime(Timestamp lchgTime) {
		this.lchgTime = lchgTime;
	}

	public String getPsx_area() {
		return psx_area;
	}

	public void setPsx_area(String psx_area) {
		this.psx_area = psx_area;
	}

	public String getPsx_batchid() {
		return psx_batchid;
	}

	public void setPsx_batchid(String psx_batchid) {
		this.psx_batchid = psx_batchid;
	}

	@Override
	public String toString() {
		return "NegativeBaseDetails [area=" + area + ", area_id=" + area_id + ", area_unq_id=" + area_unq_id + ", city="
				+ city + ", eventType=" + eventType + ", org=" + org + ", pin=" + pin + ", segment=" + segment
				+ ", state=" + state + ", batchid=" + batchid + ", duiFlag=" + duiFlag + ", insertTime=" + insertTime
				+ ", lchgTime=" + lchgTime + ", psx_area=" + psx_area + ", psx_batchid=" + psx_batchid + "]";
	}

}
