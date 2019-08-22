package john.learning.SessionTests.controllers;

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
        model.addAttribute("firstName", username);
        model.addAttribute("lastName", color);
    }

    @GetMapping("/welcomepage")
    public String getWelcomePage(HttpSession session, Model model){
        updateModel(model, session);
        return "welcomepage";
    }

    @PostMapping("/welcomepage/colorChange")
    public String colorChange(@RequestParam String color, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        session.setAttribute("color", color);
        return "redirect:/welcomepage";
    }

    @GetMapping("/page2")
    public String getSecondPage(HttpSession session, Model model){
        updateModel(model, session);
        return "page2";
    }

}
