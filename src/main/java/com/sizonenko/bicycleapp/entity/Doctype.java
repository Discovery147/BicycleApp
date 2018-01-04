package com.sizonenko.bicycleapp.entity;

public class Doctype extends Entity {
    private long doctypeId;
    private String name;

    public Doctype(){

    }

    public long getDoctypeId() {
        return doctypeId;
    }

    public void setDoctypeId(long doctypeId) {
        this.doctypeId = doctypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
