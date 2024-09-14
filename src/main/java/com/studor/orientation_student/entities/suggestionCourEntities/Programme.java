package com.studor.orientation_student.entities.suggestionCourEntities;

import com.studor.orientation_student.entities.Profil;

// import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Programme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgramme;
    private Long duree;
    private String difficulte;
    private String description;

    @ManyToOne
    @JoinColumn(name = "idProfil")
    private Profil profil;

    @ManyToOne
    @JoinColumn(name = "idNiveau")
    private NiveauAcademique niveauAcademique;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEmploiTemps", referencedColumnName = "idEmploiTemps")
    private EmploiTemps emploiTemps;

    public Programme() {
    }

    public EmploiTemps getEmploiTemps() {
        return emploiTemps;
    }

    public void setEmploiTemps(EmploiTemps emploiTemps) {
        this.emploiTemps = emploiTemps;
    }

    public NiveauAcademique getNiveauAcademique() {
        return niveauAcademique;
    }

    public void setNiveauAcademique(NiveauAcademique niveauAcademique) {
        this.niveauAcademique = niveauAcademique;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Programme(Long duree, String difficulte, String description) {
        this.duree = duree;
        this.difficulte = difficulte;
        this.description = description;
    }

    public Long getIdProgramme() {
        return idProgramme;
    }

    public void setIdProgramme(Long idProgramme) {
        this.idProgramme = idProgramme;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}