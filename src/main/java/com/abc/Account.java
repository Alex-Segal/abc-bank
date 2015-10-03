package com.abc;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    public static final int MAXI_SAVINGS_ACCRUE = 3;
    
    public static int promotionDayRule = 10; 	// In most cases will come from config file or external source. 
    											// For the exercise it is hard coded to match 10 day requirement.

    private final int accountType;
    public List<Transaction> transactions;
    private double accountBalance;
    private int accountId;

	public Account(int accountType, int accId) {
		this.accountId = accId;
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountBalance += amount;
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
        accountBalance += amount;
    }
}

    public double interestEarned() {
        double amount = sumTransactions();
        DateTime dt = new DateTime();
    	DateTime earlierDate = dt.minusDays(promotionDayRule);
    	DateTime tranDate;
    	Boolean promoRate;
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	promoRate = false;


            	for (Transaction tran : transactions){
            		tranDate = new DateTime(tran.getTransactionDate());
            		promoRate = tranDate.isBefore(earlierDate) && tran.getTransactionAmount() > 0? true : false;
            	}
            	return amount = promoRate ? amount * 0.05 : amount * 0.001;
            	
            case MAXI_SAVINGS_ACCRUE:
            	promoRate = false;
            	Double totalInterest = 0.0;
            	
            	for (Transaction tran : transactions){
            		DateTime singleTranDate = new DateTime(tran.getTransactionDate());
            		long dayDiffMills = dt.getMillis() - singleTranDate.getMillis();
            		
            		// Doesn't include today
            		int dayDiff = (int)(dayDiffMills / (1000*60*60*24));

            		
            		tranDate = new DateTime(tran.getTransactionDate());
            		promoRate = tranDate.isBefore(earlierDate) && tran.getTransactionAmount() > 0? true : false;
            		double rate = promoRate? 0.05 : 0.001;
            		for (int i = 0; i < dayDiff; i++){
            			totalInterest += tran.getTransactionAmount() * rate / 365; // 365 makes the rate per year. 
            		}
            	}
            	return Math.round(totalInterest*100)/100.00;

            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
		return accountType;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

}
