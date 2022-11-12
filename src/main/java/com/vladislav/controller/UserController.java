package com.vladislav.controller;

import com.google.gson.Gson;
import com.vladislav.model.User;
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

@WebServlet(urlPatterns = "/api/v1/users/*")
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson gson = LocalDateAdapter.getAdaptedGson();
    private PrintWriter out;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        if (req.getPathInfo() != null) {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            User userById = userService.getUserById(id);
            out.println(gson.toJson(userById));
        } else {
            List<User> allUsers = userService.getAllUsers();
            allUsers.forEach(user -> out.println(gson.toJson(user)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        User newUser = userService.addNewUser(returnUserFromBody(req));
        out.println(gson.toJson(newUser));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        User newUser = returnUserFromBody(req);
        newUser.setId(id);
        User user = userService.updateUser(newUser);
        out.println(user == null ? "No user" : gson.toJson(user));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        if (req.getPathInfo() == null) {
            out.println("Enter id!");
        } else {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            boolean result = userService.deleteUserById(id);
            out.println(result ? "Deleted." : "Not deleted");
        }
    }

    private User returnUserFromBody(HttpServletRequest req) throws IOException {
        String body = req.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        return gson.fromJson(body, User.class);
    }
}
