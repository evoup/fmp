package org.springframework.social.facebook.api.ads;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ads.impl.FacebookAdsTemplate;

/**
 * Interface specifying a basic set of operations for interacting with Facebook Marketing API.
 * Implemented by {@link FacebookAdsTemplate}.
 *
 * @author Sebastian Górecki
 */
public interface FacebookAds extends Facebook{
	AccountOperations accountOperations();
	CampaignOperations campaignOperations();
	AdSetOperations adSetOperations();
	CreativeOperations creativeOperations();
}