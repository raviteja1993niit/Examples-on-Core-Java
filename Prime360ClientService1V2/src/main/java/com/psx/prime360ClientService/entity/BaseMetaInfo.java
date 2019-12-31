/**
 * 
 */
package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author Rahul
 *
 */
@Entity
@Table(name = "PSX_NSP_BASE_META_INFO_DEMO")
public class BaseMetaInfo implements Serializable {

	private static final long serialVersionUID = -6555522960682115341L;

	@EmbeddedId
	private BaseMetaInfoIdentity metaInfoIdentity;

	@Lob
	@Column(name = "CSVSTRING", nullable = false)
	private String csvString;

	@Column(name = "SRCSYSTEMNAME")
	private String srcSystemName;

	public BaseMetaInfoIdentity getMetaInfoIdentity() {
		return metaInfoIdentity;
	}

	public void setMetaInfoIdentity(BaseMetaInfoIdentity metaInfoIdentity) {
		this.metaInfoIdentity = metaInfoIdentity;
	}

	public Long getId() {
		return metaInfoIdentity.getId();
	}

	public Date getLchgTime() {
		return metaInfoIdentity.getLchgtime();
	}

	public String getCsvString() {
		return csvString;
	}

	public void setCsvString(String csvString) {
		this.csvString = csvString;
	}

	public String getSrcSystemName() {
		return srcSystemName;
	}

	public void setSrcSystemName(String srcSystemName) {
		this.srcSystemName = srcSystemName;
	}

	@Override
	public String toString() {
		return "BaseMetaInfo [metaInfoIdentity=" + metaInfoIdentity + ", csvString=" + csvString + ", srcSystemName="
				+ srcSystemName + "]";
	}

	
}
