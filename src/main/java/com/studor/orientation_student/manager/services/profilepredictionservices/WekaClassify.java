package com.studor.orientation_student.manager.services.profilepredictionservices;

import java.io.FileNotFoundException;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class WekaClassify 
{
    public static String trainModel(String arffFilePath, double mathNotes, double phyNotes, double infoNotes, double mean) 
    throws Exception 
    {
        Instances trainingData = new Instances(new java.io.FileReader(arffFilePath));

        // Set the class attribute index (last attribute)
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        // Preprocess the data if needed

        // Split the data into a training set and a test set (70% training, 30%
        int numInstances = trainingData.numInstances();
        int trainSize = (int) Math.round(numInstances * 0.7);
        // int testSize = numInstances - trainSize;
        Instances trainData = new Instances(trainingData, 0, trainSize);
        // Instances testData = new Instances(trainingData, trainSize, testSize);

        // Choose the best Weka algorithm for classification
        Classifier classifier = chooseBestClassifier(trainData);

        // Train the chosen algorithm on the entire training set
        classifier.buildClassifier(trainData);

        // Prompt the user to enter values for the new data instance
        Instances newData = createNewDataInstance(arffFilePath, mathNotes, phyNotes, infoNotes, mean);

        // Set the class attribute index (if not already set) and the dataset of the new
        // instance
        if (newData.classIndex() == -1) {
            newData.setClassIndex(newData.numAttributes() - 1);
        }

        // Apply the trained algorithm to predict the class_model for the new instance
        double predictedClass = classifier.classifyInstance(newData.lastInstance());

        // Get the predicted class value
        String predictedClassValue = newData.classAttribute().value((int) predictedClass);

        // Print the predicted class
        System.out.println("Predicted class: " + predictedClassValue);

        return predictedClassValue;
    }

    private static Classifier chooseBestClassifier(Instances data) throws Exception {
        // Evaluate different classifiers using cross-validation
        Evaluation eval = new Evaluation(data);
        Classifier bestClassifier = null;
        double bestAccuracy = 0.0;

        // Naive Bayes classifier
        Classifier naiveBayes = new NaiveBayes();
        eval.crossValidateModel(naiveBayes, data, 10, new java.util.Random());
        double naiveBayesAccuracy = eval.pctCorrect();
        if (naiveBayesAccuracy > bestAccuracy) {
            bestClassifier = naiveBayes;
            bestAccuracy = naiveBayesAccuracy;
            System.out.println("Accuracy: "+ bestAccuracy + "\n Algorithm: " + bestClassifier);
        }

        // Decision Tree classifier (J48)
        Classifier decisionTree = new J48();
        eval.crossValidateModel(decisionTree, data, 10, new java.util.Random());
        double decisionTreeAccuracy = eval.pctCorrect();
        if (decisionTreeAccuracy > bestAccuracy) {
            bestClassifier = decisionTree;
            bestAccuracy = decisionTreeAccuracy;
            System.out.println("Accuracy: "+ bestAccuracy + "\n Algorithm: " + bestClassifier);
        }

        // Logistic Regression classifier
        Classifier logisticRegression = new Logistic();
        eval.crossValidateModel(logisticRegression, data, 10, new java.util.Random());
        double logisticRegressionAccuracy = eval.pctCorrect();
        if (logisticRegressionAccuracy > bestAccuracy) {
            bestClassifier = logisticRegression;
            bestAccuracy = logisticRegressionAccuracy;
            System.out.println("Accuracy: "+ bestAccuracy + "\n Algorithm: " + bestClassifier);
        }

        return bestClassifier;
    }

    private static Instances createNewDataInstance(String arffFilePath, double mathNotes, double phyNotes, double infoNotes, double mean)
    throws FileNotFoundException, IOException {
        
        Instances newData = new Instances(new java.io.FileReader(arffFilePath));
        newData.setClassIndex(newData.numAttributes() - 1);

        Instance newInstance = new DenseInstance(5);
        newInstance.setValue(0, mathNotes);
        newInstance.setValue(1, phyNotes);
        newInstance.setValue(2, infoNotes);
        newInstance.setValue(3, mean);
        newInstance.setDataset(newData);
        newData.add(newInstance);

        return newData;
    }
}