package com.tuflex.admin.app.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.tuflex.admin.app.user.model.Admin;
import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.model.Role;
import com.tuflex.admin.app.user.payload.request.AdminSignupRequest;
import com.tuflex.admin.app.user.repository.AdminRepository;
import com.tuflex.admin.app.user.repository.RoleRepository;
import com.tuflex.admin.tool.Utils;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "index")
    public String index() {
        System.out.println("index");
        ERole role = Utils.getRole();
        switch (role) {
            case ROLE_ADMIN:
                return "redirect:/manage/user";
            default:
                return "redirect:/user/loginView";
        }
    }

    @GetMapping(value = "user/loginView")
    public String loginView() {
        System.out.println("login");
        ERole role = Utils.getRole();
        switch (role) {
            case ROLE_ADMIN:
                return "redirect:/admin/user";
            default:
                return "user/login";
        }
    }

    @PostMapping("/admin_signup")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminSignupRequest adminSignupRequest) {
        if (adminRepository.existsByPhone(adminSignupRequest.getPhone())) {
            return ResponseEntity
                    .badRequest().build();
        }

        Admin admin = new Admin(adminSignupRequest.getPhone(), encoder.encode(adminSignupRequest.getPassword()),
                adminSignupRequest.getName());

        Set<String> strRoles = adminSignupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    default:
                        Role managerRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(managerRole);
                }
            });
        }

        admin.setRoles(roles);
        adminRepository.save(admin);

        return ResponseEntity.ok().build();
    }
}