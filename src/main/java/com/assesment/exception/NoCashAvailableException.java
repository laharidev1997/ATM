package com.assesment.exception;

public class NoCashAvailableException extends ATMExceptions {

	private static final long serialVersionUID = 1L;
	
	public NoCashAvailableException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Now cash available.";
	}
	
}
