package com.systems1221.exception;

public class IllegalNameForUserDtoException extends RuntimeException{

    public IllegalNameForUserDtoException(String message) {
        super(message);
    }
}