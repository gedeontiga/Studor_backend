package com.studor.orientation_student.manager.services.profilepredictionservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.HashMap;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.studor.orientation_student.entities.User;
import com.studor.orientation_student.entities.establishmentsuggestion.Level;
import com.studor.orientation_student.entities.profilejobprediction.Matter;
import com.studor.orientation_student.entities.profilejobprediction.Notes;
import com.studor.orientation_student.entities.profilejobprediction.NotesReport;
import com.studor.orientation_student.entities.profilejobprediction.Options;
import com.studor.orientation_student.manager.repositories.profilejobpredictrepository.LevelRepository;
import com.studor.orientation_student.manager.repositories.profilejobpredictrepository.NotesReportRepository;
import com.studor.orientation_student.manager.repositories.profilejobpredictrepository.OptionsRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@SessionScope
public class NotesReportSaverService {

    private final NotesReportRepository notesReportRepository;
    private final LevelRepository levelRepository;
    private final OptionsRepository optionsRepository;

    public Boolean checkIfNotesReportExists(){

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getProfil().getNotesReports() != null;
    }

    public List<String> getAllOption(){
        List<String> allNameOption = new ArrayList<>();
        optionsRepository.findAll().forEach(option -> allNameOption.add(option.getNom()));
        return allNameOption;
    }

    public List<String> getAllLevel(String optionName){
        List<String> allCodeLevel = new ArrayList<>();
        Options option = new Options();
        option = optionsRepository.findByNom(optionName);
        option.getLevels().forEach(level -> {
            allCodeLevel.add(level.getCode());
        });
        return allCodeLevel;
    }

    public Map<String, Integer> getAllCoef(String codeLevel) {
        Map<String, Integer> allMatterCoeficients = new HashMap<>();
        Level level = new Level();
        level = levelRepository.findByCode(codeLevel);
        level.getMatters().forEach(matter -> {
            allMatterCoeficients.put(matter.getNom(), matter.getCoef());
        });
        return allMatterCoeficients;
    }

    public void saveNotesReport(Map<String, Object> notesReportMap){

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NotesReport notesReport = new NotesReport();


        List<Matter> matters = levelRepository.findByCode(notesReportMap.get("niveau").toString()).getMatters();

        @SuppressWarnings("unchecked")
        Map<String, Object> notes = (Map<String, Object>)notesReportMap.get("notes");
        List<Notes> notesList = new ArrayList<Notes>();

        matters.forEach(matter -> {
            notesList.add(new Notes((double)notes.get(matter.getNom()), notesReport, matter));
        });

        notesReport.setNotes(notesList);
        notesReport.setMoyenne((double) notesReportMap.get("average"));
        notesReport.setMention((String) notesReportMap.get("mention"));
        notesReport.setProfil(user.getProfil());
        notesReportRepository.save(notesReport);
    }
}
