package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.models.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	
	public VehicleService(VehicleDao VehicleDao) {
		this.vehicleDao = VehicleDao;
	}
	
	
	public void create(Vehicle vehicle) throws ServiceException {
		try {
			if(vehicle.getSeats()>2 && vehicle.getSeats()<9){
				vehicleDao.create(vehicle);
			}
			else{
				System.out.println("Erreur : Le nombre de place d'une voiture doit être compris entre 2 et 9.");
			}
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public void delete(long id) throws ServiceException {
		// TODO: supprimer un client
		try {
			vehicleDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public void edit(long id, String constructor, int seats, String model) throws ServiceException {
		// TODO: modifier un vehicule
		try {
			if(seats>2 && seats<9){
				vehicleDao.edit(id, constructor, seats, model);
			}
			else{
				System.out.println("Erreur : Le nombre de place d'une voiture doit être compris entre 2 et 9.");
			}

		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public int countAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return vehicleDao.countAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
}
