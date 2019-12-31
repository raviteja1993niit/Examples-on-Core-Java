package com.psx.prime360ClientService.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.psx.prime360ClientService.entity.RolesIdentity;

/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
@Entity
@Table(name = "psx_nsp_roles")
public class Roles implements Serializable {

	private static final long serialVersionUID = 6594566741315182925L;

	@EmbeddedId
	private RolesIdentity rolesIdentity;
	@Column(name = "ROLE_NAME")
	private String roleName;
	@Column(name = "ACTIVE")
	private String active;
	@Column(name = "MAKER")
	private String maker;
	@Column(name = "CHECKER")
	private String checker;
	@Column(name = "APPROVAL_TS")
	private Date approvalTs;
	@Column(name = "TENANT_ID")
	private String tenantID;
	@Column(name = "MENU_ID_JSON", length = 4000)
	private String menuIdjson;
	@Column(name = "ROLE_DESCRIPTION")
	private String roleDescription;
	@Column(name = "ROLE_PURPOSE")
	private String rolePurpose;
	@Column(name = "approval_rejected")
	private String approvalRejected;
	@Column(name = "suspended") // Intentionally suspend a user
	private Integer suspended;

	public String getApprovalRejected() {
		return approvalRejected;
	}

	public void setApprovalRejected(String approvalRejected) {
		this.approvalRejected = approvalRejected;
	}

	public Integer getSuspended() {
		return suspended;
	}

	public void setSuspended(Integer suspended) {
		this.suspended = suspended;
	}

	public RolesIdentity getRolesIdentity() {
		return rolesIdentity;
	}

	public void setRolesIdentity(RolesIdentity rolesIdentity) {
		this.rolesIdentity = rolesIdentity;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Date getApprovalTs() {
		return approvalTs;
	}

	public void setApprovalTs(Date approvalTs) {
		this.approvalTs = approvalTs;
	}

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getMenuIdjson() {
		return menuIdjson;
	}

	public void setMenuIdjson(String menuIdjson) {
		this.menuIdjson = menuIdjson;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getRolePurpose() {
		return rolePurpose;
	}

	public void setRolePurpose(String rolePurpose) {
		this.rolePurpose = rolePurpose;
	}

	public String getRoleID() {
		return rolesIdentity.getRoleID();
	}

}
