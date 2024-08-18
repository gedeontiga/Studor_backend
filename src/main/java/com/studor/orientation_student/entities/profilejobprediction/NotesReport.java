package com.studor.orientation_student.entities.profilejobprediction;

import java.util.List;

import com.studor.orientation_student.entities.Profil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.JoinColumn;
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
public class NotesReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mention;
    private double moyenne;

    @OneToMany(mappedBy = "notesReport", cascade = CascadeType.ALL)
    // @JoinColumn(name = "notes")
    private List<Notes> notes;

    @ManyToOne
    private Profil profil;
}
