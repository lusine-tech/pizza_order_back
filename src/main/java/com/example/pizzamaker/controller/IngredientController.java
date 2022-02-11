package com.example.pizzamaker.controller;

import com.example.pizzamaker.model.Ingredient;
import com.example.pizzamaker.service.IngredientService;
import com.example.pizzamaker.service.impl.IngredientServiceImpl;
import com.example.pizzamaker.util.AccessControlOriginFilter;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class IngredientController extends HttpServlet {

    private final IngredientService ingredientService = new IngredientServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        List<Ingredient> data = new LinkedList<>();
        final String url = req.getParameter("url");

        switch (url) {
            case "get-all":
                data.addAll(ingredientService.readAll());
                break;
            case "get-by-id":
                int id = Integer.parseInt(req.getParameter("id"));
                Ingredient readById = ingredientService.read(id);
                if (readById != null) {
                    data.add(readById);
                }
                break;
            case "get-name":
                String name = req.getParameter("name");
                Ingredient readByName = ingredientService.read(name);
                if (readByName != null) {
                    data.add(readByName);
                }
                break;
            default:
                resp.sendError(404, "hargelis sxalvel es");
                break;
        }
        resp.getWriter().println(gson.toJson(data));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        String name = req.getParameter("name");

        Ingredient ingredient = new Ingredient(0, name);
        ingredientService.create(ingredient);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        Ingredient ingr = mapper(req);

        Ingredient update = ingredientService.update(ingr.getId(),ingr);
        if (update==null){
            resp.sendError(400,"id not found for update object");
            return;
        }
        resp.getWriter().println(gson.toJson(update));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        int id= Integer.parseInt(req.getParameter("id"));
        ingredientService.delete(id);
    }

    private Ingredient mapper(HttpServletRequest req) {
        int id;
        String name;

        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException ex) {
            id = 0;
        }
        try {
            name = req.getParameter("name");
        } catch (Exception ex) {
            name = "";
        }

        Ingredient ingredient = new Ingredient(id, name);
        return ingredient;
    }
}

