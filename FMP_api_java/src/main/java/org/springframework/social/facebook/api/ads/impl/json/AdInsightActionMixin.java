package org.springframework.social.facebook.api.ads.impl.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.social.facebook.api.impl.json.MadFacebookObjectMixin;

/**
 * @author Sebastian Górecki
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AdInsightActionMixin extends MadFacebookObjectMixin {
    @JsonProperty("action_type")
    private String actionType;

    @JsonProperty("value")
    private double value;
}