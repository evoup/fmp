package com.madhouse.fmp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.madhouse.fmp.domain.Account;
import com.madhouse.fmp.domain.SignupForm;
import com.madhouse.fmp.service.IAccountService;
import com.madhouse.fmp.signin.SignInUtils;

@Controller
public class SignupController {

	private final IAccountService accountService;

	@Autowired
	public SignupController(IAccountService accountService) {
		this.accountService = accountService;
	}

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public ModelAndView signupForm(WebRequest request) {
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
        SignupForm signupForm = null;
		if (connection != null) {
			request.setAttribute("message", "Your " + StringUtils.capitalize(connection.getKey().getProviderId()) + " account is not associated with a Spring Social Network Viewer account. If you're new, please sign up.", WebRequest.SCOPE_REQUEST);
            signupForm = SignupForm.fromProviderUser(connection.fetchUserProfile());
		} else {
            signupForm = new SignupForm();
		}
        return new ModelAndView("signup", "signup", signupForm);
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("signup") SignupForm form, BindingResult formBinding, WebRequest request) {
		if (formBinding.hasErrors()) {
			return null;
		}
		Account account = createAccount(form, formBinding);
		if (account != null) {
			SignInUtils.signin(account.getUsername());
			ProviderSignInUtils.handlePostSignUp(account.getUsername(), request);
			return "redirect:/";
		}
		return null;
	}

	private Account createAccount(SignupForm form, BindingResult formBinding) {
		try {
			Account account = new Account();
			account.setFirstName(form.getFirstName());
			account.setLastName(form.getLastName());
			account.setPassword(form.getPassword());
			account.setUsername(form.getUsername());
			accountService.addAccount(account);
			return account;
		} catch (Exception e) {
			formBinding.rejectValue("username", "user.duplicateUsername", "already in use");
			return null;
		}
	}

}
