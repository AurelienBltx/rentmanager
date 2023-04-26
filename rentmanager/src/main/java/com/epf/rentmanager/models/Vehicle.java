package com.epf.rentmanager.models;

public class Vehicle {
    private long id;
    private String constructor;
    private int seats;
    private String model;

    public Vehicle() {
    }

    public Vehicle(long id, String constructor, int seats, String model) {
        this.id = id;
        this.constructor = constructor;
        this.seats = seats;
        this.model = model;
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

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructor='" + constructor + '\'' +
                ", seats=" + seats +
                ", model='" + model + '\'' +
                '}';
    }
}
