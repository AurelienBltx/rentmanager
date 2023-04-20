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

import org.springframework.beans.factory.annotation.Autowire;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	public ClientDao() {}
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT * FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT * FROM Client;";
	
	public void create(Client client) throws DaoException {
		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_CLIENT_QUERY);


			stmt.setString(1, client.getName());
			stmt.setString(2, client.getLastName());
			stmt.setString(3, client.getEmail());
			stmt.setDate(4, java.sql.Date.valueOf(client.getDate()));
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
			PreparedStatement stmt = connection.prepareStatement(DELETE_CLIENT_QUERY);
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

	public Client findById(long id) throws DaoException {

		try
		{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_CLIENT_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			String email = rs.getString("email");
			LocalDate date = rs.getDate("naissance").toLocalDate();

			connection.close();
			stmt.close();
			rs.close();

			return new Client(prenom, id, date, nom, email);
		}
		catch(SQLException e)
		{
			throw new RuntimeException();
		}
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try{
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			while(rs.next()){
				long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate date = rs.getDate("naissance").toLocalDate();

				clients.add(new Client(prenom, id, date, nom, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return clients;
	}


	public int count() throws DaoException{
		int nbClients = 0;
		try{
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			while(rs.next()){
				nbClients += 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

		return nbClients;
	}
}
