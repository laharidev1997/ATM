package com.assesment.api;

import java.util.List;
import java.util.Map;

import com.assesment.exception.ATMExceptions;

public interface IATMOperations {
	
	Map<Integer, Integer> withdraw(int amount) throws ATMExceptions;
	
	List<Integer> getAvailableDenominations();
	
}
