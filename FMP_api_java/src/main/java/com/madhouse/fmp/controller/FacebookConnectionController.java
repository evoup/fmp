package com.madhouse.fmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.ads.FacebookAds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("facebook")
public class FacebookConnectionController {
	
	@Autowired
	private FacebookAds facebook;

//    @Inject
//    public FacebookConnectionsController(LinkedIn linkedIn) {
//        this.linkedIn = linkedIn;
//    }

    @RequestMapping(value="connections", method= RequestMethod.GET)
    public String connections(Model model) {
        model.addAttribute("connection", facebook.userOperations().getUserProfile());
        return "connections";
    }
}
