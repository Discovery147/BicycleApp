package com.sizonenko.bicycleapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class SessionRequestContent {
    private Map<String,Object> requestArrtibutes;
    private Map<String,String> requestParameters;
    private Map<String,Object> sessionAttributes;
    private Part file;

    void insertValues(HttpServletRequest request){

        requestArrtibutes = new HashMap<>();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            requestArrtibutes.put(attributeName, request.getAttribute(attributeName));
        }

        requestParameters = new HashMap<>();
        Enumeration<String> parametersNames = request.getParameterNames();
        while (parametersNames.hasMoreElements()) {
            String parameterName = parametersNames.nextElement();
            requestParameters.put(parameterName, request.getParameter(parameterName));
        }

        sessionAttributes = new HashMap<>();
        Enumeration<String> sessionAttrNames = request.getSession().getAttributeNames();
        while (sessionAttrNames.hasMoreElements()) {
            String sessionAttrName = sessionAttrNames.nextElement();
            sessionAttributes.put(sessionAttrName, request.getSession().getAttribute(sessionAttrName));
        }
    }

    void extractValues(HttpServletRequest request){
        requestArrtibutes.forEach((k,v) -> request.setAttribute(k,v));
        if(sessionAttributes.containsKey("invalidate")){
            request.getSession().invalidate();
        }
        else{
            sessionAttributes.forEach((k,v) -> request.getSession().setAttribute(k,v));
        }
    }

    public String getRequestParameter(String name){
        return requestParameters.get(name);
    }

    public Object getSessionAttribute(String name){
        return sessionAttributes.get(name);
    }

    public void setRequestAttribute(String name, Object value){
        requestArrtibutes.put(name, value);
    }

    public void setSessionAttribute(String name, Object value){
        sessionAttributes.put(name, value);
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
}
