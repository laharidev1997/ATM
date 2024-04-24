package com.assesment.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.assesment.exception.ATMExceptions;
import com.assesment.exception.InvalidCashWithdrawal;
import com.assesment.exception.LowCashAvailableException;
import com.assesment.exception.NoCashAvailableException;
import com.assesment.impl.ATMOperationsImpl;

import java.util.*;

class ATMTest {
	@Test
	void testWithdraw() throws ATMExceptions {

		Map<Integer, Integer> denominations = new HashMap<>();

		denominations.put(100, 10);
		denominations.put(50, 20);
		denominations.put(20, 30);
		denominations.put(10, 50);

		ATMOperationsImpl atm = new ATMOperationsImpl(denominations);

		Map<Integer, Integer> result1 = atm.withdraw(150);
		assertEquals(1, result1.get(100));
		assertEquals(1, result1.get(50));

		assertThrows(InvalidCashWithdrawal.class, () -> {
			atm.withdraw(0);
		});

		Map<Integer, Integer> result3 = atm.withdraw(140);
		assertEquals(1, result3.get(100));
		assertEquals(2, result3.get(20));

		Map<Integer, Integer> result4 = atm.withdraw(200);
		assertEquals(2, result4.get(100));

		assertThrows(InvalidCashWithdrawal.class, () -> {
			atm.withdraw(147);
		});

	}

	@Test
	void testWithdrawWithNoCashAvailable() throws ATMExceptions {

		Map<Integer, Integer> denominations = new HashMap<>();

		ATMOperationsImpl atm = new ATMOperationsImpl(denominations);

		assertThrows(NoCashAvailableException.class, () -> {
			atm.withdraw(100);
		});

	}

	@Test
	void testWithdrawWithLowCashAvailable() throws ATMExceptions {

		Map<Integer, Integer> denominations = new HashMap<>();
		denominations.put(100, 1);
		denominations.put(50, 5);
		denominations.put(20, 2);
		denominations.put(10, 5);

		ATMOperationsImpl atm = new ATMOperationsImpl(denominations);

		assertThrows(LowCashAvailableException.class, () -> {
			atm.withdraw(10000);
		});

	}

	@Test
	void testWithdrawWithInvalidCashWithdrawal() throws ATMExceptions {

		Map<Integer, Integer> denominations = new HashMap<>();
		denominations.put(100, 20);
		denominations.put(50, 2);
		denominations.put(20, 3);
		denominations.put(10, 5);

		ATMOperationsImpl atm = new ATMOperationsImpl(denominations);

		assertThrows(InvalidCashWithdrawal.class, () -> {
			atm.withdraw(0);
		});
		
		assertThrows(InvalidCashWithdrawal.class, () -> {
			atm.withdraw(147);
		});

	}

}
