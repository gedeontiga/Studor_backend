package com.studor.orientation_student.entities.suggestionCourEntities;

import java.util.ArrayList;
import java.util.List;

import com.studor.orientation_student.entities.Profil;

// import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class NiveauAcademique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNiveau;
    private String nomNiveau;
    private Long nombreMatiere;
    private String objectif;

    @ManyToOne
    private Profil profil;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idProgramme")
    private Programme programme;

    @OneToMany(mappedBy = "niveauAcademique", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Discipline> disciplines = new ArrayList<>();

    public NiveauAcademique() {
    }

    public NiveauAcademique(String nomNiveau, Long nombreMatiere, String objectif) {
        this.nomNiveau = nomNiveau;
        this.nombreMatiere = nombreMatiere;
        this.objectif = objectif;
    }

    public Long getIdNiveau() {
        return idNiveau;
    }

    public void setIdNiveau(Long idNiveau) {
        this.idNiveau = idNiveau;
    }

    public String getNomNiveau() {
        return nomNiveau;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public void setNomNiveau(String nomNiveau) {
        this.nomNiveau = nomNiveau;
    }

    public Long getNombreMatiere() {
        return nombreMatiere;
    }

    public void setNombreMatiere(Long nombreMatiere) {
        this.nombreMatiere = nombreMatiere;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }
}
