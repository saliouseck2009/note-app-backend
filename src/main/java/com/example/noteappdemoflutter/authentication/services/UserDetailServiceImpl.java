package com.example.noteappdemoflutter.authentication.services;

import com.example.noteappdemoflutter.authentication.models.AppUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);
        SimpleGrantedAuthority[] roles = appUser.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).toArray(SimpleGrantedAuthority[]::new);
        if (appUser == null) throw new UsernameNotFoundException("Invalid user");
        UserDetails userDetails = User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(roles)
                .build();
        return userDetails;
    }
}
