package com.demo.convertor;

public class ConverterValidationException extends RuntimeException {

	private static final long serialVersionUID = 7099391171783779447L;

	public ConverterValidationException(String msg) {
		super(msg);
	}

	public ConverterValidationException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
