package com.sizonenko.bicycleapp.entity;

public class Confirm extends Entity{
    private String login;
    private String code;

    public Confirm(String login, String code) {
        this.login = login;
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
