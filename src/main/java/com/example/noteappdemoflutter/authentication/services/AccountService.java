package com.example.noteappdemoflutter.authentication.services;

import com.example.noteappdemoflutter.authentication.models.AppRole;
import com.example.noteappdemoflutter.authentication.models.AppUser;

import java.util.ArrayList;
import java.util.List;

public interface AccountService {
    AppUser addUser(AppUser appUser);

    AppRole addRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    AppUser loadUserByUsername(String username);

    AppUser loadUserByEmail(String email);

    List<AppUser> listUsers();

    AppRole loadRoleByRoleName(String roleName);

    AppUser signup(String username, String password, String email, ArrayList<AppRole> roles);
}
