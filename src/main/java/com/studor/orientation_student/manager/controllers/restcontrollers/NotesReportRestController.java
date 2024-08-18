package com.studor.orientation_student.manager.controllers.restcontrollers;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.manager.services.profilepredictionservices.NotesReportSaverService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notesreport-api")
public class NotesReportRestController {

    @Autowired
    private NotesReportSaverService notesReportSaverService;

    // @Autowired
    // private JobPredictService jobPredictService;

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
    public ResponseEntity<Map<String, String>> sendNotesForJobPrediction(@RequestBody Map<String, Object> data)
        throws Exception {

        Map<String, String> response = new HashMap<>();
        System.out.println(data);
        try {
            // Traitement de la note (par exemple, sauvegarde en base de données)

            // En cas de succès
            response.put("status", "success");
            response.put("message", "Notesreport have been recieved successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En cas d'erreur
            response.put("status", "error");
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        // String result = notesReportSaverService.saveNotesReport(session, noteMathematique, notePhysique, noteInformatique, noteChimie, noteAnglais,
        //         noteFrancais, option, niveau);
        // if (result == "OK") {
        //     String projectPath = Paths.get("").toAbsolutePath().toString();
        //     String trainingDataFile = projectPath + "/data/dataSource.arff";
        //     return jobPredictService.predictJobUsingDataInDatabase(session, trainingDataFile);
        // }
        // else{
        //     return null;
        // }
    }
}
