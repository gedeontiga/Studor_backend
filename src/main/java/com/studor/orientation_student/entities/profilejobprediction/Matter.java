package com.studor.orientation_student.entities.profilejobprediction;

import java.util.List;

import com.studor.orientation_student.entities.establishmentsuggestion.Training;

import jakarta.persistence.CascadeType;
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
public class Matter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int coef;

    @OneToMany(mappedBy = "matter", cascade = CascadeType.ALL)
    private List<Notes> notes;

    @ManyToOne
    private Training training;

    @ManyToOne
    private Level level;
}
