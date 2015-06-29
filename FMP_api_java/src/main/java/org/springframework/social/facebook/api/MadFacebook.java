/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package org.springframework.social.facebook.api;

import org.springframework.social.ApiBinding;
import org.springframework.web.client.RestOperations;

public abstract interface MadFacebook extends MadGraphApi, ApiBinding {
	
	/**
	 * API for working with achievements.
	 * @return {@link AchievementOperations}
	 */
	AchievementOperations achievementOperations();
	
	/**
	 * API for reading and posting comments.
	 * @return {@link CommentOperations}
	 */
	CommentOperations commentOperations();

	/**
	 * API for performing operations on events.
	 * @return {@link EventOperations}
	 */
	EventOperations eventOperations();
	
	/**
	 * API for performing operations on feeds.
	 * @return {@link FeedOperations}
	 */
	FeedOperations feedOperations();

	/**
	 * API for performing operations with a user's set of friends.
	 * @return {@link FriendOperations}
	 */
	FriendOperations friendOperations();
	
	/**
	 * API for performing operations on groups.
	 * @return {@link GroupOperations}
	 */
	GroupOperations groupOperations();

	/**
	 * API for performing operations against user likes and interests.
	 * @return {@link LikeOperations}
	 */
	LikeOperations likeOperations();
	
	/**
	 * API for performing operations on albums, photos, and videos.
	 * @return {@link MediaOperations}
	 */
	MediaOperations mediaOperations();
	
	/**
	 * API for working with OpenGraph actions.
	 * @return {@link OpenGraphOperations}
	 */
	OpenGraphOperations openGraphOperations();
	
	/**
	 * API for working with Facebook pages.
	 * @return {@link PageOperations}
	 */
	PageOperations pageOperations();
	
	/**
	 * API for working with social contexts.
	 * @return {@link SocialContextOperations}
	 */
	SocialContextOperations socialContextOperations();
	
	/**
	 * API for working with test users on Facebook.
	 * @return {@link TestUserOperations}
	 */
	TestUserOperations testUserOperations();
	
	/**
	 * API for performing operations on Facebook user profiles.
	 * @return {@link UserOperations}
	 */
	UserOperations userOperations();
	
	/**
	 * Returns the underlying {@link RestOperations} object allowing for consumption of Facebook endpoints that may not be otherwise covered by the API binding.
	 * The RestOperations object returned is configured to include an OAuth 2 "Authorization" header on all requests.
	 * @return RestOperations instrumented to include Authorization header on all requests
	 */
	RestOperations restOperations();
	
	/**
	 * @return the application namespace that this FacebookTemplate was created for. May be null if no namespace was given.
	 */
	String getApplicationNamespace();
	
//	AccountOperations accountOperations();
//	
//	CampaignOperations campaignOperations();
//	
//	AdSetOperations adSetOperations();
//	
//	CreativeOperations creativeOperations();
	
}