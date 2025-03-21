package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ValidEmail {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidEmail(UserDto userDto) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return userDto.getEmail() != null && userDto.getEmail().matches(emailRegex);
    }
}
