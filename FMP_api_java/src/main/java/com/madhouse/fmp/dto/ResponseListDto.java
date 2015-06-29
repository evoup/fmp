package com.madhouse.fmp.dto;

import java.util.List;

public class ResponseListDto<T> extends RespDto {
	
	private long total;
	private List<T> list;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
