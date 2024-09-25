package com.studor.orientation_student.entities.profilejobprediction;

import java.util.List;

import com.studor.orientation_student.entities.Profil;
import com.studor.orientation_student.entities.establishmentsuggestion.Subdomain;
import com.studor.orientation_student.entities.establishmentsuggestion.Training;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private Long salaire;

    private String cheminImage;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Profil> profils;

    @ManyToOne
    private Subdomain subdomain;

    @OneToOne
    private Training training;
}
