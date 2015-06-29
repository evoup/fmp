package com.madhouse.fmp.dao;

import com.madhouse.fmp.domain.FmpAccountInfo;

public interface IFmpAccountInfoDao{
	
	FmpAccountInfo add(FmpAccountInfo fmpAccountInfo);

	FmpAccountInfo queryFmpAccountInfoByUserName(String userName);
	
}
