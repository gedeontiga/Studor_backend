package com.studor.orientation_student.manager.controllers.establimentsuggestioncontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/establishment")
public class EstablishmentSuggestionController {

    // @Autowired
    // private EstablishmentSuggestService establishmentSuggestService;

    @GetMapping("/suggested")
    public String getMethodName(Model model, HttpSession session) {
        // Map<String, Object> establishmentMap = establishmentSuggestService.suggestEstablishmentOnJob(session);
        // model.addAttribute("establishmentMap", establishmentMap);
        return "establishment-job-templates/establishment-details";
    }
    
}
