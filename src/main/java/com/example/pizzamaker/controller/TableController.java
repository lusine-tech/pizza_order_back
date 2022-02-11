package com.example.pizzamaker.controller;

import com.example.pizzamaker.model.Table;
import com.example.pizzamaker.service.TableService;
import com.example.pizzamaker.service.impl.TableServiceImpl;
import com.example.pizzamaker.util.AccessControlOriginFilter;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class TableController extends HttpServlet {

    private final TableService tableService = new TableServiceImpl();
    private final Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        List<Table> data = new LinkedList<>();
        final String url = req.getParameter("url");

        switch (url) {
            case "get-all":
                data = tableService.readAll();
                break;
            case "get-by-id":
                int id = Integer.parseInt(req.getParameter("id"));
                Table read = tableService.read(id);
                if (read != null) {
                    data.add(read);
                }
                break;
            case "get-by-seat":
                int seats = Integer.parseInt(req.getParameter("seats"));
                data = tableService.readBySeatCount(seats);
                break;
            case "get-by-busy":
                boolean isBusy = Boolean.parseBoolean(req.getParameter("is-busy"));
                data = tableService.readByBusy(isBusy);
                break;
            default:
                resp.sendError(404, "provided URL not found for analyse");
                break;
        }

        resp.getWriter().println(gson.toJson(data));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        Table table = mapper(req);
        tableService.create(table);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        Table table = mapper(req);
        int id = table.getId();
        resp.
                getWriter().
                println(gson.toJson(tableService.update(id, table)));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        int id = Integer.parseInt(req.getParameter("id"));
        tableService.delete(id);

    }


    private Table mapper(HttpServletRequest req) {
        int id;
        int number;
        int seats;
        boolean isBusy;

        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException ex) {
            id = 0;
        }
        try {
            number = Integer.parseInt(req.getParameter("number"));
        } catch (NumberFormatException ex) {
            number = 0;
        }
        try {
            seats = Integer.parseInt(req.getParameter("seats"));
        } catch (NumberFormatException ex) {
            seats = 0;
        }
        isBusy = Boolean.parseBoolean(req.getParameter("is-busy"));

        Table table = new Table(id, number, seats, isBusy);
        return table;
    }
}

