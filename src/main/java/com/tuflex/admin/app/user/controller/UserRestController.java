package com.tuflex.admin.app.user.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuflex.admin.app.user.model.Admin;
import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.model.Role;
import com.tuflex.admin.app.user.payload.request.AdminSignupRequest;
import com.tuflex.admin.app.user.repository.AdminRepository;
import com.tuflex.admin.app.user.repository.RoleRepository;
import com.tuflex.admin.app.user.repository.UserRepository;
import com.tuflex.admin.app.user.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("withdrawal")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<?> withdrawal(Authentication authentication, @RequestParam Long pid) {
        userService.withdrawal(pid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin_signup")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminSignupRequest adminSignupRequest) {
        System.out.println("signup");
        System.out.println(adminSignupRequest.toString());
        if (adminRepository.existsByPhone(adminSignupRequest.getPhone())) {
            return ResponseEntity
                    .badRequest()
                    .build();
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
                                .orElseThrow(() -> new RuntimeException(
                                        "Error: Role is not found."));
                        roles.add(managerRole);
                }
            });
        }

        admin.setRoles(roles);
        adminRepository.save(admin);

        return ResponseEntity.ok().build();
    }
}
