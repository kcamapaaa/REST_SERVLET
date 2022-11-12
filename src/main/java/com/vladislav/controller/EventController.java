package com.vladislav.controller;

import com.google.gson.Gson;
import com.vladislav.model.Event;
import com.vladislav.service.EventService;
import com.vladislav.service.FileService;
import com.vladislav.service.UserService;
import com.vladislav.util.LocalDateAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/events/*")
public class EventController extends HttpServlet {
    private final EventService eventService = new EventService();
    private final FileService fileService = new FileService();
    private final UserService userService = new UserService();
    private final Gson gson = LocalDateAdapter.getAdaptedGson();
    private PrintWriter out;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        if (req.getPathInfo() != null) {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            Event eventById = eventService.getEventById(id);
            out.println(gson.toJson(eventById));
        } else {
            List<Event> allEvents = eventService.getAllEvents();
            allEvents.forEach(event -> out.println(gson.toJson(event)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        Event event = returnEventFromBody(req);
        int fileId = Integer.parseInt(req.getParameter("file_id"));
        int userId = Integer.parseInt(req.getParameter("user_id"));
        event.setUser(userService.getUserById(userId));
        event.setFile(fileService.getFileById(fileId));
        Event newEvent = eventService.addNewEvent(event);
        out.println(gson.toJson(newEvent));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        Event newEvent = returnEventFromBody(req);
        newEvent.setId(id);
        Event event = eventService.updateEvent(newEvent);
        out.println(event == null ? "No file" : gson.toJson(event));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        if (req.getPathInfo() == null) {
            out.println("Enter id!");
        } else {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            boolean result = eventService.deleteEventById(id);
            out.println(result ? "Deleted." : "Not deleted");
        }
    }

    private Event returnEventFromBody(HttpServletRequest req) throws IOException {
        String body = req.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        return gson.fromJson(body, Event.class);
    }
}
