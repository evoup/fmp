package org.springframework.social.facebook.api.ads.impl.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.social.facebook.api.impl.json.MadFacebookObjectMixin;

/**
 * @author Sebastian Górecki
 */
public abstract class TargetingEntryMixin extends MadFacebookObjectMixin {
	@JsonProperty("id")
	private long id;

	@JsonProperty("name")
	private String name;
}