package com.madhouse.fmp.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.ads.FacebookAds;

public class FacebookAdsConnectionFactory extends
		OAuth2ConnectionFactory<FacebookAds> {
	
	public FacebookAdsConnectionFactory(String appId, String appSecret) {
		this(appId, appSecret, null);
	}

	public FacebookAdsConnectionFactory(String appId, String appSecret,
			String appNamespace) {
		super("facebook", new FacebookAdsServiceProvider(appId, appSecret,
				appNamespace), new FacebookAdsAdapter());
	}

}
