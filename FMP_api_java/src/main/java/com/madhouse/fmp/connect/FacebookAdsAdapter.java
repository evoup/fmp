package com.madhouse.fmp.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.ads.FacebookAds;

public class FacebookAdsAdapter implements ApiAdapter<FacebookAds> {

	@Override
	public boolean test(FacebookAds facebook) {
		try {
			facebook.userOperations().getUserProfile();
			return true;
		} catch (ApiException e) {
		}
		return false;
	}

	@Override
	public void setConnectionValues(FacebookAds facebook,
			ConnectionValues values) {
		User profile = facebook.userOperations().getUserProfile();
		values.setProviderUserId(profile.getId());
		values.setDisplayName(profile.getName());
		values.setProfileUrl(profile.getLink());
		values.setImageUrl("https://graph.facebook.com/v2.3/" + profile.getId()
				+ "/picture");
	}

	@Override
	public UserProfile fetchUserProfile(FacebookAds facebook) {
		User profile = facebook.userOperations().getUserProfile();
		return new UserProfileBuilder().setName(profile.getName())
				.setFirstName(profile.getFirstName())
				.setLastName(profile.getLastName())
				.setEmail(profile.getEmail()).build();
	}

	@Override
	public void updateStatus(FacebookAds facebook, String message) {
		facebook.feedOperations().updateStatus(message);
	}

}
