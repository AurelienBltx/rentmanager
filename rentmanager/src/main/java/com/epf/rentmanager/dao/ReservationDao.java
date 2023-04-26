package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epf.rentmanager.models.Vehicle;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	public ReservationDao() {}

	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String EDIT_RESERVATION_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id=?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT * FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_CLIENT_RESERVATION_QUERY = "SELECT * FROM Reservation INNER JOIN Client ON Reservation.client_id = Client.id WHERE Reservation.id=?;";
	private static final String FIND_VEHICLE_RESERVATION_QUERY = "SELECT * FROM Reservation INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id WHERE Reservation.id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_ALL_RESERVATIONS_QUERY = "SELECT COUNT(id) FROM Reservation;";
	private static final String COUNT_RESERVATION_BY_CLIENT_QUERY = "SELECT COUNT(id) FROM Reservation WHERE client_id=?;";
	private static final String COUNT_RESERVATION_BY_VEHICLE_QUERY = "SELECT COUNT(id) FROM Reservation WHERE vehicle_id=?;";


	public void create(Reservation reservation) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_RESERVATION_QUERY);


			stmt.setLong(1, reservation.getClient_id());
			stmt.setLong(2, reservation.getVehicle_id());
			stmt.setDate(3, java.sql.Date.valueOf(reservation.getDebut()));
			stmt.setDate(4, java.sql.Date.valueOf(reservation.getFin()));

			stmt.executeUpdate();

			connection.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
	}

	public void delete(long id) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			stmt.setLong(1, id);
			stmt.executeUpdate();

			connection.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
	}

	public void edit(long id, long vehicle_id, long client_id, LocalDate debut, LocalDate fin) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(EDIT_RESERVATION_QUERY);

			stmt.setLong(1, client_id);
			stmt.setLong(2, vehicle_id);
			stmt.setDate(3, java.sql.Date.valueOf(debut));
			stmt.setDate(4, java.sql.Date.valueOf(fin));
			stmt.setLong(5, id);
			stmt.executeUpdate();

			connection.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
	}

	public Reservation findById(long id) throws DaoException {

		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATION_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			long client_id = rs.getLong("client_id");
			long vehicle_id = rs.getLong("vehicle_id");
			LocalDate debut = rs.getDate("debut").toLocalDate();
			LocalDate fin = rs.getDate("fin").toLocalDate();

			connection.close();
			stmt.close();
			rs.close();

			return new Reservation(id, client_id, vehicle_id, debut, fin);
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
	}

	
	public List<Reservation> findResaByClientId(long client_id) throws DaoException {
		List<Reservation> resa = new ArrayList<Reservation>();
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			stmt.setLong(1, client_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				long id = rs.getLong("id");
				long vehicle_id = rs.getLong("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				resa.add(new Reservation(id, client_id, vehicle_id, debut, fin));
			}

			connection.close();
			stmt.close();
			rs.close();

		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
		return resa;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicle_id) throws DaoException {
		List<Reservation> resa = new ArrayList<Reservation>();
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			stmt.setLong(1, vehicle_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				long id = rs.getLong("id");
				long client_id = rs.getLong("client_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				resa.add(new Reservation(id, client_id, vehicle_id, debut, fin));
			}

			connection.close();
			stmt.close();
			rs.close();

		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
		return resa;
	}

	public Client findClientResaById(long id) throws DaoException {
		Client client = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_CLIENT_RESERVATION_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				long client_id = rs.getLong("Client.id");
				String nom = rs.getString("Client.nom");
				String prenom = rs.getString("Client.prenom");
				String email = rs.getString("Client.email");
				LocalDate naissance = rs.getDate("Client.naissance").toLocalDate();

				client = new Client(client_id, prenom, nom, naissance, email);
			}

			connection.close();
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return client;
	}

	public Vehicle findVehicleResaById(long id) throws DaoException {
		Vehicle vehicle = null;
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement stmt = connection.prepareStatement(FIND_VEHICLE_RESERVATION_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				long vehicle_id = rs.getLong("Vehicle.id");
				String constructor = rs.getString("Vehicle.constructor");
				int seats = rs.getInt("Vehicle.seats");
				String model = rs.getString("Vehicle.model");

				vehicle = new Vehicle(vehicle_id, constructor, seats, model);

			}

			connection.close();
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicle;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try{
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while(rs.next()){
				long id = rs.getLong("id");
				long client_id = rs.getLong("client_id");
				long vehicle_id = rs.getLong("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				reservations.add(new Reservation(id, client_id, vehicle_id, debut, fin));
			}

			connection.close();
			statement.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return reservations;
	}

	public int countAll() throws DaoException{
		int nbReservations = 0;
		try{
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(COUNT_ALL_RESERVATIONS_QUERY);

			if(rs.next()){
				nbReservations = rs.getInt(1);
			}

			connection.close();
			statement.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return nbReservations;
	}

	public int countResaByClient(long id) throws DaoException{
		int nbReservations = 0;
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(COUNT_RESERVATION_BY_CLIENT_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()) {
				nbReservations = rs.getInt(1);
			}
			connection.close();
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return nbReservations;
	}

	public int countResaByVehicle(long id) throws DaoException{
		int nbReservations = 0;
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(COUNT_RESERVATION_BY_VEHICLE_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()) {
				nbReservations = rs.getInt(1);
			}
			connection.close();
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return nbReservations;
	}

}
