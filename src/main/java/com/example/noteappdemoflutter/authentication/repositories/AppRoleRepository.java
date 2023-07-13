package com.example.noteappdemoflutter.authentication.repositories;

import com.example.noteappdemoflutter.authentication.models.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String roleName);
}
