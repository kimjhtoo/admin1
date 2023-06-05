package com.tuflex.admin.app.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.tuflex.admin.app.common.CommonVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Admin extends CommonVO {

    @NotBlank
    @Size(max = 20)
    private String phone;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Size(max = 20)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "admin_role", joinColumns = @JoinColumn(name = "admin_pid"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Admin(String phone, String password, String name) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.status = true;
        this.roles = new HashSet<>();
    }
}