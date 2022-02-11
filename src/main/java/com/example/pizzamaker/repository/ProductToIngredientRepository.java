package com.example.pizzamaker.repository;

import com.example.pizzamaker.model.commons.ProductToIngredient;
import com.example.pizzamaker.util.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductToIngredientRepository {
    public List<ProductToIngredient> readByProduct(int id) {
        return readAll().stream().filter(item -> item.getProductId() == id).collect(Collectors.toList());
    }

    public List<ProductToIngredient> readByIngredient(int id) {
        return readAll().stream().filter(item -> item.getIngredientId() == id).collect(Collectors.toList());
    }

    public List<ProductToIngredient> readAll() {
        List<ProductToIngredient> data = new LinkedList<>();
        Connection connection = SQLConnector.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `product_to_ingredient`");
            ResultSet resultSet = preparedStatement.executeQuery();
            data.addAll(listMapper(resultSet));

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return data;
    }

    public void create(ProductToIngredient productToIngredient) {
        Connection connection = SQLConnector.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `product_to_ingredient` VALUES (0,?,?)");
            preparedStatement.setInt(1, productToIngredient.getIngredientId());
            preparedStatement.setInt(2, productToIngredient.getProductId());
            int i = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ProductToIngredient update(ProductToIngredient productToIngredient) {
        Connection connection = SQLConnector.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `product_to_ingredient` SET ingredient_id = ? WHERE product_id = ?");
            preparedStatement.setInt(1, productToIngredient.getIngredientId());
            preparedStatement.setInt(2, productToIngredient.getProductId());
            int i = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return productToIngredient;
    }

    public void deleteByProduct(int id) {
        Connection connection = SQLConnector.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `product_to_ingredient` WHERE product_id = ?");
            preparedStatement.setInt(1, id);
            int i = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void delete(int id) {
        Connection connection = SQLConnector.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `product_to_ingredient` WHERE id = ?");
            preparedStatement.setInt(1, id);
            int i = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private List<ProductToIngredient> listMapper(ResultSet resultSet) throws SQLException {
        List<ProductToIngredient> data = new LinkedList<>();
        while (resultSet.next()) {
            data.add(mapper(resultSet));
        }
        return data;
    }

    private ProductToIngredient mapper(ResultSet resultSet) throws SQLException {
        ProductToIngredient productToIngredient = new ProductToIngredient();
        productToIngredient.setId(resultSet.getInt("id"));
        productToIngredient.setIngredientId(resultSet.getInt("ingredient_id"));
        productToIngredient.setIngredientId(resultSet.getInt("product_id"));

        return productToIngredient;
    }
}
