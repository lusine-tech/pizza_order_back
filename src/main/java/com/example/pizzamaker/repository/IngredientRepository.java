package com.example.pizzamaker.repository;

import com.example.pizzamaker.model.Ingredient;
import com.example.pizzamaker.model.Table;
import com.example.pizzamaker.util.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class IngredientRepository {

    public Ingredient read(int id) {

        Connection connection = SQLConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM `ingredient` WHERE id=?");
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Ingredient ingredient=null;
        try{
            while (resultSet.next()){
                ingredient=mapper(resultSet);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            pstmt.close();
            resultSet.close();
            connection.close();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return ingredient;
    }

    public Ingredient read(String name) {

        Connection connection = SQLConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement("SELECT * FROM `ingredient` WHERE name=?");
            pstmt.setString(1,name);
            resultSet = pstmt.executeQuery();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Ingredient ingredient=null;
        try{
            while (resultSet.next()){
                ingredient=mapper(resultSet);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        try{
            pstmt.close();
            resultSet.close();
            connection.close();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return ingredient;
    }

    public List<Ingredient> readAll() {
        Connection connection = SQLConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement("SELECT * from `ingredient`");
            resultSet = pstmt.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        List<Ingredient> data = mapperList(resultSet);


        try {
            pstmt.close();
            resultSet.close();
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return data;
    }

    public void create(Ingredient ingredient) {
        Connection connection = SQLConnector.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `ingredient` values (0,?)");
            preparedStatement.setString(1, ingredient.getName());
            int i = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Ingredient update(Ingredient ingredient) {

        Connection connection = SQLConnector.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `ingredient` SET name = ? WHERE id = ?");
            preparedStatement.setString(1,ingredient.getName());
            preparedStatement.setInt(2,ingredient.getId());
            int i = preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ingredient;
    }

    public void delete(int id) {
        Connection connection = SQLConnector.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `ingredient` where id=?");
            preparedStatement.setInt(1, id);
            int i = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static List<Ingredient> mapperList(ResultSet resultSet) {
        List<Ingredient> data = new LinkedList<>();
        try {
            while (resultSet.next()) {
                data.add(mapper(resultSet));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return data;
    }

    private static Ingredient mapper(ResultSet resultSet) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(resultSet.getInt("id"));
        ingredient.setName(resultSet.getString("name"));
        return ingredient;
    }
}
