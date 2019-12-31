package com.psx.prime360ClientService.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author jayantronald
 *
 */

@Entity
@Table(name = "PSX_NSP_APP_CONFIG_BKP27")
public class InstallablesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "PROP_KEY")
	private String propKey;
	@Lob
	@Column(name = "PROP_VALUE")
	private String propValue;
	@Column(name = "MODULE")
	private String module;
	@Column(name = "LCHGTIME")
	private Date lchgTime;
	@Column(name = "SRCSYSTEMNAME")
	private String srcSystemName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Date getLchgTime() {
		return lchgTime;
	}

	public void setLchgTime(Date lchgTime) {
		this.lchgTime = lchgTime;
	}

	public String getSrcSystemName() {
		return srcSystemName;
	}

	public void setSrcSystemName(String srcSystemName) {
		this.srcSystemName = srcSystemName;
	}

	@Override
	public String toString() {
		return "InstallablesEntity [id=" + id + ", propKey=" + propKey + ", propValue=" + propValue + ", module="
				+ module + ", lchgTime=" + lchgTime + ", srcSystemName=" + srcSystemName + "]";
	}

}
