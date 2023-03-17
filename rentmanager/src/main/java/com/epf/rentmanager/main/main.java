package com.epf.rentmanager.main;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.time.LocalDate;
import java.util.Scanner;


public class main {

    public static void main(String args[]) throws ServiceException {

        //Trouver client par ID
        ClientDao client = new ClientDao();
        System.out.println(client.toString());
        ClientService clients = new ClientService(client);
        clients.findAll();
        System.out.println(clients);
        System.out.println("Quel client ?");
        Scanner sc1 = new Scanner(System.in);
        long id1 = sc1.nextInt();
        Client clientele = clients.findById(id1);
        System.out.println(clientele);

        //Trouver vehicule par ID
        VehicleDao vehicle = new VehicleDao();
        System.out.println(vehicle.toString());
        VehicleService vehicles = new VehicleService(vehicle);
        vehicles.findAll();
        System.out.println(vehicles);
        System.out.println("Quel véhicule ?");
        Scanner sc2 = new Scanner(System.in);
        long id2 = sc2.nextInt();
        Vehicle voiture = vehicles.findById(id2);
        System.out.println(voiture);

        //Nombre de clients
        int nbClients = 0;
        nbClients = clients.count();
        System.out.println("Nombre de clients : " + nbClients);

        //Nombre de vehicules
        int nbVehicle = 0;
        nbVehicle = vehicles.count();
        System.out.println("Nombre de véhicules : " + nbVehicle);

        //Nombre de reservations
        /*int nbReservation = 0;
        ReservationService reservations = new ReservationService(nbReservation);
        nbReservation = reservations.count();
        System.out.println("Nombre de réservations : " + nbReservation);*/

    }
}
