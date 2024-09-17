package com.studor.orientation_student.manager.controllers.apis.jobcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.studor.orientation_student.manager.services.profilepredictionservices.JobPredictService;
import com.studor.orientation_student.manager.services.profilepredictionservices.NotesReportSaverService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/job-api")
public class JobPredictionRestController {

    @Autowired
    private JobPredictService jobPredictService;

    @Autowired
    private NotesReportSaverService notesReportSaverService;

    @GetMapping("/check-test")
    public Boolean checkNotesReport(HttpSession session) throws Exception {
        return notesReportSaverService.checkIfNotesReportExists();
    }

    @GetMapping("/predicted")
    public Map<String, Object> jobPredictByDataFromTheForm(HttpSession session) throws Exception {

        // String projectPath = Paths.get("").toAbsolutePath().toString();
        // String trainingDataFile = projectPath + "/data/dataSource.arff";

        return jobPredictService.getPredictedJob(session);
    }

    // @GetMapping("/predict")
    // public ModelAndView getJobInfo(HttpSession session) throws Exception {
    //     ModelAndView model = new ModelAndView("job-prediction");

    //     String projectPath = Paths.get("").toAbsolutePath().toString();
    //     String trainingDataPath = projectPath+"/data/dataSource.arff";

    //     Map<String, Object> jobpredicted = jobPredictService.predictJobUsingDataInDatabase(session, trainingDataPath);
    //     model.addObject("jobMap", jobpredicted);
    //     return model;
    // }
    
}
