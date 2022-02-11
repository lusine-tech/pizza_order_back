package com.example.pizzamaker.controller;

import com.example.pizzamaker.model.Order;
import com.example.pizzamaker.service.OrderService;
import com.example.pizzamaker.service.impl.OrderServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OrderController extends HttpServlet{
    private final OrderService orderService = new OrderServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");

        switch (url) {
            case "read-all":
                resp.getWriter().println(gson.toJson(orderService.readAll()));
                break;
            case "read-by-table-id":
                int tableId = Integer.parseInt(req.getParameter("table-id"));
                resp.getWriter().println(gson.toJson(orderService.read(tableId)));
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = mapper(req);
        orderService.create(order);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = mapper(req);
        orderService.update(order);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        orderService.delete(id);
    }


    private Order mapper(HttpServletRequest req) {
        boolean isInProcess = Boolean.parseBoolean(req.getParameter("in-process"));
        int id = 0;
        int tableId = 0;
        int productId = 0;
        int quantity = 0;
        float amount = 0;


        try {
            id = Integer.parseInt(req.getParameter("id"));
            tableId = Integer.parseInt(req.getParameter("table-id"));
            productId = Integer.parseInt(req.getParameter("product-id"));
            quantity = Integer.parseInt(req.getParameter("quantity"));
            amount = Float.parseFloat(req.getParameter("amount"));
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }

        return new Order(id, tableId, isInProcess, productId, quantity, amount);
    }
}
