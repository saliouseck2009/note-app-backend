package com.example.noteappdemoflutter.authentication.controllers;

import com.example.noteappdemoflutter.authentication.models.AppUser;
import com.example.noteappdemoflutter.authentication.services.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/")
public class AuthController {
    private JwtEncoder jwtEncoder;
    private AuthenticationManager authenticationManager;
    private AccountServiceImpl accountService;


    @GetMapping("private")
    public String privatePage() {

        return "Welcome to private page";
    }

    @PostMapping("auth/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        );
        if (authentication.isAuthenticated()) {
            Map<String, String> idToken = generateToken(authentication);

            return new ResponseEntity<>(idToken, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    @PostMapping("auth/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequest signupRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            AppUser user = accountService.signup(signupRequest.username, signupRequest.password, signupRequest.email,
                    new ArrayList<>());
            response.put("user", user);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signupRequest.username, signupRequest.password)
            );
            if (authentication.isAuthenticated()) {
                Map<String, String> idToken = generateToken(authentication);
                response.put("idToken", idToken);
            }


        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Map<String, String> generateToken(Authentication authentication) {
        String subject;
        String scope;
        Map<String, String> idToken = new HashMap<>();
        Instant instant = Instant.now();
        subject = authentication.getName();
        scope = authentication.getAuthorities()
                .stream().map(aut -> aut.getAuthority())
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = getJwtClaimsSet(subject, scope, instant);
        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        idToken.put("accessToken", jwtAccessToken);
        return idToken;
    }

    private JwtClaimsSet getJwtClaimsSet(String subject, String scope, Instant instant) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(60, ChronoUnit.MINUTES))
                .issuer("security-service")
                .claim("scope", scope)
                .build();
        return jwtClaimsSet;
    }

    record AuthRequest(String username, String password) {
    }

    record SignupRequest(String username, String password, String email) {
    }
}
