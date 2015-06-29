package com.madhouse.fmp.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.ads.AdCampaign;
import org.springframework.social.facebook.api.ads.AdSet;
import org.springframework.social.facebook.api.ads.CampaignOperations;
import org.springframework.social.facebook.api.ads.FacebookAds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.madhouse.fmp.dto.RespDto;
import com.madhouse.fmp.dto.ResponseDataDto;
import com.madhouse.fmp.dto.ResponseListDto;
import com.madhouse.fmp.service.ICampaignOperationsService;

@Controller
@RequestMapping("facebook/campagin")
public class FacebookCampaignOperationController {
	
	private static final Logger log = LoggerFactory.getLogger(FacebookCampaignOperationController.class);
	
	@Autowired
	private ICampaignOperationsService campaignOperationsService;
	
	@Autowired
	private FacebookAds facebook;
	
	CampaignOperations opr;
	
	private CampaignOperations init() {
		opr = facebook.campaignOperations();
		return opr;
	}
	
	@RequestMapping("adCampaign")
	@ResponseBody
	public ResponseDataDto getAdCampaign(HttpServletRequest req) {
		init();
		ResponseDataDto respDto = new ResponseDataDto();
		String campaignId = req.getParameter("campaignId");
		respDto.setData(opr.getAdCampaign(campaignId));
		return respDto;
	}
	
	@RequestMapping("adCampaigns")
	@ResponseBody
	public ResponseListDto<AdCampaign> getAdCampaigns(HttpServletRequest req) {
		init();
		String adAccountId = req.getParameter("adAccountId");
		ResponseListDto<AdCampaign> respDto = new ResponseListDto<>();
		respDto.setList(opr.getAdCampaigns(adAccountId));
		return respDto;
	}
	
	@RequestMapping("adCampaignInsight")
	@ResponseBody
	public ResponseDataDto getAdCampaignInsight(HttpServletRequest req) {
		init();
		String campaignId = req.getParameter("campaignId");
		ResponseDataDto respDto = new ResponseDataDto();
		respDto.setData(opr.getAdCampaignInsight(campaignId));
		return respDto;
	}
	
	@RequestMapping("adCampaignSets")
	@ResponseBody
	public ResponseListDto<AdSet> getAdCampaignSets(HttpServletRequest req) {
		init();
		String campaignId = req.getParameter("campaignId");
		ResponseListDto<AdSet> respDto = new ResponseListDto<>();
		respDto.setList(opr.getAdCampaignSets(campaignId));
		return respDto;
	}
	
	@RequestMapping("toCreateAdCampaign")
	public String toCreateAdCampaign() {
		return "createAdCampaign";
	}
	
	@RequestMapping(value="createAdCampaign", method=RequestMethod.POST)
	@ResponseBody
	public RespDto createAdCampaign(@RequestParam("adAccountId") String adAccountId, @RequestParam("adCampaign") AdCampaign adCampaign) {
		init();
		RespDto respDto = new RespDto();
		String id = opr.createAdCampaign(adAccountId, adCampaign);
		respDto.setResult(0);
		respDto.setMsg("new ad campaign id is :" + id);
		return respDto;
	}
	
	
	
	
	
	
	
	
	
	
}
