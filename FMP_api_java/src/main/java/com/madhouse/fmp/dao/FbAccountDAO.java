package com.madhouse.fmp.dao;

import com.madhouse.fmp.model.FbAccount;

public interface FbAccountDAO 
{
	public void saveOrUpdate(FbAccount fbaccount,int type);
	
}