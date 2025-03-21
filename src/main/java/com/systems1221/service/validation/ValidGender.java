package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.UserDto;
import com.systems1221.enums.Gender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class ValidGender {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidGender(UserDto userDto) {
        return Arrays.asList(Gender.values()).contains(userDto.getGender());
    }
}
