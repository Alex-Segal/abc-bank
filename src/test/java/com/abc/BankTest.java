package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING,1));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING,1);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS,1);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiAccount = new Account(Account.MAXI_SAVINGS,1);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccount));

        maxiAccount.deposit(3000.0);
//        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA); old test
        
        // we deposit $3000 In case of no promo the rate is 0.1% and the interest is $3
        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        //force promo to be 0 days to see promo rule enforced.
        Account.promotionDayRule = 0;
        // we deposit $3000 In case of no promo the rate is 5% and the interest is $3
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account_accrue() {
        Bank bank = new Bank();
        Account.promotionDayRule = 10;
        Account maxiAccrueAccount = new Account(Account.MAXI_SAVINGS_ACCRUE,1);
        bank.addCustomer(new Customer("Bill").openAccount(maxiAccrueAccount));
        maxiAccrueAccount.deposit(1000.0);
        // force all transactions to be first of the month.
        List<Transaction> accTrans = maxiAccrueAccount.transactions;
        for (Transaction tran : accTrans){
        	tran.reduceMonthsFromDate(6);
        }
        assertEquals(25.07, bank.totalInterestPaid(), DOUBLE_DELTA);
        for (Transaction tran : accTrans){
        	tran.reduceMonthsFromDate(6);
        }
        assertEquals(50.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        //deposited today and there is not interest on it
        maxiAccrueAccount.deposit(1000.0);
        assertEquals(50.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        
    }

}
