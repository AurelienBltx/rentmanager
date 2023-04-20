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

import com.epf.rentmanager.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowire;


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
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";

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
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return reservations;
	}

	public int count() throws DaoException{
		int nbReservations = 0;
		try{
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while(rs.next()){
				nbReservations += 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return nbReservations;
	}
}
