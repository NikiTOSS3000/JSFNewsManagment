package com.epam.jsf.database;

import java.sql.*;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

class DataSource {

    private final static Logger logger = Logger.getLogger("com.epam.jsf.database");
    private Stack<Connection> connections;
    private int maxConnections;
    private int minConnections;
    private int activeConnections;
    private String path, name, pass;
    private final Lock lock = new ReentrantLock();

    public DataSource(String driverClassName, String path, String name, String pass, int minConn, int maxConn) {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            logger.error(e.getLocalizedMessage());
        }
        this.path = path;
        this.name = name;
        this.pass = pass;
        this.minConnections = minConn;
        this.maxConnections = maxConn;
        try {
            connections = new Stack<Connection>();
            for (int i = 0; i < minConnections; i++) {
                Connection conn = newConnection();
                connections.push(conn);
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
        }
        activeConnections = 0;
    }

    private Connection newConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(path, name, pass);
        if (logger.isInfoEnabled()) {
            logger.info("Opened connection " + conn.toString());
        }
        return conn;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        lock.lock();
        try {
            if (activeConnections < maxConnections) {
                activeConnections++;
                if (!connections.isEmpty()) {
                    connection = connections.pop();
                } else {
                    connection = newConnection();
                }
            }
        } finally {
            lock.unlock();
        }
        return connection;
    }

    public void returnConnection(Connection conn) {
        lock.lock();
        try {
            if (conn != null) {
                activeConnections--;
                connections.push(conn);
            }
        } finally {
            lock.unlock();
        }
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        if (maxConnections < activeConnections) {
            throw new IllegalArgumentException();
        }
        this.maxConnections = maxConnections;
    }

    public int getActiveConnections() {
        return activeConnections;
    }

    protected void finalize() {
        while (!connections.isEmpty()) {
            try {
                connections.pop().close();
            } catch (SQLException e) {
                logger.error(e.getLocalizedMessage());
            }
        }
    }
}
