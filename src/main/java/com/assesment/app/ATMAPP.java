package com.assesment.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.assesment.exception.ATMExceptions;
import com.assesment.impl.ATMOperationsImpl;

public class ATMAPP {

	private static ATMOperationsImpl atm;

	public static void main(String[] args) {
		Map<Integer, Integer> denominations = new HashMap<>();
		denominations.put(100, 10);
		denominations.put(50, 20);
		denominations.put(20, 30);
		denominations.put(10, 50);

		atm = new ATMOperationsImpl(denominations);

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Select an option:");
			System.out.println("1. Withdraw cash");
			System.out.println("2. Show remaining denominations");
			System.out.println("3. Exit");

			int choice = getIntInput(scanner);
			scanner.nextLine(); // Consume newline

			switch (choice) {
				case 1:
					withdrawCash(scanner);
					break;
				case 2:
					showRemainingDenominations();
					break;
				case 3:
					System.out.println("Exiting...");
					return;
				default:
					System.out.println("Invalid option");
			}
		}
	}

	private static void withdrawCash(Scanner scanner) {
		int amount = 0;
		boolean validAmount = false;

		while (!validAmount) {
			System.out.println("Enter amount to withdraw:");
			try {
				amount = getIntInput(scanner);
				validAmount = true;
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter a valid number.");
				scanner.nextLine(); // Consume invalid input
			}
		}

		try {
			Map<Integer, Integer> withdrawal = atm.withdraw(amount);
			System.out.println("Dispensed cash:");
			withdrawal.forEach((denomination, count) ->
					System.out.println(denomination + ": " + count)
			);
		} catch (ATMExceptions e) {
			System.out.println(e.getMessage());
		}
	}

	private static void showRemainingDenominations() {
		System.out.println("Remaining denominations:");
		atm.getAvailableDenominations().forEach(d -> System.out.print(d + " "));
		System.out.println();
	}

	private static int getIntInput(Scanner scanner) {
		while (true) {
			try {
				return scanner.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter a valid number.");
				scanner.nextLine(); // Consume invalid input
//				printMainMenu();
			}
		}
	}
}
