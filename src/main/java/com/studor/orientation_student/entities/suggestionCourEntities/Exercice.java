package com.studor.orientation_student.entities.suggestionCourEntities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Exercice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdExercice;
    private String titre;
    private Long niveau;
    private Long duree;

    @ManyToOne
    @JoinColumn(name = "idCour")
    private Cour cour;


    public Exercice() {
    }

    public Exercice(String titre, Long niveau, Long duree) {
        this.titre = titre;
        this.niveau = niveau;
    this.duree = duree;
    }

    public Long getIdExercice() {
        return IdExercice;
    }

    public void setIdExercice(Long idExercice) {
        IdExercice = idExercice;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getNiveau() {
        return niveau;
    }

    public void setNiveau(Long niveau) {
        this.niveau = niveau;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public Cour getCour() {
        return cour;
    }

    public void setCour(Cour cour) {
        this.cour = cour;
    }

    
    
    
}
