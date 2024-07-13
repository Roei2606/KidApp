package com.example.kidapp.Models;

public class KidEvent {
    private String eventTitle;
    private MyDate eventDate;
    private String eId;
    private Boolean isApproved;

    public KidEvent() {
        isApproved=null;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public MyDate getEDate() {
        return eventDate;
    }

    public KidEvent setApproved(Boolean approved) {
        isApproved = approved;
        return this;
    }

    @Override
    public String toString() {
        return "KidEvent{" +
                "eventTitle='" + eventTitle + '\'' +
                ", eventDate=" + eventDate +
                ", eId='" + eId + '\'' +
                ", isApproved=" + isApproved +
                '}';
    }
}