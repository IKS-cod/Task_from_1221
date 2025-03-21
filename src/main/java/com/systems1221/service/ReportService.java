package com.systems1221.service;

import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.*;
import com.systems1221.exception.UserNotFoundException;
import com.systems1221.model.Dish;
import com.systems1221.model.Meal;
import com.systems1221.model.User;
import com.systems1221.repository.MealRepository;
import com.systems1221.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public ReportService(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public InfoSumCaloriesAndCountMealInDayDto getInfoSumCaloriesAndCountMealInDay(long userId) {
        InfoSumCaloriesAndCountMealInDayDto infoSumCaloriesAndCountMealInDayDto =
                new InfoSumCaloriesAndCountMealInDayDto();
        infoSumCaloriesAndCountMealInDayDto.setCalories(mealRepository.sumCalories(userId, LocalDate.now()));
        infoSumCaloriesAndCountMealInDayDto.setCountMeal(mealRepository.countMeals(userId, LocalDate.now()));
        return infoSumCaloriesAndCountMealInDayDto;
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public ListMealOfAllTimeDto getListMealOfAllTime(long userId) {
        List<Meal> mealsByUserIdOfAllTime = mealRepository.getMealsByUserId(userId);
        List<MealDto> mealDtoListFromDb = new ArrayList<>();
        for (Meal e : mealsByUserIdOfAllTime) {
            MealDto mealDto = new MealDto();
            mealDto.setId(e.getId());
            mealDto.setUserId(e.getUser().getId());
            mealDto.setCurrentDate(e.getDate());
            List<Dish> dishList = e.getDishes();
            List<DishForMealDto> dishForMealDtoList = new ArrayList<>();
            for (Dish m : dishList) {
                dishForMealDtoList.add(new DishForMealDto(m.getId()));
            }
            mealDto.setDishes(dishForMealDtoList);
            mealDtoListFromDb.add(mealDto);
        }

        ListMealOfAllTimeDto listMealOfAllTimeDto = new ListMealOfAllTimeDto();
        listMealOfAllTimeDto.setListMealDto(mealDtoListFromDb);
        return listMealOfAllTimeDto;
    }

    public MessageDto getCheckingUserFitNormCalorieInDay(long userId) {
        User userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        double calculatedCountCalories = userFromDb.getDailyCalorieNorm();
        double actuallyCountCalories = mealRepository.sumCalories(userId, LocalDate.now());
        String result = "";
        if(actuallyCountCalories>=calculatedCountCalories*0.9&&actuallyCountCalories<=calculatedCountCalories*1.1){
            result = String.format("Количество калорий в пределах нормы! %.1f", actuallyCountCalories);
        }
        if(actuallyCountCalories<calculatedCountCalories*0.9){
            double needToAdd = calculatedCountCalories * 0.9 - actuallyCountCalories;
            result = String.format("Количество калорий ниже нормы! %.1f нужно добрать %.1f",
                    actuallyCountCalories, needToAdd);
        }
        if(actuallyCountCalories>calculatedCountCalories*1.1){
            result = String.format("Количество калорий выше нормы! %.1f", actuallyCountCalories);
        }
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(result);
        return messageDto;
    }
}
