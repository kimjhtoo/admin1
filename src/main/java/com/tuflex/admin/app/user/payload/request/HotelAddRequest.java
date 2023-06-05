package com.tuflex.admin.app.user.payload.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelAddRequest {
    private String name, address, introduce;

    private MultipartFile image1, image2, image3, image4, image5, image6, image7, image8, image9, image10;
}