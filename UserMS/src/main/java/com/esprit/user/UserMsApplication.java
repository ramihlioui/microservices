package com.esprit.user;

import com.esprit.user.entities.Admin;
import com.esprit.user.entities.Role;
import com.esprit.user.entities.User;
import com.esprit.user.repositories.IAdminRepository;
import com.esprit.user.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
@EnableDiscoveryClient
public class UserMsApplication implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IAdminRepository adminRepository;
    public static void main(String[] args) {
        SpringApplication.run(UserMsApplication.class, args);
    }
    public void run(String... args) {
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            Admin admin = new Admin();
            admin.setEmail("admin@gmail.com");
            admin.setNom("admin");
            admin.setPrenom("admin");
            admin.setRole(Role.ADMIN);
            admin.setImage("ffffffff");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            adminRepository.save(admin);
        }
    }
}
