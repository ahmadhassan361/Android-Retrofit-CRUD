package com.rizoma.myproducts.utils;

public class Respo {
    private boolean error;
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Respo() {
    }

    public Respo(boolean error, String message) {
        this.error = error;
        this.message = message;
    }
}
