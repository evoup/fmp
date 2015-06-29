package com.madhouse.fmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.madhouse.fmp.domain.FmpAccountInfo;
import com.madhouse.fmp.dto.RespDto;
import com.madhouse.fmp.service.IFacebookAccessService;

@Controller
@RequestMapping("access")
public class FacebookAccessController {
	
	@Autowired
	IFacebookAccessService facebookAccessService;
	
	@RequestMapping("add")
	@ResponseBody
	public RespDto addFmpAccount(FmpAccountInfo fmpAccountInfo) {
		
		facebookAccessService.addFmpAccount(fmpAccountInfo);
		return new RespDto();
	}
}
