package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.exceptions.IncorrectAccNumException;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public List<Account> getAllAccounts(){
    	return accounts;
    }
    
    public void accountTransfer(int srcAccNum, int tgtAccNum, double amount) throws IncorrectAccNumException{
    	Account srcAcc = null;
    	Account tgtAcc = null;
    	for (Account acc : accounts){
    		if (acc.getAccountId() == srcAccNum){
    			srcAcc = acc;
    		}
    		if (acc.getAccountId() == tgtAccNum){
    			tgtAcc = acc;
    		}    		
    		// In a realistic environment I will do it as a single transaction, most likely in a stored procedure,
    		// but for this exercise will do one operation at a time
    	}
    	if (srcAcc == null){
    		throw new IncorrectAccNumException("Incorrect source account number");
    	}
    	if (tgtAcc == null){
    		throw new IncorrectAccNumException("Incorrect target account number");
    	}
		if (srcAcc != null && tgtAcc != null){
			srcAcc.withdraw(amount);
			tgtAcc.deposit(amount);
		}
    }
    
	public Account getAccountById (int id){
		Account account = null;
		for (Account acc: accounts){
			if (acc.getAccountId() == id){
				account = acc;
			}
		}
		return account;
	}
    
}
