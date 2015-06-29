package com.madhouse.fmp.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.ads.AdAccount;
import org.springframework.social.facebook.api.ads.AdCampaign;
import org.springframework.social.facebook.api.ads.impl.FacebookAdsTemplate;

public class TemplateTest extends SpringContextTestBase{

	FacebookAdsTemplate template;
	@Before
	public void setup() {
		template = new FacebookAdsTemplate("CAACEdEose0cBAN83aGINnaWXvlQHZBjHuPQw0E4a2BCvVmrS1GfbE1JDI5udkAIOi2IVsT74KGwSaKZADg6acOU88A22uj0SNBB9wPibDGSRSsvf4nOWzZCdNEicZBhHq1z71DhDjMC26sQgJQcei9as3fJf3uSoQuGdWjpHEJix03PklIiNi95ZADurRj38XTZCg8UwC5uEp7769JDpUe");
	}
	
	@Test
	public void AccountOperationsTest() {
		
		
		PagedList<AdAccount> accounts = template.accountOperations().getAdAccounts("435512906629427");
		
		for (AdAccount ac : accounts) {
			System.out.println("name: " + ac.getName());
			System.out.println("AccountId: " + ac.getAccountId());
			System.out.println("Id: " + ac.getId());
		}
		
	}
	
	@Test
	public void CampaignOperationsTest() {
		
		PagedList<AdCampaign> campaigns = template.campaignOperations().getAdCampaigns("435512906629427");
		for (AdCampaign campaign : campaigns) {
			System.out.println(campaign.getAccountId());
			System.out.println(campaign.getId());
			System.out.println(campaign.getName());
			System.out.println(campaign.getStatus());
		}
		
	}
}
