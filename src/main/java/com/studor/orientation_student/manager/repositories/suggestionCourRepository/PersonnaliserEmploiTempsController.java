package com.studor.orientation_student.manager.repositories.suggestionCourRepository;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/emploiPersonnalise")
public class PersonnaliserEmploiTempsController {
    
    @GetMapping("/acceuilEmploiPersonnalise")
    public String acceuilemploiPersonnalise(){
        return "fromAcceuilEmploiPersonnalise";
    }

    @PostMapping("/saveEmploiPersonnalise")
    public String saveEmploiPersonnalise(@RequestParam("discipline") List<String> disciplines){
        
        return "formConfirmationEmploiPersonnalise";
    }

    public class UniteEnseignement {
        private String discipline;
        private Long duree;
        public UniteEnseignement() {
        }
        public UniteEnseignement(String discipline, Long duree) {
            this.discipline = discipline;
            this.duree = duree;
        }
        public String getDiscipline() {
            return discipline;
        }
        public void setDiscipline(String discipline) {
            this.discipline = discipline;
        }
        public Long getDuree() {
            return duree;
        }
        public void setDuree(Long duree) {
            this.duree = duree;
        }

        
        
    }
}
