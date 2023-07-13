package com.example.noteappdemoflutter.controllers;


import com.example.noteappdemoflutter.authentication.models.AppUser;
import com.example.noteappdemoflutter.authentication.services.AccountService;
import com.example.noteappdemoflutter.models.Note;
import com.example.noteappdemoflutter.services.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Notes", description = "The Note API")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/notes")
public class NoteController {
    private NoteService noteService;
    private AccountService accountService;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createNote(@RequestBody() CreateNoteRequest createNoteRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            AppUser user = getAppUser();
            String title = createNoteRequest.title();
            String content = createNoteRequest.content();
            Note createdNote = noteService.addNote(title, content, user.getId());
            response.put("note", createdNote);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PutMapping("/{idNote}")
    public ResponseEntity<Map<String, Object>> updateNote(@PathVariable Long idNote, @RequestBody Note note) {
        Map<String, Object> response = new HashMap<>();
        try {
            AppUser user = getAppUser();
            Note updatedNote = noteService.updateNoteById(idNote, note);
            response.put("note", updatedNote);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllNotes() {
        Map<String, Object> response = new HashMap<>();
        AppUser user = getAppUser();
        List<Note> notes = noteService.listNotesByUser(user.getId());
        response.put("notes", notes);
        return ResponseEntity.ok(response);

    }

    @Operation(
            summary = "Retrieve a Note by Id",
            description = "Get a Note object by specifying its id. The response is Note object with id, title, content and UserId.",
            tags = {"note", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Note.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNoteById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Note note = noteService.getNoteById(id);
        response.put("note", note);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNoteById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        noteService.deleteNoteById(id);
        response.put("message", "Note deleted successfully");
        return ResponseEntity.ok(response);
    }


    private AppUser getAppUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = accountService.loadUserByUsername(auth.getName());
        return user;
    }

}

record CreateNoteRequest(String title, String content) {
}
