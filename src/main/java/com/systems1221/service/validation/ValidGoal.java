package com.systems1221.service.validation;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.UserDto;
import com.systems1221.enums.Goal;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class ValidGoal {
    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public boolean isValidGoal(UserDto userDto) {
        return Arrays.asList(Goal.values()).contains(userDto.getGoal());
    }
}
