package com.studor.orientation_student.entities.suggestionCourEntities;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// import org.hibernate.annotations.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
// import jakarta.persistence.OneToOne;
import weka.classifiers.Classifier;
// import weka.core.SerializationHelper;

@Entity
public class ModelSuggestionCour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModel;
    private String version;
    @Lob
    private byte[] serialisezeModel;

    public Classifier getClassifier() {
        try {
            if(serialisezeModel != null){
                ByteArrayInputStream bis = new ByteArrayInputStream(serialisezeModel);
                ObjectInputStream ois = new ObjectInputStream(bis);

                return (Classifier) ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setClassifier(Classifier classifier) {
        try {
            if(classifier != null){
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(classifier);
                oos.flush();
                serialisezeModel = bos.toByteArray();
            }
            else{
                serialisezeModel = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    



    public byte[] getSerialisezeModel() {
        return serialisezeModel;
    }

    public void setSerialisezeModel(byte[] serialisezeModel) {
        this.serialisezeModel = serialisezeModel;
    }

    public Long getIdModel() {
        return idModel;
    }

    public void setIdModel(Long idModel) {
        this.idModel = idModel;
    }



    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}    
