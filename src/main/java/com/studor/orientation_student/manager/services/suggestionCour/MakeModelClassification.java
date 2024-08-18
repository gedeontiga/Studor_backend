package com.studor.orientation_student.manager.services.suggestionCour;
// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.io.Serializable;

// import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.studor.orientation_student.entities.suggestionCourEntities.ModelSuggestionCour;
import com.studor.orientation_student.manager.repositories.suggestionCourRepository.ModelSuggestionCourRepository;

import weka.classifiers.Classifier;
// import weka.core.SerializationHelper;

public class MakeModelClassification {

    @Autowired
    private ModelSuggestionCourRepository modelSuggestionCourRepository;

    public ModelSuggestionCour buildAndSaveModel() {
        // System.out.println("+++++======> ok test !");

        Classifier tree = ClassificationData.classifyData();
        

        ModelSuggestionCour modelSuggestionCour = new ModelSuggestionCour();
        modelSuggestionCour.setClassifier(tree);
        modelSuggestionCour.setVersion("version 1.1.0");
        // byte[] serialisezeModel = SerializationHelper.((Serializable) tree);
        modelSuggestionCour.setSerialisezeModel(modelSuggestionCour.getSerialisezeModel());

        

        modelSuggestionCour = modelSuggestionCourRepository.save(modelSuggestionCour);
        if (modelSuggestionCour != null)
            System.out.println("+++++======> ok test !");

            // Sérialisation
// Classifier classifier = ... // Votre modèle de classification Weka
    // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // try {
    //     // IOUtils.write writeObject(tree, outputStream);
    // } catch (IOException e) {
    //     // Gérer les exceptions
    // }
    // byte[] serializedClassifier = outputStream.toByteArray();
        return modelSuggestionCour;
    }
}
