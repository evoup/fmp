package com.madhouse.fmp.service;

import com.madhouse.fmp.domain.Account;

public interface IAccountService {

	public Account addAccount(Account account);

	public Account queryAccount(String username);

}
