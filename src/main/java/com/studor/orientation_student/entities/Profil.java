package com.studor.orientation_student.entities;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;

import com.studor.orientation_student.entities.establishmentsuggestion.Establishment;
import com.studor.orientation_student.entities.profilejobprediction.Job;
import com.studor.orientation_student.entities.profilejobprediction.NotesReport;
import com.studor.orientation_student.entities.suggestionCourEntities.NiveauAcademique;
import com.studor.orientation_student.entities.suggestionCourEntities.ObjectifProfil;
import com.studor.orientation_student.entities.suggestionCourEntities.Programme;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profil")
public class Profil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    private String sexe;
    private String loisirs;
    private String metierDuPere;
    private String metierDeLaMere;
    private String religion;
    private String sport;

    @Lob
    private Blob photoProfile;

    @ManyToOne
    private Job job;

    @OneToMany(mappedBy = "profil", cascade = CascadeType.ALL)
    private List<NotesReport> notesReports;

    @OneToOne(mappedBy = "profil", cascade = CascadeType.ALL)
    private User user;

    @ManyToMany
    private List<Establishment> establishments;

    @OneToMany(mappedBy = "profil", cascade = CascadeType.ALL)
    private List<Programme> programmes;

    @OneToOne
    @JoinColumn(name = "idObjectif", referencedColumnName = "idObjectif")
    private ObjectifProfil objectifProfil;

    @OneToMany(mappedBy = "profil")
    private List<NiveauAcademique> niveauAcademiques;
}
