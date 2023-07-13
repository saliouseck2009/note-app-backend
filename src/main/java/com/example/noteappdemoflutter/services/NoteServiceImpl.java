package com.example.noteappdemoflutter.services;

import com.example.noteappdemoflutter.models.Note;
import com.example.noteappdemoflutter.repositories.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public Note addNote(String title, String content, Long userId) {
        if (userId != null && title != null) {

            return noteRepository.save(Note.builder().title(title).content(content).userId(userId).build());
        }
        throw new RuntimeException("User id and title are required");
    }

    @Override
    public Note updateNoteById(Long id, Note note) {
        Optional<Note> noteToUpdate = noteRepository.findById(id);
        if (noteToUpdate.isPresent()) {
            Note note1 = noteToUpdate.get();
            note1.setTitle(note.getTitle());
            note1.setContent(note.getContent());
            return noteRepository.save(note1);
        }
        throw new EntityNotFoundException("Note not found");
    }

    @Override
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Note getNoteById(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return note.get();
        }
        throw new RuntimeException("Note not found");
    }

    @Override
    public List<Note> listNotes() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> listNotesByUser(Long userId) {
        return noteRepository.findByUserId(userId);
    }
}
