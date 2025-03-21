package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.DishDto;
import org.springframework.stereotype.Service;

@Service
public class ValidCalories {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidCalories(DishDto dishDto) {
        return dishDto.getCalories() > 0;
    }
}
