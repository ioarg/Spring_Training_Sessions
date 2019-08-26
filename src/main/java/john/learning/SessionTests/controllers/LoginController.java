package john.learning.SessionTests.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String logIn(){
        return "homepage";
    }

    //After successful log in Spring Security automatically invokes this
    @GetMapping("/")
    public String redirect(){
        return "redirect:/welcome";
    }

    @GetMapping("/unauthorized")
    public String unauthorizedAccess(){
        return "unauthorized";
    }
}
