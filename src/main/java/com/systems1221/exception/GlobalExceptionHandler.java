package com.systems1221.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class,
            MealNotFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IllegalAgeException.class,
            IllegalEmailException.class,
            IllegalGenderException.class,
            IllegalGoalException.class,
            IllegalHeightException.class,
            IllegalNameForUserDtoException.class,
            IllegalWeightException.class,
            IllegalNameForDishDtoException.class,
            IllegalCaloriesException.class,
            IllegalCarbsException.class,
            IllegalFatsException.class,
            IllegalProteinsException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
