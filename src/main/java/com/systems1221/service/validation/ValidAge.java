package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ValidAge {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidAge(UserDto userDto) {
        return userDto.getAge() > 0 && userDto.getAge() < 110;
    }
}
