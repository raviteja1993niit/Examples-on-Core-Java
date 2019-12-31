package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.psx.prime360ClientService.entity.RolesIdentity;

/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@Embeddable
public class RolesIdentity implements Serializable {
	private static final long serialVersionUID = 2124248309836260888L;
	@Column(name="role_ID")
	private String roleID;
	@Column(name="lchgTime")
	private Date lchgTime;

	public RolesIdentity() {
		super();
	}

	public RolesIdentity(String roleID, Date lchgTime) {
		super();
		this.roleID = roleID;
		this.lchgTime = lchgTime;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public Date getLchgTime() {
		return lchgTime;
	}

	public void setLchgTime(Date lchgTime) {
		this.lchgTime = lchgTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lchgTime == null) ? 0 : lchgTime.hashCode());
		result = prime * result + ((roleID == null) ? 0 : roleID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolesIdentity other = (RolesIdentity) obj;
		if (lchgTime == null) {
			if (other.lchgTime != null)
				return false;
		} else if (!lchgTime.equals(other.lchgTime))
			return false;
		if (roleID == null) {
			if (other.roleID != null)
				return false;
		} else if (!roleID.equals(other.roleID))
			return false;
		return true;
	}

}
