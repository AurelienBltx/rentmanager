package com.epf.rentmanager.models;

public class Vehicle {
    private long id;
    private String constructor;
    private int seats;
    private int owner_id;

    public Vehicle() {
    }

    public Vehicle(long id, String constructor, int seats, int owner_id) {
        this.id = id;
        this.constructor = constructor;
        this.seats = seats;
        this.owner_id = owner_id;
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

    public int getOwner_id() {
        return owner_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
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
