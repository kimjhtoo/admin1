package com.tuflex.admin.app.user.payload.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaResponseDto {
    private Long pid;
    // private String userEmail;
    // private String title;
    // private String content;
    // private String repliedContent;
    // private boolean replied;
    // private boolean alarm;
    // private List<QnaImageDto> images;
    // private LocalDateTime repliedDt;
    // private LocalDateTime regDt;

    // public static QnaResponseDto toDto(Qna qna) {
    // return new QnaResponseDto(
    // qna.getPid(),
    // qna.getUser().getEmail(),
    // qna.getTitle(),
    // qna.getContent(),
    // qna.getRepliedContent(),
    // qna.getReplied(),
    // qna.getAlarm(),
    // qna.getImages().stream().map(i -> QnaImageDto.toDto(i)).collect(toList()),
    // qna.getRepliedDt(),
    // qna.getRegDt());
    // }
}