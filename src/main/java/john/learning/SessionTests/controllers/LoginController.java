package john.learning.SessionTests.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/home")
    public String logIn(){
        return "homepage";
    }

    @GetMapping("/unauthorized")
    public String unauthorizedAccess(){
        return "unauthorized";
    }
}
