package com.tuflex.admin.tool;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tuflex.admin.app.user.model.ERole;
import com.tuflex.admin.app.user.service.AdminDetailsImpl;

public class Utils {
    static public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    static public ERole getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return ERole.ROLE_USER;
        }
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());
        String role = roles.iterator().next();
        return ERole.ROLE_ADMIN.name().equals(role) ? ERole.ROLE_ADMIN : ERole.ROLE_ADMIN;
    }

    static public String getName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return "";
        }
        return ((AdminDetailsImpl) authentication.getPrincipal()).getName();
    }

    static public Long getPid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return -1L;
        }
        return ((AdminDetailsImpl) authentication.getPrincipal()).getPid();
    }

    static public boolean checkLockedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return ((AdminDetailsImpl) authentication.getPrincipal()).isAccountNonLocked();
    }
}
