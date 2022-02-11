package com.example.pizzamaker.repository;

import com.example.pizzamaker.model.Ingredient;
import com.example.pizzamaker.model.Order;
import com.example.pizzamaker.util.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRepository {

    public void create(Order order) {
        Connection connection = SQLConnector.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `order` VALUES(0,?,?,?,?,?) ");
            preparedStatement.setInt(1, order.getTableId());
            preparedStatement.setBoolean(2, order.isInProcess());
            preparedStatement.setInt(3, order.getProductId());
            preparedStatement.setInt(4, order.getQuantity());
            preparedStatement.setFloat(5, order.getAmount());
            int i = preparedStatement.executeUpdate();


            preparedStatement.close();
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public List<Order> read(int tableId) {
        return readAll().stream().filter(item -> item.getTableId() == tableId).collect(Collectors.toList());
    }

    public List<Order> readAll() {
        Connection connection = SQLConnector.getConnection();
        List<Order> data = new LinkedList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from `order`");
            ResultSet resultSet = preparedStatement.executeQuery();
            data.addAll(listMapper(resultSet));

            preparedStatement.close();
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return data;
    }

    public Order update(Order order) {

        Connection connection = SQLConnector.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `order` SET  table_id = ?,  in_process = ?, product_id=?, quantity=?, amount=? WHERE id = ?");
            preparedStatement.setInt(1, order.getTableId());
            preparedStatement.setBoolean(2, order.isInProcess());
            preparedStatement.setInt(3, order.getProductId());
            preparedStatement.setInt(4, order.getQuantity());
            preparedStatement.setFloat(5, order.getAmount());
            preparedStatement.setInt(6, order.getId());

            int i = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }


        return order;
    }

    public void delete(int tableId) {
        Connection connection = SQLConnector.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `order` WHERE table_id = ?");
            preparedStatement.setInt(1, tableId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    private List<Order> listMapper(ResultSet resultSet) throws SQLException {
        List<Order> data = new LinkedList<>();
        while (resultSet.next()) {
            data.add(mapper(resultSet));
        }
        return data;
    }

    private Order mapper(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int table_id = resultSet.getInt("table_id");
        boolean in_process = resultSet.getBoolean("in_process");
        int product_id = resultSet.getInt("product_id");
        int quantity = resultSet.getInt("quantity");
        float amount = resultSet.getFloat("amount");

        return new Order(id, table_id, in_process, product_id, quantity, amount);

    }
}