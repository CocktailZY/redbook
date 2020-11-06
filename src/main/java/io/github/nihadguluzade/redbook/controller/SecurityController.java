package io.github.nihadguluzade.redbook.controller;

import io.github.nihadguluzade.redbook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private UsersService usersService;

    @GetMapping("/oauth_login")
    public String loginPage(Model model) {
        model.addAttribute("metaTitle", "Sign in to Redbook");
        return "login";
    }

    @RequestMapping("/oauth2LoginSuccess")
    public ModelAndView OAuth2LoginInfo(Model model, @AuthenticationPrincipal OAuth2AuthenticationToken authenticationToken) {
        OAuth2User oAuth2User = authenticationToken.getPrincipal();
        usersService.checkIfUserExists(oAuth2User);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/formLoginSuccess")
    public ModelAndView formLoginInfo(Model model, @AuthenticationPrincipal Authentication authentication) {
        // UserDetails is for form-based login
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new ModelAndView("redirect:/");
    }

}
