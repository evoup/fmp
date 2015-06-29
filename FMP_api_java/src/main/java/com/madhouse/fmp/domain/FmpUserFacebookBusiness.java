package com.madhouse.fmp.domain;

import java.util.Date;

public class FmpUserFacebookBusiness {

	private Integer id;
	private Integer fmpUserId;
	private Integer fbBusinessId;
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
	public Integer getFbBusinessId() {
		return fbBusinessId;
	}
	public void setFbBusinessId(Integer fbBusinessId) {
		this.fbBusinessId = fbBusinessId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
