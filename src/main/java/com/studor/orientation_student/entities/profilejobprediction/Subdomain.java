package com.studor.orientation_student.entities.profilejobprediction;

import java.util.List;

import com.studor.orientation_student.entities.establishmentsuggestion.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subdomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;

    @ManyToOne
    private Domain domain;

    @OneToMany(mappedBy = "option")
    private List<Job> jobs;

    @OneToMany(mappedBy = "option")
    private List<Level> levels;
}
