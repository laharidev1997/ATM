package com.assesment.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.assesment.api.IATMOperations;
import com.assesment.exception.ATMExceptions;
import com.assesment.exception.InvalidCashWithdrawal;
import com.assesment.exception.LowCashAvailableException;
import com.assesment.exception.NoCashAvailableException;

public final class ATMOperationsImpl implements IATMOperations {

	Map<Integer, Integer> cash = new HashMap<>();

	public ATMOperationsImpl(Map<Integer, Integer> cash) {
		this.cash.putAll(cash);
	}

	@Override
	public final synchronized Map<Integer, Integer> withdraw(int amount) throws ATMExceptions {
		if (cash.isEmpty()) {
			throw new NoCashAvailableException();
		}
		checkValidCashWithdrwal(0,amount);

		Map<Integer, Integer> result = new HashMap<>();
		AtomicInteger remainingAmount = new AtomicInteger(amount);

		cash.keySet().stream().sorted(Comparator.reverseOrder()).forEach(denomination -> {
			int notesRequired = remainingAmount.get() / denomination;
			if (notesRequired > 0) {
				int availableNotes = cash.getOrDefault(denomination, 0);
				int dispensedNotes = Math.min(notesRequired, availableNotes);
				result.put(denomination, dispensedNotes);
				remainingAmount.set(remainingAmount.get() - dispensedNotes * denomination);
			}
		});

		checkValidCashWithdrwal(remainingAmount.get(),amount);

		for (int denomination : result.keySet()) {
			cash.put(denomination, cash.get(denomination) - result.get(denomination));
		}

		return result;
	}

	private void checkValidCashWithdrwal(int remainingAmount, int withdrawalAmount) throws ATMExceptions {
		if(withdrawalAmount!=0) {
			 int sum = cash.entrySet().stream().mapToInt(entry-> entry.getKey()*entry.getValue()).sum();
			 if(withdrawalAmount>sum) {
				 throw new LowCashAvailableException();
			 }

		}
		if (withdrawalAmount ==0 || remainingAmount != 0 ) {
			throw new InvalidCashWithdrawal(
					 cash.entrySet().stream()
						.filter(entry -> entry.getValue()>0)
						.map(e -> e.getValue())
						.sorted()
						.collect(Collectors.toList())
						);
		}
	}

	@Override
	public List<Integer> getAvailableDenominations() {
		return this.cash.entrySet().stream()
		.filter(entry -> entry.getValue()>0)
		.map(e -> e.getValue())
		.sorted()
		.collect(Collectors.toList());
	}



}
