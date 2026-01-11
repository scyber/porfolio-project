package com.example.api.dto;

import java.io.Serializable;

public class Resp<T> implements Serializable {

	private Status status;
	private String message;
	private T data;

	public Resp() {
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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

	public Resp(Status status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> Resp<T> ok(T data) {
		return new Resp<>(Status.OK, null, data);
	}

	public static <T> Resp<T> ok(String message, T data) {
		return new Resp<>(Status.OK, message, data);
	}

	public static <T> Resp<T> error(String message) {
		return new Resp<>(Status.ERROR, message, null);
	}

}