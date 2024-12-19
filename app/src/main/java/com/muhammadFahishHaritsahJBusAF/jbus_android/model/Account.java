package com.muhammadFahishHaritsahJBusAF.jbus_android.model;

import java.util.ArrayList;
//import java.util.List;

public class Account extends Serializable {
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter company;
    public static ArrayList<Account> accounts = new ArrayList<>();

//    public Account(String username, String email, String password, double balance) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.balance = balance;
//
//        accounts.add(this);
//    }

    public Account(String username, String email, String password) {
        this.name = username;
        this.email = email;
        this.password = password;
        this.balance = 0.0d;
    }

}
