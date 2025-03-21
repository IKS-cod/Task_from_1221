package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ValidHeight {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidHeight(UserDto userDto) {
        return userDto.getHeight() > 50 && userDto.getHeight() < 250;
    }
}
