package com.vladislav.controller;

import com.google.gson.Gson;
import com.vladislav.service.FileService;
import com.vladislav.util.LocalDateAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(urlPatterns = "/files")
public class ShowFileServlet extends HttpServlet {
    FileService fileService = new FileService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        Gson gson = LocalDateAdapter.getAdaptedGson();

        String convertUserToJson = gson.toJson(fileService.getFileById(id));

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.println(convertUserToJson);
        out.flush();
    }
}
