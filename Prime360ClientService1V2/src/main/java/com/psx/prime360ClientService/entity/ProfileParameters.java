package com.psx.prime360ClientService.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PSX_NSP_PROFILE_PARAMETERS")
public class ProfileParameters implements Serializable {

	private static final long serialVersionUID = 2466793342603533308L;

	@EmbeddedId
	private ProfileParamsIdentity profileParamsIdentity;

	@Column(name = "ACTIVE")
	private String active;

	@Column(name = "DEDUPE_TYPE")
	private Integer dedupe_type;

	@Column(name = "DISPLAY_CATEGORY")
	private Integer display_category;

	@Column(name = "ENGINE_PARAMETER_NAME")
	private String engine_parameter_name;

	@Column(name = "MATCH_CRITERIA", length = 4000)
	private String match_criteria;

	@Column(name = "UI_PARAMETER_NAME")
	private String ui_parameter_name;

	public ProfileParamsIdentity getProfileParamsIdentity() {
		return profileParamsIdentity;
	}

	public void setProfileParamsIdentity(ProfileParamsIdentity profileParamsIdentity) {
		this.profileParamsIdentity = profileParamsIdentity;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Integer getDedupe_type() {
		return dedupe_type;
	}

	public void setDedupe_type(Integer dedupe_type) {
		this.dedupe_type = dedupe_type;
	}

	public Integer getDisplay_category() {
		return display_category;
	}

	public void setDisplay_category(Integer display_category) {
		this.display_category = display_category;
	}

	public String getEngine_parameter_name() {
		return engine_parameter_name;
	}

	public void setEngine_parameter_name(String engine_parameter_name) {
		this.engine_parameter_name = engine_parameter_name;
	}

	public String getMatch_criteria() {
		return match_criteria;
	}

	public void setMatch_criteria(String match_criteria) {
		this.match_criteria = match_criteria;
	}

	public String getUi_parameter_name() {
		return ui_parameter_name;
	}

	public void setUi_parameter_name(String ui_parameter_name) {
		this.ui_parameter_name = ui_parameter_name;
	}

	@Override
	public String toString() {
		return "ProfileParameters [profileParamsIdentity=" + profileParamsIdentity + ", active=" + active
				+ ", dedupe_type=" + dedupe_type + ", display_category=" + display_category + ", engine_parameter_name="
				+ engine_parameter_name + ", match_criteria=" + match_criteria + ", ui_parameter_name="
				+ ui_parameter_name + "]";
	}

}
