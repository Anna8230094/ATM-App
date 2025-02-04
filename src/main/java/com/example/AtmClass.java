package com.example;

    
public class AtmClass extends  Transactions {
    public  static AtmClass atmClass ;
   
    public AtmClass(double balance){
        super(balance);
    }

    @Override
    public String toString(){
       return  "ATM balance:" + getBalance();
    }
 
    @Override
    public void  withdrawal(double ammount){
        setBalance(getBalance()-ammount);  
    }

    @Override
    public  void deposit(double amount){
        setBalance(getBalance() +amount);
    }

}
