package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import com.abc.exceptions.IncorrectAccNumException;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING, 1);
        Account savingsAccount = new Account(Account.SAVINGS, 2);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS, 1));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS, 1));
        oscar.openAccount(new Account(Account.CHECKING, 2));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS, 1));
        oscar.openAccount(new Account(Account.CHECKING, 2));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void accountTransfer(){
    	
        Customer oscar = new Customer("Oscar");
        
        Account checkingAccount = new Account(Account.CHECKING, 1);
        Account savingsAccount = new Account(Account.SAVINGS, 2);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingsAccount);
        oscar.getAccountById(1).deposit(100);
        oscar.getAccountById(2).deposit(1500);
        try {
			oscar.accountTransfer(2, 1, 500.0);
		} catch (IncorrectAccNumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        assertEquals(600, oscar.getAccountById(1).getAccountBalance(), 0.0);
        
        

    }
}
