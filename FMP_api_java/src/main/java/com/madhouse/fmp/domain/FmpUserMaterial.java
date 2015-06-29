package com.madhouse.fmp.domain;

import java.util.Date;

public class FmpUserMaterial {
	
	private Integer id;
	private Integer fmpUserId;
	private String fmpMaterialHash;
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFmpUserId() {
		return fmpUserId;
	}
	public void setFmpUserId(Integer fmpUserId) {
		this.fmpUserId = fmpUserId;
	}
	public String getFmpMaterialHash() {
		return fmpMaterialHash;
	}
	public void setFmpMaterialHash(String fmpMaterialHash) {
		this.fmpMaterialHash = fmpMaterialHash;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
