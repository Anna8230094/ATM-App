package com.example;

import java.util.ArrayList;
import java.util.List;

public class  Users extends  Transactions{

    public static List<Users> users   = new ArrayList<>();
    private String name;
    private int id;
    private String account_number;
    private int pin ;

    public Users(String name,  String account_number, int pin, double balance, int id) {
        super(balance);
        this.name = name; 
        this.account_number = account_number;
        this.pin = pin;
        this.id = id;//I am going to change that
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public void setId(int id){
        this.id = id;
    }

  
    public int getPin() {
        return this.pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
    @Override
    public String toString(){
        return "Customer "+ getId()+
            "\nname:"+ getName()+"\naccountNumber: "+getAccount_number()+
            "\nbalance:" +getBalance()+"\nid:"+ getId() + "\npin:" + getPin() ;
    }  
    
    @Override
    public void withdrawal(double ammount){
        setBalance(getBalance()-ammount);
           
    }

    @Override
    public  void deposit(double amount){
        setBalance(getBalance() +amount);
    }
}
