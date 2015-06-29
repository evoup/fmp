package com.madhouse.fmp.domain;

import java.util.Date;

public class FmpAccountInfo {

	private Integer id;
	private String performadAccountId;
	private String performadAccountName;
	private String facebookUserId;
	private String facebookUserName;
	private String accessToken;
	private Date updateTime;
	private String profileUrl;
	private String imageUrl;
	private Integer expireTime;

	
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPerformadAccountId() {
		return performadAccountId;
	}
	public void setPerformadAccountId(String performadAccountId) {
		this.performadAccountId = performadAccountId;
	}
	public String getPerfirmadAccountName() {
		return performadAccountName;
	}
	public void setPerfirmadAccountName(String perfirmadAccountName) {
		this.performadAccountName = perfirmadAccountName;
	}
	public String getFacebookUserId() {
		return facebookUserId;
	}
	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}
	public String getFacebookUserName() {
		return facebookUserName;
	}
	public void setFacebookUserName(String facebookUserName) {
		this.facebookUserName = facebookUserName;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
