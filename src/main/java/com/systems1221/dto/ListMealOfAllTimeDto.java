package com.systems1221.dto;

import java.util.List;

public class ListMealOfAllTimeDto {

    List<MealDto> listMealDto;

    public ListMealOfAllTimeDto() {
    }

    public ListMealOfAllTimeDto(List<MealDto> listMealDto) {
        this.listMealDto = listMealDto;
    }

    public List<MealDto> getListMealDto() {
        return listMealDto;
    }

    public void setListMealDto(List<MealDto> listMealDto) {
        this.listMealDto = listMealDto;
    }

    @Override
    public String toString() {
        return "ListMealOfAllTimeDto{" +
                "listMealDto=" + listMealDto +
                '}';
    }
}
