package com.psx.prime360ClientService.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfileParamsIdentity implements Serializable {

	private static final long serialVersionUID = -1819714867446685899L;
	@Column(name = "ID")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ProfileParamsIdentity [id=" + id + "]";
	}

}
