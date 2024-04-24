package com.assesment.exception;

public class LowCashAvailableException extends ATMExceptions {

	private static final long serialVersionUID = 1L;

	public LowCashAvailableException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Low cash available try entering lower amount.";
	}
	
}
