package com.madhouse.fmp.controller;

import java.security.Principal;

import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.madhouse.fmp.service.IAccountService;

@Controller
public class IndexController {
	
    private final Provider<ConnectionRepository> connectionRepositoryProvider;
    private final IAccountService accountService;

	@Autowired
    public IndexController(Provider<ConnectionRepository> connectionRepositoryProvider, IAccountService accountService) {
        this.connectionRepositoryProvider = connectionRepositoryProvider;
        this.accountService = accountService;
    }
	
    @RequestMapping("/")
    public String home(Principal currentUser, Model model) {
        model.addAttribute("connectionsToProviders", getConnectionRepository().findAllConnections());
        model.addAttribute(accountService.queryAccount(currentUser.getName()));
        return "view";
    }

    private ConnectionRepository getConnectionRepository() {
        return connectionRepositoryProvider.get();
    }
}
