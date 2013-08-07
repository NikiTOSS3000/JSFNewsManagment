package com.epam.jsf.database;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class AbstractDAO {  

	protected DataSource source;

	final static Logger logger = Logger.getLogger("com.epam.jsf.database");

	protected Statement statement = null;
	protected PreparedStatement preparedStatement = null;
	protected Connection connection = null;
	protected ResultSet resultSet = null;

	protected AbstractDAO(DataSource s) {
		source = s;
	}

	protected boolean createStatement(){
		try {
			connection = source.getConnection();
			if(connection==null){
				return false;
			}            
			statement = connection.createStatement();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	protected boolean prepareStatement(String query) { 
		try {
			connection = source.getConnection();
			if(connection==null){
				return false;
			}            
			preparedStatement = connection.prepareStatement(query);
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	protected boolean executeSQL(String query) {
		try{
			if(statement!=null){
				resultSet = statement.executeQuery(query);
				return true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} 
		return false;
	}

	protected boolean executeQuery() { 
		try {
			if (preparedStatement != null) {
				resultSet = preparedStatement.executeQuery();
				return true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	protected boolean executeUpdate() { 
		boolean success = false;
		try {
			if (preparedStatement != null) {
				connection.setAutoCommit(false);
				preparedStatement.executeUpdate();
				connection.commit();
				success = true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException ex) {
				logger.error(ex.getMessage());
			}
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		return success;
	}

	protected void closeAll() {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		try{
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		try{
			if (preparedStatement != null) {
				preparedStatement.close();
				preparedStatement = null;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		if (connection != null) {
			source.returnConnection(connection);
			connection=null;
		}
	}

	public DataSource getSource() {
		return source;
	}

	public void setSource(DataSource source) {
		this.source = source;
	}
}
