package com.studor.orientation_student.manager.controllers.suggestionCourController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.entities.Profil;
import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.suggestionCourEntities.ObjectifProfil;
import com.studor.orientation_student.entities.suggestionCourEntities.Programme;
import com.studor.orientation_student.manager.repositories.ProfilRepository;
import com.studor.orientation_student.manager.repositories.UserRepository;
import com.studor.orientation_student.manager.repositories.suggestionCourRepository.ObjectifUtilisateurRepository;
import com.studor.orientation_student.manager.repositories.suggestionCourRepository.ProgrammeRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/objectif")
public class ObjectifController {

    @Autowired
    private HttpServletRequest request;

    @Autowired 
    private ProgrammeRepository programmeRepository;

    @Autowired
    private ObjectifUtilisateurRepository objectifUtilisateurRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfilRepository profilRepository;

    @GetMapping("/infoObjectif")
    public String infoObjectif(HttpSession session, Model model){
        model.addAttribute("objectif", new ObjectifProfil());

        return "suggestionCour/formObjectifUtilisateur";
    }

    @PostMapping("/saveObjectif")
    public String saveObjectif(HttpSession session, @ModelAttribute("objectif") ObjectifProfil objectifUtilisateur){
        User user = (User) session.getAttribute("user"); // on recupere les identifiants de l'utilisateur
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            // for(Cookie cookie : cookies){
            //     // User user2 =  (User) cookie.getValue();
            // }
        }
        if(user == null ){
            System.out.println(">>>>> la session a ete suprimer");
            // return "suggestionCour/formErrorSession";
            return "loginForm";
        }
        else{
            Profil profil = userRepository.findByEmail(user.getEmail()).get().getProfil(); // on recupere en base de donnee

            ObjectifProfil objectifUtilisateur1 = objectifUtilisateurRepository.findByDescriptionAndMoyenneAndStatuVise(objectifUtilisateur.getDescription(), objectifUtilisateur.getMoyenne(), objectifUtilisateur.getStatuVise());
            
            if(objectifUtilisateur1 != null ){
                System.out.println(">>>>>> cet objectif a deja ete defini !");
            }
            else{
                objectifUtilisateur.setProfil(profil);;
                objectifUtilisateurRepository.save(objectifUtilisateur); // on sauvegarde l'objectif defini par l'utilisateur
                objectifUtilisateur = objectifUtilisateurRepository.findByDescriptionAndMoyenneAndStatuVise(objectifUtilisateur.getDescription(), objectifUtilisateur.getMoyenne(), objectifUtilisateur.getStatuVise());
                profil.setObjectifProfil(objectifUtilisateur); // on met a jour les informations de la table utilisateur
            }

            
            Programme programme = new Programme(null, objectifUtilisateur.getDifficulte(), objectifUtilisateur.getDescription());
            
            Programme programme1 = programmeRepository.findByDescriptionAndDifficulte(programme.getDescription(), programme.getDifficulte());
            if(programme1 != null){
                System.out.println(">>>>>> ce programme a deja ete defini !");
            }
            else{
                programme.setProfil(profil);;
                programmeRepository.save(programme);
                programme = programmeRepository.findByDescriptionAndDifficulte(programme.getDescription(), programme.getDifficulte());
                List<Programme> programmes = new ArrayList<>();
                programmes.add(programme1);
                profil.setProgrammes(programmes);
                profilRepository.save(profil);
            }
            
            // on sauvegarde l'utilisateur en base de donne
            
            System.out.println("\n\n>>>>>>l' objectif et le programme de l'utilisateur "+profil.getNom()+"\n a ete defini avec success !");
            return "suggestionCour/formConfirmationSaveObjectif";
        }    
    }

    @GetMapping("/testClassification")
    public String testClassification(){
        return "suggestionCour/test_classification";
    }


}
