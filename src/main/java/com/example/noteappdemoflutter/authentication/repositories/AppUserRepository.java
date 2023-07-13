package com.example.noteappdemoflutter.authentication.repositories;

import com.example.noteappdemoflutter.authentication.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);
}
