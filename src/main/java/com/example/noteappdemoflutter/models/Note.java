package com.example.noteappdemoflutter.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Note {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private Long userId;
}
