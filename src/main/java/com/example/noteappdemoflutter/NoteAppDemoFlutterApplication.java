package com.example.noteappdemoflutter;

import com.example.noteappdemoflutter.authentication.RsaKeysConfig;
import com.example.noteappdemoflutter.authentication.models.AppRole;
import com.example.noteappdemoflutter.authentication.models.AppUser;
import com.example.noteappdemoflutter.authentication.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeysConfig.class)
public class NoteAppDemoFlutterApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteAppDemoFlutterApplication.class, args);
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
            accountService.addRole(new AppRole(null, "USER"));
            accountService.addRole(new AppRole(null, "ADMIN"));
            accountService.addRole(new AppRole(null, "CUSTOMER_MANAGER"));
            accountService.addRole(new AppRole(null, "PRODUCT_MANAGER"));
            accountService.addRole(new AppRole(null, "BILLS_MANAGER"));

            accountService.addUser(new AppUser(null, "seck", "seck", "", new ArrayList<>()));
            accountService.addUser(new AppUser(null, "user2", "1234", "", new ArrayList<>()));
            accountService.addUser(new AppUser(null, "admin", "1234", "", new ArrayList<>()));

            accountService.addRoleToUser("seck", "USER");
            accountService.addRoleToUser("user2", "USER");
            accountService.addRoleToUser("user2", "PRODUCT_MANAGER");
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("seck", "ADMIN");


        };
    }

}
