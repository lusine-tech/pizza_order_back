package com.example.pizzamaker.util;

import javax.servlet.http.HttpServletResponse;

public class AccessControlOriginFilter {
    public static  void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Method", "GET, PUT, POST, DELETE");

    }
}
