/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import domain.DomainObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author remar
 */
public class DatabaseBroker {

    private Connection connection;

    public void connect() throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            Class.forName(driver);

            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            System.out.println("Connected successfully!");
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void disconnect() throws Exception {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
    }

    public void commit() throws Exception {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
    }

    public void rollback() throws Exception {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
    }

    public DomainObject insert(DomainObject domainObject) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("INSERT INTO %s (%s) VALUES(%s)",
                    domainObject.getTableName(),
                    domainObject.getAttributeNamesInsert(),
                    domainObject.getAttributeValuesInsert());
            System.out.println(query);
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (domainObject.isAutoIncrement()) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    domainObject.setObjectId(rs.getLong(1));
                }
            }
            return domainObject;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public DomainObject update(DomainObject domainObject) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("UPDATE %s SET %s WHERE id = %s",
                    domainObject.getTableName(),
                    domainObject.getSet(),
                    domainObject.getObjectId());
            statement.executeUpdate(query);
            System.out.println(query);
            return domainObject;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public DomainObject delete(DomainObject domainObject) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("DELETE FROM %s WHERE %s",
                    domainObject.getTableName(),
                    domainObject.getWhere(domainObject, false));
            System.out.println(query);
            statement.executeUpdate(query);
            return domainObject;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<DomainObject> select(DomainObject domainObject) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("SELECT * FROM %s",
                    domainObject.getTableName());
            System.out.println(query);
            try (ResultSet rs = statement.executeQuery(query)) {
                return domainObject.getList(rs);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<DomainObject> selectWhere(DomainObject domainObject) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("SELECT * FROM %s WHERE %s",
                    domainObject.getTableName(),
                    domainObject.getWhere(domainObject, false));
            System.out.println(query);
            try (ResultSet rs = statement.executeQuery(query)) {
                return domainObject.getList(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public List<DomainObject> selectJoin(DomainObject domainObject) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("SELECT %s FROM %s %s ORDER BY %s",
                    domainObject.getAttributeNamesJoin(),
                    domainObject.getTableName(),
                    domainObject.getJoin(),
                    domainObject.getOrderBy(true));
            System.out.println(query);
            try (ResultSet rs = statement.executeQuery(query)) {
                return domainObject.getList(rs);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<DomainObject> selectJoinWhere(DomainObject domainObject) throws Exception {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("SELECT %s FROM %s %s WHERE %s ORDER BY %s",
                    domainObject.getAttributeNamesJoin(),
                    domainObject.getTableName(),
                    domainObject.getJoin(),
                    domainObject.getWhere(domainObject, true),
                    domainObject.getOrderBy(true));
            System.out.println(query);
            try (ResultSet rs = statement.executeQuery(query)) {
                return domainObject.getList(rs);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
