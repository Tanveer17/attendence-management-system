package com.tanveer.data;

import java.time.LocalDate;

public class LeaveRequest {
    private String message;
    private LocalDate from;
    private LocalDate to;
    private int userId;

    public LeaveRequest(String message, LocalDate from, LocalDate to, int userId) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public int getUserId() {
        return userId;
    }
}
