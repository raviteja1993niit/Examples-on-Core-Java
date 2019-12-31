/**
 * 
 */
package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;



/**
 * @author sunny
 *
 */
@Embeddable
public class BaseMetaInfoIdentity implements Serializable{

	private static final long serialVersionUID = 7367281487398259866L;

	
	@Column(name = "ID")
	private Long id;

	@Column(name = "LCHGTIME", nullable = false, length=25)
	private Date lchgtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLchgtime() {
		return lchgtime;
	}

	public void setLchgtime(Date lchgtime) {
		this.lchgtime = lchgtime;
	}

	@Override
	public String toString() {
		return "BaseMetaInfoIdentity [id=" + id + ", lchgtime=" + lchgtime + "]";
	}

	

}
