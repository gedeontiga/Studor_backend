package com.studor.orientation_student.entities.suggestionCourEntities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class EmploiTemps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploiTemps;
    private Long duree;
    private String typeEmploiTemps;
    private String difficulte;
    private String nomEmploiTemps;

    @OneToOne(mappedBy = "emploiTemps", cascade = CascadeType.ALL)
    private Programme programme;

    @OneToMany(mappedBy = "emploiTemps", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Discipline> disciplines = new ArrayList<>();

    public EmploiTemps() {
    }

    public EmploiTemps(Long duree, String difficulte, String typeEmploiTemps) {
        this.duree = duree;
        this.difficulte = difficulte;
        this.typeEmploiTemps = typeEmploiTemps;
    }

    public EmploiTemps(Long duree, String typeEmploiTemps, String difficulte, Programme programme,
            List<Discipline> disciplines) {
        this.duree = duree;
        this.typeEmploiTemps = typeEmploiTemps;
        this.difficulte = difficulte;
        this.programme = programme;
        this.disciplines = disciplines;

    }

    public String getTypeEmploiTemps() {
        return typeEmploiTemps;
    }

    public void setTypeEmploiTemps(String typeEmploiTemps) {
        this.typeEmploiTemps = typeEmploiTemps;
    }

    public Long getIdEmploiTemps() {
        return idEmploiTemps;
    }

    public void setIdEmploiTemps(Long idEmploiTemps) {
        this.idEmploiTemps = idEmploiTemps;
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

    public String getNomEmploiTemps() {
        return nomEmploiTemps;
    }

    public void setNomEmploiTemps(String nomEmploiTemps) {
        this.nomEmploiTemps = nomEmploiTemps;
    }

}
