package com.madhouse.fmp.connect;

import org.springframework.social.facebook.api.ads.FacebookAds;
import org.springframework.social.facebook.api.ads.impl.FacebookAdsTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class FacebookAdsServiceProvider extends AbstractOAuth2ServiceProvider<FacebookAds> {

	private String appNamespace;

	public FacebookAdsServiceProvider(String appId, String appSecret,
			String appNamespace) {
		super(getOAuth2Template(appId, appSecret));
		this.appNamespace = appNamespace;
	}

	private static OAuth2Template getOAuth2Template(String appId,
			String appSecret) {
		OAuth2Template oAuth2Template = new OAuth2Template(appId, appSecret,
				"https://www.facebook.com/v2.3/dialog/oauth",
				"https://graph.facebook.com/v2.3/oauth/access_token");

		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}

	@Override
	public FacebookAds getApi(String accessToken) {
		return new FacebookAdsTemplate(accessToken);
	}

}
