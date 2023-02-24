package com.otus.deliveryservice.error;


import com.otus.deliveryservice.domain.response.AResponse;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -6772091593303257397L;

	private Integer code;

	private String data;

	public ApplicationException() {
	}

	public ApplicationException(Integer code, String message, String data) {
		super(message);
		this.code = code;
		this.data = data;
	}


	public ApplicationException(int code, String message) {
		super(message);
		this.code = code;
		this.data = "";
	}

	public ApplicationException(ApplicationError errorContent) {
		super(errorContent.getMessage());
		this.code = errorContent.getErrorCode();
		this.data = "";
	}

	public ApplicationException(AResponse response) {
		super(response.getErrorMessage());
		this.code = response.getErrorCode();
	}

	public ApplicationException(ApplicationError errorContent, String data) {
		super(errorContent.getMessage());
		this.code = errorContent.getErrorCode();
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
