package com.epf.rentmanager.models;

import java.time.LocalDate;


public class Client {

    private String name;
    private long id;
    private LocalDate date;
    private String lastName;
    private String email;

    public Client(String name, long id, LocalDate date, String lastName, String email) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.lastName = lastName;
        this.email = email;
    }

    public Client(){
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", date=" + date +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
