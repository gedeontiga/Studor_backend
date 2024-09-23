package com.studor.orientation_student.entities.establishmentsuggestion;

import java.util.List;

import com.studor.orientation_student.entities.profilejobprediction.Job;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @OneToMany
    private List<Job> jobs;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Level> levels;
}
