package com.example.noteappdemoflutter.services;

import com.example.noteappdemoflutter.models.Note;

import java.util.List;

public interface NoteService {
    Note addNote(String title, String content, Long userId);

    Note updateNoteById(Long id, Note note);

    void deleteNoteById(Long id);

    Note getNoteById(Long id);

    List<Note> listNotes();

    List<Note> listNotesByUser(Long userId);
}
