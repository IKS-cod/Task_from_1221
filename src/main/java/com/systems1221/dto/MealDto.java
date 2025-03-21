package com.systems1221.dto;

import java.time.LocalDate;
import java.util.List;

public class MealDto {
    private Long id;
    private LocalDate currentDate;
    private Long userId;
    private List<DishForMealDto> dishes;

    public MealDto() {
    }

    public MealDto(Long id, LocalDate currentDate, Long userId, List<DishForMealDto> dishes) {
        this.id = id;
        this.currentDate = currentDate;
        this.userId = userId;
        this.dishes = dishes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<DishForMealDto> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishForMealDto> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "MealDto{" +
                "id=" + id +
                ", currentDate=" + currentDate +
                ", userId=" + userId +
                ", dishes=" + dishes +
                '}';
    }
}
