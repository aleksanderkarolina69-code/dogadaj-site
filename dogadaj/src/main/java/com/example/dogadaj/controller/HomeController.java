package com.example.dogadaj.controller;

import com.example.dogadaj.repository.ServicesRepositories;
import com.example.dogadaj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    final private String title = "DOGadaj | Behawiorysta psów - Karolina Teper |";

    @Autowired
    private ServicesRepositories servicesRepositories;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("title", title);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();

            if (!email.equals("anonymousUser")) {
                var user = userRepository.findByEmail(email).orElse(null);

                if (user != null) {
                    model.addAttribute("name", user.getName());
                }
            }
        }

        return "home";
    }
    @GetMapping("/uslugi")
    public String uslugi(Model model) {
        model.addAttribute("title", title);
        // Pobierz wszystkie usługi z bazy
        model.addAttribute("services", servicesRepositories.findAll());
        return "uslugi";
    }

    @GetMapping("/omnie")
    public String omnie(Model model){
        model.addAttribute("title", title);
        return "omnie";
    }

    @GetMapping("/kontakt")
    public String kontakt(Model model){
        model.addAttribute("title", title);
        return "kontakt";
    }

}
