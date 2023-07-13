package com.example.noteappdemoflutter.repositories;

import com.example.noteappdemoflutter.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findByTitle(String title);

    List<Note> findByUserId(Long userId);

    Note findById(long id);

    int deleteByTitle(String title);

    int deleteByUserId(Long userId);

    int deleteNoteById(Long id);

}
