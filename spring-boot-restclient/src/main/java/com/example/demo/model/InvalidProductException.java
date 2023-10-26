package com.example.demo.model;

public class InvalidProductException extends RuntimeException {
    private final DummyAPIError error;

    public InvalidProductException(DummyAPIError error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return this.error.message();
    }
}
