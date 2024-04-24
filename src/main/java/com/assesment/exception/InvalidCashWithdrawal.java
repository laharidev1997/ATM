package com.assesment.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidCashWithdrawal extends ATMExceptions{

	private static final long serialVersionUID = 1L;

	List<Integer> denominations = new ArrayList<Integer> ();

	public InvalidCashWithdrawal(List<Integer> denominations) {
		this.denominations= denominations;
	}

	@Override
	public String getMessage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Invalid Cash withdrawl, enter denominations in multiples of ");
		denominations.stream().forEach(denomination -> buffer.append(denomination+ ", "));
		return buffer.toString();
	}

}
