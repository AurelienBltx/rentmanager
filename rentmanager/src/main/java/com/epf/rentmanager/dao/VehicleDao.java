package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowire;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private ReservationDao reservationDao;
	public VehicleDao() {
	}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructor, seats, model) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String EDIT_VEHICLE_QUERY = "UPDATE Vehicle SET constructor = ?, seats = ?, model = ? WHERE id=?;";

	private static final String FIND_VEHICLE_QUERY = "SELECT * FROM Vehicle WHERE id = ?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT * FROM Vehicle;";
	private static final String COUNT_ALL_VEHICLES_QUERY = "SELECT COUNT(id) FROM Vehicle;";

	public void create(Vehicle vehicle) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_VEHICLE_QUERY);


			stmt.setString(1, vehicle.getConstructor());
			stmt.setInt(2, vehicle.getSeats());
			stmt.setString(3, vehicle.getModel());
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
			PreparedStatement stmt = connection.prepareStatement(DELETE_VEHICLE_QUERY);
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

	public void edit(long id, String constructor, int seats, String model) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(EDIT_VEHICLE_QUERY);

			stmt.setString(1, constructor);
			stmt.setInt(2, seats);
			stmt.setString(3, model);
			stmt.setLong(4, id);
			stmt.executeUpdate();

			connection.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
	}

	public Vehicle findById(long id) throws DaoException {

		Vehicle vehicle = null;
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_VEHICLE_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				String constructor = rs.getString("constructor");
				int seats = rs.getInt("seats");
				String model = rs.getString("model");


				vehicle = new Vehicle(id, constructor, seats, model);
				connection.close();
				stmt.close();
				rs.close();
			}
			return vehicle;
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try {
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while (rs.next()) {
				long id = rs.getLong("id");
				String constructor = rs.getString("constructor");
				int seats = rs.getInt("seats");
				String model = rs.getString("model");

				vehicles.add(new Vehicle(id, constructor, seats, model));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return vehicles;
	}


	public int countAll() throws DaoException{
		int nbVehicles = 0;
		try{
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(COUNT_ALL_VEHICLES_QUERY);

			if(rs.next()){
				nbVehicles = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return nbVehicles;
	}

}
