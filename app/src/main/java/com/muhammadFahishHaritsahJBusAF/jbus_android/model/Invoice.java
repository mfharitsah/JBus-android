package com.muhammadFahishHaritsahJBusAF.jbus_android.model;

import java.sql.Timestamp;

/**
 * Write a description of class Invoice here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Invoice extends Serializable
{
    //fields
    public Timestamp time;
    public int buyerId;
    public int renterId;
    public BusRating rating;
    public PaymentStatus status;

    //enum
    public enum BusRating{
        NONE,
        NEUTRAL,
        GOOD,
        BAD;
    }

    public enum PaymentStatus{
        FAILED,
        WAITING,
        SUCCESS;
    }

    protected Invoice(int buyerId, int renterId){
        super();
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = new Timestamp(System.currentTimeMillis());
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }

    public Invoice(Account buyer, Renter renter){
        super();
        this.buyerId = buyer.id;
        this.renterId = renter.id;
        this.time = new Timestamp(System.currentTimeMillis());
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }

    //method
    public String toString(){
        String print = "ID : " + id + "\nTime : " + time.getTime() + "\nBuyer ID = " + buyerId + "\nRenter ID = " + renterId;
        return print;
    }
}