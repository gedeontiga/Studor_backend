package com.studor.orientation_student.manager.controllers.apis.jobcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.manager.services.profilepredictionservices.JobPredictService;
import com.studor.orientation_student.manager.services.profilepredictionservices.NotesReportSaverService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/notesreport-api")
public class NotesReportRestController {

    private final NotesReportSaverService notesReportSaverService;

    private final JobPredictService jobPredictService;

    @GetMapping("/all-option")
    public List<String> getAllOptionFromDatabase() {
        return notesReportSaverService.getAllOption();
    }
    
    @GetMapping("/all-level/{optionName}")
    public List<String> getAllLevelFromDatabase(@PathVariable String optionName) {
        return notesReportSaverService.getAllLevel(optionName);
    }

    @GetMapping("/all-coef/{codeLevel}")
    public Map<String, Integer> getAllCoefficient(@PathVariable String codeLevel) {
        return notesReportSaverService.getAllCoef(codeLevel);
    }

    @PostMapping("/send-notes")
    public Map<String, Object> sendNotesForJobPrediction(@RequestBody Map<String, Object> data)
        throws Exception {

        notesReportSaverService.saveNotesReport(data);
        return jobPredictService.predictJob(data);
    }
}
