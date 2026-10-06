package com.book;

import java.time.LocalDate;

public class Transaction implements java.io.Serializable {
    private java.lang.String id;
    private double amount;
    private java.lang.String category;
    private java.time.LocalDate date;
    private java.lang.String note;

    public Transaction(String id, double amount, String category, LocalDate date, String note) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    public java.lang.String getId() { return this.id; }

    public double getAmount() {return this.amount; }

    public java.lang.String getCategory() {return this.category; }

    public java.time.LocalDate getDate() {return this.date; }

    public java.lang.String getNote() {return this.note; }

    public java.lang.String toString() { return this.id + " " + this.amount + " " + this.category + " " + this.date + " " + this.note; }
}
