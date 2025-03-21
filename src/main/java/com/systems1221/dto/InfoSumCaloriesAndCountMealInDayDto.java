package com.systems1221.dto;

public class InfoSumCaloriesAndCountMealInDayDto {
    double calories;

    int countMeal;

    public InfoSumCaloriesAndCountMealInDayDto() {
    }

    public InfoSumCaloriesAndCountMealInDayDto(double calories, int countMeal) {
        this.calories = calories;
        this.countMeal = countMeal;
    }

    public int getCountMeal() {
        return countMeal;
    }

    public void setCountMeal(int countMeal) {
        this.countMeal = countMeal;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "InfoSumCaloriesAndCountMealInDayDto{" +
                "calories=" + calories +
                ", countMeal=" + countMeal +
                '}';
    }
}
