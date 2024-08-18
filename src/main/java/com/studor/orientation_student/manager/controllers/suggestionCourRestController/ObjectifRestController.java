package com.studor.orientation_student.manager.controllers.suggestionCourRestController;

import java.util.ArrayList;
import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.entities.suggestionCourEntities.DataModel;
import com.studor.orientation_student.manager.services.suggestionCour.DataGeneration;
import com.studor.orientation_student.manager.services.suggestionCour.GetAbsoluePath;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpSession;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

@RestController
@RequestMapping("/cour")
public class ObjectifRestController {
    // @Autowired
    // private HttpServletRequest request;
    // @Autowired
    // private UserRepository userRepository;

    @PostMapping("/classification")
    public String effectuerClassification(@RequestParam String statutProfessionnel, @RequestParam Double moyenneScolaire, @RequestParam String niveau, @RequestParam String objectif) throws Exception {
        //------( recuperation des identifiants de l'utilisateur )-------
        // HttpSession session = request.getSession();
        // User user = new User();
        // user = (User) session.getAttribute("user");
        // user = userRepository.findByMotDePasseAndEmail(user.getMotDePasse(), user.getEmail());


        //-------( pretraitement des donnees )-----------
        DataModel dataModel = new DataModel();
        dataModel.setMoyenneScolaire(getMention(moyenneScolaire));
        dataModel.setStatutProfessionnel(statutProfessionnel);
        dataModel.setNiveauAcademique(niveau);
        dataModel.setNomEmpoitemps("?");

        //-------( generation du fichier pour la recherche de la class )---------

        List<DataModel> dataModels = new ArrayList<>();
        dataModels.add(dataModel);
        DataGeneration dataGeneration = new DataGeneration();
        dataGeneration.writeARFFFile(dataModels, 0);

        //-----------( Chargement du modèle à partir du fichier )-----

        GetAbsoluePath getAbsoluePath = new GetAbsoluePath();
        String path1 = getAbsoluePath.getAbsolutePathOfFile();
        String path;
        path = path1 + "/data/suggestionCourData/model.model";
        Classifier modele = (Classifier) SerializationHelper.read(path);

        String predictedClassName = " aucune class trouver";
        Instances testData;
        try {

            path = path1 + "/data/suggestionCourData/dataForPrediction.arff";

            //-------( Chargement des donnees pour la prediction )---------
            DataSource testSource = new DataSource(path);
            testData = testSource.getDataSet();
            System.out.println("==============> "+path);

            //-------( Définir l'attribut de classe pour les données de test )---------
            testData.setClassIndex(testData.numAttributes() - 1);

            //-----( Effectuer la classification sur les données de prediction )----
            for (int i = 0; i < testData.numInstances(); i++) {
                double predictedClass = modele.classifyInstance(testData.instance(i));
                predictedClassName = testData.classAttribute().value((int) predictedClass);
                System.out.println("Instance " + (i + 1) + ": Classe prédite = " + predictedClassName);
            }
            return "<h1> L'emploi de temps predit est > : " + predictedClassName + " </h1>";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la classification.\n" + e.getMessage() + " \n";
        }
    }

    public String getMention(Double moyenne){

        String mention = "";
        if(moyenne >= 10 && moyenne < 12)
            mention = "passable";
        else if(moyenne >= 12 && moyenne < 14)
            mention ="assez_bien";
        else if(moyenne >= 14 && moyenne < 16)
            mention = "bien";
        else if(moyenne >= 17 && moyenne < 19)
            mention = "tres_bien";
        else if(moyenne >= 19 && moyenne <= 20)
            mention = "excellent";
        else
            return "erreur";    


        return mention;
    }

}
