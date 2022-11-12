package com.mahsin.calendar.calendar;
import org.threeten.bp.LocalDate;

public class Event {
    String eventID;
    String eventColor;


    private LocalDate date;
    private String events;
    int month;
    public Event(LocalDate date, String events,String eventColor) {
        this.date = date;
        this.events = events;
        this.eventColor = eventColor;
    }

    public Event(LocalDate date, String eventID, String events, String eventColor, int month) {
        this.date = date;
        this.eventID = eventID;
        this.events = events;
        this.eventColor = eventColor;
        this.month = month;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEvents() {
        return events;
    }
    public String getEventColor() {
        return eventColor;
    }

    public int getMonth() {
        return month;
    }
}

//public class Event {
//    private LocalDate date;
//    private String events;
//
//    public Event(LocalDate date, String events) {
//        this.date = date;
//        this.events = events;
//
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public String getEvents() {
//        return events;
//    }
//}