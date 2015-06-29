package com.madhouse.fmp.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.TestUser;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.api.impl.MadFacebookTemplate;
import org.springframework.social.facebook.connect.FacebookServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

import com.madhouse.fmp.dao.IFacebookAccountDao;
import com.madhouse.fmp.domain.FacebookAccount;

public class FacebookTest extends SpringContextTestBase{

	@Autowired
	private IFacebookAccountDao dao;
	
	@Test
	public void facebookAccountDaoTest() {
		FacebookAccount fba = new FacebookAccount();
		fba.setAccessToken("AccessToken");
		fba.setAdAccountDetail("adAccountDetail");
		fba.setAdAccountId("adAccountId");
		fba.setAdAccountName("adAccountName");
		fba.setUpdateTime(new Date());
		fba.setWantSync(1);
		dao.addFacebookAccount(fba);
		System.out.println();
	}
	
	
	@Test
	public void facebookAccountTest() {
		
	}
	
	@Test
	public void setup() {
		OAuth2Operations oauth = new FacebookServiceProvider("1644776589073923", "655b6a6d3484c0abfce98c72ca13c839", null).getOAuthOperations();
		AccessGrant clientGrant = oauth.authenticateClient();
		MadFacebookTemplate clientFacebook = new MadFacebookTemplate(clientGrant.getAccessToken(), "", "1644776589073923");
		TestUser testUser = clientFacebook.testUserOperations().createTestUser(true, "", "wolegequ");
		Facebook fb = new FacebookTemplate(testUser.getAccessToken());
		fb.feedOperations().getFeed();
		System.out.println();
	}
	
}
