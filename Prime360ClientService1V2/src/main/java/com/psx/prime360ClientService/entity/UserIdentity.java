package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.psx.prime360ClientService.entity.UserIdentity;

/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@Embeddable
public class UserIdentity implements Serializable {
	private static final long serialVersionUID = -4312086167091011060L;
	@Column(name="user_ID")
    private String userID;
	@Column(name="lchgTime")
    private Date lchgTime;
	public UserIdentity() {

    }
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public UserIdentity(String userID, Date lchgTime) {
		super();
		this.userID = userID;
		this.lchgTime = lchgTime;
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
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		UserIdentity other = (UserIdentity) obj;
		if (lchgTime == null) {
			if (other.lchgTime != null)
				return false;
		} else if (!lchgTime.equals(other.lchgTime))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	
}
