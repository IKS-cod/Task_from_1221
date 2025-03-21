package com.systems1221.service;

import com.systems1221.aspect.annotation.CustomExceptionHandling;
import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.DishForMealDto;
import com.systems1221.dto.MealDto;
import com.systems1221.dto.UserDto;
import com.systems1221.exception.MealNotFoundException;
import com.systems1221.exception.UserNotFoundException;
import com.systems1221.model.Dish;
import com.systems1221.model.Meal;
import com.systems1221.model.User;
import com.systems1221.repository.DishRepository;
import com.systems1221.repository.MealRepository;
import com.systems1221.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public MealService(MealRepository mealRepository, UserRepository userRepository, DishRepository dishRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public MealDto createMeal(MealDto mealDto) {
        Meal mealForDb = new Meal();
        mealForDb.setDate(LocalDate.now());
        List<DishForMealDto> listDishForMealDtoForDb = mealDto.getDishes();
        List<Long> listIdDish = new ArrayList<>();
        for (DishForMealDto e : listDishForMealDtoForDb) {
            listIdDish.add(e.getDishId());
        }
        mealForDb.setDishes(dishRepository.findDishesByIds(listIdDish));
        mealForDb.setUser(userRepository.findById(mealDto.getUserId()).orElseThrow(() ->
                new UserNotFoundException("User not found with ID: " + mealDto.getUserId())));
        mealForDb.setId(null);
        Meal mealSaveInDb = mealRepository.save(mealForDb);
        return getMealDtoFromMeal(mealSaveInDb);

    }


    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public MealDto getMeal(long id) {
        Meal mealFromDb = mealRepository.findById(id)
                .orElseThrow(() -> new MealNotFoundException("Meal not found with ID: " + id));
        return getMealDtoFromMeal(mealFromDb);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public void updateMeal(long id, MealDto mealDto) {
        Meal mealFromDb = mealRepository.findById(id)
                .orElseThrow(() -> new MealNotFoundException("Meal not found with ID: " + id));
        List<DishForMealDto> listDishForMealDtoFromDb = mealDto.getDishes();
        List<Long> listIdDish = new ArrayList<>();
        for (DishForMealDto e : listDishForMealDtoFromDb) {
            listIdDish.add(e.getDishId());
        }
        mealFromDb.setDishes(dishRepository.findDishesByIds(listIdDish));
        mealRepository.save(mealFromDb);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public void deleteMeal(long id) {
        if (!mealRepository.existsById(id)) {
            throw new MealNotFoundException("Meal not found with ID: " + id);
        }
        mealRepository.deleteById(id);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public Collection<MealDto> findAllMealsForToday(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        List<Meal> mealsListFromDb = mealRepository.findByUserIdAndDate(userId, LocalDate.now());
        List<MealDto> mealsDtoListFromDb = new ArrayList<>();
        for (Meal e : mealsListFromDb) {
            MealDto mealDto = new MealDto();
            mealDto.setId(e.getId());
            mealDto.setUserId(e.getUser().getId());
            List<Dish> dishListFromDb = e.getDishes();
            List<DishForMealDto> listDishForMealDto = new ArrayList<>();
            for(Dish m: dishListFromDb){
                listDishForMealDto.add(new DishForMealDto(m.getId()));
            }
            mealDto.setDishes(listDishForMealDto);
            mealDto.setCurrentDate(e.getDate());
            mealsDtoListFromDb.add(mealDto);
        }
        return mealsDtoListFromDb;
    }

    private MealDto getMealDtoFromMeal(Meal meal) {
        MealDto mealDtoFromDb = new MealDto();
        mealDtoFromDb.setId(meal.getId());
        mealDtoFromDb.setCurrentDate(meal.getDate());
        List<Dish> listDishFromDb = meal.getDishes();
        List<DishForMealDto> dishForMealDtoList = new ArrayList<>();
        for (Dish e : listDishFromDb) {
            dishForMealDtoList.add(new DishForMealDto(e.getId()));
        }
        mealDtoFromDb.setDishes(dishForMealDtoList);
        mealDtoFromDb.setUserId(meal.getUser().getId());
        return mealDtoFromDb;
    }

}
