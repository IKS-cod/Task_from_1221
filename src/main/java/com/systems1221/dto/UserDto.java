package com.systems1221.dto;

import com.systems1221.enums.Gender;
import com.systems1221.enums.Goal;

public class UserDto {

    private Long id;

    private String name;

    private String email;

    private int age;

    private double weight;

    private double height;

    private Goal goal;

    private Gender gender;

    private double dailyCalorieNorm;

    public UserDto() {
    }

    public UserDto(Long id,
                   String name,
                   String email,
                   int age,
                   double weight,
                   double height,
                   Goal goal,
                   Gender gender,
                   double dailyCalorieNorm) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.gender = gender;
        this.dailyCalorieNorm = dailyCalorieNorm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getDailyCalorieNorm() {
        return dailyCalorieNorm;
    }

    public void setDailyCalorieNorm(double dailyCalorieNorm) {
        this.dailyCalorieNorm = dailyCalorieNorm;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", goal=" + goal +
                ", gender=" + gender +
                ", dailyCalorieNorm=" + dailyCalorieNorm +
                '}';
    }
}
