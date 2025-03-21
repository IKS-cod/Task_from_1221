package com.systems1221.controller;

import com.systems1221.dto.DishDto;
import com.systems1221.dto.UserDto;
import com.systems1221.model.Dish;
import com.systems1221.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/dishes")
@Tag(name = "Блюда")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }


    @PostMapping
    @Operation(summary = "Добавление нового блюда")
    public DishDto createDish(@RequestBody DishDto dishDto) {
        return dishService.createDish(dishDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о блюде по ID")
    public DishDto getDish(@PathVariable long id) {
        return dishService.getDish(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных блюда по ID")
    public void updateDish(@PathVariable long id, @RequestBody DishDto dishDto) {
        dishService.updateDish(id, dishDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление блюда по ID")
    public void deleteDish(@PathVariable long id) {
        dishService.deleteDish(id);
    }

    @GetMapping
    @Operation(summary = "Получение списка всех блюд")
    public Collection<DishDto> getAllDishes() {
        return dishService.findAllDishes();
    }

}
