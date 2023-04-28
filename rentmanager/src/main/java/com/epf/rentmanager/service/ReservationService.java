package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.models.Vehicle;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    public ReservationService(ReservationDao ReservationDao) {

        this.reservationDao = ReservationDao;
    }



    public void create(Reservation reservation) throws ServiceException {
        // TODO: créer un client
        try {
            if(reservationDao.verifierDateReservation(reservation) == false){
                System.out.println("Erreur : ce véhicule est déjà réservé à cette date.");
            }
            else if(reservationDao.verifierLongueurReservation(reservation) == false){
                System.out.println("Erreur : un véhicule ne peux pas être réservé plus de 7 jours de suite par le même utilisateur.");
            }
            else{
                reservationDao.create(reservation);
            }

        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public void delete(long id) throws ServiceException {
        // TODO: supprimer un client
        try {
            reservationDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public void edit(long id, long vehicle_id, long client_id, LocalDate debut, LocalDate fin) throws ServiceException {
        // TODO: modifier une reservation
        try {
            reservationDao.edit(id, vehicle_id, client_id, debut, fin);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public Reservation findById(long id) throws ServiceException {
        // TODO: récupérer une reservation par son id
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByClientId(long id) throws ServiceException {
        // TODO: récupérer une Reservation par son id
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        // TODO: récupérer une Reservation par son id
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public Client findClientResaById(long id) throws ServiceException {
        // TODO: récupérer les infos d'une Reservation par son id
        try {
            return reservationDao.findClientResaById(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public Vehicle findVehicleResaById(long id) throws ServiceException {
        // TODO: récupérer les infos d'une Reservation par son id
        try {
            return reservationDao.findVehicleResaById(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        // TODO: récupérer toutes les Reservations
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public int countAll() throws ServiceException {
        // TODO: récupérer tous les clients
        try {
            return reservationDao.countAll();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public int countResaByClient(long id) throws ServiceException {
        // TODO: récupérer tous les clients
        try {
            return reservationDao.countResaByClient(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public int countResaByVehicle(long id) throws ServiceException {
        // TODO: récupérer tous les clients
        try {
            return reservationDao.countResaByVehicle(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}
