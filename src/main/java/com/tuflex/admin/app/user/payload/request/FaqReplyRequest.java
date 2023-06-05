package com.tuflex.admin.app.user.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqReplyRequest {
    private String title, content;
}
