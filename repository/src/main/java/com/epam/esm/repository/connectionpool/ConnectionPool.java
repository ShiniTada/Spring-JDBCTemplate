package com.epam.esm.repository.connectionpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/** Class for contain connections */
public class ConnectionPool extends AbstractDataSource {

  private static final Logger LOGGER_CONN_POOL = LogManager.getLogger(ConnectionPool.class);
  private BlockingQueue<Connection> availableConns;
  private BlockingQueue<Connection> usedConns;

  @Value("${db.driver}")
  private String driver;

  @Value("${db.url}")
  private String url;

  @Value("${db.user}")
  private String user;

  @Value("${db.password}")
  private String password;

  @Value("#{T(java.lang.Integer).parseInt(${db.poolCapacity})}")
  private int poolCapacity;

  @PostConstruct
  private void init() throws ConnectionPoolException {
    try {
      Class.forName(driver);
      availableConns = new ArrayBlockingQueue<>(poolCapacity);
      usedConns = new ArrayBlockingQueue<>(poolCapacity);
    } catch (ClassNotFoundException e) {
      LOGGER_CONN_POOL.error("Connection initialization ClassNotFoundException");
      throw new ConnectionPoolException("Connection initialization ClassNotFoundException", e);
    }
  }

  /**
   * Get instance.
   *
   * @return connection pool instance
   */
  private Connection createConnection() throws SQLException {
    Connection connection = DriverManager.getConnection(url, user, password);
    Connection proxy =
        (Connection)
            Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[] {Connection.class},
                new ProxyConnectionHandler(connection));
    usedConns.add(proxy);
    return proxy;
  }

  /**
   * Get connection.
   *
   * @return proxy connection
   * @throws SQLException when sql error
   */
  @Override
  public Connection getConnection() throws SQLException {
    Connection newConn = null;
    try {
      if (usedConns.size() < poolCapacity && availableConns.isEmpty()) {
        newConn = createConnection();
      } else {
        newConn = availableConns.poll(10, TimeUnit.SECONDS);
      }
    } catch (InterruptedException e) {
      LOGGER_CONN_POOL.error(
          "Interrupted exception. Wait poll connection from avaliable connections too long.\n" + e);
      Thread.currentThread().interrupt();
    } catch (SQLException e) {
      LOGGER_CONN_POOL.error("Exception in getConnection from connection pool\n" + e);
      Thread.currentThread().interrupt();
    }
    return newConn;
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    throw new UnsupportedOperationException();
  }

  /**
   * Put back connection.
   *
   * @param connection connection
   */
  public void returnConnection(Connection connection) throws SQLException, ConnectionPoolException {
    if (connection != null) {
      usedConns.remove(connection);
      if (connection.isReadOnly()) {
        connection.setReadOnly(false);
      }
      if (!connection.getAutoCommit()) {
        connection.commit();
      }
      if (connection.isClosed()) {
        LOGGER_CONN_POOL.error("Try to return in connection pool closed connection");
        throw new ConnectionPoolException("Try to return in connection pool closed connection");
      }
      availableConns.offer(connection);
    }
  }

  private void closeConnections(BlockingQueue<Connection> connections)
      throws ConnectionPoolException {
    try {
      for (Connection conn : connections) {
        if (!conn.isClosed()) {
          if (!conn.getAutoCommit()) {
            conn.commit();
          }
          ProxyConnectionHandler handler =
              (ProxyConnectionHandler) Proxy.getInvocationHandler(conn);
          handler.getConnection().close();
        }
      }
    } catch (SQLException e) {
      throw new ConnectionPoolException("Destroy pool error. ", e);
    }
  }

  @PreDestroy
  public void destroy() throws ConnectionPoolException {
    closeConnections(availableConns);
    closeConnections(usedConns);
  }

  class ProxyConnectionHandler implements InvocationHandler {

    private static final String METHOD_CLOSE = "close";
    private Connection connection;

    ProxyConnectionHandler(Connection connection) {
      this.connection = connection;
    }

    public Connection getConnection() {
      return connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (method.getName().equals(METHOD_CLOSE)) {
        returnConnection((Connection) proxy);
        return null;
      } else {
        return method.invoke(connection, args);
      }
    }
  }
}
