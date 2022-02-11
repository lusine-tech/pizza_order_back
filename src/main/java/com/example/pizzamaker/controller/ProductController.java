package com.example.pizzamaker.controller;

import com.example.pizzamaker.model.dto.ProductDto;
import com.example.pizzamaker.service.ProductService;
import com.example.pizzamaker.service.impl.ProductServiceImpl;
import com.example.pizzamaker.util.AccessControlOriginFilter;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class ProductController extends HttpServlet {

    private final ProductService productService = new ProductServiceImpl();
    private final Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        String url = req.getParameter("url");
        Gson gson = new Gson();
        List<ProductDto> data = new LinkedList<>();

        switch (url) {
            case "get-all-by-product-type":
                int productTypeId = Integer.parseInt(req.getParameter("product_type_id"));
                data.addAll(productService.readAllByProductType(productTypeId));
                break;
            case "get-by-id":

                int id = Integer.parseInt(req.getParameter("product_id"));
                ProductDto product = productService.read(id);
                data.add(product);
                break;

            case "get-all":
                data.addAll(productService.readAll());
                break;
        }

        resp.getWriter().println(gson.toJson(data));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessControlOriginFilter.setAccessControlHeaders(resp);
        int id = Integer.parseInt(req.getParameter("id"));
        productService.delete(id);
    }
}

