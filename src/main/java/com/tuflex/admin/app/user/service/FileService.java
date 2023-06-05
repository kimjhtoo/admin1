package com.tuflex.admin.app.user.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void upload(MultipartFile file, String filename);

    void delete(String filename);
}