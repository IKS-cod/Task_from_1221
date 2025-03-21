package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ValidWeight {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidWeight(UserDto userDto) {
        return userDto.getWeight() > 0 && userDto.getWeight() < 300;
    }
}
