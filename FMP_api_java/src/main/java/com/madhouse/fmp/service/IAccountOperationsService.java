package com.madhouse.fmp.service;

import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.ads.AdAccount;

public interface IAccountOperationsService {
	
	AdAccount getAdAccount(String userName, String adAccountId) throws Exception;

	PagedList<AdAccount> getAdAccounts(String userName) throws Exception;
}
