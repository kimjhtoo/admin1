package com.tuflex.admin.app.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuflex.admin.app.user.model.Admin;

public class AdminDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long pid;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String phone;
    @JsonIgnore
    private String name;

    private Boolean isEnable = true;

    private Collection<? extends GrantedAuthority> authorities;

    public AdminDetailsImpl(Long id, String phone, String password, String name,
            Collection<? extends GrantedAuthority> authorities, Boolean isEnable) {
        this.pid = id;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.authorities = authorities;
        this.isEnable = isEnable;
    }

    public static AdminDetailsImpl build(Admin admin) {
        List<GrantedAuthority> authorities = admin.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new AdminDetailsImpl(
                admin.getPid(),
                admin.getPhone(),
                admin.getPassword(),
                admin.getName(),
                authorities, admin.getIsEnable());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getPid() {
        return pid;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AdminDetailsImpl user = (AdminDetailsImpl) o;
        return Objects.equals(pid, user.pid);
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }
}