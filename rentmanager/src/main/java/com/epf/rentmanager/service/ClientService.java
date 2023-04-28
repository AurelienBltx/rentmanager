package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Reservation;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class ClientService {

	private ClientDao clientDao;
	private ReservationService reservationDao;
	
	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	
	public void create(Client client) throws ServiceException {
		// TODO: créer un client
		try {

			if(clientDao.verifierClientMajeur(client) == false){
				System.out.println("Erreur : le client n'est pas majeur.");
			}
			else if(clientDao.verifierNomClient(client) == false){
				System.out.println("Erreur : le nom doit faire au moins 3 caractères.");
			}
			else if (clientDao.verifierPrenomClient(client) == false){
				System.out.println("Erreur : le prénom doit faire au moins 3 caractères.");
			}
			else {
				clientDao.create(client);
			}

		} catch (DaoException e) {
			throw new ServiceException();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(long id) throws ServiceException {
		// TODO: supprimer un client
		try {
			clientDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public void edit(long id, String nom, String prenom, String email, LocalDate date) throws ServiceException {
		// TODO: modifier un client
		try {
			Client client = new Client(id, nom, prenom, date, email);
			if(clientDao.verifierClientMajeur(client) == false){
				System.out.println("Erreur : le client n'est pas majeur.");
			}
			else if(clientDao.verifierNomClient(client) == false){
				System.out.println("Erreur : le nom doit faire au moins 3 caractères.");
			}
			else if (clientDao.verifierPrenomClient(client) == false){
				System.out.println("Erreur : le prénom doit faire au moins 3 caractères.");
			}
			else{
				clientDao.edit(id, nom, prenom, email, date);
			}

		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public Client findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public int countAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.countAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
}
