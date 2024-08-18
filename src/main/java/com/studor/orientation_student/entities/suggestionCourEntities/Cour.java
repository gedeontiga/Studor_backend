package com.studor.orientation_student.entities.suggestionCourEntities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity

public class Cour {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idCour;
    private String intitule;
    private String niveau;
    private Long duree;

    @ManyToOne
    @JoinColumn(name = "idDiscipline")
    private Discipline discipline;

    @OneToMany(mappedBy = "cour", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Exercice> exercices = new ArrayList<>();

    public Cour() {
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public List<Exercice> getExercices() {
        return exercices;
    }

    public void setExercices(List<Exercice> exercices) {
        this.exercices = exercices;
    }

    public Cour(String intitule, String niveau, Long duree) {
        this.intitule = intitule;
        this.niveau = niveau;
        this.duree = duree;
    }

    public Long getIdCour() {
        return idCour;
    }

    public void setIdCour(Long idCour) {
        this.idCour = idCour;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    

    
    
}