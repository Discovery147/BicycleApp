package com.sizonenko.bicycleapp.controller;

public class Router {
    public enum RouterType{
        FORWARD, REDIRECT, NOTHING
    }
    private String pagePath;
    private RouterType route = RouterType.FORWARD;

    public String getPagePath(){
        return pagePath;
    }

    public void setPagePath(String pagePath){
        this.pagePath = pagePath;
    }

    public RouterType getRoute(){
        return route;
    }

    public void setRoute(RouterType route){
        this.route = (route != null) ? route : RouterType.FORWARD;
    }
}
