package com.madhouse.fmp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madhouse.fmp.dao.IFmpAccountInfoDao;
import com.madhouse.fmp.domain.FmpAccountInfo;
import com.madhouse.fmp.service.IFacebookAccessService;

@Service
public class FacebookAccessService implements IFacebookAccessService {

	@Autowired
	private IFmpAccountInfoDao fmpAccountInfoDao;
	
	@Override
	public FmpAccountInfo addFmpAccount(FmpAccountInfo fmpAccountInfo) {
		
		return fmpAccountInfoDao.add(fmpAccountInfo);
	}

}
