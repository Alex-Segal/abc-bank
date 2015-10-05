package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public Date getTransactionDate(){
    	return transactionDate;
    }
    
    public double getTransactionAmount(){
    	return amount;
    }
    
    // This is for internal testing only to mock a date.
    public void reduceMonthsFromDate (int month){
    	this.transactionDate.setMonth(transactionDate.getMonth() -month);
    }

}
