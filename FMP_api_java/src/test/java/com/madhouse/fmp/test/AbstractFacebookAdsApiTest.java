package com.madhouse.fmp.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ads.impl.FacebookAdsTemplate;
import org.springframework.social.facebook.api.impl.MadFacebookTemplate;
import org.springframework.test.web.client.MockRestServiceServer;

public class AbstractFacebookAdsApiTest {

	protected static final String ACCESS_TOKEN = "CAACEdEose0cBAO45yZCBL2KjDcTDLa6CAMhLkw6T0NCNoFhOj90MUc8YxybgyCJ1N8YmGlJin93F54l8aTib8ZCIiZA3W4fsbYGeDouNFCuSDhRA6ActpppsLxujsZAQH1vG9EZAcNkpA1DKTVCZAGkmJnFc2Ub69JalcLz0lyfcw3khpZBZBJK5QVXjyTkoAjTCBJw3VyMSjF0KPkAVRy0tt1qNEGkmRYAZD";
	private static final DateFormat FB_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
	protected FacebookAdsTemplate facebookAds;
	protected FacebookAdsTemplate unauthorizedFacebookAds;
	protected MockRestServiceServer mockServer;
	protected MockRestServiceServer unauthorizedMockServer;
	public MadFacebookTemplate facebook;
	public Facebook fb;

	@Before
	public void setUp() throws Exception {
		facebookAds = new FacebookAdsTemplate(ACCESS_TOKEN);
		unauthorizedFacebookAds = new FacebookAdsTemplate();
		
//		mockServer = MockRestServiceServer.createServer(facebookAds.getRestTemplate());
//		unauthorizedMockServer = MockRestServiceServer.createServer(unauthorizedFacebookAds.getRestTemplate());
	}

	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}

	protected Date toDate(String dateString) {
		try {
			return FB_DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

}
