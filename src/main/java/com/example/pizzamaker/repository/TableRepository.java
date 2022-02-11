package com.example.pizzamaker.repository;

import com.example.pizzamaker.model.Table;
import com.example.pizzamaker.util.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableRepository {

    public Table read(int id) {
        Connection connection = SQLConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement("SELECT * from `table` WHERE id=?");
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Table table=null;
        try{
            while (resultSet.next()){
                table=mapper(resultSet);
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
        return table;

    }

    public List<Table> readAll() {

        Connection connection = SQLConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement("SELECT * from `table`");
            resultSet = pstmt.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        List<Table> data = mapperList(resultSet);


        try {
            pstmt.close();
            resultSet.close();
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return data;
    }

    public void create(Table table) {
        Connection connection = SQLConnector.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `table` values (0,?,?,?)");
            preparedStatement.setInt(1, table.getNumber());
            preparedStatement.setInt(2, table.getSeats());
            preparedStatement.setBoolean(3, table.isBusy());

            int i = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Table update(int id, Table table) {
        Connection connection = SQLConnector.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `table` SET number = ?, seats = ?, is_busy=? WHERE id = ?");
            preparedStatement.setInt(1, table.getNumber());
            preparedStatement.setInt(2, table.getSeats());
            preparedStatement.setBoolean(3, table.isBusy());
            preparedStatement.setInt(4, table.getId());

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

        return table;
    }

    public void delete(int id) {

        Connection connection = SQLConnector.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `table` where id=?");
            preparedStatement.setInt(1, id);
            int i = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    private static List<Table> mapperList(ResultSet resultSet) {
        List<Table> data = new LinkedList<>();
        try {
            while (resultSet.next()) {
                data.add(mapper(resultSet));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return data;
    }

    private static Table mapper(ResultSet resultSet) {
        Table t = new Table();
        try {
            t.setId(resultSet.getInt("id"));
            t.setNumber(resultSet.getInt("number"));
            t.setSeats(resultSet.getInt("seats"));
            t.setBusy(resultSet.getBoolean("is_busy"));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return t;
    }
}

