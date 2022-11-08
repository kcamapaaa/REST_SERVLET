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

@WebServlet(urlPatterns = "/events")
public class EventController extends HttpServlet {
    private final EventService eventService = new EventService();
    private final FileService fileService = new FileService();
    private final UserService userService = new UserService();
    private final Gson gson = LocalDateAdapter.getAdaptedGson();
    private PrintWriter out;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        if (req.getParameter("id") != null) {
            getEvent(req, resp);
        } else {
            getAllEvents(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        addNewEvent(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        updateEvent(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        deleteEvent(req, resp);
    }

    private void getEvent(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        resp.setContentType("application/json");
        Event event = eventService.getEventById(id);
        if (event == null) {
            out.println("No events.");
        } else {
            String convertEventToJson = gson.toJson(event);
            out.println(convertEventToJson);
        }
    }

    private void getAllEvents(HttpServletRequest req, HttpServletResponse resp) {
        List<Event> allEvents = eventService.getAllEvents();
        resp.setContentType("application/json");
        if (allEvents.isEmpty()) {
            out.println("No files yet.");
        } else {
            allEvents.forEach(x -> out.println(gson.toJson(x)));
        }
    }

    private void updateEvent(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        resp.setContentType("application/json");
        String eventName = req.getParameter("eventName");
        Event eventById = eventService.getEventById(id);
        if (eventById == null) {
            out.println("No event with this id.");
        } else {
            eventById.setName(eventName);
            Event updatedEvent = eventService.updateEvent(eventById);
            out.println(gson.toJson(updatedEvent));
        }
    }

    private void deleteEvent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        out = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isDeleted = eventService.deleteEventById(id);
        out.println(isDeleted ? "Event was deleted" : "Event was not deleted");
    }

    private void addNewEvent(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        String eventName = req.getParameter("eventName");
        int fileId = Integer.parseInt(req.getParameter("fileId"));
        int userId = Integer.parseInt(req.getParameter("userId"));

        Event event = new Event();
        event.setName(eventName);
        event.setFile(fileService.getFileById(fileId));
        event.setUser(userService.getUserById(userId));

        Event newEvent = eventService.addNewEvent(event);
        out.println(gson.toJson(newEvent));
    }
}
