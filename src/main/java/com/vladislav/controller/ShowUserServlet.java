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
public class ShowUserServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson gson = LocalDateAdapter.getAdaptedGson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id") != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            User user = userService.getUserById(id);
            PrintWriter out = resp.getWriter();
            if(user == null) {
                out.println("No user.");
            } else {
                String convertUserToJson = gson.toJson(user);
                out.println(convertUserToJson);
            }
        } else {
            List<User> allUsers = userService.getAllUsers();
            PrintWriter out = resp.getWriter();
            if(allUsers.isEmpty()) {
                out.println("No users yet.");
            } else {
                for (User u : allUsers) {
                    String convertUserToJson = gson.toJson(u);
                    out.println(convertUserToJson);
                }
            }
        }
    }
}
