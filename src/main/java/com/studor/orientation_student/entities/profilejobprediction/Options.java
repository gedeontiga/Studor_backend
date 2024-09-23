package com.studor.orientation_student.entities.profilejobprediction;

import java.util.List;

import com.studor.orientation_student.entities.establishmentsuggestion.Level;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Options {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Matter> matters;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Level> levels;
}
