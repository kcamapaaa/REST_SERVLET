package com.vladislav.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vladislav.model.Event;
import com.vladislav.service.EventService;
import com.vladislav.util.LocalDateAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = "/events")
public class EventServlet extends HttpServlet {
    private final EventService eventService = new EventService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();

        Event event = eventService.getEventById(id);

        String convertEventToJson = gson.toJson(event);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.println(convertEventToJson);
        out.flush();
    }
}
