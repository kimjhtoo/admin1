package com.tuflex.admin.app.user.payload.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAddRequest {
    private Long id;
    private String name, information;
    private Integer price;

    private MultipartFile image;
}