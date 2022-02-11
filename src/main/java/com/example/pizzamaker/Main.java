package com.example.pizzamaker;

import com.example.pizzamaker.repository.OrderRepository;
import com.example.pizzamaker.repository.ProductRepository;
import com.example.pizzamaker.util.SQLConnector;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
//        Connection connection = SQLConnector.getConnection();
//        try{
//            PreparedStatement preparedStatement = connection.prepareStatement("insert into `table` values (0,1,1,true)");
//            int count = preparedStatement.executeUpdate();
//        }catch(SQLException exception){
//            exception.printStackTrace();
//        }

//        ProductRepository productRepository = new ProductRepository();
//        Gson g = new Gson();
//        System.out.println(g.toJson(productRepository.read(1)));

        OrderRepository orderRepository=new OrderRepository();
        Gson g = new Gson();
        System.out.println(g.toJson(orderRepository.readAll()));
    }
}
