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

@WebServlet(urlPatterns = "/users")
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson gson = LocalDateAdapter.getAdaptedGson();
    private PrintWriter out;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        if(req.getParameter("id") != null) {
            getUser(req);
        } else {
            getAllUsers();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        if(req.getParameter("id") != null) {
            updateUser(req);
        } else {
            addNewUser(req);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        deleteUser(req, resp);
    }

    private void getUser(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = userService.getUserById(id);
        if(user == null) {
            out.println("No user with this id.");
        } else {
            String convertUserToJson = gson.toJson(user);
            out.println(convertUserToJson);
        }
    }

    private void getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        if(allUsers.isEmpty()) {
            out.println("No users yet.");
        } else {
            allUsers.forEach(x -> out.println(gson.toJson(x)));
        }
    }

    private void updateUser(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        User userById = userService.getUserById(id);
        if(userById == null) {
            out.println("No user with this id.");
        } else {
            userById.setFirstName(firstName);
            userById.setLastName(lastName);
            User updatedUser = userService.updateUser(userById);
            out.println(gson.toJson(updatedUser));
        }
    }

    private void addNewUser(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        User newUser = userService.addNewUser(user);
        out.println(gson.toJson(newUser));
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        out = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isDeleted = userService.deleteUserById(id);
        out.println(isDeleted ? "User was deleted" : "User was not deleted");
    }
}
