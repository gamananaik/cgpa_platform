
package com.example.cgpaplatform;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.cgpaplatform.entity.User;
import com.example.cgpaplatform.repository.UserRepository;

@SpringBootApplication
public class CgpaPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CgpaPlatformApplication.class, args);
    }

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User(
                    "admin",
                    passwordEncoder.encode("Admin@123"),
                    "ADMIN"
                );

                userRepository.save(admin);

                System.out.println("Admin account created!");
            }
        };
    }
}
