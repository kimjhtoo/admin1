package com.tuflex.admin.app.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CKResponse {
    private Integer uploaded;
    private String fileName;
    private String url;
}