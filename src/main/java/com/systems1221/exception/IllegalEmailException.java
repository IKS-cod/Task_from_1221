package com.systems1221.exception;

public class IllegalEmailException extends RuntimeException{

    public IllegalEmailException(String message) {
        super(message);
    }
}
