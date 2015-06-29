package com.madhouse.fmp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.ads.AdAccount;
import org.springframework.social.facebook.api.ads.AdCampaign;
import org.springframework.social.facebook.api.ads.AdInsight;
import org.springframework.social.facebook.api.ads.AdUser;
import org.springframework.social.facebook.api.ads.AdUser.AdUserRole;
import org.springframework.social.facebook.api.ads.FacebookAds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.madhouse.fmp.dto.RespDto;
import com.madhouse.fmp.dto.ResponseDataDto;
import com.madhouse.fmp.dto.ResponseListDto;
import com.madhouse.fmp.service.IAccountOperationsService;

@Controller
@RequestMapping("facebook/account")
public class FaceBookAccountOperationController {

	private static final Logger log = LoggerFactory.getLogger(FaceBookAccountOperationController.class);
	
	@Autowired
	private IAccountOperationsService accountOperationsService;
	
	@Autowired
	private FacebookAds facebook;
	
	
//	@RequestMapping("adAccount")
//	@ResponseBody
//	public RespDto getAdAccount(HttpServletRequest req) {
//		RespDto respDto = new RespDto();
//		respDto.setCode(0);
//		respDto.setMsg("get ad account succeeded");
//		String adAccountId = req.getParameter("adAccountId");
//		try {
//			AdAccount adAccount = accountOperationsService.getAdAccount(getUser(req), adAccountId);
////			respDto.setResult(adAccount);
//		} catch (Exception e) {
//			log.debug(e.getMessage());
//			respDto.setCode(1);
//			respDto.setMsg("an error occurred");
//		}
//		
//		return respDto;
//	}
	
	@RequestMapping("adAccounts")
	@ResponseBody
	public ResponseListDto<AdAccount> getAdAccounts(HttpServletRequest req) {
		ResponseListDto<AdAccount> respDto = new ResponseListDto<>();
		String userId = facebook.userOperations().getUserProfile().getId();
		PagedList<AdAccount> adAcc = facebook.accountOperations().getAdAccounts(userId);
		respDto.setTotal(adAcc.size());
		respDto.setList(adAcc);
		return respDto;
	}
	
	@RequestMapping("adAccoutCampaigns")
	@ResponseBody
	public ResponseListDto<AdCampaign> getAdAccountCampaigns(HttpServletRequest req) {
		String adAccountId = req.getParameter("adAccountId");
		ResponseListDto<AdCampaign> respDto = new ResponseListDto<>();
		PagedList<AdCampaign> adCam = facebook.accountOperations().getAdAccountCampaigns(adAccountId);
		respDto.setList(adCam);
		return respDto;
	}
	
	@RequestMapping("adAccountInsight")
	@ResponseBody
	public ResponseDataDto getAdAccountCampaign(HttpServletRequest req) {
		String adAccountId = req.getParameter("adAccountId");
		ResponseDataDto respDto = new ResponseDataDto();
		List<AdInsight> adIns = new ArrayList<>();
		adIns.add(facebook.accountOperations().getAdAccountInsight(adAccountId));
		respDto.setData(adIns);
		return respDto;
	}
	
	
	@RequestMapping("adAccount")
	@ResponseBody
	public ResponseDataDto getAdAccount(HttpServletRequest req) {
		String adAccountId = req.getParameter("adAccountId");
		ResponseDataDto respDto = new ResponseDataDto();
		List<AdAccount> adAcc = new ArrayList<>();
		adAcc.add(facebook.accountOperations().getAdAccount(adAccountId));
		respDto.setData(adAcc);
		return respDto;
	}
	
	@RequestMapping("adAccountUsers")
	@ResponseBody
	public ResponseListDto<AdUser> getAdAccountUsers(HttpServletRequest req) {
		String adAccountId = req.getParameter("adAccountId");
		ResponseListDto<AdUser> respDto = new ResponseListDto<>();
		PagedList<AdUser> adUsr = facebook.accountOperations().getAdAccountUsers(adAccountId);
		respDto.setList(adUsr);
		return respDto;
	}
	
	
	
	@RequestMapping("addUserToAdAccount")
	@ResponseBody
	public RespDto addUserToAdAccount(HttpServletRequest req) {
		String adAccountId = req.getParameter("adAccountId");
		String userId = req.getParameter("userId");
//		String role = req.getParameter("role");
		RespDto respDto = new RespDto();respDto.setResult(0);
		try {
			facebook.accountOperations().addUserToAdAccount(adAccountId, userId, AdUserRole.ADVERTISER);
		} catch (Exception e) {
			respDto.setMsg("operation failed.");
		}
		return respDto;
	}
	
	
	@RequestMapping("deleteUserFromAdAccount")
	@ResponseBody
	public RespDto deleteUserFromAdAccount(HttpServletRequest req) {
		String adAccountId = req.getParameter("adAccountId");
		String userId = req.getParameter("userId");
		RespDto respDto = new RespDto();respDto.setResult(0);
		facebook.accountOperations().deleteUserFromAdAccount(adAccountId, userId);
		return respDto;
	}
	
	
}
