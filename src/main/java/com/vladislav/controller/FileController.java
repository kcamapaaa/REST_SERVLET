package com.vladislav.controller;

import com.google.gson.Gson;
import com.vladislav.model.File;
import com.vladislav.service.FileService;
import com.vladislav.util.LocalDateAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/files/*")
public class FileController extends HttpServlet {
    FileService fileService = new FileService();
    private final Gson gson = LocalDateAdapter.getAdaptedGson();
    private PrintWriter out;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        if (req.getPathInfo() != null) {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            File fileById = fileService.getFileById(id);
            out.println(gson.toJson(fileById));
        } else {
            List<File> allFiles = fileService.getAllFiles();
            allFiles.forEach(file -> out.println(gson.toJson(file)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        File newFile = fileService.addNewFile(returnFileFromBody(req));
        out.println(gson.toJson(newFile));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("application/json");
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        File newFile = returnFileFromBody(req);
        newFile.setId(id);
        File file = fileService.updateFile(newFile);
        out.println(file == null ? "No file" : gson.toJson(file));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        if (req.getPathInfo() == null) {
            out.println("Enter id!");
        } else {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            boolean result = fileService.deleteFileById(id);
            out.println(result ? "Deleted." : "Not deleted");
        }
    }

    private File returnFileFromBody(HttpServletRequest req) throws IOException {
        String body = req.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        return gson.fromJson(body, File.class);
    }
}
