package com.studor.orientation_student.manager.services.profilepredictionservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.HashMap;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.profilejobprediction.Level;
import com.studor.orientation_student.entities.profilejobprediction.Subdomain;
import com.studor.orientation_student.manager.repositories.profilejobpredictrepository.LevelRepository;
import com.studor.orientation_student.manager.repositories.profilejobpredictrepository.MatterRepository;
import com.studor.orientation_student.manager.repositories.profilejobpredictrepository.OptionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@SessionScope
public class NotesReportSaverService {

    // private UserRepository userRepository;
    private MatterRepository matterRepository;
    // private NotesReportRepository notesReportRepository;
    private LevelRepository levelRepository;
    private OptionRepository optionRepository;

    public Boolean checkIfNotesReportExists(){

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("=====================--->"+user.getUsername());
        return true;
        // if (session.getAttribute("email") != null) {
        //     User user = userRepository.findByEmail(userEmail).get();
        //     List<NotesReport> notesReports = user.getProfil().getNotesReports();
        //     // NotesReport notesReport = notesReports.get(notesReports.size());
        //     if(notesReports.isEmpty()){
        //         System.out.println("hello");
        //         return "false";
        //     }
        //     else{
        //         System.out.println("bye");
        //         return "true";
        //     }
        // }
        // return null;
    }

    public List<String> getAllOption(){
        List<String> allNameOption = new ArrayList<>();
        optionRepository.findAll().forEach(option -> allNameOption.add(option.getNom()));
        return allNameOption;
    }

    public List<String> getAllLevel(String optionName){
        List<String> allCodeLevel = new ArrayList<>();
        Subdomain option = new Subdomain();
        option = optionRepository.findByNom(optionName);
        levelRepository.findByOption(option).forEach(level -> {
            allCodeLevel.add(level.getCode());
        });
        return allCodeLevel;
    }

    public Map<String, Integer> getAllCoef(String codeLevel) {
        Map<String, Integer> allMatterCoeficients = new HashMap<>();
        Level level = new Level();
        level = levelRepository.findByCode(codeLevel);
        matterRepository.findByLevel(level).forEach(matter -> {
            allMatterCoeficients.put(matter.getNom(), matter.getCoef());
        });
        return allMatterCoeficients;
    }

    public void saveNotesReport(Map<String, Object> notesReportMap){

            // if(session.getAttribute("email") != null){
                // String userEmail = (String)session.getAttribute("email");
                // User user = userRepository.findByEmail(userEmail);
                // NotesReport notesReport = new NotesReport();
                // Level level = levelRepository.findByCode(niveau);
                // List<Matter> matters = matterRepository.findByLevel(level);
                // System.out.println("----------========="+type+"===============-----------------=============="+niveau);
                // int mathCoef = matters.get(0).getCoef();
                // int phyCoef = matters.get(1).getCoef();
                // int infoCoef = matters.get(2).getCoef();
                // int chmCoef = matters.get(3).getCoef();
                // int engCoef = matters.get(4).getCoef();
                // int fraCoef = matters.get(5).getCoef();
                // int total = mathCoef + phyCoef + infoCoef + chmCoef + engCoef + fraCoef;
                // double mean = (mathNote*mathCoef + phyNote*phyCoef + infoNote*infoCoef + chmNote*chmCoef + engNote*engCoef + fraNote*fraCoef)/total;
                // notesReport.setMoyenne(mean);
                // List<Notes> notes = new ArrayList<>();
                // Object notes = notesReportMap.get("notes");
                // notesReport.setNotes(notes);
                // notesReport.setProfil(user.getProfil());
                // notesReportRepository.save(notesReport);
            // }
    }
}
