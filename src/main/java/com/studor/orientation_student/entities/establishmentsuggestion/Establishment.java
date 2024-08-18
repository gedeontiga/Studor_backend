package com.studor.orientation_student.entities.establishmentsuggestion;

import java.sql.Blob;
import java.util.List;

import com.studor.orientation_student.entities.Profil;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String localisation;
    private String nomDirecteur;

    @Lob
    private Blob image;

    @ManyToMany(mappedBy = "establishments")
    private List<Training> trainings;
    
    @ManyToMany(mappedBy = "establishments")
    private List<Domain> domains;

    @ManyToMany(mappedBy = "establishments")
    private List<Profil> profils;
}
