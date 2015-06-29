package com.madhouse.fmp.dao.imp;

import org.springframework.stereotype.Repository;

import com.madhouse.fmp.base.BaseDao;
import com.madhouse.fmp.dao.IAccountDao;
import com.madhouse.fmp.domain.Account;

@Repository
public class AccountDao extends BaseDao<Account, Integer>
implements IAccountDao {
	
	@Override
	public Account addAccount(Account account) {
		try {
			return this.add(account);
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	@Override
	public Account queryAccount(String username) {
		try {
			return (Account) this.session.selectOne("getAccountByUserName", username);
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
