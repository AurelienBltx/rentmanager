package com.epf.rentmanager.service;

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
			vehicleDao.create(vehicle);
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

	public int count() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
}
