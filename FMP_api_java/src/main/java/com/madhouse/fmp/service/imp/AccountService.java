package com.madhouse.fmp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.madhouse.fmp.dao.IAccountDao;
import com.madhouse.fmp.domain.Account;
import com.madhouse.fmp.service.IAccountService;

@Service
@Transactional
public class AccountService implements IAccountService {
	@Autowired
	private IAccountDao accountDao;
	
	@Override
	public Account addAccount(Account account) {
		
		return accountDao.addAccount(account);
	}
	
	@Override
	public Account queryAccount(String username) {
		
		return accountDao.queryAccount(username);
	}

}
