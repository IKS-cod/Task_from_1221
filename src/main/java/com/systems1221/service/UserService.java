package com.systems1221.service;

import com.systems1221.aspect.annotation.CustomExceptionHandling;
import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.UserDto;
import com.systems1221.enums.Gender;
import com.systems1221.exception.*;
import com.systems1221.mapper.Mappers;
import com.systems1221.model.User;
import com.systems1221.repository.UserRepository;
import com.systems1221.service.validation.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service

public class UserService {

    private final UserRepository userRepository;
    private final Mappers mappers;
    private final ValidEmail validEmail;
    private final ValidAge validAge;
    private final ValidWeight validWeight;
    private final ValidGender validGender;
    private final ValidHeight validHeight;
    private final ValidNameForUserDto validNameForUserDto;
    private final ValidGoal validGoal;

    public UserService(UserRepository userRepository, Mappers mappers, ValidEmail validEmail, ValidAge validAge, ValidWeight validWeight, ValidGender validGender, ValidHeight validHeight, ValidNameForUserDto validNameForUserDto, ValidGoal validGoal) {
        this.userRepository = userRepository;
        this.mappers = mappers;
        this.validEmail = validEmail;
        this.validAge = validAge;
        this.validWeight = validWeight;
        this.validGender = validGender;
        this.validHeight = validHeight;
        this.validNameForUserDto = validNameForUserDto;
        this.validGoal = validGoal;
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public UserDto createUser(UserDto userDto) {
        validateUserDto(userDto);
        User userForDb = mappers.toUser(userDto);
        userForDb.setId(null);
        userForDb.setDailyCalorieNorm(calculateDailyCalorieNorm(userDto));
        User userSaveInDb = userRepository.save(userForDb);
        return mappers.toUserDto(userSaveInDb);

    }


    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public UserDto getUser(long id) {
        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return mappers.toUserDto(userFromDb);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public void updateUser(long id, UserDto userDto) {
        validateUserDto(userDto);
        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        userFromDb.setDailyCalorieNorm(calculateDailyCalorieNorm(userDto));
        userFromDb.setName(userDto.getName());
        userFromDb.setEmail(userDto.getEmail());
        userFromDb.setAge(userDto.getAge());
        userFromDb.setWeight(userDto.getWeight());
        userFromDb.setHeight(userDto.getHeight());
        userFromDb.setGoal(userDto.getGoal());
        userFromDb.setGender(userDto.getGender());
        userRepository.save(userFromDb);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public Collection<UserDto> findAllUsers() {
        List<User> usersListFromDb = userRepository.findAll();
        List<UserDto> usersDtoList = new ArrayList<>();
        for (User e : usersListFromDb) {
            usersDtoList.add(mappers.toUserDto(e));
        }
        return usersDtoList;
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    private double calculateDailyCalorieNorm(UserDto userDto) {
        double bmr = userDto.getGender() == Gender.MALE ?
                88.36 + (13.4 * userDto.getWeight()) + (4.8 * userDto.getHeight()) - (5.7 * userDto.getAge()) :
                447.6 + (9.2 * userDto.getWeight()) + (3.1 * userDto.getHeight()) - (4.3 * userDto.getAge());

        double calorieNorm = switch (userDto.getGoal()) {
            case WEIGHT_LOSS -> bmr * 0.85;
            case MUSCLE_GAIN -> bmr * 1.15;
            default -> bmr;
        };

        return BigDecimal.valueOf(calorieNorm)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    private void validateUserDto(UserDto userDto) {
        if (!validAge.isValidAge(userDto)) {
            throw new IllegalAgeException("Invalid Age");
        }
        if (!validEmail.isValidEmail(userDto)) {
            throw new IllegalEmailException("Invalid Email");
        }
        if (!validGender.isValidGender(userDto)) {
            throw new IllegalGenderException("Invalid Gender");
        }
        if (!validGoal.isValidGoal(userDto)) {
            throw new IllegalGoalException("Invalid Goal");
        }
        if (!validHeight.isValidHeight(userDto)) {
            throw new IllegalHeightException("Invalid Height");
        }
        if (!validNameForUserDto.isValidNameForUserDto(userDto)) {
            throw new IllegalNameForUserDtoException("Invalid NameUserDto");
        }
        if (!validWeight.isValidWeight(userDto)) {
            throw new IllegalWeightException("Invalid Weight");
        }
    }

}
