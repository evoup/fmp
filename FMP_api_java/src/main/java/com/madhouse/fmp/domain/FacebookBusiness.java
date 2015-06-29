package com.madhouse.fmp.domain;

import java.sql.Blob;
import java.util.Date;

public class FacebookBusiness {

	private Integer id;
	private String businessId;
	private String businessName;
	private String primaryPageCategory;
	private String primaryPageName;
	private String primaryPageId;
	private Blob profilePic;
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPrimaryPageCategory() {
		return primaryPageCategory;
	}
	public void setPrimaryPageCategory(String primaryPageCategory) {
		this.primaryPageCategory = primaryPageCategory;
	}
	public String getPrimaryPageName() {
		return primaryPageName;
	}
	public void setPrimaryPageName(String primaryPageName) {
		this.primaryPageName = primaryPageName;
	}
	public String getPrimaryPageId() {
		return primaryPageId;
	}
	public void setPrimaryPageId(String primaryPageId) {
		this.primaryPageId = primaryPageId;
	}
	public Blob getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(Blob profilePic) {
		this.profilePic = profilePic;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
