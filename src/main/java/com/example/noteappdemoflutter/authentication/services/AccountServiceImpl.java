package com.example.noteappdemoflutter.authentication.services;

import com.example.noteappdemoflutter.authentication.models.AppRole;
import com.example.noteappdemoflutter.authentication.models.AppUser;
import com.example.noteappdemoflutter.authentication.repositories.AppRoleRepository;
import com.example.noteappdemoflutter.authentication.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addUser(AppUser appUser) {
        String pass = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pass));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getRoles().add(appRole);

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public AppUser loadUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }


    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppRole loadRoleByRoleName(String roleName) {
        return appRoleRepository.findByRoleName(roleName);
    }

    @Override
    public AppUser signup(String username, String password, String email, ArrayList<AppRole> roles) {
//        if (loadUserByEmail(email) != null) {
//            throw new RuntimeException("Email already exist");
//        }
        if (loadUserByUsername(username) != null) {
            throw new RuntimeException("Username already exist");
        }
        AppUser appUser = AppUser.builder()
                .username(username)
                .password(password)
                .email(email)
                .roles(roles)
                .build();
        appUser = addUser(appUser);
        addRoleToUser(appUser.getUsername(), "USER");
        return appUser;
    }
}
