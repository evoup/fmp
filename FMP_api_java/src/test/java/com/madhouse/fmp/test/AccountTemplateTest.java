package com.madhouse.fmp.test;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

import java.util.List;

import org.junit.Test;
import org.springframework.social.facebook.api.ads.AdAccount;

public class AccountTemplateTest extends AbstractFacebookAdsApiTest {

	private static final String GET_ADACCOUNT_REQUEST_URI = "https://graph.facebook.com/v2.3/act_712344008863677?fields=id%2Caccount_id%2Caccount_status%2Cage%2Camount_spent%2Cbalance%2Cbusiness_city%2Cbusiness_country_code%2Cbusiness_name%2Cbusiness_state%2Cbusiness_street%2Cbusiness_street2%2Cbusiness_zip%2Ccapabilities%2Ccreated_time%2Ccurrency%2Cdaily_spend_limit%2Cend_advertiser%2Cfunding_source%2Cfunding_source_details%2Cis_personal%2Cmedia_agency%2Cname%2Coffsite_pixels_tos_accepted%2Cpartner%2Cspend_cap%2Ctimezone_id%2Ctimezone_name%2Ctimezone_offset_hours_utc%2Cusers%2Ctax_id_status";
	private static final String GET_ADACCOUNTS_REQUEST_URI = "https://graph.facebook.com/v2.3/653740558060746/adaccounts?fields=id%2Caccount_id%2Caccount_status%2Cage%2Camount_spent%2Cbalance%2Cbusiness_city%2Cbusiness_country_code%2Cbusiness_name%2Cbusiness_state%2Cbusiness_street%2Cbusiness_street2%2Cbusiness_zip%2Ccapabilities%2Ccreated_time%2Ccurrency%2Cdaily_spend_limit%2Cend_advertiser%2Cfunding_source%2Cfunding_source_details%2Cis_personal%2Cmedia_agency%2Cname%2Coffsite_pixels_tos_accepted%2Cpartner%2Cspend_cap%2Ctimezone_id%2Ctimezone_name%2Ctimezone_offset_hours_utc%2Cusers%2Ctax_id_status";
	private static final String GET_ADACCOUNT_USERS_REQUEST_URI = "https://graph.facebook.com/v2.3/act_712344008863677/users";
	private static final String GET_ADACCOUNT_INSIGHT = "https://graph.facebook.com/v2.3/act_712344008863677/insights?fields=account_id%2Caccount_name%2Cdate_start%2Cdate_stop%2Cactions_per_impression%2Cclicks%2Cunique_clicks%2Ccost_per_result%2Ccost_per_total_action%2Ccpc%2Ccost_per_unique_click%2Ccpm%2Ccpp%2Cctr%2Cunique_ctr%2Cfrequency%2Cimpressions%2Cunique_impressions%2Cobjective%2Creach%2Cresult_rate%2Cresults%2Croas%2Csocial_clicks%2Cunique_social_clicks%2Csocial_impressions%2Cunique_social_impressions%2Csocial_reach%2Cspend%2Ctoday_spend%2Ctotal_action_value%2Ctotal_actions%2Ctotal_unique_actions%2Cactions%2Cunique_actions%2Ccost_per_action_type%2Cvideo_start_actions";
	private static final double EPSILON = 0.000000000001;
	
	@Test
	public void getAccount() {
//		mockServer.expect(requestTo(GET_ADACCOUNT_REQUEST_URI)).andExpect(method(GET)).andExpect(header("Authorization", "OAuth someAccessToken"));
//		List<AdAccount> adAccounts = facebookAds.accountOperations().getAdAccounts("653740558060746");
//		System.out.println(adAccounts.size());
//		AdAccount adAccount = facebookAds.accountOperations().updateAdAccount(paramString, paramAdAccount)
		System.out.println();
	}

}
