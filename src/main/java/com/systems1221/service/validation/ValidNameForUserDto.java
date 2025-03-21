package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ValidNameForUserDto {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidNameForUserDto(UserDto userDto) {
        return userDto.getName() != null && !userDto.getName().isEmpty();
    }
}
