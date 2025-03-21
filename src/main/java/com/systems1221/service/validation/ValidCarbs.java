package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.DishDto;
import org.springframework.stereotype.Service;

@Service
public class ValidCarbs {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidCarbs(DishDto dishDto) {
        return dishDto.getCarbs() >= 0;
    }
}
