package com.systems1221.exception;

public class IllegalNameForDishDtoException extends RuntimeException {
    public IllegalNameForDishDtoException(String message) {
        super(message);
    }
}
