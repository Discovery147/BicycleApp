package com.sizonenko.bicycleapp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Reservation extends Entity {
    private long reservationId;
    private Member member;
    private Bicycle bicycle;
    private Timestamp startTime;
    private Timestamp endTime;
    private BigDecimal amount;

    public Reservation(){

    }

    public Reservation(long reservationId, Member member, Bicycle bicycle, Timestamp startTime, Timestamp endTime, BigDecimal amount) {
        this.reservationId = reservationId;
        this.member = member;
        this.bicycle = bicycle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservationId == that.reservationId &&
                amount == that.amount &&
                Objects.equals(member, that.member) &&
                Objects.equals(bicycle, that.bicycle) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, member, bicycle, startTime, endTime, amount);
    }

    @Override
    public String toString() {
        return new com.google.gson.Gson().toJson(this);
    }
}
