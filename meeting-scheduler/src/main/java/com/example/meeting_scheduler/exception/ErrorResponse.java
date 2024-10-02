package com.example.meeting_scheduler.exception;

import lombok.Data;
import java.util.Map;
import java.util.HashMap;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private Map<String, String> details;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public void addDetail(String key, String value) {
    this.details.put(key, value);}
}
