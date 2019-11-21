package com.example.bean;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = -8750804773293292060L;

	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int NO_PERMISSION = 2;
	public static final int FAIL_VALIDATION =3;

	private int code = SUCCESS;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeStamp;

	private String msg = "success";

	private T data;

	public JsonResult() {
		this.setTimeStamp(LocalDateTime.now());
	}

	public JsonResult(T data) {
		this();
		this.data = data;
	}

	public JsonResult(int code, T data, String msg) {
		this();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
