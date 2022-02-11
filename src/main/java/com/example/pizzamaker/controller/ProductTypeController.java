package com.example.pizzamaker.controller;

import com.example.pizzamaker.model.ProductType;
import com.example.pizzamaker.service.ProductTypeService;
import com.example.pizzamaker.service.impl.ProductTypeServiceImpl;
import com.example.pizzamaker.util.AccessControlOriginFilter;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ProductTypeController extends HttpServlet {
    private final ProductTypeService productTypeService = new ProductTypeServiceImpl();
    private List<ProductType> list = new LinkedList<>();
    private static Random random = new Random();
    private Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        List<ProductType> data = new LinkedList<>();
        final String url = req.getParameter("url");

        switch (url) {
            case "get-all":
                data = productTypeService.readAll();
                break;
            case "get-by-id":
                int id = Integer.parseInt(req.getParameter("id"));
                data.add(productTypeService.read(id));
                break;
            case "get-by-name":
                String name = req.getParameter("name");
                data.add(productTypeService.read(name));
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
        resp.getWriter().println("this is POST method");

        int id = list.get(list.size() - 1).getId() + 1;
        String name = req.getParameter("name");

           }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        System.out.println("desk");
        resp.getWriter().println("this is PUT method");

        ProductType pdtype = null;

        int id = Integer.parseInt(req.getParameter("id"));

        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getId() == id){
                pdtype = list.get(i);
                break;
            }
        }

        if(pdtype == null){
            resp.sendError(400, "there is no table with mentioned id");
            return;
        }

        String name = req.getParameter("name");
        pdtype.setName(name);

        resp.getWriter().println(gson.toJson(list));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        resp.getWriter().println("this is a delete method");

        int delId = Integer.parseInt(req.getParameter("idToDelete"));

        List<ProductType> collect = list.stream().filter(item -> item.getId() == delId).collect(Collectors.toList());

        list.removeAll(collect);

        resp.getWriter().println(gson.toJson(list));
    }

}
