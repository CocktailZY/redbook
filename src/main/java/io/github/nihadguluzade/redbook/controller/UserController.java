package io.github.nihadguluzade.redbook.controller;

import io.github.nihadguluzade.redbook.dao.UserRepository;
import io.github.nihadguluzade.redbook.entity.UsersEntity;
import io.github.nihadguluzade.redbook.service.UsersService;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        /*if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress:" + accountDto.getEmail());
        }*/
        model.addAttribute("user", new UsersEntity());
        model.addAttribute("metaTitle", "Sign up to Redbook");
        return "register";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("MM/dd/yyyy"));
    }

    @PostMapping("/processSignUp")
    public String processSignUp(@ModelAttribute("user") UsersEntity userEntity) {
        usersService.save(userEntity);
        return "redirect:/oauth_login?signup=success";
    }

}
