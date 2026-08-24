package com.braindata.bankmanagement.client;

import com.braindata.bankmanagement.serviceImpl.Sbi;
import com.braindata.bankmangement.service.Rbi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		Rbi bank = new Sbi();

		do {
			System.out.println(
					"Enter 1 for Create Account\nEnter 2 for Add Account Details\nEnter 3 to Display User Details\nEnter 4 to Deposite Money\nEnter 5 to Withdraw Money\nEnter 6 to Check balance\nEnter 7 to exit");
			int ch = sc.nextInt();

			switch (ch) {

			case 1:
				bank.createAccount();
				break;

			case 2:
				bank.addAccountDetails();
				break;

			case 3:
				bank.displayAllDetails();
				break;

			case 4:
				bank.depositeMoney();
				break;

			case 5:
				bank.withdrawal();
				break;

			case 6:
				bank.balanceCheck();
				break;
			case 7:
				System.out.println("Thank you for visiting");
				System.exit(0);

			default:
				System.out.println("Invalid choice");

			}
		} while (true);

	}

}
