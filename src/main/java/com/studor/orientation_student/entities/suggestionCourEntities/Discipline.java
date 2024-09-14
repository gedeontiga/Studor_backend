package com.studor.orientation_student.entities.suggestionCourEntities;

import java.util.ArrayList;
import java.util.List;

// import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiscipline;
    private String nom;
    private Double progression;

    @ManyToOne
    @JoinColumn(name = "idNiveau")
    private NiveauAcademique niveauAcademique;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Cour> cours = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idEmploiTemps")
    private EmploiTemps emploiTemps;

    public Discipline() {
    }

    public Discipline(String nom, Double progression) {
        this.nom = nom;
        this.progression = progression;
    }

    public Long getIdDiscipline() {
        return idDiscipline;
    }

    public void setIdDiscipline(Long idDiscipline) {
        this.idDiscipline = idDiscipline;
    }

    public String getNom() {
        return nom;
    }

    public NiveauAcademique getNiveauAcademique() {
        return niveauAcademique;
    }

    public void setNiveauAcademique(NiveauAcademique niveauAcademique) {
        this.niveauAcademique = niveauAcademique;
    }

    public List<Cour> getCours() {
        return cours;
    }

    public void setCours(List<Cour> cours) {
        this.cours = cours;
    }

    public EmploiTemps getEmploiTemps() {
        return emploiTemps;
    }

    public void setEmploiTemps(EmploiTemps emploiTemps) {
        this.emploiTemps = emploiTemps;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getProgression() {
        return progression;
    }

    public void setProgression(Double progression) {
        this.progression = progression;
    }

}
