package com.sizonenko.bicycleapp.controller;

import com.sizonenko.bicycleapp.command.Command;
import com.sizonenko.bicycleapp.command.CommandMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/InvokerServlet")
public class InvokerServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandMap.getInstance().get(request);
        SessionRequestContent requestContent = new SessionRequestContent();
        requestContent.insertValues(request);
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
                response.getWriter().write(String.valueOf(request.getAttribute("content")));
        }
    }
}
