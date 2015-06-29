package com.madhouse.fmp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.ads.AdAccount;
import org.springframework.social.facebook.api.ads.impl.FacebookAdsTemplate;
import org.springframework.stereotype.Service;

import com.madhouse.fmp.dao.IFmpAccountInfoDao;
import com.madhouse.fmp.domain.FmpAccountInfo;
import com.madhouse.fmp.service.IAccountOperationsService;

@Service
public class AccountOperationsService implements IAccountOperationsService {

	@Autowired
	private IFmpAccountInfoDao fmpAccountInfoDao;

	private FmpAccountInfo getAccountInfo(String userName) throws Exception {
		
		FmpAccountInfo info = fmpAccountInfoDao
				.queryFmpAccountInfoByUserName(userName);

		return info;
	}

	@Override
	public AdAccount getAdAccount(String userName, String adAccountId)
			throws Exception {

		FmpAccountInfo info = getAccountInfo(userName);
		if (info == null) {
			return null;
		}
		
		FacebookAdsTemplate template = new FacebookAdsTemplate(info.getAccessToken());
		return template.accountOperations().getAdAccount(adAccountId);

	}
	
	@Override
	public PagedList<AdAccount> getAdAccounts(String userName) throws Exception{
		
		FmpAccountInfo info = getAccountInfo(userName);
		if (info == null) {
			return null;
		}
		
		FacebookAdsTemplate template = new FacebookAdsTemplate(info.getAccessToken());
		return template.accountOperations().getAdAccounts(info.getFacebookUserId());
	}

}
