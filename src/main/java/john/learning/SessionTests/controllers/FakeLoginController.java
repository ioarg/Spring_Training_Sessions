package john.learning.SessionTests.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class FakeLoginController {

    private void updateModel(Model model, HttpSession session){
        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
    }


    @GetMapping("/")
    public String getHomePage(){
        return "homepage";
    }

    @GetMapping("/welcomepage")
    public String getWelcomePage(HttpSession session, Model model){
        updateModel(model, session);
        return "welcomepage";
    }

    @GetMapping("/page2")
    public String getSecondPage(HttpSession session, Model model){
        updateModel(model, session);
        return "page2";
    }

    @PostMapping("/fakelogin")
    public String loginRedirect(@RequestParam String firstName, @RequestParam String lastName,
                                HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        return "redirect:/welcomepage";
    }
}
