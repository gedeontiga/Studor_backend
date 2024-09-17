package com.studor.orientation_student.manager.controllers.apis.suggestionCourRestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studor.orientation_student.manager.services.suggestionCour.GetAbsoluePath;

import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.core.converters.ConverterUtils.DataSource;

@RestController
public class DecisionTreeController {

    @GetMapping("/classify")
    public String classifyData() {
        try {
            // Charger les données à partir d'un fichier .arff
            GetAbsoluePath getAbsoluePath = new GetAbsoluePath();
            String path = getAbsoluePath.getAbsolutePathOfFile();
            path = path + "/data/dataTest.arff";
            DataSource source = new DataSource(path);
            Instances data = source.getDataSet();
            
            System.out.println(">>>>>>>>>>( "+path);

            System.out.println();
            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1);
            }

            // Entraîner un modèle J48 (arbre de décision)
            Classifier tree = new J48();
            tree.buildClassifier(data);

           

            // Afficher le modèle entraîné
            // System.out.println(tree);
            // Classer de nouvelles instances
            Instance newInstance = data.instance(8); // Remplacez 0 par l'index de la nouvelle instance que vous souhaitez classer
            double classValue = tree.classifyInstance(newInstance);

            // Afficher la valeur de classe prédite
            System.out.println("Classe prédite : " + data.classAttribute().value((int) classValue));
            // System.out.println(tree);
            // Classer de nouvelles instances
            // Instance newInstance = data.instance(0); // Remplacez 0 par l'index de la nouvelle instance que vous souhaitez classer
            // double classValue = tree.classifyInstance(newInstance);

            // Afficher la valeur de classe prédite
            // System.out.println("Classe prédite : " + data.classAttribute().value((int) classValue));
            return "ok test\n"+ tree;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ok";
}
}
