package com.madhouse.fmp.domain;

import java.util.Date;

public class FacebookAccount {
	
	private Integer id;
	private String adAccountName;
	private String adAccountId;
	private String accessToken;
	private String adAccountDetail;
	private Integer wantSync;
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAdAccountName() {
		return adAccountName;
	}
	public void setAdAccountName(String adAccountName) {
		this.adAccountName = adAccountName;
	}
	public String getAdAccountId() {
		return adAccountId;
	}
	public void setAdAccountId(String adAccountId) {
		this.adAccountId = adAccountId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getAdAccountDetail() {
		return adAccountDetail;
	}
	public void setAdAccountDetail(String adAccountDetail) {
		this.adAccountDetail = adAccountDetail;
	}
	public Integer getWantSync() {
		return wantSync;
	}
	public void setWantSync(Integer wantSync) {
		this.wantSync = wantSync;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
