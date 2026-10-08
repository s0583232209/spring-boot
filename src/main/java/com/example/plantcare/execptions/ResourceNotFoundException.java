package com.example.plantcare.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String m) { super(m); }
}