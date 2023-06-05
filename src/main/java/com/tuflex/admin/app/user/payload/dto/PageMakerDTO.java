package com.tuflex.admin.app.user.payload.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageMakerDTO {
    private Criteria cri;
    private long total, endPage, startPage, realEnd;
    private boolean prev, next;

    public PageMakerDTO(Criteria cri, long total) {

        this.cri = cri;
        this.total = total;

        this.endPage = (long) (Math.ceil(cri.getPageNum() / 10.0)) * 10l;
        this.startPage = this.endPage - 9;

        realEnd = (long) (Math.ceil(total * 1.0 / cri.getAmount()));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;

        this.next = this.endPage < realEnd;
    }
}
