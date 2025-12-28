package com.example.api.dto;

import java.io.Serializable;

public class Resp<T> implements Serializable {

	private String status;
	private String message;
	private T data;

	public Resp() {
	}
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Resp(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> Resp<T> ok(T data) {
		return new Resp<>("OK", null, data);
	}

	public static <T> Resp<T> ok(String message, T data) {
		return new Resp<>("OK", message, data);
	}

	public static <T> Resp<T> error(String message) {
		return new Resp<>("ERROR", message, null);
	}

}