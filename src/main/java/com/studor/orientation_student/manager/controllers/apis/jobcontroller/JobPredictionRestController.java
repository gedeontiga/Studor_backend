package com.studor.orientation_student.manager.controllers.apis.jobcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.manager.services.profilepredictionservices.JobPredictService;
import com.studor.orientation_student.manager.services.profilepredictionservices.NotesReportSaverService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/job-api")
public class JobPredictionRestController {

    @Autowired
    private JobPredictService jobPredictService;

    @Autowired
    private NotesReportSaverService notesReportSaverService;

    @GetMapping("/check-test")
    public Boolean checkNotesReport() throws Exception {
        return notesReportSaverService.checkIfNotesReportExists();
    }

    @GetMapping("/predicted")
    public Map<String, Object> jobPredictByDataFromTheForm() throws Exception {
        return jobPredictService.getJobFromDatabase();
    }
}
