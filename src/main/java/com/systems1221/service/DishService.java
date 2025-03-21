package com.systems1221.service;

import com.systems1221.aspect.annotation.CustomExceptionHandling;
import com.systems1221.aspect.annotation.CustomLoggingFinishedMethod;
import com.systems1221.aspect.annotation.CustomLoggingStartMethod;
import com.systems1221.dto.DishDto;
import com.systems1221.exception.*;
import com.systems1221.mapper.Mappers;
import com.systems1221.model.Dish;
import com.systems1221.repository.DishRepository;
import com.systems1221.service.validation.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final Mappers mappers;
    private final ValidNameForDishDto validNameForDishDto;
    private final ValidCalories validCalories;
    private final ValidCarbs validCarbs;
    private final ValidFats validFats;
    private final ValidProteins validProteins;

    public DishService(DishRepository dishRepository,
                       Mappers mappers,
                       ValidNameForDishDto validNameForDishDto, ValidCalories validCalories, ValidCarbs validCarbs, ValidFats validFats, ValidProteins validProteins
    ) {
        this.dishRepository = dishRepository;
        this.mappers = mappers;
        this.validNameForDishDto = validNameForDishDto;
        this.validCalories = validCalories;
        this.validCarbs = validCarbs;
        this.validFats = validFats;
        this.validProteins = validProteins;
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public DishDto createDish(DishDto dishDto) {
        validateDishDto(dishDto);
        Dish dishForDb = mappers.toDish(dishDto);
        dishForDb.setId(null);
        Dish dishSaveInDb = dishRepository.save(dishForDb);
        return mappers.toDishDto(dishSaveInDb);

    }


    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public DishDto getDish(long id) {
        Dish dishFromDb = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with ID: " + id));
        return mappers.toDishDto(dishFromDb);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public void updateDish(long id, DishDto dishDto) {
        validateDishDto(dishDto);
        Dish dishFromDb = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with ID: " + id));
        dishFromDb.setName(dishDto.getName());
        dishFromDb.setCalories(dishDto.getCalories());
        dishFromDb.setProteins(dishDto.getProteins());
        dishFromDb.setFats(dishDto.getFats());
        dishFromDb.setCarbs(dishDto.getCarbs());
        dishRepository.save(dishFromDb);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    public void deleteDish(long id) {
        if (!dishRepository.existsById(id)) {
            throw new DishNotFoundException("Dish not found with ID: " + id);
        }
        dishRepository.deleteById(id);
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    public Collection<DishDto> findAllDishes() {
        List<Dish> dishesListFromDb = dishRepository.findAll();
        List<DishDto> dishesDtoList = new ArrayList<>();
        for (Dish e : dishesListFromDb) {
            dishesDtoList.add(mappers.toDishDto(e));
        }
        return dishesDtoList;
    }

    @CustomLoggingStartMethod
    @CustomLoggingFinishedMethod
    @CustomExceptionHandling
    private void validateDishDto(DishDto dishDto) {
        if (!validNameForDishDto.isValidNameForDishDto(dishDto)) {
            throw new IllegalNameForDishDtoException("Invalid NameForDishDto");
        }
        if (!validCalories.isValidCalories(dishDto)) {
            throw new IllegalCaloriesException("Invalid Calories");
        }
        if (!validCarbs.isValidCarbs(dishDto)) {
            throw new IllegalCarbsException("Invalid Carbs");
        }
        if (!validFats.isValidFats(dishDto)) {
            throw new IllegalFatsException("Invalid Fats");
        }
        if (!validProteins.isValidProteins(dishDto)) {
            throw new IllegalProteinsException("Invalid Proteins");
        }

    }

}
