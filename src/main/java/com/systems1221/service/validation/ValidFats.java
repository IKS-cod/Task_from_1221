package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.DishDto;
import org.springframework.stereotype.Service;

@Service
public class ValidFats {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidFats(DishDto dishDto) {
        return dishDto.getFats() >= 0;
    }
}
