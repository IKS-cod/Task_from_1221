package com.systems1221.dto;

public class DishForMealDto {
    private Long dishId;

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public DishForMealDto(Long dishId) {
        this.dishId = dishId;
    }

    public DishForMealDto() {
    }

    @Override
    public String toString() {
        return "DishForMealDto{" +
                "dishId=" + dishId +
                '}';
    }
}
