package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

import com.epf.rentmanager.models.Reservation;

import java.util.List;

public class ReservationService {
    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }


    public long create(Reservation reservation) throws ServiceException {
        // TODO: créer une Reservation
        return 0;
    }

    public List<Reservation> findResaByClientId(long id) throws ServiceException {
        // TODO: récupérer une Reservation par son id
        try {
            return ReservationDao.getInstance().findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        // TODO: récupérer une Reservation par son id
        try {
            return ReservationDao.getInstance().findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        // TODO: récupérer toutes les Reservations
        try {
            return ReservationDao.getInstance().findAll();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public int count() throws ServiceException {
        // TODO: récupérer tous les clients
        try {
            return reservationDao.getInstance().count();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}
