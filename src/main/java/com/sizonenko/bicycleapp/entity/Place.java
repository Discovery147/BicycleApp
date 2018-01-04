package com.sizonenko.bicycleapp.entity;

import java.util.Objects;

public class Place extends Entity {
    private long placeId;
    private String name;
    private String address;
    Member member;

    public Place(){
    }

    public Place(long placeId, String name, String address, Member member) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.member = member;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return placeId == place.placeId &&
                Objects.equals(name, place.name) &&
                Objects.equals(address, place.address) &&
                Objects.equals(member, place.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, name, address, member);
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeId=" + placeId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", member=" + member +
                '}';
    }
}
