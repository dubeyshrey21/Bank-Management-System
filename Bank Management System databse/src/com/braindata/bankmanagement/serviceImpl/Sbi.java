package com.braindata.bankmanagement.serviceImpl;

import com.braindata.bankmangement.model.Account;
import com.braindata.bankmangement.service.Rbi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Sbi implements Rbi {
	Account ac = new Account();
	Scanner sc = new Scanner(System.in);
	Connection con;

	public Sbi() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

	}

	@Override
	public void createAccount() {

		String query = "create table if not exists account(accNo varchar(20) primary key,name varchar(50),mobNo varchar(15),adharNo varchar(20),gender varchar(10),age int ,balance double)";
		Statement stm;
		try {
			stm = con.createStatement();
			stm.execute(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Account has been initialized");

	}

	@Override
	public void addAccountDetails() {

		for (int i = 0; i < 3; i++) {

			Account ac = new Account();
			System.out.println("Enter account number of the user ");
			String accnum = sc.next();
			ac.setAccNo(accnum);

			System.out.println("Enter name of the user ");
			String name = sc.next();
			ac.setName(name);

			System.out.println("Enter mobile number of the user");
			String mobNo = sc.next();
			ac.setMobNo(mobNo);

			System.out.println("Enter aadhar number of the user");
			String aadharnum = sc.next();
			ac.setAdharNo(aadharnum);

			System.out.println("Enter gender of the user");
			String gender = sc.next();
			ac.setGender(gender);

			System.out.println("Enter age of the user");
			int age = sc.nextInt();
			ac.setAge(age);

			System.out.println("Enter balance of the account of User");
			double balance = sc.nextDouble();
			ac.setBalance(balance);

			String query1 = " insert into account values(? , ?, ?,?,?,?, ?)";

			try {
				PreparedStatement ps = con.prepareStatement(query1);
				ps.setString(1, ac.getAccNo());

				ps.setString(2, ac.getName());

				ps.setString(3, ac.getMobNo());

				ps.setString(4, ac.getAdharNo());

				ps.setString(5, ac.getGender());

				ps.setInt(6, ac.getAge());

				ps.setDouble(7, ac.getBalance());

				int rows = ps.executeUpdate();

				if (rows > 0) {
					System.out.println("Account created");
					ps.close();
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());

			}

		}

	}

	@Override
	public void displayAllDetails() {

		String query = "Select * from account";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String accnum = rs.getString(1);
				String name = rs.getString(2);
				String mobno = rs.getString(3);
				String adharno = rs.getString(4);
				String gender = rs.getString(5);
				int age = rs.getInt(6);
				double balance = rs.getDouble(7);

				System.out.println(
						accnum + " " + name + " " + mobno + " " + adharno + " " + gender + " " + age + " " + balance);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void depositeMoney() {

		System.out.println("Enter Account Number");
		String accNo = sc.next();

		System.out.println("Enter the amount to be deposited");
		double amount = sc.nextDouble();

		String query = "update account set balance = balance + ? where accNo = ?";

		try {
			PreparedStatement ps = con.prepareStatement(query);

			ps.setDouble(1, amount);
			ps.setString(2, accNo);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("Money Deposited Successfully");
			} else {
				System.out.println("Account Not Found");
			}

			String query1 = "SELECT balance FROM account WHERE accNo = ?";
			PreparedStatement ps1 = con.prepareStatement(query1);

			ps1.setString(1, accNo);
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) {
				System.out.println("Current Balance : " + rs.getDouble("balance"));
			}
			rs.close();
			ps1.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void withdrawal() {
		System.out.println("Enter Account Number");
		String accNo = sc.next();

		System.out.println("Enter the amount to be withdraw");
		double amountwithdraw = sc.nextDouble();

		String query3 = "update account set balance = balance - ? where accNo = ?";

		try {
			PreparedStatement ps3 = con.prepareStatement(query3);
			ps3.setDouble(1, amountwithdraw);
			ps3.setString(2, accNo);
			int rows = ps3.executeUpdate();

			if (rows > 0) {
				System.out.println("Withdrawal is successful");
			} else {
				System.out.println("Account not found");
			}
			String query = "SELECT balance FROM account WHERE accNo = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, accNo);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("Updated Balance : " + rs.getDouble("balance"));
			}

			rs.close();
			ps3.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void balanceCheck() {
		System.out.println("Enter Account Number");
		String accNo = sc.next();

		String query = "select balance from account where accNo = ? ";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, accNo);
			ResultSet r = ps.executeQuery();

			while (r.next()) {
				double balance = r.getDouble(1);
				System.out.println("Your Balance is:" + balance);
				// r.close();
				ps.close();

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

}
