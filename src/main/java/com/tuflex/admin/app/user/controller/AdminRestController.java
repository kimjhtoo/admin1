package com.tuflex.admin.app.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.base.Predicate;
import com.tuflex.admin.app.user.model.Admin;
import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.model.Role;
import com.tuflex.admin.app.user.payload.request.AdminModifyRequest;
import com.tuflex.admin.app.user.payload.request.AdminRegisterRequest;
import com.tuflex.admin.app.user.repository.AdminRepository;
import com.tuflex.admin.app.user.repository.RoleRepository;
import com.tuflex.admin.app.user.service.AdminDetailsImpl;
import com.tuflex.admin.app.user.service.BlockUserService;
import com.tuflex.admin.config.security.model.ActiveUserStore;
import com.tuflex.admin.config.security.model.SessionInfo;
import com.tuflex.admin.config.security.model.UserSession;
import com.tuflex.admin.tool.Utils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BlockUserService blockUserService;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("search_list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Admin> searchList(@RequestParam String searchType, @RequestParam String searchValue) {
        Predicate<Admin> isEnabled = item -> item.getIsEnable();
        Set<Role> set = new HashSet<>();
        if (searchValue.equals("총")) {
            set.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
        } else if (searchValue.equals("일반")) {
            set.add(roleRepository.findByName(ERole.ROLE_MANAGER).get());
        }
        set = Collections.unmodifiableSet(set);
        if (searchType.equals("phone")) {
            return adminRepository.findAllByPhoneContainingAndIsEnable(searchValue, true).stream().filter(isEnabled)
                    .toList();
        } else if (searchType.equals("role")) {
            if (!set.isEmpty()) {
                return adminRepository.findByRolesInAndIsEnable(set, true).stream().filter(isEnabled).toList();
            } else {
                return new ArrayList();
            }
        } else if (searchType.equals("name")) {
            return adminRepository.findAllByNameContainingAndIsEnable(searchValue, true).stream().filter(isEnabled)
                    .toList();
        } else {
            return adminRepository.findAllByPhoneContainingOrNameContainingOrRolesIn(searchValue, searchValue,
                    set).stream().filter(isEnabled).toList();
        }
    }

    @GetMapping("withdrawal")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> withdrawal(Authentication authentication, @RequestParam Long pid) {
        Long myPid = Utils.getPid();
        if (pid == myPid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본인을 탈퇴시킬 수 없습니다.");
        }
        blockUserService.blockUser(adminRepository.findById(pid).get().getPhone());
        adminRepository.withdrawal(pid);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/register.do", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> authenticateUser(@RequestBody AdminRegisterRequest adminRegisterRequest) throws Exception {
        if (adminRepository.existsByPhone(adminRegisterRequest.getPhone())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        Admin admin = new Admin(adminRegisterRequest.getPhone(), encoder.encode("123456"),
                adminRegisterRequest.getName());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        admin.setRoles(roles);
        adminRepository.save(admin);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/modify.do", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> modify(Authentication authentication, @RequestBody AdminModifyRequest adminModifyRequest)
            throws Exception {
        AdminDetailsImpl adminDetails = (AdminDetailsImpl) authentication.getPrincipal();
        if (encoder.matches(adminModifyRequest.getPasswordOriginal(),
                adminRepository.findByPhone(adminDetails.getUsername()).get().getPassword())) {
            adminRepository.modify(encoder.encode(adminModifyRequest.getPasswordNew()), adminDetails.getUsername());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
    }

    @RequestMapping("search/phone/{phone}")
    public ResponseEntity<?> searchPhone(@PathVariable("phone") String phone) {
        if (adminRepository.findByPhone(phone).isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
    }

    @Autowired
    ActiveUserStore activeUserStore;

    @GetMapping("loggedUsers")
    public List<String> getLoggedUsers() {
        return activeUserStore.getUsers();
    }

    @GetMapping("sessions")
    public List<UserSession> sessions() {
        System.out.println(sessionRegistry.getAllPrincipals().size());
        return sessionRegistry.getAllPrincipals().stream().map(p -> UserSession.builder()
                .username(((AdminDetailsImpl) p).getUsername())
                .sessions(sessionRegistry.getAllSessions(p, false).stream().map(s -> SessionInfo.builder()
                        .sessionId(s.getSessionId())
                        .time(s.getLastRequest()).build())
                        .collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    @GetMapping("")
    @ResponseBody
    public AdminDetailsImpl getUser() throws IOException {
        AdminDetailsImpl principal = (AdminDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return principal;
    }
}
