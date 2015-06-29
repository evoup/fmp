package com.madhouse.fmp.dao.imp;

import org.springframework.stereotype.Repository;

import com.madhouse.fmp.base.BaseDao;
import com.madhouse.fmp.dao.IFacebookAccountDao;
import com.madhouse.fmp.domain.FacebookAccount;

@Repository
public class FacebookAccountDao extends BaseDao<FacebookAccount, Integer>
		implements IFacebookAccountDao {
	public FacebookAccount addFacebookAccount(FacebookAccount fbAccount) {
		try {
			return this.add(fbAccount);
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
