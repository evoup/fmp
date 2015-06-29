package com.madhouse.fmp.domain;

import java.sql.Blob;
import java.util.Date;

public class FmpLog {
	
	private Integer id;
	private Integer fmpUserId;
	private Blob logType;
	private String content;
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
	public Blob getLogType() {
		return logType;
	}
	public void setLogType(Blob logType) {
		this.logType = logType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
