package com.tuflex.admin.app.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuflex.admin.app.user.model.Admin;
import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.model.Role;
import com.tuflex.admin.app.user.model.User;
import com.tuflex.admin.app.user.payload.dto.UserSimpleDto;
import com.tuflex.admin.app.user.repository.AdminRepository;
import com.tuflex.admin.app.user.repository.RoleRepository;
import com.tuflex.admin.app.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final RoleRepository roleRepository;

    @Transactional
    public long getCount(String searchType, String searchWord) {
        Collection<Role> role = Arrays.asList(roleRepository.findByName(ERole.ROLE_USER).get());
        List<User> userList;
        if (searchType.equals("name")) {
            return userRepository.countByEmailContainingAndRolesIn(searchWord, role);
        } else if (searchType.equals("email")) {
            return userRepository.countByEmailContainingAndRolesIn(searchWord, role);
        } else {
            return userRepository.countByNameContainingAndRolesInOrEmailContainingAndRolesIn(
                    searchWord, role, searchWord, role);
        }
    }

    @Transactional
    public List<UserSimpleDto> findAllUsersBySearchOption(String searchType, String searchWord, Pageable pageable) {
        Page<User> users;
        Collection<Role> role = Arrays.asList(roleRepository.findByName(ERole.ROLE_USER).get());
        if (searchType.equals("name")) {
            users = userRepository.findByNameContainingAndRolesIn(pageable, searchWord, role);
        } else if (searchType.equals("email")) {
            users = userRepository.findByEmailContainingAndRolesIn(pageable, searchWord, role);
        } else {
            users = userRepository.findByNameContainingAndRolesInOrEmailContainingAndRolesIn(
                    pageable, searchWord, role, searchWord, role);
        }
        return users.stream().map(user -> UserSimpleDto.toDto(user)).toList();
    }

    @Transactional
    public User findByPid(Long pid) {
        return userRepository.findById(pid).orElseThrow();
    }

    @Transactional
    public void withdrawal(Long pid) {
        User user = userRepository.findById(pid).orElseThrow();
        user.setIsEnable(false);
        userRepository.save(user);
    }
}
