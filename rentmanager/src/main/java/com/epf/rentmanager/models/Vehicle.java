package com.epf.rentmanager.models;

public class Vehicle {


    private long id;
    private String constructor;
    private int seats;

    public Vehicle() {
    }

    public Vehicle(long id, String constructor, int seats) {
        this.id = id;
        this.constructor = constructor;
        this.seats = seats;
    }

    public long getId() {
        return id;
    }

    public String getConstructor() {
        return constructor;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructor='" + constructor + '\'' +
                ", seats=" + seats +
                '}';
    }


}
