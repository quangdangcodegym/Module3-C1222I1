package com.codegym.customermanager.service;

import com.codegym.customermanager.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceMysql implements CustomerService{
    private static final String SELECT_ALL_CUSTOMER = "SELECT * FROM customer";
    private static final String FIND_CUSTOMER_BY_ID = "SELECT * FROM customer where id = ?";
    private static final String UPDATE_CUSTOMER = "UPDATE `customer` SET `name` = ?, `address` = ?, `email` = ?, `createat` = ?, `image` = ? WHERE (`id` = ?);";
    private static final String DELETE_CUSTOMER = "DELETE FROM `customer` WHERE (`id` = ?);";
    private static final String INSERT_CUSTOMER = "INSERT INTO `customer` (`name`, `address`, `email`, `createat`, `image`) VALUES (?, ?, ?, ?, ?)";
    private String jdbcURL = "jdbc:mysql://localhost:3306/c12_customer_manager?allowPublicKeyRetrieval=true&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "St180729!!";


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        Connection connection = getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMER);
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery(SELECT_ALL_CUSTOMER);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                // Luôn lấy theo column label
                Customer customer = getCustomerFromResultSet(rs);
                customers.add(customer);
            }
//            preparedStatement.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return customers;
    }

    private Customer getCustomerFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String image = rs.getString("image");
        java.sql.Date date = rs.getDate("createat");

        Date createAt = new Date(date.getTime());

        //public Customer(long id, String name, String email,
        //                    String address, String image, Date createAt)
        Customer customer = new Customer(id, name, email, address, image, createAt);
        return customer;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    @Override
    public void save(Customer customer) {
        Connection connection = getConnection();


        //INSERT INTO customer` (`name`, `address`, `email`, `createat`, `image`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setDate(4, new java.sql.Date(customer.getCreateAt().getTime()));
            preparedStatement.setString(5, customer.getImage());

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Customer findById(long id) {

        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CUSTOMER_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                // Luôn lấy theo column label
                Customer customer = getCustomerFromResultSet(rs);
                return customer;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public void update(long id, Customer customer) {
        Connection connection = getConnection();

        try {
            //UPDATE `customer` SET `name` = ?, `address` = ?, `email` = ?,
            // `createat` = ?, `image` = ? WHERE (`id` = ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setDate(4, (Date) customer.getCreateAt());
            preparedStatement.setString(5, customer.getImage());

            preparedStatement.setLong(6, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void remove(long id) {
        Connection connection = getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    @Override
    public long maxId() {
        return 0;
    }
}
