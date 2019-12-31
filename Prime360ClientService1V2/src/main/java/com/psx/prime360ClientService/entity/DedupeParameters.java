package com.psx.prime360ClientService.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="PSX_NSP_PROFILE_PARAMETERS")
public class DedupeParameters implements Serializable {
	
	private static final long serialVersionUID = -8792478477829510972L;
	@Id
	@Column(name="ID")
	private Integer Id;
	@Column(name="UI_PARAMETER_NAME")
	private String uiParameterName;
	@Column(name="ENGINE_PARAMETER_NAME")
	private String engineParameterName;
	@Column(name="DEDUPE_TYPE")
	private Integer dedupeType;
	@Column(name="DISPLAY_CATEGORY")
	private Integer displayCategory;
	@Column(name="MATCH_CRITERIA",length=4000)
	private String matchCriteriaJson;
	@Column(name="ACTIVE")
	private String active;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getUiParameterName() {
		return uiParameterName;
	}
	public void setUiParameterName(String uiParameterName) {
		this.uiParameterName = uiParameterName;
	}
	public String getEngineParameterName() {
		return engineParameterName;
	}
	public void setEngineParameterName(String engineParameterName) {
		this.engineParameterName = engineParameterName;
	}
	public Integer getDedupeType() {
		return dedupeType;
	}
	public void setDedupeType(Integer dedupeType) {
		this.dedupeType = dedupeType;
	}
	public Integer getDisplayCategory() {
		return displayCategory;
	}
	public void setDisplayCategory(Integer displayCategory) {
		this.displayCategory = displayCategory;
	}
	public String getMatchCriteriaJson() {
		return matchCriteriaJson;
	}
	public void setMatchCriteriaJson(String matchCriteriaJson) {
		this.matchCriteriaJson = matchCriteriaJson;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "DedupeParametersBO [Id=" + Id + ", uiParameterName=" + uiParameterName + ", engineParameterName="
				+ engineParameterName + ", dedupeType=" + dedupeType + ", displayCategory=" + displayCategory
				+ ", matchCriteriaJson=" + matchCriteriaJson + ", active=" + active + "]";
	}

	

}
