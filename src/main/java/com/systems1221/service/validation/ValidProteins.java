package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.DishDto;
import org.springframework.stereotype.Service;

@Service
public class ValidProteins {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidProteins(DishDto dishDto) {
        return dishDto.getProteins() >= 0;
    }
}
