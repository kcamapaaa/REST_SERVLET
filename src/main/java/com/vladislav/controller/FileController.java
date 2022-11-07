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

@WebServlet(urlPatterns = "/files")
public class FileController extends HttpServlet {
    FileService fileService = new FileService();
    private final Gson gson = LocalDateAdapter.getAdaptedGson();
    private PrintWriter out;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        if (req.getParameter("id") != null) {
            getFile(req);
        } else {
            getAllFiles();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        if(req.getParameter("id") != null) {
            updateFile(req);
        } else {
            addNewFile(req);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        deleteFile(req, resp);
    }

    private void getFile(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        File file = fileService.getFileById(id);
        if (file == null) {
            out.println("No file.");
        } else {
            String convertFileToJson = gson.toJson(file);
            out.println(convertFileToJson);
        }
    }

    private void getAllFiles() {
        List<File> allFiles = fileService.getAllFiles();
        if (allFiles.isEmpty()) {
            out.println("No files yet.");
        } else {
            allFiles.forEach(x -> out.println(gson.toJson(x)));
        }
    }

    private void updateFile(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        String fileName = req.getParameter("fileName");
        File fileById = fileService.getFileById(id);
        if(fileById == null) {
            out.println("No file with this id.");
        } else {
            fileById.setFileName(fileName);
            File updatedFile = fileService.updateFile(fileById);
            out.println(gson.toJson(updatedFile));
        }
    }

    private void addNewFile(HttpServletRequest req) {
        String fileName = req.getParameter("fileName");
        String filePath = req.getParameter("filePath");
        File file = new File();
        file.setFileName(fileName);
        file.setFilePath(filePath);
        File newFile = fileService.addNewFile(file);
        out.println(gson.toJson(newFile));
    }

    private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        out = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        boolean isDeleted = fileService.deleteFileById(id);
        out.println(isDeleted ? "File was deleted" : "File was not deleted");
    }
}
