package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.DishDto;
import com.systems1221.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ValidNameForDishDto {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidNameForDishDto(DishDto dishDto) {
        return dishDto.getName() != null && !dishDto.getName().isEmpty();
    }
}
