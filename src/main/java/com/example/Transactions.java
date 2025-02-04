package com.example;

public abstract class Transactions {
    
    private  double balance ;
    
    public Transactions(double balance){
        this.balance = balance;
    }
   
    public double  getBalance() {
        return balance;
    }

    public void setBalance(double  balance) {
        this.balance = balance;
    }

    public abstract void withdrawal(double ammount);

    public abstract  void deposit(double amount);
    
    @Override
    public String toString() {
        return "{" + " balance=" + getBalance() + "'" + "}";
    }

}
