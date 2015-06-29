package com.madhouse.fmp.domain;

import java.util.Date;

public class FmpUserFacebookAccount {
	
	private Integer id;
	private Integer fmpUserId;
	private Integer fbAdaccountId;
	private Date updateTime;
	private Integer imported;
	
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
	public Integer getFbAdaccountId() {
		return fbAdaccountId;
	}
	public void setFbAdaccountId(Integer fbAdaccountId) {
		this.fbAdaccountId = fbAdaccountId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getImported() {
		return imported;
	}
	public void setImported(Integer imported) {
		this.imported = imported;
	}
	
}
