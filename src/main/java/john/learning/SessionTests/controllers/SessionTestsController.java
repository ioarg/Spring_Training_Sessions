package john.learning.SessionTests.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SessionTestsController {

    private void updateModel(Model model, HttpSession session){
        String username = (String) session.getAttribute("username");
        String color = (String) session.getAttribute("color");
        model.addAttribute("username", username);
        model.addAttribute("color", color);
        System.out.println("Session : {username=" + username+", color="+color+"}");
        System.out.println("Model : " + model);
    }

    @GetMapping("/welcome")
    public String getWelcomePage(HttpSession session, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        session.setAttribute("username", username);
        updateModel(model, session);
        return "welcomepage";
    }

    @PostMapping("/welcome/colorChange")
    public String colorChange(@RequestParam String color, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        session.setAttribute("color", color);
        return "redirect:/welcome";
    }

    @GetMapping("/page2")
    public String getSecondPage(HttpSession session, Model model){
        updateModel(model, session);
        return "page2";
    }

}
