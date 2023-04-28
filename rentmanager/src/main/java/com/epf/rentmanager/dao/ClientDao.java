package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowire;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	public ClientDao() {
	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String EDIT_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT * FROM Client WHERE id=?;";
	private static final String FIND_VEHICLE_CLIENT_QUERY = "SELECT * FROM Client INNER JOIN Vehicle ON Client.vehicle_id = Vehicle.id WHERE Reservation.id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT * FROM Client;";
	private static final String COUNT_ALL_CLIENTS_QUERY = "SELECT COUNT(id) FROM Client;";

	public void create(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_CLIENT_QUERY);


			stmt.setString(1, client.getPrenom());
			stmt.setString(2, client.getNom());
			stmt.setString(3, client.getEmail());
			stmt.setDate(4, java.sql.Date.valueOf(client.getNaissance()));
			stmt.executeUpdate();

			connection.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public void delete(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_CLIENT_QUERY);
			stmt.setLong(1, id);
			stmt.executeUpdate();

			connection.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}


	public void edit(long id, String nom, String prenom, String email, LocalDate date) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(EDIT_CLIENT_QUERY);

			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			stmt.setString(3, email);
			stmt.setDate(4, java.sql.Date.valueOf(date));
			stmt.setLong(5, id);
			stmt.executeUpdate();

			connection.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public Client findById(long id) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_CLIENT_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String email = rs.getString("email");
			LocalDate naissance = rs.getDate("naissance").toLocalDate();

			connection.close();
			stmt.close();
			rs.close();

			return new Client(id, prenom, nom, naissance, email);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public Vehicle findVehicleClientById(long id) throws DaoException {
		Vehicle vehicle = null;
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement stmt = connection.prepareStatement(FIND_VEHICLE_CLIENT_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				long vehicle_id = rs.getLong("Vehicle.id");
				String constructor = rs.getString("Vehicle.constructor");
				int seats = rs.getInt("Vehicle.seats");
				String model = rs.getString("Vehicle.model");

				vehicle = new Vehicle(vehicle_id, constructor, seats, model);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicle;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try {
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			while (rs.next()) {
				long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate naissance = rs.getDate("naissance").toLocalDate();

				clients.add(new Client(id, prenom, nom, naissance, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return clients;
	}


	public int countAll() throws DaoException {
		int nbClients = 0;
		try {
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(COUNT_ALL_CLIENTS_QUERY);

			if (rs.next()) {
				nbClients = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return nbClients;
	}


	//CONTRAINTES

	public boolean verifierClientMajeur(Client client) throws DaoException {
		boolean test;
		LocalDate dateMajeur = LocalDate.now().minusYears(18);
		if (client.getNaissance().isAfter(dateMajeur)) {
			test = false;
		} else {
			test = true;
		}
		return test;
	}

	public boolean verifierNomClient(Client client) throws DaoException {
		boolean test;
		if ((client.getNom().length() > 2) && (client.getNom().length() < 9)) {
			test = true;
		} else {
			test = false;
		}
		return test;
	}

	public boolean verifierPrenomClient(Client client) throws DaoException {
		boolean test;
		if ((client.getPrenom().length() > 2) && (client.getPrenom().length() < 9)) {
			test = true;
		} else {
			test = false;
		}
		return test;
	}
}
