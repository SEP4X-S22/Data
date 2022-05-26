package com.Apharma.sep4.Persistence.DAO.DataWarehouse.DbUtils;

import org.postgresql.Driver;

import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 Code courtesy of Ole Ildsgaard Hougaard.
 Available at: https://github.com/olehougaard/sdj3-a20/blob/master/cars-spring-soap/src/main/java/dk/via/db/DatabaseHelper.java
 
 @author Ole Ildsgaard Hougaard
 */
public class DatabaseHelper<T>
{
	private final String jdbcURL;
	private final String username;
	private final String password;
	
	public DatabaseHelper(String jdbcURL, String username, String password)
	{
		this.jdbcURL = jdbcURL;
		this.username = username;
		this.password = password;
		try
		{
			DriverManager.registerDriver(new Driver());
		}
		catch (SQLException e)
		{
			throw new RuntimeException("No JDBC driver", e);
		}
	}
	
	public DatabaseHelper(String jdbcURL)
	{
		this(jdbcURL, null, null);
	}
	
	protected Connection getConnection() throws SQLException
	{
		if (username == null)
		{
			return DriverManager.getConnection(jdbcURL);
		}
		else
		{
			return DriverManager.getConnection(jdbcURL, username, password);
		}
	}
	
	private PreparedStatement prepare(Connection connection, String sql, Object[] parameters) throws SQLException
	{
		PreparedStatement stat = connection.prepareStatement(sql);
		for (int i = 0; i < parameters.length; i++)
		{
			stat.setObject(i + 1, parameters[i]);
		}
		return stat;
	}
	
	public int executeUpdate(String sql, Object... parameters)
	{
		try (Connection connection = getConnection())
		{
			PreparedStatement stat = prepare(connection, sql, parameters);
			return stat.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public T mapSingle(DataMapper<T> mapper, String sql, Object... parameters)
	{
		try (Connection connection = getConnection())
		{
			PreparedStatement stat = prepare(connection, sql, parameters);
			ResultSet rs = stat.executeQuery();
			if (rs.next())
			{
				return mapper.create(rs);
			}
			else
			{
				return null;
			}
		}
		catch (SQLException | DatatypeConfigurationException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public List<T> map(DataMapper<T> mapper, String sql, Object... parameters)
	{
		try (Connection connection = getConnection())
		{
			PreparedStatement stat = prepare(connection, sql, parameters);
			ResultSet rs = stat.executeQuery();
			LinkedList<T> allMessages = new LinkedList<>();
			while (rs.next())
			{
				allMessages.add(mapper.create(rs));
			}
			
			return allMessages;
		}
		catch (SQLException | DatatypeConfigurationException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
