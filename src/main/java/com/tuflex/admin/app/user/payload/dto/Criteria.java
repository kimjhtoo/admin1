package com.tuflex.admin.app.user.payload.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Criteria {

	private long pageNum;

	private long amount;

	private long skip;

	public Criteria() {
		this(1, 10);
		this.skip = 0;
	}

	public Criteria(long pageNum, long amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.skip = (pageNum - 1) * amount;
	}
}