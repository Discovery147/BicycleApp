package com.sizonenko.bicycleapp.controller;

import com.sizonenko.bicycleapp.page.PageConstant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ErrorServlet")
public class ErrorServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request,response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorInfo = "";
        switch(code){
            case 404:
                errorInfo = "Page Not Found";
                break;
            case 500:
                errorInfo = "Internal Server Error";
                break;
        }
        request.setAttribute("errorStatus",code);
        request.setAttribute("errorInfo", errorInfo);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PageConstant.PATH_ERROR);
        dispatcher.forward(request, response);
    }
}
