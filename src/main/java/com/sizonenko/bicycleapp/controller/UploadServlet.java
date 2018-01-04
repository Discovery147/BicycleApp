package com.sizonenko.bicycleapp.controller;

import com.sizonenko.bicycleapp.command.Command;
import com.sizonenko.bicycleapp.command.CommandMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/UploadServlet")
@MultipartConfig(maxFileSize=1024*1024*4) // 4 MB
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandMap.getInstance().get(request);
        SessionRequestContent requestContent = new SessionRequestContent();
        requestContent.insertValues(request);
        Part file = request.getPart("fileName");
        requestContent.setFile(file);
        Router router = command.execute(requestContent);
        requestContent.extractValues(request);
        switch(router.getRoute()){
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(router.getPagePath());
                break;
            case NOTHING:
                response.getWriter().write(String.valueOf(request.getAttribute("status")));
        }
    }
}
