package com.madhouse.fmp.dao.imp;

import org.springframework.stereotype.Repository;

import com.madhouse.fmp.base.BaseDao;
import com.madhouse.fmp.dao.IFmpAccountInfoDao;
import com.madhouse.fmp.domain.FacebookAccount;
import com.madhouse.fmp.domain.FmpAccountInfo;

@Repository
public class FmpAccountInfoDao extends BaseDao<FacebookAccount, Integer>
implements IFmpAccountInfoDao{

	@Override
	public FmpAccountInfo add(FmpAccountInfo fmpAccountInfo) {
		try {
			return this.add(fmpAccountInfo);
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	@Override
	public FmpAccountInfo queryFmpAccountInfoByUserName(String userName) {
		try {
			return (FmpAccountInfo) this.session.selectOne("getRecordByUserName", userName);
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
