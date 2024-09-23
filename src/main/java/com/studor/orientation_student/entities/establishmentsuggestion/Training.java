package com.studor.orientation_student.entities.establishmentsuggestion;

import java.util.List;

import com.studor.orientation_student.entities.profilejobprediction.Job;
import com.studor.orientation_student.entities.profilejobprediction.Matter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int duree;
    private double cout;

    @ManyToMany
    private List<Establishment> establishments;

    @OneToOne(mappedBy = "training")
    private Job job;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Matter> matters;
}
