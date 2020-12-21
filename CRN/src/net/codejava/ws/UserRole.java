package net.codejava.ws;

import java.sql.Timestamp;

public class UserRole {
	
	private int id;
	private int version;
	private int userId;
	private int unitId;
	private int roleId;
	private String validFrom;
	private String validTo;
	
	public UserRole() {
	}

	public UserRole(int id, int version, int userId, int unitId, int roleId, String validFrom, String validTo) {
		this.id = id;
		this.version = version;
		this.userId = userId;
		this.unitId = unitId;
		this.roleId = roleId;
		this.validFrom = validFrom;
		this.validTo = validTo;
	}

	public UserRole(int id, int version) {
		this.id = id;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		UserRole other = (UserRole) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
