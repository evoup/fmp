package com.madhouse.fmp.dao;

import com.madhouse.fmp.domain.Account;

public interface IAccountDao {

	public Account addAccount(Account account);

	public Account queryAccount(String username);

}
