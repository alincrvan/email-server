package com.gbtec.interview.email_server.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException() {
        super("Email Not Found");
    }
}
