package com.studor.orientation_student.entities.profilejobprediction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double note;

    @ManyToOne
    private NotesReport notesReport;

    @OneToOne
    private Matter matter;

    public Notes(double note, NotesReport notesReport, Matter matter) {
        this.note = note;
        this.notesReport = notesReport;
        this.matter = matter;
    }
}
