package com.tuflex.admin.app.user.service;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuflex.admin.app.user.model.Admin;
import com.tuflex.admin.app.user.repository.AdminRepository;

@RequiredArgsConstructor
@Service
public class AdminDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phone) {
        Optional<Admin> admin = adminRepository.findByPhone(phone);
        if (admin.isPresent()) {
            if (admin.get().getIsEnable()) {
                return AdminDetailsImpl.build(admin.get());
            }
            throw new UsernameNotFoundException("User is locked with username: " + phone);
        } else {
            throw new UsernameNotFoundException("User Not Found with username: " + phone);
        }
    }
}