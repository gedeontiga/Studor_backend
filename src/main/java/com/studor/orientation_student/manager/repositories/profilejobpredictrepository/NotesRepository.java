package com.studor.orientation_student.manager.repositories.profilejobpredictrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studor.orientation_student.entities.profilejobprediction.Matter;
import com.studor.orientation_student.entities.profilejobprediction.Notes;
import com.studor.orientation_student.entities.profilejobprediction.NotesReport;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
    List<Notes> findByNote(double note);

    List<Notes> findByMatter(Matter matter);

    List<Notes> findByNotesReport(NotesReport notesReport);
}
