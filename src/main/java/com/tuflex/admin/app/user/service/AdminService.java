package com.tuflex.admin.app.user.service;

import com.tuflex.admin.app.user.model.Admin;

public interface AdminService {
    Admin findByPhone(String phone);

    Admin findByRole(String role);

    Admin findByName(String name);

}