package com.tuflex.admin.app.user.payload.dto;

import java.time.format.DateTimeFormatter;

import com.tuflex.admin.app.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleDto {
    private Long pid;
    private String name;
    private String email;
    private String snsType;
    private String regDt;
    private boolean enable;

    public static UserSimpleDto toDto(User user) {
        return new UserSimpleDto(user.getPid(), user.getName(), user.getEmail(),
                user.getSnsType() == null || user.getSnsType().equals("") ? "일반 가입" : "카카오 가입",
                user.getRegDt().format(DateTimeFormatter.ofPattern("YYYY.MM.dd")), user.getIsEnable());
    }
}