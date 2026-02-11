package com.divij.expense_tracker.exception;

import java.util.List;

public class ApiError {
    private String message;
    private List<String> details;

    public ApiError(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() { return message; }
    public List<String> getDetails() { return details; }
}
