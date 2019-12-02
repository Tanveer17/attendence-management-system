package com.tanveer.data;

import java.time.LocalDate;

public class Attendence {
    private String firstName;
    private LocalDate date;
    private String attendence;
    private int userId;


    public Attendence(LocalDate date, String attendence) {
        this.date = date;
        this.attendence = attendence;
    }

    public Attendence(String firstName, LocalDate date, String attendence, int userId) {
        this.firstName = firstName;
        this.date = date;
        this.attendence = attendence;
        this.userId = userId;
    }
    public Attendence(String firstName, LocalDate date, String attendence) {
        this.firstName = firstName;
        this.date = date;
        this.attendence = attendence;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAttendence() {
        return attendence;
    }


    public String getFirstName() {
        return firstName;
    }


    public int getUserId() {
        return userId;
    }
}
