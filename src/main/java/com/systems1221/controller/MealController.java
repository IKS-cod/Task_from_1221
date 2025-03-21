package com.systems1221.controller;

import com.systems1221.dto.MealDto;
import com.systems1221.dto.UserDto;
import com.systems1221.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meals")
@Tag(name = "Приемы пищи")
public class MealController {

    private MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    @Operation(summary = "Добавление нового приема пищи")
    public MealDto createMeal(@RequestBody MealDto mealDto) {
        return mealService.createMeal(mealDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о приеме пищи по ID")
    public MealDto getMeal(@PathVariable long id) {
        return mealService.getMeal(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных приема пищи по ID")
    public void updateMeal(@PathVariable long id, @RequestBody MealDto mealDto) {
        mealService.updateMeal(id, mealDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление приема пищи по ID")
    public void deleteMeal(@PathVariable long id) {
        mealService.deleteMeal(id);
    }

    @GetMapping("/today/{userId}")
    @Operation(summary = "Получение списка всех приемов пищи по UserId за сегодня")
    public Collection<MealDto> getAllMealsForToday(@PathVariable long userId) {
        return mealService.findAllMealsForToday(userId);
    }
}
