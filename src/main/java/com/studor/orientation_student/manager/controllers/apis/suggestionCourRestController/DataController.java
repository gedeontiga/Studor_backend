package com.studor.orientation_student.manager.controllers.apis.suggestionCourRestController;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.entities.suggestionCourEntities.DataModel;
import com.studor.orientation_student.manager.services.suggestionCour.DataGeneration;

// import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// import java.util.Random;

@RestController
public class DataController {
    

    @GetMapping("/generate-arff")
    public String generateARFF() throws IOException {
        DataGeneration dataGeneration = new DataGeneration();
        List<DataModel> dataModels = new ArrayList<>();

        dataModels = dataGeneration.generateRandomData(100);
        dataGeneration.writeARFFFile(dataModels, 1);
        // MakeModelClassification makeModelClassification = new MakeModelClassification();
        // ModelSuggestionCour modelSuggestionCour = makeModelClassification.buildAndSaveModel();
        return "donne generer avec succes !!!";
    }

    
}