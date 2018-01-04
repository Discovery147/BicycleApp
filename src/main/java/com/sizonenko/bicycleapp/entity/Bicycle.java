package com.sizonenko.bicycleapp.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Bicycle extends Entity {
    private long bicycleId;
    private String serialNumber;
    private String maker;
    private String model;
    private String color;
    private String size;
    private String description;
    private BigDecimal price;
    private Place place;

    public Bicycle(){

    }

    public Bicycle(long bicycleId, String serialNumber, String maker, String model, String color, String size, String description, BigDecimal price, Place place) {
        this.bicycleId = bicycleId;
        this.serialNumber = serialNumber;
        this.maker = maker;
        this.model = model;
        this.color = color;
        this.size = size;
        this.description = description;
        this.price = price;
        this.place = place;
    }

    public long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(long bicycleId) {
        this.bicycleId = bicycleId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bicycle bicycle = (Bicycle) o;
        return bicycleId == bicycle.bicycleId &&
                Objects.equals(serialNumber, bicycle.serialNumber) &&
                Objects.equals(price, bicycle.price) &&
                Objects.equals(maker, bicycle.maker) &&
                Objects.equals(model, bicycle.model) &&
                Objects.equals(color, bicycle.color) &&
                Objects.equals(size, bicycle.size) &&
                Objects.equals(description, bicycle.description) &&
                Objects.equals(place, bicycle.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bicycleId, serialNumber, maker, model, color, size, description, price, place);
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "bicycleId=" + bicycleId +
                ", serialNumber=" + serialNumber +
                ", maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", place=" + place +
                '}';
    }
}

