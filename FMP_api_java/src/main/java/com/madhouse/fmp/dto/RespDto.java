package com.madhouse.fmp.dto;

public class RespDto {
	
	private int code = 200;
	
	private String msg;
	
	private int result = 1;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}

